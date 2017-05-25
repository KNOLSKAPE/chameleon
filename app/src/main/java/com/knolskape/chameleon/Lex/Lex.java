package com.knolskape.chameleon.Lex;

import android.content.Context;
import android.graphics.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by omkar on 24/5/17.
 */

public class Lex {

  private static Map<String, String> defaultLookup;
  private static Lex instance;


  public Lex(){
    setupDefaults();
  }

  public static Lex getInstance(Context context){
    if(instance == null){
      instance = new Lex();
    }
    return instance;
  }


  public String getString(String key){

      String result = defaultLookup.get(key);
      if(result != null){
        return result;
      }else{
        return key;
      }
  }

  private void setupDefaults(){
    defaultLookup = new HashMap<String, String>() {};

    // colors

    defaultLookup.put("primaryColor", "#0075B0");

    defaultLookup.put("primaryGradientColorOld", "#009FDA");
    defaultLookup.put("primaryGradientColor", "#06A1D1");

    defaultLookup.put("secondaryColor", "#3F9C35");

    //defaultLookup.put("secondaryGradientColor", "#69BE28");
    defaultLookup.put("secondaryGradientColor", "#40B26D");

    defaultLookup.put("tertiaryColor", "005C84");
    defaultLookup.put("primaryColorDark", "#005C84");
    defaultLookup.put("grey", "#8E8E8E");
    defaultLookup.put("white", "#FFFFFF");
    defaultLookup.put("greyBg", "#D8D8D8");
    defaultLookup.put("quizScoreColor", "#f49038");
    defaultLookup.put("timeBonusColor", "#05a1d2");
    defaultLookup.put("challengeBonusColor", "#e06e4f");
    defaultLookup.put("totalScoreColor", "#38b07a");
    defaultLookup.put("correctColor", "#3f9c35");
    defaultLookup.put("correctColorLight", "#e1f2d4");
    defaultLookup.put("inCorrectColor", "#e9253d");
    defaultLookup.put("black", "#000000");

    defaultLookup.put("goldPrimary", "#fdbc39");
    defaultLookup.put("goldSecondary","#fcd651");
    defaultLookup.put("goldText", "#af7219");

    defaultLookup.put("silverPrimary", "#b4b9be");
    defaultLookup.put("silverSecondary", "#d8dbdd");
    defaultLookup.put("silverText", "#777b80");

    defaultLookup.put("bronzePrimary", "#a5482b");
    defaultLookup.put("bronzeSecondary", "#e5a785");
    defaultLookup.put("bronzeText", "#741705");


  }

  public int getColor(String colorString){
    return Color.parseColor(this.getString(colorString));
  }

}