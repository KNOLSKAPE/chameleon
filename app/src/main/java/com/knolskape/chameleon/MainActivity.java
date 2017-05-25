package com.knolskape.chameleon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import com.knolskape.chameleon.thememanager.ThemeManager;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ThemeManager.build(this).applyTheme((ViewGroup) findViewById(android.R.id.content));
  }
}
