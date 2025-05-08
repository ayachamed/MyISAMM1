package com.example.myisamm.model;

public class CategoryItem {
    private String id;
    private String name;

    public CategoryItem() {
        // Default constructor required for calls to DataSnapshot.getValue(CategoryItem.class)
    }

    public CategoryItem(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}