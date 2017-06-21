package com.example.varun.finalproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by varun on 11-06-2017.
 */

public class Tab4Updates extends Fragment {



    private FirebaseAuth mFirebaseAuth;

    private ListView mListView;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab4updates, container, false);
        return rootView;
    }



    @Override
    public void onStart() {
        super.onStart();


        //initializing firebase authentication object to display logged in user name
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mFirebaseAuth.getCurrentUser();




        mListView = (ListView) getView().findViewById(R.id.news_letter_list);

        //Database Reference to get the updates news

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Updates");

        FirebaseListAdapter<String> firebaseListAdapter = new FirebaseListAdapter<String>(
                getActivity(),
                String.class,
                android.R.layout.simple_list_item_1,
                databaseReference
        )

        {
            @Override
            protected void populateView(View v, String model, int position) {

                TextView textView = (TextView) v.findViewById(android.R.id.text1);
                textView.setText("\n" + model + "\n");

            }
        };

        mListView.setAdapter(firebaseListAdapter);

    }

}
