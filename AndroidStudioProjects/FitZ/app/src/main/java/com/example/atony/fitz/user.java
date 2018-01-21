package com.example.atony.fitz;

import android.widget.Button;

/**
 * Created by atony on 1/21/2018.
 */

public class user {
    private String username;
    private boolean following;
    private boolean follower;
    private String email;
    private Button buttonFollow;

    public user(){

    }

    public user(String user, String em){
        username = user;
        following = false;
        follower = false;
        email = em;
    }
    public String getUsername(){return username;}
    public boolean getFollowing(){return following;}
    public boolean getFollower(){return follower;}
    public String getFollowerToString(){return Boolean.toString(this.follower);}
    public void followingInverse(){this.following = !this.following;}
    public void followerInverse(){this.follower = !this.follower;}
    public String getEmail(){return email;}




}
