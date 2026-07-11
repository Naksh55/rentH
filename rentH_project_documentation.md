# rentH — Property Rental Android Application: Detailed Documentation

## 1. Project Overview

**rentH** is a native Android application built in **Java** that serves as a property rental marketplace. It connects **property owners** who want to rent out their properties with **users (tenants)** looking for rental accommodations. The app provides end-to-end functionality: registration, property listing, browsing, booking, slot-based scheduling, payment processing, and owner notifications.

**Package:** `com.naksh.renth` | **Min SDK:** 24 | **Target SDK:** 34

---

## 2. Technology Stack

| Technology | Purpose |
|---|---|
| **Java** | Primary programming language |
| **Android SDK (API 34)** | Application framework |
| **Firebase Authentication** | User signup/login with email & password |
| **Firebase Realtime Database** | Storing users, properties, bookings, notifications |
| **Firebase Cloud Storage** | Uploading & serving property images |
| **Firebase Cloud Messaging (FCM)** | Push notification delivery to owners |
| **Google Maps SDK** | Displaying property locations on maps |
| **PhonePe Intent SDK** | UPI-based payment gateway integration |
| **Retrofit + Gson** | HTTP networking for payment server APIs |
| **Picasso** | Image loading and caching |
| **FirebaseUI** | Pre-built adapters for RecyclerView with Firebase |
| **DialogPlus** | Custom popup dialogs for property forms |
| **View Binding** | Type-safe view references (replaces `findViewById`) |
| **NumberPicker** | UI widget for slot selection |

---

## 3. System Architecture

### 3.1 Architecture Pattern
The app uses **Activity + Fragment** pattern with Firebase as a serverless backend (BaaS).

### 3.2 Firebase Realtime Database Schema

```
Firebase Realtime Database
├── Users/
│   └── {FirebaseUID}/
│       ├── email: "user@email.com"
│       ├── password: "hashed"
│       └── category: "User" | "Owner"
├── OwnerPersonalDetailsModel/
│   └── {ownerId}/
│       ├── oname, oage, ogender, ophoneno, oemail, id
├── UserPersonalDetailsModel/
│   └── {userId}/
│       ├── name, age, gender, phoneno, uemail, id
├── PropertyDetailsModel/
│   └── {propertyId}/
│       ├── nameofproperty, priceofproperty, typeofproperty
│       ├── address, state, city, propertydiscription
│       ├── ownerId, oName, propertyId
│       ├── imageUrl, fromdate, todate
├── UserTripDetailsModel/
│   └── {propertyId}/
│       ├── date, slots, guests
└── NotificationMessage/
    └── {notificationId}/
        ├── notificationMessage, userId, ownerId
        ├── propertyId, totalprice, notificationId
```

---

## 4. Application Flow (Screen-by-Screen)

### 4.1 Splash Screen (`SplashActivity`)
- Displays the rentH logo with a **slide-in-left animation**
- Checks `FirebaseAuth.getCurrentUser()` for existing session
- After 3.5 seconds, navigates to `LoginScreen`
- Uses `overridePendingTransition` for fade transitions

### 4.2 Sign Up (`SignUpScreen`)
- Collects: email, password, confirm password
- **Role selection** via RadioButtons: "User" or "Owner"
- **Validation:** empty fields check, password match check
- Calls `FirebaseAuth.createUserWithEmailAndPassword()`
- Creates a `Users` object with email, password, and category
- Stores under `Users/{FirebaseUID}` in Realtime Database
- Routes to `UserPersonalDetails` (User) or `OwnerPersonalDetails` (Owner)
- **Password toggle** visibility feature using `ImageView` click

### 4.3 Login (`LoginScreen`)
- Email + password fields with password visibility toggle
- **Role selection** (User/Owner) via RadioGroup
- **Forgot Password** sends reset email via `FirebaseAuth.sendPasswordResetEmail()`
- On successful `signInWithEmailAndPassword()`:
  - If **User**: queries `UserPersonalDetailsModel` by email → verifies role in `Users` node → navigates to `PropertyRecyclerActivityForUser`
  - If **Owner**: queries `OwnerPersonalDetailsModel` by email → verifies role → fetches notification data → navigates to `OwnerHomeActivity`
