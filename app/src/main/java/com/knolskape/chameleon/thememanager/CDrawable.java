package com.knolskape.chameleon.thememanager;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import java.util.Arrays;

/**
 * Created by omkar on 24/5/17.
 */

public class CDrawable extends GradientDrawable {

  public String[] gradientTypes = new String[]{
      "BL_TR",
      "BOTTOM_TOP",
      "BR_TL",
      "LEFT_RIGHT",
      "RIGHT_LEFT",
      "TL_BR",
      "TOP_BOTTOM", "TR_BL"
  };

  private float dp;

  public CDrawable(float dp){
    this.dp = dp;
  }


  public static CDrawable build(Context context){
    return new CDrawable(getPixelDensity(context));
  }

  public static float getPixelDensity(Context context){
    return context.getResources().getDisplayMetrics().density;
  }

  public CDrawable withGradientType(String type){
    int index = Arrays.asList(gradientTypes).indexOf(type);
    if(index >= 0){
      this.setOrientation(CDrawable.Orientation.valueOf(gradientTypes[index]));
    }else{

    }
    return this;
  }

  public CDrawable withColor(int color){
    this.setColor(color);
    return this;
  }

  public CDrawable withSize(int width, int height){
    int density = (int) dp;
    this.setSize(density*width, density*height);
    return this;
  }

  public CDrawable withSize(int sameWidthAndHeight){
    int density = 1;
    this.setSize(density*sameWidthAndHeight, density*sameWidthAndHeight);
    return this;
  }

  public CDrawable withColorAndAlpha(int color, float transparency){

    int alpha = Math.round(Color.alpha(color)*transparency);
    int red = Color.red(color);
    int green = Color.green(color);
    int blue = Color.blue(color);

    int colorWithAlpha = Color.argb(alpha, red, green, blue);
    this.setColor(colorWithAlpha);
    return this;
  }

  public CDrawable withColors(int[] colors){
    this.setColors(colors);
    return this;
  }

  public CDrawable withOrientation(GradientDrawable.Orientation orientation){
    this.setOrientation(orientation);
    return this;
  }

  public CDrawable withCornerRadius(float radius){
    this.setCornerRadius(radius*dp);
    return this;
  }

  public CDrawable withCornerRadii(float[] radii){

    for(int i=0;i<radii.length;i++){
      radii[i] *= dp;
    }
    this.setCornerRadii(radii);
    return this;
  }

  public CDrawable withGradientRadius(float radius){
    this.setGradientRadius(radius*dp);
    this.setGradientCenter(10,10);
    return this;
  }

  public CDrawable withStroke(int strokeWidth, int color){
    this.setStroke(strokeWidth*((int) dp), color);
    return this;
  }

  public CDrawable withStroke(int strokeWidth, int color, int dashWidth, int dashGap){
    this.setStroke(strokeWidth*((int) dp), color, dp*dashWidth, dp*dashGap);
    return this;
  }

  public CDrawable withTopRadius(float radius){
    return this.withCornerRadii(new float[]{radius, radius, radius, radius, 0,0,0,0 });
  }

  public CDrawable withLowerRadius(float radius){
    return this.withCornerRadii(new float[]{0,0,0,0,radius, radius, radius, radius});
  }

}
