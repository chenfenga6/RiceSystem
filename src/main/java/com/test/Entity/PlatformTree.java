package com.test.Entity;

import java.util.List;

/**
 * 用来描述平台中 各个模块之间的 <树型关系>
 */
public class PlatformTree {
    private Integer id;             //模块id
    private String cname;           //模块中文名
    private String ename;           //模块英文名
    private Integer pid;            //模块父亲节点
    private String notes;           //模块notes
    private String tag;             //模块tag
    private List children;          //模块 孩子节点

    public PlatformTree() {
    }

    public PlatformTree(Integer id, String cname, String ename, Integer pid, String notes, String tag) {
        this.id = id;
        this.cname = cname;
        this.ename = ename;
        this.pid = pid;
        this.notes = notes;
        this.tag = tag;
    }

    public PlatformTree(String cname, String ename, Integer pid, String notes, String tag) {
        this.id = id;
        this.cname = cname;
        this.ename = ename;
        this.pid = pid;
        this.notes = notes;
        this.tag = tag;
    }

    public PlatformTree(Integer id, String cname, String ename, Integer pid, String notes, String tag, List children) {
        this.id = id;
        this.cname = cname;
        this.ename = ename;
        this.pid = pid;
        this.notes = notes;
        this.tag = tag;
        this.children = children;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List getChildren() {
        return children;
    }

    public void setChildren(List children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "PlatformTree{" +
                "id=" + id +
                ", cname='" + cname + '\'' +
                ", ename='" + ename + '\'' +
                ", pid=" + pid +
                ", notes='" + notes + '\'' +
                ", tag='" + tag + '\'' +
                ", children=" + children +
                '}';
    }
}
