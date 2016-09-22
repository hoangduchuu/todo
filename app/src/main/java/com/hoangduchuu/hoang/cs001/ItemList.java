package com.hoangduchuu.hoang.cs001;

/**
 * Created by hoang on 9/22/16.
 */

public class ItemList {
    private int id;
    private String itName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItName() {
        return itName;
    }

    public void setItName(String itName) {
        this.itName = itName;
    }

    public ItemList(int id, String itName) {

        this.id = id;
        this.itName = itName;
    }
}