- **Role mismatch prevention:** cross-references selected role with stored `category` in database

### 4.4 Personal Details Collection
**Owner** (`OwnerPersonalDetails`): Collects name, age, gender, phone, email. Validations: age 18–100, 10-digit phone, email must exist in `Users` node. Generates unique `ownerId` via `push().getKey()`.

**User** (`UserPersonalDetails`): Similar flow, stores under `UserPersonalDetailsModel`.

### 4.5 Owner Home (`OwnerHomeActivity`)
- **Bottom Navigation** with: Home, Add Property, Notifications
- **Profile icon** in toolbar loads `OwnerProfileFragment`
- Home fragment shows owner's property listing via `RecyclerView` + `MyAdapter2`
- Retrieves properties filtered by `ownerId`
- Fetches notification data from `NotificationMessage` node

### 4.6 Property Listing (`PropertyDetails`)
Owners fill a comprehensive form:
- Property name, price per slot, type (Spinner: House/Flat/Villa/Bungalow/Cottage/Penthouse)
- Address, State, City — **cascading spinners** (selecting a state populates cities)
- Covers all **28 states + 8 UTs** of India with city-level data
- Property description, availability dates (from/to via DatePicker)
- Property image upload from gallery
- **Date validation**: prevents selecting past dates
- **Image upload flow**: gallery → `onActivityResult` → `FirebaseStorage.putFile()` → get download URL → store with property data
- Stores under `PropertyDetailsModel/{propertyId}` with auto-generated key

### 4.7 Property Browse (`PropertyRecyclerActivityForUser`)
- Displays all properties in a `RecyclerView` with `LinearLayoutManager`
- **Real-time sync** using `addValueEventListener` — list updates live
- **Search functionality**: `SearchView` filters by state name (client-side + Firebase query with `startAt`/`endAt`)
- **Bottom Navigation**: Home, Profile
- On item click → retrieves parent info → navigates to `BookingScreen`

### 4.8 Booking Screen (`BookingScreen`)
- Retrieves full property details from Firebase using `propertyId`
- Displays: name, type, state, city, address, description, image (via Picasso)
- Retrieves and displays **owner contact info** (name, phone, email)
- Owner phone number is **clickable** — opens phone dialer via `ACTION_DIAL`
- **Slide animations** applied to all text views
- "Book Now" button navigates to `UserTripDetails`

### 4.9 Trip Details (`UserTripDetails`)
- **NumberPicker** for slot selection (0–6 slots)
- **Guests input** (max 10)
- **DatePicker** for booking date with past-date validation
- **Slot system info dialog** explains the business model:
  - 1 slot = 7 hours + 1 hour cleaning
  - 3 slots = full day (21 hrs + 1 hr complimentary + 2 hr cleaning = 24 hrs)
  - 6 slots = 2 days (42 hrs + 4 hr complimentary + 2 hr cleaning = 48 hrs)
- **Date conflict detection**: checks if selected date falls within the property's unavailable range (fromdate–todate)
- Saves trip details under `UserTripDetailsModel/{propertyId}`
- Navigates to `PaymentActivity`

### 4.10 Payment (`PaymentActivity`)
- Initializes **PhonePe SDK** for UPI payments
- Calculates total: `price × slots` + 20% fees (cleaning + rentH service fee)
- Shows price breakdown: rate/night, cleaning charges, rentH fee, total
- **Terms & Conditions dialog** must be accepted before payment
- On payment acceptance:
  - Creates notification message with booking details (property, user, date, slots, time, amount, contact, email)
  - Stores notification under `NotificationMessage/{notificationId}`
  - Sends FCM notification to owner via `FirebaseMessaging`
  - Shows "Payment Done" confirmation dialog
  - Navigates back to property listing
- **SHA-256 checksum** generation for PhonePe payment verification

### 4.11 Notifications (`NotificationFragment`)
- `RecyclerView` with `NotificationAdapter` displays booking notifications
- Filters notifications by `ownerId` — owners only see their own bookings
- Shows: property name, booker name, date, slots, amount, contact info
- Data syncs in real-time via `addValueEventListener`

### 4.12 Profile Screens
- `OwnerProfileFragment` / `UserProfileFragment`: display personal details
- `UserProfileActivity`: user profile management with bottom navigation

