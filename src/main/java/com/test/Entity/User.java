package com.test.Entity;

public class User {
    private Integer uid ;
    private String uname;
    private String upwd;
    private String spermission; //系统权限
    private String mpermission; //物料权限
    private String ustate = "0";      //状态(启用-1/待审核-0)
    private Integer suser;      //超级用户标识
    private  String stime;      //注册时间
    private String mail;        //邮箱
    private String company;     //公司名称
    private String btime;       //上一次登录时间
    private String bip;         //上一次登陆ip

    public User() {
    }

    public User(Integer uid, String uname, String upwd, String spermission, String mpermission,
                String ustate, Integer suser, String stime, String mail, String company, String btime, String bip) {
        this.uid = uid;
        this.uname = uname;
        this.upwd = upwd;
        this.spermission = spermission;
        this.mpermission = mpermission;
        this.ustate = ustate;
        this.suser = suser;
        this.stime = stime;
        this.mail = mail;
        this.company = company;
        this.btime = btime;
        this.bip = bip;
    }

    public String getSpermission() {
        return spermission;
    }

    public void setSpermission(String spermission) {
        this.spermission = spermission;
    }

    public String getMpermission() {
        return mpermission;
    }

    public void setMpermission(String mpermission) {
        this.mpermission = mpermission;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBtime() {
        return btime;
    }

    public void setBtime(String btime) {
        this.btime = btime;
    }

    public String getBip() {
        return bip;
    }

    public void setBip(String bip) {
        this.bip = bip;
    }


    public Integer getSuser() {
        return suser;
    }

    public void setSuser(Integer suser) {
        this.suser = suser;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpwd() {
        return upwd;
    }

    public void setUpwd(String upwd) {
        this.upwd = upwd;
    }

    public String getUstate() {
        return ustate;
    }

    public void setUstate(String ustate) {
        this.ustate = ustate;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", uname='" + uname + '\'' +
                ", upwd='" + upwd + '\'' +
                ", spermission='" + spermission + '\'' +
                ", mpermission='" + mpermission + '\'' +
                ", ustate='" + ustate + '\'' +
                ", suser=" + suser +
                ", stime='" + stime + '\'' +
                ", mail='" + mail + '\'' +
                ", company='" + company + '\'' +
                ", btime='" + btime + '\'' +
                ", bip='" + bip + '\'' +
                '}';
    }
}
