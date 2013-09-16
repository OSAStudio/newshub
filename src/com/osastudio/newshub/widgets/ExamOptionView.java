package com.osastudio.newshub.widgets;

import com.huadi.azker_phone.R;
import com.osastudio.newshub.data.exam.Option;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class ExamOptionView extends FrameLayout {

   private Context context;
   private ViewGroup layout;
   private TextView icoView, titleView;
   private Option examOption;

   public ExamOptionView(Context context) {
      super(context);
      this.context = context;

      LayoutInflater inflater = LayoutInflater.from(context);
      this.layout = (ViewGroup) inflater
            .inflate(R.layout.ex_option, this, true);
      findViews();
   }

   private void findViews() {
      this.icoView = (TextView) this.layout.findViewById(R.id.option_ico);
      this.titleView = (TextView) this.layout.findViewById(R.id.option_title);
   }

   public void setContent(Option option) {
      this.examOption = option;
      if (this.icoView != null) {
         this.icoView.setText(option.isSelected() ? ""
               : (option != null ? option.getName() : ""));
         this.icoView.setSelected(option.isSelected());
      }
      if (this.titleView != null) {
         this.titleView.setTextColor(this.context.getResources().getColor(
               option.isSelected() ? R.color.ex_option_hl_text
                     : R.color.ex_default_text));
         this.titleView.setText(option != null ? option.getTitle() : "");
         this.titleView.setSelected(option.isSelected());
      }
   }

   public TextView getIcoView() {
      return icoView;
   }

   public TextView getTitleView() {
      return titleView;
   }

   public Option getExamOption() {
      return examOption;
   }

   @Override
   public void setSelected(boolean selected) {
      super.setSelected(selected);

      if (this.icoView != null) {
         this.icoView.setSelected(selected);
      }
      if (this.titleView != null) {
         this.titleView.setSelected(selected);
      }
   }

}
