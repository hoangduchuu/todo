package com.hoangduchuu.hoang.cs001;

/**
 * Created by hoang on 9/22/16.
 */

public class ItemList {

    private int id;
    private String itName;
    private String taskNote;
    private String dueDate;
    private int priority;
    private int status;

    public ItemList(int id, String itName, String taskNote, String dueDate, int priority, int status) {
        this.id = id;
        this.itName = itName;
        this.taskNote = taskNote;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = status;
    }

    public ItemList(int anInt, String string, String string1, int anInt1, int anInt2) {

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

    public String getTaskNote() {
        return taskNote;
    }

    public void setTaskNote(String taskNote) {
        this.taskNote = taskNote;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
