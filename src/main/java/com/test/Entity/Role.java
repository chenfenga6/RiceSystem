package com.test.Entity;

public class Role {
    Integer rid;
    String rname;
    String notes;

    public Role() {
    }

    public Role(Integer rid, String rname, String notes) {
        this.rid = rid;
        this.rname = rname;
        this.notes = notes;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Role{" +
                "rid=" + rid +
                ", rname='" + rname + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
