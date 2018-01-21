package com.example.atony.fitz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class posting extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextPostingInfo;

    private TextView textViewPostingTime;

    private Button buttonPostingCancel;
    private Button buttonPostingPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting);

        Bundle b = getIntent().getExtras();
        String time = b.getString("time");
        Log.i(time,time);

        editTextPostingInfo = (EditText) findViewById(R.id.editTextPostingInfo);
        textViewPostingTime = (TextView) findViewById(R.id.textViewPostingTime);

        textViewPostingTime.setText(time);

        buttonPostingCancel = (Button) findViewById(R.id.buttonPostingCancel);
        buttonPostingPost = (Button) findViewById(R.id.buttonPostingPost);

        buttonPostingPost.setOnClickListener(this);
        buttonPostingCancel.setOnClickListener(this);


    }

    private void post(){
        String a = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        a = a.replace("@","");
        final String s = a.replace(".","");
        DatabaseReference username = FirebaseDatabase.getInstance().getReference().child(s).child("Account").child("username");
        username.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String email = dataSnapshot.getValue(String.class);
                post p = new post(email,editTextPostingInfo.getText().toString(),textViewPostingTime.getText().toString());

                DatabaseReference posts = FirebaseDatabase.getInstance().getReference().child(s).child("Posts");
                posts.push().setValue(p);

                DatabaseReference feed = FirebaseDatabase.getInstance().getReference().child(s).child("Feed");
                feed.push().setValue(p);




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        finish();
        startActivity(new Intent(this,homepage.class));

    }

    @Override
    public void onClick(View view) {
        if(view == buttonPostingCancel){
            finish();
            startActivity(new Intent(this, homepage.class));
        }
        if(view == buttonPostingPost){
            post();
        }
    }
}
