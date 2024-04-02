package com.naksh.renth.Models;

public class UserPersonalDetailsModel {
    private String name,gender,phoneno,userId,uemail;
    int age;

    public UserPersonalDetailsModel(){}
    public UserPersonalDetailsModel(String name, int age, String gender, String phoneno) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phoneno = phoneno;
    }

    public UserPersonalDetailsModel(String name, int age,String gender, String userId,String phoneno,String uemail) {
        this.name = name;
        this.gender = gender;
        this.phoneno = phoneno;
        this.userId = userId;
        this.age = age;
        this.uemail=uemail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getId() {
        return userId;
    }

    public void setId(String id) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUemail() {
        return uemail;
    }

    public void setUemail(String uemail) {
        this.uemail = uemail;
    }
}
