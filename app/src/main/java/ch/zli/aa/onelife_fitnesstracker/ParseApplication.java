package ch.zli.aa.onelife_fitnesstracker;
/*
 * Hilfsmittel: https://www.back4app.com/docs/android/parse-android-sdk
 *
 */


import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Workout.class);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("GVedOgp9nNYDRLBQFK0j6UWxJNMi4JymZ3CWd8pE")
                .clientKey("QCj12SkVPiOG43roxDYtN8e7U47xEOpQyXyFMefU")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}


