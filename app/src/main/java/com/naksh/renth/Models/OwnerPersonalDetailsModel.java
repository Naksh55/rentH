package com.naksh.renth.Models;

public class OwnerPersonalDetailsModel {
    private String oname, ogender, ophoneno,oemail,id,oage;
//    private int oage;

    public OwnerPersonalDetailsModel() {
    }

    public OwnerPersonalDetailsModel(String oname, String oage, String ogender, String ophoneno,String oemail) {
        this.oname = oname;
        this.oage = oage;
        this.ogender = ogender;
        this.ophoneno = ophoneno;
        this.oemail=oemail;
    }

    public OwnerPersonalDetailsModel(String oname, String oage, String ogender, String ophoneno,String oemail,String id) {
        this.oname = oname;
        this.oage = oage;
        this.ogender = ogender;
        this.ophoneno = ophoneno;
        this.oemail=oemail;
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

    public String getOage() {
        return oage;
    }

    public void setOage(String oage) {
        this.oage = oage;
    }

    public String getOemail() {
        return oemail;
    }

    public void setOemail(String oemail) {
        this.oemail = oemail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}