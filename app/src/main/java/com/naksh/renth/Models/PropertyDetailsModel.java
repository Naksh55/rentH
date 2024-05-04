package com.naksh.renth.Models;

import java.util.StringTokenizer;

public class PropertyDetailsModel {


    String nameofproperty,id;
    String typeofproperty,address,city,state,propertydiscription;
    String fromdate,todate;
    String priceofproperty;
    private String propertydp,image1,image2,image3; // Assuming imageUrl is the URL of the image
    String ownerId;
    String oName;
    private String key; // Assuming key is a String
    String propertyId;


    public PropertyDetailsModel(){}

public PropertyDetailsModel(String nameofproperty,  String typeofproperty, String address, String city, String state, String propertydiscription, String priceofproperty, String propertydp,String oName){
    this.nameofproperty = nameofproperty;
    this.typeofproperty = typeofproperty;
    this.address = address;
    this.city = city;
    this.state = state;
    this.propertydiscription = propertydiscription;
    this.priceofproperty = priceofproperty;
    this.propertydp = propertydp;
    this.oName=oName;

}
    public PropertyDetailsModel(String nameofproperty,  String typeofproperty, String address, String city, String state, String propertydiscription, String priceofproperty, String propertydp,String oName,String propertyId){
        this.nameofproperty = nameofproperty;
        this.typeofproperty = typeofproperty;
        this.address = address;
        this.city = city;
        this.state = state;
        this.propertydiscription = propertydiscription;
        this.priceofproperty = priceofproperty;
        this.propertydp = propertydp;
        this.oName=oName;
        this.propertyId=propertyId;

    }

public PropertyDetailsModel(String nameofproperty, String priceofproperty, String typeofproperty, String address, String state, String city, String propertydiscription, String ownerId, String imageUrl, String fromDateString, String toDateString,String oName,String propertyId) {
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
    this.oName=oName;
    this.propertyId=propertyId;

}
    public PropertyDetailsModel( String propertyName, String typeOfProperty, String priceofproperty) {
        this.nameofproperty=propertyName;
        this.typeofproperty=typeOfProperty;
        this.priceofproperty=priceofproperty;
    }



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

    public String getPriceofproperty() {
        return priceofproperty;
    }

    public void setPriceofproperty(String priceofproperty) {
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

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    // Other fields and methods...

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getoName() {
        return oName;
    }

    public void setoName(String oName) {
        this.oName = oName;
    }
}
