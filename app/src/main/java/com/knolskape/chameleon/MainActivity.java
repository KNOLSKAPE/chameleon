package com.knolskape.chameleon;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        themeManager.applyTheme((ViewGroup) findViewById(android.R.id.content));
      }

      @Override public Context context() {
        return MainActivity.this;
      }
    };




    ThemeManagerBuilder
        .builder()
        .withListener(listener)
        .withUrl("https://knolskape.s3.amazonaws.com/MLS/ktm1/1495780632_sample_theme_1.json")
        .withAsset("sample_theme_1.json")
        .build();
  }




}
