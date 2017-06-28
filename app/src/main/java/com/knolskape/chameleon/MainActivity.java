package com.knolskape.chameleon;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import com.knolskape.chameleon.thememanager.OnLoadResourceListener;
import com.knolskape.chameleon.thememanager.ThemeManager;
import com.knolskape.chameleon.thememanager.ThemeManagerBuilder;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);


    OnLoadResourceListener listener = new OnLoadResourceListener() {
      @Override public void onLoadFinished(ThemeManager themeManager) {
        themeManager.applyTheme((ViewGroup) findViewById(android.R.id.content), MainActivity.this);
      }

      @Override public void onFirebaseChange(ThemeManager themeManager) {
        Log.d("UPDATE", "firebase change event");
        themeManager.applyTheme((ViewGroup) findViewById(android.R.id.content), MainActivity.this);
      }
    };




    ThemeManagerBuilder builder = ThemeManagerBuilder
        .getInstance();

        builder.addListener(listener);
  }




}