---

## 5. Data Models

| Model | Fields |
|---|---|
| `Users` | email, password, category |
| `OwnerPersonalDetailsModel` | oname, oage, ogender, ophoneno, oemail, id |
| `UserPersonalDetailsModel` | name, age, gender, phoneno, uemail, id |
| `PropertyDetailsModel` | nameofproperty, priceofproperty, typeofproperty, address, state, city, propertydiscription, ownerId, oName, propertyId, imageUrl, fromdate, todate |
| `UserTripDetailsModel` | date, slots, guests |
| `NotificationModel` | notificationId, notificationMessage, id, userId, propertyId, ownerId, totalprice |
| `TripDetailsModel` | Trip tracking fields |

---

## 6. Key Features Summary

1. **Dual-role authentication** (Owner vs User) with role-based routing
2. **CRUD operations** on properties with image upload to Firebase Storage
3. **Cascading state-city spinners** covering all of India
4. **Slot-based booking system** with date conflict detection
5. **Real-time search** by state with Firebase queries
6. **PhonePe UPI payment integration** with checksum verification
7. **Push notifications** to owners on booking via FCM
8. **In-app notification center** with filtered real-time updates
9. **Password visibility toggle** and forgot password via email
10. **Animations** throughout (slide-in, fade transitions)

---

## 7. Firebase Services Usage

| Service | Usage |
|---|---|
| **Authentication** | Email/password signup, login, password reset, session persistence |
| **Realtime Database** | All data storage: users, properties, bookings, notifications. Uses `push()` for auto-IDs, `orderByChild()` for queries, `addValueEventListener` for real-time sync |
| **Cloud Storage** | Property image upload with UUID-based naming, download URL retrieval |
| **Cloud Messaging** | `RemoteMessage.Builder` sends booking notifications to property owners |

---

## 8. Interview Questions & Answers

### Basic Questions

**Q1: What is rentH and what problem does it solve?**
> rentH is an Android-based property rental marketplace that connects property owners with tenants. It solves the problem of finding short-term rental accommodations by providing a platform where owners can list properties with images and availability dates, and users can browse, book, and pay for rentals — all within a single app using Firebase as the backend.

**Q2: Why did you choose Firebase over a traditional backend like Node.js/Spring Boot?**
> Firebase provides a serverless BaaS (Backend-as-a-Service) model that eliminates the need to manage servers. For this project, Firebase offers: (1) real-time data synchronization without polling, (2) built-in authentication, (3) cloud storage for images, (4) push notifications via FCM — all with minimal setup. This allowed rapid development with a single developer. However, for a production-scale app, a custom backend would offer better query flexibility and business logic encapsulation.

**Q3: Explain the role-based login mechanism.**
> During signup, users select "Owner" or "User" via RadioButton. This `category` is stored in the `Users` node. During login, after Firebase Auth verifies credentials, the app queries the respective personal details node (`OwnerPersonalDetailsModel` or `UserPersonalDetailsModel`) by email, then cross-references the stored `category` in `Users`. If the selected role doesn't match the stored category, a "Role mismatch" error is shown, preventing unauthorized access.

**Q4: How are property images handled?**
> Images follow this flow: User picks from gallery via `ACTION_PICK` intent → `onActivityResult` returns the URI → URI is uploaded to Firebase Storage under `images/{UUID}` → on success, the download URL is retrieved → URL is stored in the `PropertyDetailsModel` alongside other property details. For display, **Picasso** loads the URL into ImageViews with automatic caching.

---

### Intermediate Questions

**Q5: Explain the slot-based booking system and its pricing logic.**
> The system uses time slots instead of full days. Each slot = 7 hours + 1 hour cleaning time. Users can book 1–6 slots:
> - 1 slot = 7 hrs working + 1 hr cleaning = 8 hrs
> - 3 slots = 21 hrs + 1 hr complimentary + 2 hr cleaning = 24 hrs (1 full day)
> - 6 slots = 42 hrs + 4 hr complimentary + 2 hr cleaning = 48 hrs (2 days)
>
> Pricing: `total = pricePerSlot × numberOfSlots × 1.2` (20% surcharge for cleaning and platform fees).

