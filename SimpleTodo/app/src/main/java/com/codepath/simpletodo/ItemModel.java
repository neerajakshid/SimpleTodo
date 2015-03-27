package com.codepath.simpletodo;

public class ItemModel {
    public String itemBody, priority;
    public int id;

    public ItemModel( String itemBody, String priority) {
        super();
        this.itemBody = itemBody;
        this.priority = priority;
    }

    public ItemModel( int id, String itemBody, String priority) {
        super();
        this.id = id;
        this.itemBody = itemBody;
        this.priority = priority;
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
