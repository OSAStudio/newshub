package com.osastudio.newshub;

import com.huadi.azker_phone.R;
import com.osastudio.newshub.utils.Utils;

import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends NewsBaseActivity {

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      setContentView(R.layout.activity_about);

      TextView titleView = (TextView) findViewById(R.id.about_title);
      titleView.setText(getString(R.string.about_title,
            Utils.getVersionName(this)));
   }

}
