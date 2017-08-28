package com.knolskape.chameleon;

import android.app.Application;
import android.content.Context;
import com.knolskape.chameleon.thememanager.ThemeManagerBuilder;

/**
 * Created by omkar on 9/6/17.
 */

public class MainApplication extends Application {

  private static Context context;

  @Override public void onCreate() {
    super.onCreate();
    MainApplication.context = getApplicationContext();

    ThemeManagerBuilder builder = ThemeManagerBuilder
        .getInstance()
        .withUrl("https://knolskape.s3.amazonaws.com/MLS/ktm1/1495780632_sample_theme_1.json")
        .withAsset("sample_theme_1.json", context)
        .withFirebase("org1");

  }
}
