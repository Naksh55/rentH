package com.naksh.renth.Models;

import java.util.StringTokenizer;

public class PropertyDetailsModel {


    String nameofproperty,id;
    String typeofproperty,address,city,state,propertydiscription;
    String fromdate,todate;
    int priceofproperty;
    private String propertydp; // Assuming imageUrl is the URL of the image
    String ownerId;

    public PropertyDetailsModel(){}

    public PropertyDetailsModel(String nameofproperty,  String typeofproperty, String address, String city, String state, String propertydiscription, int priceofproperty, String propertydp) {
        this.nameofproperty = nameofproperty;
        this.typeofproperty = typeofproperty;
        this.address = address;
        this.city = city;
        this.state = state;
        this.propertydiscription = propertydiscription;
        this.priceofproperty = priceofproperty;
        this.propertydp = propertydp;
    }

//    public PropertyDetailsModel(String nameofproperty, String typeofproperty, String address, String city, String state, String propertydiscription, String fromdate, String todate, int priceofproperty, String propertydp, String randomPropertyId) {
//        this.nameofproperty = nameofproperty;
//        this.typeofproperty = typeofproperty;
//        this.address = address;
//        this.city = city;
//        this.state = state;
//        this.propertydiscription = propertydiscription;
//        this.fromdate = fromdate;
//        this.todate = todate;
//        this.priceofproperty = priceofproperty;
//        this.propertydp = propertydp;
//        this.randomPropertyId = randomPropertyId;
//    }

//    public PropertyDetailsModel(String nameofproperty, int priceofproperty, String typeofproperty, String address, String state, String city, String propertydiscription, String id, String propertydp, String fromdate, String todate) {
//        this.nameofproperty = nameofproperty;
//        this.id = id;
//        this.typeofproperty = typeofproperty;
//        this.address = address;
//        this.city = city;
//        this.state = state;
//        this.propertydiscription = propertydiscription;
//        this.priceofproperty = priceofproperty;
//        this.propertydp = propertydp;
//        this.fromdate=fromdate;
//        this.todate=todate;
//    }

    public PropertyDetailsModel(String nameofproperty, int priceofproperty, String typeofproperty, String address, String state, String city, String propertydiscription, String ownerId, String imageUrl, String fromDateString, String toDateString) {
        this.nameofproperty = nameofproperty;
        this.priceofproperty = priceofproperty;
        this.typeofproperty = typeofproperty;
        this.address = address;
        this.state = state;
        this.city = city;
        this.propertydiscription = propertydiscription;
        this.ownerId = ownerId; // Set ownerId
        this.propertydp = imageUrl;
        this.fromdate = fromDateString;
        this.todate = toDateString;
    }

    public PropertyDetailsModel( String propertyName, String typeOfProperty, int propertyPrice) {
        this.nameofproperty=propertyName;
        this.typeofproperty=typeOfProperty;
        this.priceofproperty=propertyPrice;
    }
//    public PropertyDetailsModel(String nameofproperty, String typeofproperty, String address, String city, String state, String propertydiscription, String fromdate, String todate, int priceofproperty,String imageUrl) {
////        this.nameofproperty = nameofproperty;
//////        this.typeofproperty = typeofproperty;
//////        this.address = address;
//////        this.city = city;
//////        this.state = state;
//////        this.propertydiscription = propertydiscription;
//////        this.fromdate = fromdate;
//////        this.todate = todate;
////        this.priceofproperty = priceofproperty;
//////        this.imageUrl=imageUrl;
//    }

//    public PropertyDetailsModel(String nameofproperty, String typeofproperty, String address, String city, String state, String propertydiscription, String fromdate, String todate, int priceofproperty) {
//
//        this.nameofproperty = nameofproperty;
////        this.typeofproperty = typeofproperty;
////        this.address = address;
////        this.city = city;
////        this.state = state;
////        this.propertydiscription = propertydiscription;
////        this.fromdate = fromdate;
////        this.todate = todate;
//        this.priceofproperty = priceofproperty;
//    }


//    public PropertyDetailsModel(String nameofproperty, int priceofproperty) {
//        this.nameofproperty = nameofproperty;
//        this.priceofproperty = priceofproperty;
//    }



    public String getNameofproperty() {
        return nameofproperty;
    }

    public void setNameofproperty(String nameofproperty) {
        this.nameofproperty = nameofproperty;
    }

    public String getTypeofproperty() {
        return typeofproperty;
    }

    public void setTypeofproperty(String typeofproperty) {
        this.typeofproperty = typeofproperty;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPropertydiscription() {
        return propertydiscription;
    }

    public void setPropertydiscription(String propertydiscription) {
        this.propertydiscription = propertydiscription;
    }

    public String getFordate() {
        return fromdate;
    }

    public void setFromdate(String fromdate) {
        this.fromdate = fromdate;
    }

    public String getTodate() {
        return todate;
    }

    public void setTodate(String todate) {
        this.todate = todate;
    }

    public int getPriceofproperty() {
        return priceofproperty;
    }

    public void setPriceofproperty(int priceofproperty) {
        this.priceofproperty = priceofproperty;
    }

    public String getImageUrl() {
        return propertydp;
    }

    public void setImageUrl(String propertydp) {
        this.propertydp = propertydp;
    }

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

        private String randomPropertyId;

//        public PropertyDetailsModel() {
//            // Default constructor required for Firebase Realtime Database
//        }

        public PropertyDetailsModel(String randomPropertyId) {
            this.randomPropertyId = randomPropertyId;
        }

        public String getRandomPropertyId() {
            return randomPropertyId;
        }

        public void setRandomPropertyId(String randomPropertyId) {
            this.randomPropertyId = randomPropertyId;
        }


    private String propertyId;
    // Other fields and methods...

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

}
