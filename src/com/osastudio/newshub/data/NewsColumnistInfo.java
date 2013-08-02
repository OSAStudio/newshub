package com.osastudio.newshub.data;

import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.base.NewsBaseObject;

public class NewsColumnistInfo extends NewsBaseObject {

   public static final String JSON_KEY_RESUME = "expert_resume";
   public static final String JSON_KEY_SUMMARY = "expert_summary";
   public static final String JSON_KEY_PORTRAIT_URL = "expert_picture";
   public static final String JSON_KEY_COLUMNIST_INFO = "list";

   private NewsColumnist newsColumnist;
   private String resume;
   private String summary;
   private String portraitUrl;

   public NewsColumnistInfo() {

   }

   public NewsColumnistInfo(JSONObject jsonObject) {
      super(jsonObject);

      if (isSuccess()) {
         try {
            JSONObject infoObject = null;
            if (!jsonObject.isNull(JSON_KEY_COLUMNIST_INFO)) {
               infoObject = jsonObject.getJSONObject(JSON_KEY_COLUMNIST_INFO);
            }
            if (infoObject == null) {
               return;
            }

            if (!infoObject.isNull(JSON_KEY_RESUME)) {
               setResume(infoObject.getString(JSON_KEY_RESUME).trim());
            }
            if (!infoObject.isNull(JSON_KEY_SUMMARY)) {
               setSummary(infoObject.getString(JSON_KEY_SUMMARY).trim());
            }
            if (!infoObject.isNull(JSON_KEY_PORTRAIT_URL)) {
               setPortraitUrl(infoObject.getString(JSON_KEY_PORTRAIT_URL));
            }
            setColumnist(NewsColumnist.parseJsonObject(infoObject));
         } catch (JSONException e) {

         }
      }
   }

   public NewsColumnist getColumnist() {
      return newsColumnist;
   }

   public NewsColumnistInfo setColumnist(NewsColumnist columnist) {
      this.newsColumnist = columnist;
      return this;
   }

   public String getIconUrl() {
      return (this.newsColumnist != null) ? this.newsColumnist.getIconUrl()
            : null;
   }

   public NewsColumnistInfo setIconUrl(String url) {
      if (this.newsColumnist == null) {
         this.newsColumnist = new NewsColumnist();
      }
      this.newsColumnist.setIconUrl(url);
      return this;
   }

   public String getId() {
      return (this.newsColumnist != null) ? this.newsColumnist.getId() : null;
   }

   public NewsColumnistInfo setId(String id) {
      if (this.newsColumnist == null) {
         this.newsColumnist = new NewsColumnist();
      }
      this.newsColumnist.setId(id);
      return this;
   }

   public String getName() {
      return (this.newsColumnist != null) ? this.newsColumnist.getName() : null;
   }

   public NewsColumnistInfo setName(String name) {
      if (this.newsColumnist == null) {
         this.newsColumnist = new NewsColumnist();
      }
      this.newsColumnist.setName(name);
      return this;
   }

   public String getOutline() {
      return (this.newsColumnist != null) ? this.newsColumnist.getName() : null;
   }

   public NewsColumnistInfo setOutline(String outline) {
      if (this.newsColumnist == null) {
         this.newsColumnist = new NewsColumnist();
      }
      this.newsColumnist.setOutline(outline);
      return this;
   }

   public int getSortOrder() {
      return (this.newsColumnist != null) ? this.newsColumnist.getSortOrder()
            : 0;
   }

   public NewsColumnistInfo setSortOrder(int order) {
      if (this.newsColumnist == null) {
         this.newsColumnist = new NewsColumnist();
      }
      this.newsColumnist.setSortOrder(order);
      return this;
   }

   public String getPortraitUrl() {
      return this.portraitUrl;
   }

   public NewsColumnistInfo setPortraitUrl(String url) {
      this.summary = url;
      return this;
   }

   public String getResume() {
      return this.resume;
   }

   public NewsColumnistInfo setResume(String resume) {
      this.resume = resume;
      return this;
   }

   public String getSummary() {
      return this.summary;
   }

   public NewsColumnistInfo setSummary(String summary) {
      this.summary = summary;
      return this;
   }

}
