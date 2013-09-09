package com.osastudio.library.json;

import org.json.JSONString;

public abstract class JSONHelper implements JSONString, JSONInterface {

   @Override
   public String toJSONString() {
      return JSONUtils.toJSONString(this);
   }

}
