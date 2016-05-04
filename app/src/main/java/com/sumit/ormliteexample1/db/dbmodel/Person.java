package com.sumit.ormliteexample1.db.dbmodel;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by sumit on 5/4/2016.
 */

@DatabaseTable(tableName = "person")
public class Person {
    @DatabaseField(generatedId = true)
    private Long id;
    @DatabaseField
    private String name;
    @DatabaseField
    private String comments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
