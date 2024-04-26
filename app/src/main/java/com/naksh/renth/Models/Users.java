package com.naksh.renth.Models;

import com.naksh.renth.LoginScreen;
//import com.naksh.renth.LoginActivity;


public class Users {
    String password, email, category;

    //signup constructor
    public Users(String email, String password, String category) {
        this.email = email;
        this.password = password;
        this.category = category;
    }


    public Users() {
    }

    public Users(LoginScreen loginScreen) {
    }

    public Users(String email) {
        this.email=email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }



}

