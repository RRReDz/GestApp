package mcteamgestapp.momo.com.mcteamgestapp;

import android.app.Application;

import com.beardedhen.androidbootstrap.TypefaceProvider;


import mcteamgestapp.momo.com.mcteamgestapp.Models.UserInfo;

/**
 * Created by YassIne on 09/11/2015.
 */
public class MyApp extends Application {

    //The user who is actually connected/Who is successfully logged in
    private UserInfo mCurrentUser;

    public void onCreate() {
        super.onCreate();
        mCurrentUser = null;
        TypefaceProvider.registerDefaultIconSets();
    }

    public void setCurrentUser(UserInfo user) {
        mCurrentUser = user;
    }

    public UserInfo getCurrentUser() {
        return this.mCurrentUser;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
