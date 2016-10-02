package com.hoangduchuu.hoang.cs001;

/**
 * Created by hoang on 9/28/16.
 */

public class TaskList{
    private int id;
    private String taskName;
    private String dueDate;
    private String taskNote;
    private int priority;
    private int status;

    public TaskList(int id, String taskName, String dueDate, String taskNote, int priority, int status) {
        this.id = id;
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.taskNote = taskNote;
        this.priority = priority;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
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
