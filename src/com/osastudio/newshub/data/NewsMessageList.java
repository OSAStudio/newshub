package com.osastudio.newshub.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.base.NewsItemList;

public class NewsMessageList extends NewsItemList<NewsMessage> {

   public NewsMessageList(JSONObject jsonObject) {
      super(jsonObject);

      if (isSuccess()) {
         try {
            if (!jsonObject.isNull(JSON_KEY_LIST)) {
               JSONArray jsonArray = jsonObject.getJSONArray(JSON_KEY_LIST);
               NewsMessage msg = null;
               for (int i = 0; i < jsonArray.length(); i++) {
                  try {
                     if (!jsonArray.isNull(i)) {
                        msg = NewsMessage.parseJsonObject(jsonArray
                              .getJSONObject(i));
                        if (msg != null) {
                           this.list.add(msg);
                        }
                     }
                  } catch (JSONException e) {
                     continue;
                  }
               }
               setList(this.list);
            }
         } catch (JSONException e) {

         }
      }
      // XTEST
      NewsMessage msg = new NewsMessage();
      msg.setType(1);
      msg.setContent("通知类消息说明标题");
      msg.setId("1");
      this.list.add(msg);
      msg = new NewsMessage();
      msg.setType(5);
      msg.setContent("今日课程说明");
      msg.setId("1");
      this.list.add(msg);
      setList(this.list);
      setResultCode(NewsResult.RESULT_OK);
   }

}
