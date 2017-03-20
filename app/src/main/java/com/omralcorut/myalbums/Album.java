package com.omralcorut.myalbums;

/**
 * Created by omral on 20.03.2017.
 */

public class Album {

    private int userId;
    private int id;
    private String title;

    public Album(int userId, int id, String title) {
        this.userId = userId;
        this.id = id;
        this.title = title;
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
