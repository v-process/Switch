package com.androidbegin.onoffzone;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;
 
public class ParseApplication extends Application {
 
    @Override
    public void onCreate() {
        super.onCreate();
 
        Parse.initialize(this, "ntOZOUf2S4MVuGQ4D7SHdJ4qxlCJa7Em8sp00hOn", "DcFDqWrpJlRPUjCVZmRaMLDw5oHQPQUprqhCN4oy");
 
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
 
}