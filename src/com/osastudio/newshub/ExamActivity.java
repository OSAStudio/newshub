package com.osastudio.newshub;

import com.huadi.azker_phone.R;
import com.osastudio.newshub.data.exam.Exam;
import com.osastudio.newshub.data.exam.ExamInfo;
import com.osastudio.newshub.data.exam.ExamReport;
import com.osastudio.newshub.utils.Utils;
import com.osastudio.newshub.widgets.ExamIntroView;
import com.osastudio.newshub.widgets.ExamLayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import android.widget.ViewSwitcher.ViewFactory;

public class ExamActivity extends NewsBaseActivity implements ViewFactory {

   private static final int NONE = 0;
   private static final int INTRO = 1;
   private static final int EXAM = 2;
   private static final int REPORT = 3;
   
   private ViewSwitcher mViewSwitcher;
   private ExamInfo mExamInfo;
   private Exam mExam;
   private ExamReport mExamReport;
   
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      setContentView(R.layout.activity_exam);

      findViews();
      initViews();
   }

   private void findViews() {
      mViewSwitcher = (ViewSwitcher) findViewById(R.id.ex_switcher);
   }

   private void initViews() {
      mViewSwitcher.setFactory(this);
      ExamLayout layout = (ExamLayout) mViewSwitcher.getCurrentView();
      layout.showLoadingBar();
      layout.hideViewContainer();
   }

   @Override
   public void onBackPressed() {
      super.onBackPressed();
   }
   
   @Override
   public View makeView() {
      return new ExamLayout(this);
   }
   
   private void loadCurrView() {
      
   }
   
   private void loadNextView() {
      
   }
   
   private void moveNext() {
      
   }

}
