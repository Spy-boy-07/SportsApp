package com.example.varun.finalproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MakeTeamActivity extends AppCompatActivity {

    private TextView availableplayers;
    private EditText enterplayer;
    private Button selectplayer;
    private String s="Available Players"+"\n";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_team);

        availableplayers = (TextView)findViewById(R.id.textView4);
        enterplayer = (EditText)findViewById(R.id.editText);
        selectplayer = (Button) findViewById(R.id.select);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference myRefselectedplayers = database.getReference("Selected Players");

        DatabaseReference myRefavailableplayers = database.getReference("Availability");



        myRefavailableplayers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child: children) {
                    s = s+child.getValue(String.class)+"\n";
                }

                availableplayers.setText(s);
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        selectplayer.setOnClickListener(new View.OnClickListener() {
                                         public void onClick(View v) {
                                             myRefselectedplayers.push().setValue(enterplayer.getText().toString());
                                         }
                                     }
        );





    }
}
