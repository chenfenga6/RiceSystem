package com.test.Entity;

import java.util.ArrayList;
import java.util.List;

public class Resdata {
    private Integer id;
    private String name;
    private List data;

    public Resdata() {
    }

    public Resdata(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Resdata(Integer id, String name, List data) {
        this.id = id;
        this.name = name;
        this.data = data;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }
}
