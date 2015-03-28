package com.codepath.simpletodo;

import java.util.Date;

public class ItemModel {
    public String itemBody, priority;
    public int id;
    public Date dueDate;

    public ItemModel( String itemBody, String priority, Date dueDate ) {
        super();
        this.itemBody = itemBody;
        this.priority = priority;
        this.dueDate = dueDate;
    }
    public ItemModel(){
        super();
    }

    public ItemModel( int id, String itemBody, String priority, Date dueDate) {
        super();
        this.id = id;
        this.itemBody = itemBody;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }


    public String getItemBody() {
        return itemBody;
    }

    public void setItemBody(String body) {
        this.itemBody = body;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
