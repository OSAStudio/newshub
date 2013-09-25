package com.osastudio.newshub;

import java.util.LinkedList;

import android.app.Activity;

public class ActivityStack {
   
   private LinkedList<Activity> mActivities;
   
   public ActivityStack() {
      mActivities = new LinkedList<Activity>();
   }
   
   public int getCount() {
      return mActivities.size();
   }
   
   public void push(Activity activity) {
      mActivities.addFirst(activity);
   }
   
   public Activity pop() {
      if (!mActivities.isEmpty()) {
	      return mActivities.removeFirst();
      }
      return null;
   }
   
   public void remove(Activity activity) {
      if (mActivities.contains(activity)) {
         mActivities.remove(activity);
      }
   }
   
   public Activity getTop() {
      if (!mActivities.isEmpty()) {
	      return mActivities.getFirst();
      }
      return null;
   }
   
   public Activity getBottom() {
      if (!mActivities.isEmpty()) {
	      return mActivities.getLast();
      }
      return null;
   }
   
   public void finishAll() {
      while (!mActivities.isEmpty()) {
         mActivities.removeFirst().finish();
      }
   }
   
   public void cleanup() {
      finishAll();
   }
   
}
