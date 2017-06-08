# Project Chameleon

-----

## Motivation

Have you ever stumbled upon a situation where you want to show completely different themes of the same app for different sets of your app users?
If yes, you must have definitely worried about the clutter it adds in your code to maintain different drawables for different cohorts of users; this also includes updating your code for every new theme that has to be added.

Chameleon is here to save you from that hassle! 


## Introduction


Chameleon is a **CSS like framework for Android**. Chameleon can read styles in JSON format and apply them on views in Android. This way you can skip all the painful steps involved in creating drawables etc.. As of now, Chamelon is limited to only changing the appearence of the views and not the positioning. 

**Not only can you choose from the variety of styles provided by the library but also add your own styles by changing just one file.**

**The icing on the cake is, once set up, themes can be updated without pushing a new version of your app to the playstore. So, your users can see your changes on the fly.**

Also, we will be soon updating the library to enable you to save your themes' key-values in your backend and hence, not disturb your app codebase!  


-----
  


![CSS to Drawables](app/src/main/assets/chameleon.jpg)


## Steps

### Inside your Activity

```
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
           .builder(listener)
           .withUrl("https://knolskape.s3.amazonaws.com/MLS/ktm1/1495780632_sample_theme_1.json")
           .withAsset("sample_theme_1.json")
           .build();
		
 }
```

### Create your style JSON

```
{
  "sampleStyle1":{
    "borderRadius":"10",
    "borderWidth":"3",
    "borderColor":"#3F9C35",
    "bgGradientColors":"#0075B0, #3F9C35",
    "textColor":"#FFFFFF",
    "bgGradientType":"BL_TR",
    "textSize":"16"
  },
  "sampleStyle2":{
    "borderRadius":"15",
    "textColor":"#000000",
    "borderWidth":"3",
    "textSize":"16",
    "borderColor":"#0075B0"
  }
}
```


### Inside XML
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.knolskape.chameleon.MainActivity"
    android:orientation="vertical"
    >
<!-- Note how we supplied the style name from the json in the tag here-->
  <TextView
      android:layout_width="match_parent"
      android:gravity="center"
      android:layout_height="70dp"
      android:layout_margin="10dp"
      android:text="Sample Style 1"
      android:tag="sampleStyle1"
      />
</LinearLayout>

```

### WIP

* Making a gradle plugin
* Read JSON from server
* Suport drawable tint for API < 21
* Combine local and server styles
* Include multiple style files
* Exception handling