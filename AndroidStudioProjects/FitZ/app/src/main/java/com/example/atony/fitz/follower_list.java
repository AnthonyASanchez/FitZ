package com.example.atony.fitz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class follower_list extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private FirebaseListAdapter<user> adapter;

    private Button buttonWorkout;
    private Button buttonHomePage;
    private Button buttonFollow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower_list);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, log_in.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        buttonFollow = (Button) findViewById(R.id.buttonCreateFollowers);
        buttonHomePage = (Button) findViewById(R.id.buttonFollowersHome);
        buttonWorkout = (Button) findViewById(R.id.buttonFollowersWorkout);

        buttonHomePage.setOnClickListener(this);
        buttonWorkout.setOnClickListener(this);
        buttonFollow.setOnClickListener(this);

        displayFollowers();
    }


    public String getUserRef(){
        String s = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        s = s.replace("@","");
        s = s.replace(".","");
        return s;
    }
    private void addFollowers(){
        DatabaseReference followers = FirebaseDatabase.getInstance().getReference().child(getUserRef()).child("Followers");
        user u = new user("AwesomeDude","agmailcom");
        user u2 = new user("CrazyFitnessMonster","aowgmailcom");
        followers.push().setValue(u);
        followers.push().setValue(u2);

    }

    private void displayFollowers(){
        ListView listFollowers = (ListView) findViewById(R.id.listViewFollowerList);
        listFollowers.setClickable(true);
        Log.i("Created a list","Created a list");
        adapter = new FirebaseListAdapter<user>(this,user.class,R.layout.activity_user,
                FirebaseDatabase.getInstance().getReference().child(getUserRef()).child("Followers")){
            @Override
            protected void populateView(View v, final user model, int position) {
                Log.i("Created a list","Populating view");
                TextView followerUsername = (TextView) v.findViewById(R.id.textViewUserUsername);
                Button buttonUser = (Button) v.findViewById(R.id.buttonUserFollow);
                Log.i("Created obj","Created objs");
                followerUsername.setText(model.getUsername());
                buttonUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatabaseReference followersdb = FirebaseDatabase.getInstance().getReference().child(getUserRef()).child("Followers");
                        //Log.i(postsdb.toString(),postsdb.toString());
                        followersdb.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot snap : dataSnapshot.getChildren()){
                                    user u = snap.getValue(user.class);
                                    if(u.getUsername().equals(model.getUsername())) {
                                        u.followerInverse();
                                        snap.getRef().setValue(u);
                                        Toast.makeText(follower_list.this, "Following Status: " + u.getFollowerToString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                });


            }
        };
        Log.i("Above List feed","Above list feed");
        listFollowers.setAdapter(adapter);


    }

    @Override
    public void onClick(View view) {
        if(view == buttonFollow){
            addFollowers();
        }
        if(view == buttonWorkout){
            finish();
            startActivity(new Intent(this, workout.class));
        }
        if(view == buttonHomePage){
            finish();
            startActivity(new Intent(this, homepage.class));
        }
    }
}
