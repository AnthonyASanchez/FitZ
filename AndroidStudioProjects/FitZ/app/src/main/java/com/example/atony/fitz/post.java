package com.example.atony.fitz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class post {

    private String postInformation;
    private String time;
    private String username;

    private Button deletePostButton;

    public post(){

    }

    public post(String username, String postInformation, String time){
        username = "@"+username;
        this.username = username;
        this.time = time;
        this.postInformation = postInformation;

    }
    public String getPostInformation(){
        return postInformation;
    }
    public void setPostInformation(String pi){
        this.postInformation = pi;
    }
    public String getTime(){
        return time;
    }
    public void setTime(String time){
        this.time = time;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public Button getDeletePostButton(){
        return deletePostButton;
    }


}
