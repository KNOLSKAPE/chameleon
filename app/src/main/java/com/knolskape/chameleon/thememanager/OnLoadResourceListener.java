package com.knolskape.chameleon.thememanager;

import android.content.Context;

/**
 * Created by omkar on 26/5/17.
 */

public interface OnLoadResourceListener {
  public void onLoadFinished(ThemeManager themeManager);
  public void onFirebaseChange(ThemeManager themeManager);
}
