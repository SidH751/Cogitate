package com.my.cogitateapp;

public class User {
    String userName;
    String emailId;
    public User(){
    }

    public void setUserName(String userName){
        this.userName = userName;
    }
    public void setEmailId(String emailId){
        this.emailId = emailId;
    }


    public String getUserName(){
        return userName;
    }
    public String getEmailId(){
        return emailId;
    }
}
