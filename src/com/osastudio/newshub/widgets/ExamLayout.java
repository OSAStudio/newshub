package com.osastudio.newshub.widgets;

import com.huadi.azker_phone.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ExamLayout extends FrameLayout {

   private Context context;
   private ViewGroup layout;
   private TextView titleView, actionBtn;
   private ViewGroup viewContainer;
   private ProgressBar loadingBar;

   public ExamLayout(Context context) {
      super(context);
      this.context = context;

      LayoutInflater inflater = LayoutInflater.from(context);
      this.layout = (ViewGroup) inflater.inflate(R.layout.ex_main, this, true);

      findViews();
   }

   private void findViews() {
      this.titleView = (TextView) this.layout.findViewById(R.id.ex_title);
      this.actionBtn = (TextView) this.layout.findViewById(R.id.ex_action_btn);
      this.viewContainer = (ViewGroup) this.layout
            .findViewById(R.id.ex_container);
      this.loadingBar = (ProgressBar) this.layout
            .findViewById(R.id.ex_loading_bar);
   }

   public TextView getTitleView() {
      return this.titleView;
   }

   public TextView getActionBtn() {
      return this.actionBtn;
   }

   public ViewGroup getViewContainer() {
      return this.viewContainer;
   }

   public void showViewContainer() {
      this.viewContainer.setVisibility(View.VISIBLE);
   }

   public void hideViewContainer() {
      this.viewContainer.setVisibility(View.INVISIBLE);
   }

   public ProgressBar getLoadingBar() {
      return this.loadingBar;
   }

   public void showLoadingBar() {
      this.loadingBar.setVisibility(View.VISIBLE);
   }

   public void hideLoadingBar() {
      this.loadingBar.setVisibility(View.INVISIBLE);
   }

   public void showActionBtn() {
      this.actionBtn.setVisibility(View.VISIBLE);
   }

   public void hideActionBtn() {
      this.actionBtn.setVisibility(View.INVISIBLE);
   }

}
