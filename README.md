# Project Chameleon

-----

## Introduction

Chameleon is a CSS like framework for Android. Chameleon can read styles in JSON format and apply them on views in Android. This way you can skip all the painful steps involved in creating drawables etc.. As of now, Chamelon is limited to only changing the appearence of the views and not the positioning. 



![](blob:chrome-extension://cdneggiaafcmelgcaiihmhiagieomgfj/0a8917c3-dd80-46cc-a801-6c1da4274eb3)


## Steps

### Inside your Activity

```
@Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
		
    ThemeManager.build(this).applyTheme((ViewGroup) findViewById(android.R.id.content));
		
 }
```
### Inside Fragment
```
@Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    ViewGroup rootView = (ViewGroup) inflater.inflate(
        R.layout.fragment, container, false);
				
    ThemeManager.build(getActivity())..applyTheme(rootView);
		
    return rootView;
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

