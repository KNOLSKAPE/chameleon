package com.knolskape.chameleon.thememanager;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.ViewGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by omkar on 26/5/17.
 */

public class ThemeManagerBuilder{

  JsonObject rulesJson;
  int numOfRequestsPending = 0;
  boolean isBuildInvocted = false;
  OnLoadResourceListener listener;
  List<OnLoadResourceListener> listenerList = new ArrayList<OnLoadResourceListener>();
  ThemeManager manager;
  FirebaseDatabase db;
  DatabaseReference dbRef;

  private static ThemeManagerBuilder builder;

  public static ThemeManagerBuilder builder(){
    return new ThemeManagerBuilder();
  }

  public static ThemeManagerBuilder getInstance(){
    if(builder == null){
      builder = new ThemeManagerBuilder();
    }
    return builder;
  }


  public ThemeManagerBuilder withAsset(String fileName, Context context){
    Gson gson = new Gson();
    try{
      InputStream is = context.getAssets().open(fileName);
      int size = is.available();
      byte[] buffer = new byte[size];
      is.read(buffer);
      is.close();
      String jsonString = new String(buffer, "UTF-8");
      JsonObject newJsonElement = gson.fromJson(jsonString, JsonElement.class).getAsJsonObject();

      if(rulesJson == null){
        rulesJson = newJsonElement;
      }else{
        rulesJson = mergeJson(new JsonObject[]{rulesJson, newJsonElement});
      }
    }catch (IOException e){
      Log.e("Error in reading file", e.getMessage(), e);
    }

    return this;
  }

  public void addListener(OnLoadResourceListener listener){
    if(listenerList.isEmpty()){
      if(manager == null){
        manager = new ThemeManager(rulesJson);
      }
      listener.onLoadFinished(manager);
      //TODO check
      this.listenerList.add(listener);
    }else{
      this.listenerList.add(listener);
    }

  }

  public ThemeManagerBuilder withUrl(String url){
    numOfRequestsPending++;
    LoadStyleTask task = new LoadStyleTask();
    task.execute(new String[]{url});
    return this;
  }

  public ThemeManagerBuilder withFirebase(String url){

    db = FirebaseDatabase.getInstance();
    dbRef = db.getReference(url);

    dbRef.addValueEventListener(new ValueEventListener() {
      @Override public void onDataChange(DataSnapshot dataSnapshot) {

        String jsonString = new Gson().toJson(dataSnapshot.getValue());
        Gson gson = new GsonBuilder().setLenient().create();

        JsonReader reader = new JsonReader(new StringReader(jsonString.trim()));
        reader.setLenient(true);

        JsonObject newJsonObject = gson.fromJson(reader, JsonElement.class);
        if(rulesJson == null){
          rulesJson = newJsonObject;
        }else{
          rulesJson = mergeJson(new JsonObject[]{rulesJson, newJsonObject});
        }
        for(OnLoadResourceListener listener: listenerList){
          listener.onFirebaseChange(new ThemeManager(rulesJson));
        }
      }

      @Override public void onCancelled(DatabaseError databaseError) {

      }
    });



    return this;
  }


  private class LoadStyleTask extends AsyncTask<String, Void, String> {

    @Override protected String doInBackground(String... strings) {
      OkHttpClient client = new OkHttpClient();
      Request request =  new Request.Builder().url(strings[0]).build();
      try {
        Response response = client.newCall(request).execute();
        if(response.isSuccessful()){
          return response.body().string();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      return null;
    }

    @Override protected void onPostExecute(String s) {
      numOfRequestsPending--;
      Gson gson = new Gson();
      JsonObject newJsonObject = gson.fromJson(s, JsonElement.class).getAsJsonObject();
      if(rulesJson == null){
        rulesJson = newJsonObject;
      }else{
        rulesJson = mergeJson(new JsonObject[]{rulesJson, newJsonObject});
      }
      if(numOfRequestsPending == 0 ){
        if(manager == null){
          manager = new ThemeManager(rulesJson);
        }

        for(OnLoadResourceListener listenerItem: listenerList){
          listenerItem.onLoadFinished(manager);
        }
        //listenerList.clear();
      }
    }
  }



  public static JsonObject mergeJson(JsonObject[] jsonObjects){
    JsonObject finalObject = new JsonObject();

    for(JsonObject jsonObject: jsonObjects){
      for(Map.Entry<String, JsonElement> entry : jsonObject.entrySet()){
        if(!(finalObject.get(entry.getKey()) instanceof JsonNull)){
          finalObject.remove(entry.getKey());
        }
        finalObject.add(entry.getKey(), entry.getValue());
      }
    }

    return finalObject;
  }


}
