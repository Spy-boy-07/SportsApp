package com.example.varun.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SingleEventActivityAdmin extends AppCompatActivity {


    private TextView mDate;
    private TextView mDay;
    private TextView mTime;
    private TextView mLocation;
    private TextView mTitle;
    private RadioGroup mrg;
    private RadioButton myes;
    private RadioButton mno;
    private Button mSendavailability;
    private int mselectedoptionID;
    private RadioButton mselectedradiobutton;
    private String moption;
    private Button mnavigation;
    private String Location;
    private Button mmaketeam;
    private String userc;
    private String Title;
    private String availablepath;
    private Button mviewteam;

    private String EventNo;

    FirebaseDatabase database = FirebaseDatabase.getInstance();


    DatabaseReference myRefavailableplayers = database.getReference("Availability");

    public void updateavailability (String s) {

        if (s.equals("yes")) {
            System.out.println("******************"+availablepath+"******************");
            myRefavailableplayers.push().setValue(userc);
        }

    }

    public void getuser () {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            userc = user.getDisplayName() + " " + Title;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_event_admin);

        mDate = (TextView) findViewById(R.id.Date);
        mDay = (TextView) findViewById(R.id.Day);
        mTime = (TextView) findViewById(R.id.Time);
        mLocation = (TextView) findViewById(R.id.Location);
        mTitle = (TextView) findViewById(R.id.Title);
        mSendavailability = (Button) findViewById(R.id.Sendavailability);
        mnavigation = (Button)findViewById(R.id.Navigate);
        mmaketeam = (Button) findViewById(R.id.maketeam);
        mrg = (RadioGroup) findViewById(R.id.rg);
        mviewteam = (Button)findViewById(R.id.Viewteam);


        mDate.setText(getIntent().getStringExtra("Date"));
        mDay.setText(getIntent().getStringExtra("Day"));
        mTime.setText(getIntent().getStringExtra("Time"));
        Location = getIntent().getStringExtra("Location");
        EventNo = getIntent().getStringExtra("EventNo");
        Title = getIntent().getStringExtra("Title");
        mLocation.setText(Location);
        mTitle.setText(getIntent().getStringExtra("Title"));

        getuser();

        mSendavailability.setOnClickListener(new View.OnClickListener() {
                                                 public void onClick(View v) {
                                                     mselectedoptionID = mrg.getCheckedRadioButtonId();
                                                     mselectedradiobutton = (RadioButton) findViewById(mselectedoptionID);
                                                     moption = mselectedradiobutton.getText().toString();
                                                     System.out.println("******************"+moption+"******************");
                                                     System.out.println("******************"+Title+"******************");
                                                     updateavailability(moption);
                                                 }
                                             }
        );

        mnavigation.setOnClickListener(new View.OnClickListener() {
                                           public void onClick(View v) {
                                               Intent intent = new Intent (SingleEventActivityAdmin.this, MapsActivity.class);
                                               intent.putExtra("Location", Location);
                                               startActivity(intent);



                                           }
                                       }
        );

        mmaketeam.setOnClickListener(new View.OnClickListener() {
                                           public void onClick(View v) {
                                               Intent intent = new Intent (SingleEventActivityAdmin.this, MakeTeamActivity.class);
                                               startActivity(intent);
                                           }
                                       }
        );

        mviewteam.setOnClickListener(new View.OnClickListener() {
                                         public void onClick(View v) {
                                             Intent intent = new Intent (SingleEventActivityAdmin.this, ViewTeamActivity.class);
                                             startActivity(intent);
                                         }
                                     }
        );
    }
}
