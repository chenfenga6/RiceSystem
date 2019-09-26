package com.test.Entity;

public class Platform {
    private Integer pid ;
    private String pname;
    private String pcode;
    private String purl;
    private String plog;

    public Platform() {
    }

    public Platform(Integer pid, String pname, String pcode, String purl, String plog) {
        this.pid = pid;
        this.pname = pname;
        this.pcode = pcode;
        this.purl = purl;
        this.plog = plog;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }

    public String getPlog() {
        return plog;
    }

    public void setPlog(String plog) {
        this.plog = plog;
    }

    @Override
    public String toString() {
        return "Platform{" +
                "pid=" + pid +
                ", pname='" + pname + '\'' +
                ", pcode='" + pcode + '\'' +
                ", purl='" + purl + '\'' +
                ", plog='" + plog + '\'' +
                '}';
    }
}
