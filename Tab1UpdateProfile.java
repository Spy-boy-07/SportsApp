package com.example.varun.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by varun on 11-06-2017.
 */

public class Tab1UpdateProfile extends Fragment {


    private Button mButtonProfile;
    private ImageView mProfileImageView;
    private TextView mName,mEmail;
    private TextView mAge;



    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1updateprofile, container, false);
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();



        //Add Emoji
        mButtonProfile = (Button) getView().findViewById(R.id.btn_home_update_profile);


       mProfileImageView = (ImageView) getView().findViewById(R.id.iv_home);
        mName= (TextView) getView().findViewById(R.id.tv_home);
        mEmail = (TextView) getView().findViewById(R.id.tv_home_email);


       /* emojiconEditText = (EmojiconEditText) getView().findViewById(R.id.emojicon_edit_text);
        emojIconActions = new EmojIconActions(getActivity(), activity_chat, emojiButton, emojiconEditText);
        emojIconActions.ShowEmojicon(); */



        mButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ProfileActivity.class));
            }
        });


 mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            Glide.with(this)
                    .load(firebaseUser.getPhotoUrl())
                    .into(mProfileImageView);
            mName.setText("Player Name: " + firebaseUser.getDisplayName());
            mEmail.setText("Email: " + firebaseUser.getEmail());
        }

    }


}

