package com.osastudio.newshub.cache;

import android.content.Context;

import com.osastudio.newshub.data.base.NewsBaseAbstract;

public class NewsBaseAbstractCache<T extends NewsBaseAbstract> extends
      NewsListCache<T> {

   public NewsBaseAbstractCache(Context context) {
      super(context);
   }

}