**Q6: How does the cascading state-city spinner work?**
> A `HashMap<String, String[]>` maps each state name to its array of cities. When the state spinner's `onItemSelected` fires, it looks up the selected state in the map and creates a new `ArrayAdapter` for the city spinner with the corresponding cities array. This provides a dynamic, dependent dropdown experience without any network call.

**Q7: How does the notification system work end-to-end?**
> 1. User completes payment in `PaymentActivity`
> 2. A notification message string is constructed with booking details (property name, user name, date, slots, time, amount, contact info)
> 3. This message is stored in `NotificationMessage/{autoId}` in Realtime Database with fields: notificationMessage, userId, ownerId, propertyId, totalprice
> 4. FCM `RemoteMessage` is sent to the owner's device
> 5. When the owner opens Notifications tab, `NotificationFragment` queries `NotificationMessage` and filters by `ownerId` matching the current owner
> 6. Messages display in a RecyclerView via `NotificationAdapter` with real-time updates

**Q8: How do you prevent double-booking / date conflicts?**
> In `UserTripDetails`, when a user selects a booking date:
> 1. The app parses the selected date, the property's `fromdate`, and `todate` using `SimpleDateFormat`
> 2. It checks if the selected date equals or falls between fromdate and todate
> 3. If it does, a toast shows "Selected date falls within the unavailable range" and booking is blocked
> 4. It also queries the property's date in the database to ensure the selected date doesn't match the stored property date

**Q9: How is View Binding used and why?**
> View Binding is enabled in `build.gradle` with `viewBinding { enabled = true }`. It generates a binding class for each XML layout (e.g., `ActivityLoginScreenBinding`). In `onCreate`, we call `binding = ActivityLoginScreenBinding.inflate(getLayoutInflater())` and `setContentView(binding.getRoot())`. This provides type-safe, null-safe access to views like `binding.emailet` instead of `findViewById(R.id.emailet)`, eliminating `ClassCastException` and `NullPointerException` risks.

---

### Advanced / Tricky Questions

**Q10: You store passwords in the Users model and Firebase Realtime Database. Isn't that a security vulnerability?**
> **Yes, this is a significant security flaw.** The `Users` model stores plain-text passwords in the Realtime Database, which is separate from Firebase Auth's secure credential storage. Firebase Auth already handles password hashing and storage securely. Storing passwords again in the Realtime Database creates: (1) a plain-text password exposure risk, (2) redundant data, (3) a violation of security best practices. The fix: remove the `password` field from the `Users` model entirely and rely solely on Firebase Auth for credential management.

**Q11: The app uses `addListenerForSingleValueEvent` in many places. What happens if the data changes after the initial read?**
> `addListenerForSingleValueEvent` reads data exactly once and detaches. If another user modifies the data after this read, the app won't receive updates. This is appropriate for login/booking flows but problematic for property listings. The property listing screen correctly uses `addValueEventListener` (persistent listener) for real-time updates. However, screens like `BookingScreen` use single-value events, meaning if the owner updates property details while a user is viewing them, the user sees stale data until they re-open the screen.

**Q12: There's no Firebase Security Rules configuration visible. What are the implications?**
> Without proper security rules, the Realtime Database likely uses default or overly permissive rules (e.g., `".read": true, ".write": true`). This means:
> - Any authenticated (or unauthenticated) user could read/write any data
> - A malicious user could modify other users' bookings, delete properties, or read all notification messages
> - **Production fix**: implement rules like `"Users": { "$uid": { ".read": "$uid === auth.uid", ".write": "$uid === auth.uid" } }` and similar restrictions for all nodes.

**Q13: The login screen has deeply nested Firebase callbacks. What problems does this cause?**
> The `checkOwnerPersonalDetails` method has 3+ levels of nested `ValueEventListener` callbacks (query OwnerPersonalDetailsModel → query NotificationMessage → query Users). This creates:
> - **Callback hell**: reduced readability and maintainability
> - **Error handling gaps**: `onCancelled` callbacks are often empty
> - **Race conditions**: nested async calls may complete in unexpected order
> - **Fix**: Use `Task` chaining with `Tasks.whenAllSuccess()`, or adopt an architecture like MVVM with `LiveData`/`ViewModel` to separate data logic from UI.

