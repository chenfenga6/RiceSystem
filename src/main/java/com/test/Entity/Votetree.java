package com.test.Entity;

import java.util.ArrayList;
import java.util.List;

public class Votetree {
    private Integer id;                             // 模块编号
    private String title;                           //模块标题
    private boolean spread;                         //是否展开
    private List children = new ArrayList();        //子节点集合

    public Votetree() {
    }

    public Votetree(Integer id, String title, boolean spread, List<Votetree> children) {
        this.id = id;
        this.title = title;
        this.spread = spread;
        this.children = children;
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

    public List getChildren() {
        return children;
    }

    public void setChildren(List children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "Votetree{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", spread=" + spread +
                ", children=" + children +
                '}';
    }
}
