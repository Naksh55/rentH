package com.naksh.renth.Models;

public class OwnerPersonalDetailsModel {
    private String oname, ogender, ophoneno,email,id;
    private int oage;

    public OwnerPersonalDetailsModel() {
    }

    public OwnerPersonalDetailsModel(String oname, int oage, String ogender, String ophoneno,String email) {
        this.oname = oname;
        this.oage = oage;
        this.ogender = ogender;
        this.ophoneno = ophoneno;
        this.email=email;
    }

    public OwnerPersonalDetailsModel(String oname, int oage, String ogender, String ophoneno,String email,String id) {
        this.oname = oname;
        this.oage = oage;
        this.ogender = ogender;
        this.ophoneno = ophoneno;
        this.email=email;
        this.id=id;
    }
    public String getOname() {
        return oname;
    }

    public void setOname(String oname) {
        this.oname = oname;
    }

    public String getOgender() {
        return ogender;
    }

    public void setOgender(String ogender) {
        this.ogender = ogender;
    }

    public String getOphoneno() {
        return ophoneno;
    }

    public void setOphoneno(String ophoneno) {
        this.ophoneno = ophoneno;
    }

    public int getOage() {
        return oage;
    }

    public void setOage(int oage) {
        this.oage = oage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}