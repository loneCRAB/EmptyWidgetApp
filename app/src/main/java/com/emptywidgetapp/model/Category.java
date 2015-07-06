package com.emptywidgetapp.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "Categories")
public class Category extends Model {

    @Column(name = "Name", unique = true, onUniqueConflict = Column.ConflictAction.IGNORE)
    public String name;

    public Category(){
        super();
    }

    public Category(String name) {
        this();
        this.name = name;
        save();
    }

    public static List<Category> getAllCategories(){
        return new Select()
                .all()
                .from(Category.class)
                .execute();
    }

    public static List<ListItem> getAllItems() {
        return new Select()
                .all()
                .from(ListItem.class)
                .execute();
    }

    public static List<ListItem> getAllItems(int id) {
        return new Select()
                .from(ListItem.class)
                .where("Category = ?", id)
                .orderBy("id ASC")
                .execute();
    }

    @Override
    public String toString(){
        return "Category name: " + name;
    }

}