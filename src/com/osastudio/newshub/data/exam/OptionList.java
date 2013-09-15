package com.osastudio.newshub.data.exam;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.base.NewsItemList;

public class OptionList extends NewsItemList<Option> {

   public static final String JSON_KEY_LIST = "option_list";

   public OptionList(JSONObject jsonObject) {
      try {
         if (!jsonObject.isNull(JSON_KEY_LIST)) {
            JSONArray jsonArray = jsonObject.getJSONArray(JSON_KEY_LIST);
            Option opt = null;
            for (int i = 0; i < jsonArray.length(); i++) {
               try {
                  if (!jsonArray.isNull(i)) {
                     opt = new Option(jsonArray.getJSONObject(i));
                     opt.setOrder(i + 1);
                     opt.setTotalCount(jsonArray.length());
                     this.list.add(opt);
                  }
               } catch (JSONException e) {
                  continue;
               }
            }
         }
      } catch (JSONException e) {

      }
   }

}
