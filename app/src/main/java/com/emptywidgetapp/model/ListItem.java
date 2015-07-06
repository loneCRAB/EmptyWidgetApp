package com.emptywidgetapp.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "ListItems")
public class ListItem extends Model {

    @Column(name = "title")
    public String title;
    @Column(name = "message")
    public String message;
    @Column(name = "color")
    public int color;
    @Column(name = "category")
    public Category category;

    public ListItem() {
        super();
    }

    public ListItem(String title, String message, Category category) {
        this();
        this.title = title;
        this.message = message;
        this.category = category;
    }

    public ListItem(String title, String message, Category category, int color) {
        this(title, message, category);
        this.color = color;
    }

}
