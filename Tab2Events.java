package com.example.varun.finalproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by varun on 11-06-2017.
 */

public class Tab2Events extends Fragment {


    private RecyclerView recView;
    private DerpAdapter adapter;
    private ArrayList<Event> Events;
    private String emailadmin;
    private String emailcurrent;
    private String value;

    public String getEmailcurrent () {
        return emailcurrent;
    }

    public String getemailadmin () {
        return emailadmin;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2events, container, false);
        return rootView;
    }


    public void updateadapterview () {
        adapter = new DerpAdapter(Events, getActivity(), emailadmin);
        recView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();


        recView = (RecyclerView) getView().findViewById(R.id.rec_list);
        recView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Events");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            emailcurrent = user.getEmail();
        }


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                Events = new ArrayList<Event>();

                for (DataSnapshot child: children) {
                    Event event = child.getValue(Event.class);
                    Events.add(event);
                }
                updateadapterview();
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });


        DatabaseReference myRef1 = database.getReference("Admin");
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                emailadmin = dataSnapshot.getValue(String.class);
                updateadapterview();

            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });



    }

}
