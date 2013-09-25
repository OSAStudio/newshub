package com.osastudio.newshub;

import android.content.Intent;
import android.os.Bundle;

public class EntryActivity extends NewsBaseActivity {

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      checkActivityStack();
   }

   @Override
   public void onDestroy() {
      super.onDestroy();

      deliverPackage();
   }

   private void checkActivityStack() {
      ActivityStack stack = getActivityStack();
      while (stack.getCount() > 0 && stack.getTop() != null
            && !(stack.getTop() instanceof CategoryActivity)) {
         stack.pop().finish();
      }
      finish();
   }

   private void deliverPackage() {
      Intent intent = new Intent(getApplicationContext(),
            CategoryActivity.class);
      intent.putExtras(getIntent().getExtras());
      startActivity(intent);
   }

}
