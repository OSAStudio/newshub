package com.osastudio.newshub.data;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NewsAbstractList extends NewsBaseObject {

   public static final String JSON_KEY_ABSTRACT_LIST = "list";

   private List<NewsAbstract> abstractList;

   public NewsAbstractList() {
      this.abstractList = new ArrayList<NewsAbstract>();
   }

   public List<NewsAbstract> getAbstractList() {
      return this.abstractList;
   }

   public NewsAbstractList setAbstractList(List<NewsAbstract> list) {
      this.abstractList = list;
      return this;
   }

   public static NewsAbstractList parseJsonObject(JSONObject jsonObject) {
      NewsAbstractList result = null;
      try {
         if (jsonObject.isNull(JSON_KEY_RESULT_CODE)
               || NewsAbstractList.isResultFailure(jsonObject
                     .getString(JSON_KEY_RESULT_CODE))) {
            return null;
         }

         result = new NewsAbstractList();
         if (!jsonObject.isNull(JSON_KEY_ABSTRACT_LIST)) {
            JSONArray jsonArray = jsonObject
                  .getJSONArray(JSON_KEY_ABSTRACT_LIST);
            for (int i = 0; i < jsonArray.length(); i++) {
               try {
                  if (!jsonArray.isNull(i)) {
                     result.getAbstractList().add(
                           NewsAbstract.parseJsonObject(jsonArray
                                 .getJSONObject(i)));
                  }
               } catch (JSONException e) {
                  continue;
               }
            }
         }
      } catch (JSONException e) {

      }

      return result;
   }

}
