package com.osastudio.newshub.widgets;

import com.huadi.azker_phone.R;
import com.osastudio.newshub.data.exam.ExamIntro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class ExamIntroView extends FrameLayout {

   private Context context;
   private ViewGroup layout;
   private TextView introTitleView, introContentView;
   private TextView howTitleView, howContentView;
   private TextView timeTitleView, timeContentView;
   private ExamIntro examIntro;

   public ExamIntroView(Context context) {
      super(context);
      this.context = context;

      LayoutInflater inflater = LayoutInflater.from(context);
      this.layout = (ViewGroup) inflater.inflate(R.layout.ex_intro, this, true);
      findViews();
   }

   private void findViews() {
      this.introTitleView = (TextView) this.layout
            .findViewById(R.id.intro_title);
      this.introContentView = (TextView) this.layout
            .findViewById(R.id.intro_content);
      this.howTitleView = (TextView) this.layout.findViewById(R.id.how_title);
      this.howContentView = (TextView) this.layout
            .findViewById(R.id.how_content);
      this.timeTitleView = (TextView) this.layout.findViewById(R.id.time_title);
      this.timeContentView = (TextView) this.layout
            .findViewById(R.id.time_content);
   }

   public void setContent(ExamIntro intro) {
      this.examIntro = intro;
      if (this.introTitleView != null) {
         this.introTitleView
               .setText(intro != null ? intro.getIntroTitle() : "");
      }
      if (this.introContentView != null) {
         this.introContentView.setText(intro != null ? intro.getIntroContent()
               : "");
      }
      if (this.howTitleView != null) {
         this.howTitleView.setText(intro != null ? intro.getHowTitle() : "");
      }
      if (this.howContentView != null) {
         this.howContentView
               .setText(intro != null ? intro.getHowContent() : "");
      }
      if (this.timeTitleView != null) {
         this.timeTitleView.setText(intro != null ? intro.getTimeTitle() : "");
      }
      if (this.timeContentView != null) {
         this.timeContentView.setText(intro != null ? intro.getTimeContent()
               : "");
      }
   }

   public TextView getIntroTitleView() {
      return this.introTitleView;
   }

   public TextView getIntroContentView() {
      return this.introContentView;
   }

   public TextView getHowTitleView() {
      return this.howTitleView;
   }

   public TextView getHowContentView() {
      return this.howContentView;
   }

   public TextView getTimeTitleView() {
      return this.timeTitleView;
   }

   public TextView getTimeContentView() {
      return this.timeContentView;
   }

   public ExamIntro getExamIntro() {
      return this.examIntro;
   }

}
