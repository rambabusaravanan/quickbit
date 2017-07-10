package com.rambabusaravanan.quickbit.app;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by andro on 10/7/17.
 */

public class QuickBit extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
