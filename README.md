<p align="center">
  <h1 align="center">🏠 rentH</h1>
  <p align="center">
    <b>A full-featured Android property rental platform connecting property owners with tenants across India.</b>
  </p>
  <p align="center">
    <img src="https://img.shields.io/badge/Platform-Android-3DDC84?logo=android&logoColor=white" alt="Platform">
    <img src="https://img.shields.io/badge/Language-Java-ED8B00?logo=openjdk&logoColor=white" alt="Language">
    <img src="https://img.shields.io/badge/Backend-Firebase-FFCA28?logo=firebase&logoColor=black" alt="Backend">
    <img src="https://img.shields.io/badge/Min%20SDK-24-blue" alt="Min SDK">
    <img src="https://img.shields.io/badge/Target%20SDK-34-blue" alt="Target SDK">
    <img src="https://img.shields.io/badge/Version-1.0-brightgreen" alt="Version">
  </p>
</p>

---

## 📖 About

**rentH** is a native Android application that serves as a marketplace for renting residential properties. It provides a dual-interface experience — one for **property owners** who list and manage their properties, and another for **users (tenants)** who browse, book, and pay for rentals. The app leverages Firebase for real-time data synchronization, authentication, and cloud storage, ensuring a seamless and responsive user experience.

---

## ✨ Features

### 👤 User (Tenant) Side
- **Browse Properties** — Explore rental listings with images, descriptions, pricing, and availability dates
- **Search & Filter** — Filter properties by state and city across all Indian states
- **Property Details** — View detailed property information including type, address, description, and owner contact info
- **Booking System** — Select dates and number of slots, then proceed to book a property
- **Integrated Payments** — Secure payment processing with PhonePe payment gateway integration
- **Trip Management** — View and manage upcoming and past trip/booking details
- **User Profile** — Manage personal details, view profile information, and update account settings
- **Real-time Notifications** — Receive booking confirmations and updates via in-app notifications
- **Direct Owner Contact** — One-tap phone dialing to reach property owners directly

### 🏢 Owner Side
- **Property Listing** — Add new properties with multiple images, pricing, descriptions, and availability dates
- **Property Management** — View, update, and manage all listed properties from a dedicated dashboard
- **Booking Notifications** — Get real-time notifications when a tenant books a property, including tenant contact details
- **Profile Management** — Maintain owner profile with personal and contact information
- **Wallet** — Track earnings and financial transactions

### 🔐 Authentication & Security
- **Firebase Authentication** — Secure sign-up and login with email/password
- **Role-based Access** — Separate flows and dashboards for owners and tenants
- **Session Persistence** — Auto-login for returning users with splash screen handling

### 🗺️ Additional Features
- **Google Maps Integration** — View property locations on an interactive map
- **Animated UI** — Smooth slide-in animations and transitions for a polished user experience
- **Pan-India Coverage** — Property listings supported across all Indian states and major cities

---

## 🏗️ Tech Stack

| Layer | Technology |
|---|---|
| **Language** | Java |
| **Platform** | Android (Min SDK 24 / Target SDK 34) |
| **Build System** | Gradle 8.2.0 |
| **Authentication** | Firebase Auth |
| **Database** | Firebase Realtime Database |
| **Cloud Storage** | Firebase Cloud Storage |
| **Push Notifications** | Firebase Cloud Messaging (FCM) |
| **Image Loading** | Picasso |
| **Payments** | PhonePe Intent SDK |
| **Maps** | Google Maps SDK |
| **UI Components** | Material Design, ConstraintLayout, ViewBinding |
| **Networking** | Retrofit 2 + Gson Converter |
| **UI Extras** | DialogPlus, NumberPicker |
| **Backend Services** | Backendless |

---

## 📁 Project Structure

```
rentH/
├── app/
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml
│           ├── java/com/naksh/renth/
│           │   ├── SplashActivity.java          # Animated splash screen with auth check
│           │   ├── LoginScreen.java             # Login & authentication
│           │   ├── SignUpScreen.java             # User/Owner registration
│           │   ├── UserHomeActivity.java         # Tenant home dashboard
│           │   ├── OwnerHomeActivity.java        # Owner home dashboard
│           │   ├── UserPersonalDetails.java      # Tenant profile setup
│           │   ├── OwnerPersonalDetails.java     # Owner profile setup
│           │   ├── PropertyDetails.java          # Add new property listing
│           │   ├── PropertyRecyclerActivityForUser.java   # Browse properties (tenant)
│           │   ├── PropertyRecyclerActivityForOwner.java  # Manage properties (owner)
│           │   ├── BookingScreen.java            # Property booking flow
│           │   ├── PaymentActivity.java          # Payment processing (PhonePe)
│           │   ├── TripDetails.java              # Trip/booking details view
│           │   ├── UserTripDetails.java          # User-specific trip management
│           │   ├── UserProfileActivity.java      # User profile screen
│           │   ├── MapsActivity.java             # Google Maps integration
│           │   ├── NotificationFragment.java     # In-app notifications
│           │   ├── WalletFragment.java           # Wallet & earnings
│           │   ├── MyAdapter.java                # RecyclerView adapter (tenant view)
│           │   ├── MyAdapter2.java               # RecyclerView adapter (owner view)
│           │   ├── SharedViewModel.java          # Shared ViewModel for fragments
│           │   └── Models/
│           │       ├── PropertyDetailsModel.java       # Property data model
│           │       ├── UserPersonalDetailsModel.java   # User profile model
│           │       ├── OwnerPersonalDetailsModel.java  # Owner profile model
│           │       ├── TripDetailsModel.java           # Trip data model
│           │       ├── UserTripDetailsModel.java       # User trip data model
│           │       ├── NotificationModel.java          # Notification data model
│           │       └── Users.java                      # Base user model
│           └── res/
│               ├── layout/         # XML layout files (30 screens)
│               ├── anim/           # Animation resources
│               ├── drawable/       # Icons, backgrounds, shapes
│               ├── menu/           # Navigation menus
│               ├── values/         # Colors, strings, themes
│               └── mipmap-*/       # App launcher icons
├── build.gradle                    # Root build configuration
├── settings.gradle                 # Project settings
├── gradle.properties               # Gradle properties
└── .gitignore
```

