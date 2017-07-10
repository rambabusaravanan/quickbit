package com.rambabusaravanan.quickbit;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rambabusaravanan.quickbit.adapter.BitsAdapter;
import com.rambabusaravanan.quickbit.databinding.ActivityMainBinding;
import com.rambabusaravanan.quickbit.model.Bit;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity {

    private DatabaseReference reference;
    private BitsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);


        // Recycler View

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        binding.recyclerView.addItemDecoration(dividerItemDecoration);

        adapter = new BitsAdapter();
        binding.recyclerView.setAdapter(adapter);

        // FireBase

        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        final long now = Calendar.getInstance().getTime().getTime();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        reference = database.getReference("bits");
        reference.keepSynced(true);

        reference.orderByChild("due").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Bit> bits = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Bit bit = child.getValue(Bit.class);

                    try {
                        System.out.println(bit.due);
                        Date due = df.parse(bit.due);
                        if (now > due.getTime())
                            bits.add(bit);
                    } catch (Exception e) {
                        Log.e("QuickBit", "Date format Invalid: " + e.getMessage());
                    }
                }
                Collections.reverse(bits);
                adapter.update(bits, true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

//    public void write() {
//        Bit bit = new Bit();
//        bit.title = "Mongo DB";
//        bit.due = new Date().toString();
//        bit.message = "This is written is C++ " + bit.due;
//
//        String key = reference.push().getKey();
//        reference.child(key).setValue(bit);
//    }
}