**Q14: Why is `overridePendingTransition` used instead of the newer Activity Transitions API?**
> `overridePendingTransition` is the legacy approach (pre-API 21). The newer `ActivityOptions.makeSceneTransitionAnimation()` supports shared element transitions and is more flexible. The app targets SDK 34, so it could use the modern API. However, `overridePendingTransition` is simpler and works across all API levels the app supports (24+). For a production app, migrating to the Transitions API or Jetpack Navigation with animations would be recommended.

**Q15: The app queries all Users to find a matching email during login. What's the performance concern?**
> In `checkUserDetails` and `checkOwnerPersonalDetails`, the code iterates through ALL users with `userRef.addListenerForSingleValueEvent` and manually checks `eMailFromDatabase.equals(email)`. This is an **O(n) scan** that downloads the entire Users node. With thousands of users, this causes:
> - High bandwidth consumption
> - Slow login times
> - Unnecessary Firebase read costs
> - **Fix**: Use `orderByChild("email").equalTo(emailInput)` to let Firebase filter server-side, which is already done in some places but inconsistently.

**Q16: What happens if the PhonePe SDK initialization fails?**
> The `PhonePe.init(this)` call is wrapped in a generic `try-catch(Exception)` that only calls `e.printStackTrace()`. The payment flow continues even if initialization fails, which means:
> - The payment button exists but PhonePe intents will throw `PhonePeInitException`
> - Currently, the app uses an `AlertDialog` with "Accept" as the payment confirmation (simulated payment), so the actual PhonePe SDK flow is commented out
> - **Production fix**: Check initialization status before enabling the payment button, show meaningful error messages, and implement a fallback payment method.

**Q17: The app passes sensitive data (userId, ownerId, email) via Intent extras. Is this secure?**
> Intent extras are not encrypted and can be intercepted by:
> - Other apps with `INTERACT_ACROSS_USERS` permission (on rooted devices)
> - ADB debugging tools
> - Third-party libraries with broad permissions
>
> **Better approaches**: (1) Pass only minimal identifiers and fetch sensitive data from Firebase in the receiving Activity, (2) Use `EncryptedSharedPreferences` for session data, (3) Implement a proper session/state management with `ViewModel` and `SharedViewModel`.

**Q18: Why does `PropertyRecyclerActivityForUser` call `myAdapter.reverseList()` inside the data change loop?**
> This is a **bug/inefficiency**. Inside `onDataChange`, for each child snapshot, `reverseList()` and `notifyDataSetChanged()` are called. This means if there are 100 properties, the list is reversed 100 times and the adapter is notified 100 times. The correct approach is to call these **once**, after the loop completes:
> ```java
> for (DataSnapshot ds : snapshot.getChildren()) {
>     list.add(ds.getValue(PropertyDetailsModel.class));
> }
> myAdapter.reverseList(); // once
> myAdapter.notifyDataSetChanged(); // once
> ```

**Q19: The app doesn't implement any offline caching. How would you add it?**
> Firebase Realtime Database has built-in disk persistence. Enable it with:
> ```java
> FirebaseDatabase.getInstance().setPersistenceEnabled(true);
> ```
> This caches data locally, allowing offline reads. Additionally:
> - Use `keepSynced(true)` on critical database references
> - Implement `ConnectivityManager` checks to show online/offline status
> - Queue write operations for when connectivity returns
> - For images, Picasso already caches loaded images in memory/disk

**Q20: How would you improve this app's architecture for production?**
> Key improvements:
> 1. **MVVM + Repository Pattern**: Separate UI from data logic using `ViewModel`, `LiveData`, and Repository classes
> 2. **Room Database**: Local caching for offline support
> 3. **Dependency Injection**: Use Hilt/Dagger for cleaner dependency management
> 4. **Security Rules**: Implement proper Firebase security rules
> 5. **Remove password storage**: from Realtime Database
> 6. **Error handling**: Replace empty `onCancelled` callbacks with proper error UI
> 7. **Pagination**: Use Firebase `limitToFirst()`/`limitToLast()` for large property lists
> 8. **Input sanitization**: Prevent injection attacks in text inputs
> 9. **ProGuard/R8**: Enable minification for release builds
> 10. **Unit/UI Testing**: Add JUnit tests and Espresso UI tests
