package com.rambabusaravanan.quickbit.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by andro on 9/7/17.
 */

@IgnoreExtraProperties
public class Bit {
    public String title;
    public String message;
    public String icon;
    public String due;

    public Bit() {
        this.title = "";
        this.message = "";
        this.icon = "";
        this.due = "";
    }

    @Override
    public String toString() {
        return title;
    }
}
