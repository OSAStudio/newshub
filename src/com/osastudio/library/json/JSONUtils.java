package com.osastudio.library.json;

import org.json.JSONException;
import org.json.JSONObject;


public class JSONUtils {

   public static final int DEFAULT_INDENT_SPACES = 2;
   
   
   public static String toJSONString(JSONInterface jsonInterface) {
      JSONObject jsonObject = jsonInterface.toJSONObject();
      if (jsonObject != null) {
         return jsonObject.toString();
      }
      return null;
   }
   
   public static String toJSONString(JSONInterface jsonInterface, int indentSpaces) {
      JSONObject jsonObject = jsonInterface.toJSONObject();
      if (jsonObject != null) {
         try {
            return jsonObject.toString(indentSpaces);
         } catch (JSONException e) {
         }
      }
      return null;
   }
   
}