---

## 🚀 Getting Started

### Prerequisites

- **Android Studio** Arctic Fox (2021.3.1) or later
- **JDK** 8 or higher
- **Android SDK** with API Level 34 installed
- A **Firebase project** with Realtime Database, Authentication, Cloud Storage, and FCM enabled
- A **Google Maps API Key**
- A **PhonePe merchant account** (for payment integration)

### Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/Naksh55/rentH.git
   cd rentH
   ```

2. **Open in Android Studio**
   - Open Android Studio → `File` → `Open` → Select the `rentH` directory

3. **Configure Firebase**
   - Create a new Firebase project at [Firebase Console](https://console.firebase.google.com/)
   - Enable **Authentication** (Email/Password)
   - Enable **Realtime Database**
   - Enable **Cloud Storage**
   - Enable **Cloud Messaging**
   - Download `google-services.json` and place it in the `app/` directory

4. **Configure Google Maps**
   - Obtain a Google Maps API key from [Google Cloud Console](https://console.cloud.google.com/)
   - Add the key to your `local.properties` file:
     ```properties
     MAPS_API_KEY=your_api_key_here
     ```

5. **Configure PhonePe (Optional)**
   - Obtain merchant credentials from [PhonePe Business](https://business.phonepe.com/)
   - Update the payment configuration in `PaymentActivity.java`

6. **Build & Run**
   ```bash
   ./gradlew assembleDebug
   ```
   Or simply click the **Run** ▶️ button in Android Studio.

---

## 📱 App Flow

```
┌─────────────┐     ┌──────────────┐     ┌─────────────────────┐
│   Splash     │────▶│   Login /    │────▶│  Role Selection     │
│   Screen     │     │   Sign Up    │     │  (Owner / Tenant)   │
└─────────────┘     └──────────────┘     └──────────┬──────────┘
                                                     │
                          ┌──────────────────────────┼──────────────────────────┐
                          ▼                                                     ▼
                 ┌─────────────────┐                                  ┌─────────────────┐
                 │  Owner Dashboard │                                  │ Tenant Dashboard  │
                 │  ┌─────────────┐ │                                  │  ┌─────────────┐ │
                 │  │ Add Property│ │                                  │  │Browse Props  │ │
                 │  │ My Listings │ │                                  │  │ Book & Pay   │ │
                 │  │ Notifications│ │                                  │  │ My Trips     │ │
                 │  │ Profile     │ │                                  │  │ Notifications│ │
                 │  │ Wallet      │ │                                  │  │ Profile      │ │
                 │  └─────────────┘ │                                  │  └─────────────┘ │
                 └─────────────────┘                                  └─────────────────┘
```

---

## 🗄️ Firebase Database Schema

```
Firebase Realtime Database
│
├── Users/
│   └── {userId}
│       ├── email
│       ├── password
│       └── userType (owner/user)
│
├── UserPersonalDetailsModel/
│   └── {userId}
│       ├── id, name, email, phoneno, address, ...
│
├── OwnerPersonalDetailsModel/
│   └── {ownerId}
│       ├── id, oname, oemail, ophoneno, ...
│
├── PropertyDetailsModel/
│   └── {propertyId}
│       ├── nameofproperty, typeofproperty, priceofproperty
│       ├── address, city, state, propertydiscription
│       ├── fromdate, todate
│       ├── ownerId, oName, propertyId
│       └── propertydp1, propertydp2, propertydp3, propertydp4
│
└── NotificationMessage/
    └── {notificationId}
        ├── notificationMessage, userId
        ├── ownerId, propertyId
        └── totalprice
```

---

## 🎨 UI Highlights

- **Animated Splash Screen** — Slide-in logo animation with 3.5s delay
- **Slide Transitions** — Left-to-right slide animations on property and booking screens
- **Material Design** — Material Components with consistent theming and styling
- **Responsive Layouts** — ConstraintLayout-based designs for all screen sizes
- **Dark Mode Support** — Night theme resources included (`values-night/`)

---

## 📄 License

This project is open source and available for educational purposes.

---

## 👤 Author

**Naksh**
- GitHub: [@Naksh55](https://github.com/Naksh55)

---

<p align="center">
  Made with ❤️ for the Indian rental market
</p>
