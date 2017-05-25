package com.knolskape.chameleon.thememanager;

import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by omkar on 24/5/17.
 */

public class CTag {
  List<String> tags;
  public CTag(@Nullable List<String> tags){
    if(tags != null){
      this.tags = tags;
    }else{
      this.tags = new ArrayList<String>();
    }

  }
  public void add(String tag){
    this.tags.add(tag);
  }

  public String first(){
    return this.tags.get(0);
  }
}
