package com.test.Entity;

import java.util.ArrayList;
import java.util.List;

public class Permissiontree {
    private Integer id;                             // 模块编号
    private String title;                           //模块标题
    private boolean spread;                         //是否展开
    private boolean checked;                        //是否勾选(叶子结点为true)
    private List children = new ArrayList();        //子节点集合

    public Permissiontree() {
    }

    public Permissiontree(Integer id, String title, boolean spread, boolean checked) {
        this.id = id;
        this.title = title;
        this.spread = spread;
        this.checked = checked;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSpread() {
        return spread;
    }

    public void setSpread(boolean spread) {
        this.spread = spread;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List getChildren() {
        return children;
    }

    public void setChildren(List children) {
        this.children = children;
    }
}
