package com.example.atony.fitz;

/**
 * Created by atony on 1/20/2018.
 */

public class account {

    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String age;
    private String weight;
    private String height;

    public account(String em, String user, String fn, String ln, String a, String w, String h){
        email = em;
        username = user;
        firstName = fn;
        lastName = ln;
        age = a;
        weight = w;
        height = h;
    }

    public String getEmail(){return email;}
    public void setEmail(String em){email = em;};

    public String getUsername(){return username;};
    public void setUsername(String user){username = user;}

    public String getFirstName(){return firstName;}
    public void setFirstName(String fn){firstName = fn;}

    public String getLastName(){return lastName;}
    public void setLastName(String ln){lastName = ln;}

    public String getAge(){return age;}
    public void setAge(String a){age = a;}

    public String getWeight(){return weight;}
    public void setWeight(String w){weight = w;}

    public String getHeight(){return height;}
    public void setHeight(String h){height = h;}


}
