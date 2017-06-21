package com.example.varun.finalproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewTeamActivity extends AppCompatActivity {

    private TextView viewteam;
    String s = "Selected Players" + "\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_team);

        viewteam = (TextView)findViewById(R.id.textView4);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRefselectedplayers = database.getReference("Selected Players");


        myRefselectedplayers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child: children) {
                    s = s+child.getValue(String.class)+"\n";
                }

                viewteam.setText(s);
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

    }
}
