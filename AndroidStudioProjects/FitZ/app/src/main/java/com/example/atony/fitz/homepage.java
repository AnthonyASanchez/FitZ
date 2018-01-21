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


import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class homepage extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private FirebaseListAdapter<post> adapter;
    private DatabaseReference userPostsReference;
    private DatabaseReference userFeedReference;
    private DatabaseReference userFollowers;
    private Button buttonPost;
    private Button buttonWorkout;
    private Button buttonFollowers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this,log_in.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();
        userFollowers = FirebaseDatabase.getInstance().getReference().child(getUserRef()).child("Followers");

        buttonPost = (Button)findViewById(R.id.buttonHomePageMakePost);
        buttonPost.setOnClickListener(this);

        buttonFollowers = (Button) findViewById(R.id.buttonHomePageFollowers);
        buttonWorkout = (Button) findViewById(R.id.buttonHomePageWorkout);

        buttonWorkout.setOnClickListener(this);
        buttonFollowers.setOnClickListener(this);

        displayHomePost();
        //displayHomeFeed();
    }
    public void displayHomeFeed(){
        ListView listFeed = (ListView) findViewById(R.id.listViewFeed);
        listFeed.setClickable(true);
        Log.i("Created a list","Created a list");
        adapter = new FirebaseListAdapter<post>(this,post.class,R.layout.activity_post,
                FirebaseDatabase.getInstance().getReference().child(getUserRef()).child("Feed")){
            @Override
            protected void populateView(View v, final post model, final int position) {
                Log.i("Created a list","Populating view");
                TextView postUsername = (TextView) v.findViewById(R.id.textViewPostUsername);
                TextView postInfo = (TextView) v.findViewById(R.id.textViewPostInfo);
                TextView postTime = (TextView) v.findViewById(R.id.textViewPostTime);
                Button deleteButton = (Button) v.findViewById(R.id.buttonPostDelete);

                postUsername.setText(model.getUsername());
                postInfo.setText(model.getPostInformation());
                postTime.setText(model.getTime());
                DatabaseReference fbDB = FirebaseDatabase.getInstance().getReference();

                fbDB.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snap : dataSnapshot.child(getUserRef()).child("Followers").getChildren()){
                            user u = snap.getValue(user.class);
                            if(u.getFollower()){
                                DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(u.getEmail()).child("Posts");
                                db.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for(DataSnapshot snap: dataSnapshot.getChildren()){
                                            post p = snap.getValue(post.class);
                                            DatabaseReference feed = FirebaseDatabase.getInstance().getReference().child(getUserRef()).child("Feed");
                                            feed.push().setValue(p);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

               /** deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatabaseReference postsdb = FirebaseDatabase.getInstance().getReference().child(getUserRef()).child("Posts");
                        //Log.i(postsdb.toString(),postsdb.toString());
                        postsdb.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot snap : dataSnapshot.getChildren()){
                                    post p = snap.getValue(post.class);
                                    if(p.getPostInformation().equals(model.getPostInformation())){
                                        snap.getRef().removeValue();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                });**/

            }
        };
        Log.i("Above List feed","Above list feed");
        listFeed.setAdapter(adapter);
    }
    public void displayHomePost(){
        ListView listFeed = (ListView) findViewById(R.id.listViewFeed);
        listFeed.setClickable(true);
        Log.i("Created a list","Created a list");
        adapter = new FirebaseListAdapter<post>(this,post.class,R.layout.activity_post,
                FirebaseDatabase.getInstance().getReference().child(getUserRef()).child("Posts")){
            @Override
            protected void populateView(View v,final post model, int position) {
                Log.i("Created a list","Populating view");
                TextView postUsername = (TextView) v.findViewById(R.id.textViewPostUsername);
                TextView postInfo = (TextView) v.findViewById(R.id.textViewPostInfo);
                TextView postTime = (TextView) v.findViewById(R.id.textViewPostTime);
                Button deleteButton = (Button) v.findViewById(R.id.buttonPostDelete);

                postUsername.setText(model.getUsername());
                postInfo.setText(model.getPostInformation());
                postTime.setText(model.getTime());


                 deleteButton.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         DatabaseReference postsdb = FirebaseDatabase.getInstance().getReference().child(getUserRef()).child("Posts");
                         //Log.i(postsdb.toString(),postsdb.toString());
                         postsdb.addListenerForSingleValueEvent(new ValueEventListener() {
                             @Override
                             public void onDataChange(DataSnapshot dataSnapshot) {
                                 for(DataSnapshot snap : dataSnapshot.getChildren()){
                                     post p = snap.getValue(post.class);
                                     if(p.getPostInformation().equals(model.getPostInformation())){
                                        snap.getRef().removeValue();
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
        listFeed.setAdapter(adapter);

    }
    public String getUserRef(){
        String s = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        s = s.replace("@","");
        s = s.replace(".","");
        return s;
    }
    private void makePost(){
        String acctemail = getUserRef();
        DatabaseReference posts = FirebaseDatabase.getInstance().getReference().child(acctemail).child("Posts");
        post p = new post("@AwesomeDude","Man that was such a  fun run I can't believe it went by so quickly, i hope to see you guys next time","1:22");
        post p2 = new post("@CrazyBro","Squating too hard and hurt myself, have to remind myself im only human.","0:22");
        post p3 = new post("@WowMan","Had a bro sesh with my boys at the gym, we benched pressed and curled, took a good three hours just like the pros","2:45");
        posts.push().setValue(p);
        posts.push().setValue(p2);
        posts.push().setValue(p3);

    }

    @Override
    public void onClick(View view) {
        if(view == buttonPost){
            //finish();
            //startActivity(new Intent(this, workout.class));
            makePost();
        }
        if(view == buttonWorkout){

            finish();
            startActivity(new Intent(this, workout.class));
        }
        if(view == buttonFollowers){
            finish();
            startActivity(new Intent(this, follower_list.class));
        }
    }
}
