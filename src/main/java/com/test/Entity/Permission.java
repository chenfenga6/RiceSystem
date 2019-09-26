package com.test.Entity;

public class Permission {
    Integer id ;
    Integer rId ;
    Integer nId ;
    Integer pId;

    public Permission() {
    }

    public Permission(Integer id, Integer rId, Integer nId, Integer pId) {
        this.id = id;
        this.rId = rId;
        this.nId = nId;
        this.pId = pId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getrId() {
        return rId;
    }

    public void setrId(Integer rId) {
        this.rId = rId;
    }

    public Integer getnId() {
        return nId;
    }

    public void setnId(Integer nId) {
        this.nId = nId;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", rId=" + rId +
                ", nId=" + nId +
                ", pId=" + pId +
                '}';
    }
}
