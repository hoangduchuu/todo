package com.hoangduchuu.hoang.cs001;

/**
 * Created by hoang on 9/22/16.
 */

public class ItemList {
    private int id;
    private String itName;
    private String dueDate;

    public ItemList() {
    }

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

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public ItemList(int id, String itName, String dueDate) {

        this.id = id;
        this.itName = itName;
        this.dueDate = dueDate;
    }
}
