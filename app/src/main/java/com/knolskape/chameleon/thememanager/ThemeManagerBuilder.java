package com.knolskape.chameleon.thememanager;

import android.os.AsyncTask;
import android.util.Log;
import android.view.ViewGroup;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by omkar on 26/5/17.
 */

public class ThemeManagerBuilder{

  JsonElement rulesJson;
  int numOfRequestsPending = 0;
  boolean isBuildInvocted = false;
  OnLoadResourceListener listener;

  public ThemeManagerBuilder(OnLoadResourceListener listener){
    this.listener = listener;
  }

  public static ThemeManagerBuilder builder(OnLoadResourceListener listener){
    return new ThemeManagerBuilder(listener);
  }
  public ThemeManagerBuilder withAsset(String fileName){
    Gson gson = new Gson();
    try{
      InputStream is = listener.context().getAssets().open(fileName);
      int size = is.available();
      byte[] buffer = new byte[size];
      is.read(buffer);
      is.close();
      String jsonString = new String(buffer, "UTF-8");
      JsonElement newJsonElement = gson.fromJson(jsonString, JsonElement.class);

      if(rulesJson == null){
        rulesJson = newJsonElement;
      }else{
        rulesJson = mergeJson(new JsonElement[]{rulesJson, newJsonElement});
      }


    }catch (IOException e){
      Log.e("Error in reading file", e.getMessage(), e);
    }

    return this;
  }

  public ThemeManagerBuilder withUrl(String url){
    numOfRequestsPending++;
    LoadStyleTask task = new LoadStyleTask();
    task.execute(new String[]{url});
    return this;
  }

  public void build(){
    if(numOfRequestsPending > 0){
      isBuildInvocted = true;
    }else{
      Log.d("Rules while building = ", rulesJson.toString());
      listener.onLoadFinished(new ThemeManager(listener, rulesJson));
    }
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
      JsonElement newJsonElement = gson.fromJson(s, JsonElement.class);
      if(rulesJson == null){
        rulesJson = newJsonElement;
      }else{
        rulesJson = mergeJson(new JsonElement[]{rulesJson, newJsonElement});
      }
      if(numOfRequestsPending == 0 && isBuildInvocted){
        listener.onLoadFinished(new ThemeManager(listener, rulesJson));
      }
    }
  }

  public static JsonElement mergeJson(JsonElement[] jsonElements){
    Set<Map.Entry<String, JsonElement>> finalRuleSet = new HashSet<Map.Entry<String, JsonElement>>();

    for(JsonElement jsonElement: jsonElements){
      for(Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()){
        finalRuleSet.add(entry);
      }
    }

    Gson gson = new Gson();
    //return gson.fromJson(gson.toJson(finalRuleSet), JsonElement.class);
    return jsonElements[0];
  }


}
