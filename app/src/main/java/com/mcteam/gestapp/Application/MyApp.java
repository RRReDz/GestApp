package com.mcteam.gestapp.Application;

import android.app.Application;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.crashlytics.android.Crashlytics;
import com.mcteam.gestapp.Models.UserInfo;
import com.mcteam.gestapp.R;

import io.fabric.sdk.android.Fabric;

/**
 * Created by YassIne on 09/11/2015.
 */
public class MyApp extends Application {

    /*You should know that the application context itself gets destroyed if left unused for a long time in the background.
    So there is no guarantee that your static and singleton objects will not be cleared when the app is in background.
    Instead what you can do is persist your objects from time to time (either in a flat-file or shared preference or database)
    and restore them in the onCreate method of the Application class*/

    //The user who is actually connected/Who is successfully logged in
    private UserInfo mCurrentUser;

    public void onCreate() {
        super.onCreate();
        if(this.getString(R.string.debug_flag).equals("false"))
            Fabric.with(this, new Crashlytics());
        mCurrentUser = null;
        TypefaceProvider.registerDefaultIconSets();
    }

    public void setCurrentUser(UserInfo user) {
        mCurrentUser = user;
        if(this.getString(R.string.debug_flag).equals("false"))
            logUser(user);
    }

    public UserInfo getCurrentUser() {
        return this.mCurrentUser;
    }

    private void logUser(UserInfo user) {
        try {
            Crashlytics.setUserIdentifier(String.valueOf(user.getID()));
            Crashlytics.setUserEmail(String.valueOf(user.getEmail()));
            Crashlytics.setUserName(String.valueOf(user.getCognome())); //Cognome invece di soprannome
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
