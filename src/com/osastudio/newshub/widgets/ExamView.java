package com.osastudio.newshub.widgets;

import com.huadi.azker_phone.R;
import com.osastudio.newshub.data.exam.Option;
import com.osastudio.newshub.data.exam.OptionList;
import com.osastudio.newshub.data.exam.Question;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ExamView extends FrameLayout {

   private Context context;
   private ViewGroup layout;
   private TextView currProgressView, totalProgressView;
   private ProgressBar progressBar;
   private TextView questionTitleView;
   private ListView optionsListView;
   private OptionAdapter optionAdapter;
   private Question question;

   public ExamView(Context context) {
      super(context);
      this.context = context;

      LayoutInflater inflater = LayoutInflater.from(context);
      this.layout = (ViewGroup) inflater.inflate(R.layout.ex_exam, this, true);

      findViews();
   }

   private void findViews() {
      this.currProgressView = (TextView) this.layout
            .findViewById(R.id.progress_current);
      this.totalProgressView = (TextView) this.layout
            .findViewById(R.id.progress_total);
      this.progressBar = (ProgressBar) this.layout
            .findViewById(R.id.progress_bar);
      this.questionTitleView = (TextView) this.layout
            .findViewById(R.id.question_title);
      this.optionsListView = (ListView) this.layout
            .findViewById(R.id.options_list);
   }

   public void setContent(Question question) {
      this.question = question;
      if (this.currProgressView != null) {
         this.currProgressView.setText(question != null ? String
               .valueOf(question.getOrder()) : "0");
      }
      if (this.totalProgressView != null) {
         this.totalProgressView.setText(question != null ? new StringBuilder(
               "/").append(question.getTotalCount()).toString() : "0");
      }
      if (this.progressBar != null) {
         this.progressBar.setMax(question != null ? question.getTotalCount()
               : 100);
         this.progressBar.setProgress(question != null ? question.getOrder()
               : 0);
      }
      if (this.questionTitleView != null) {
         this.questionTitleView.setText(question != null ? question.getTitle()
               : "");
      }
      if (this.optionsListView != null) {
         this.optionAdapter = new OptionAdapter(context, question.getOptions());
         this.optionsListView.setAdapter(this.optionAdapter);
      }
   }

   public TextView getCurrProgressView() {
      return this.currProgressView;
   }

   public TextView getTotalProgressView() {
      return this.totalProgressView;
   }

   public ProgressBar getProgressBar() {
      return this.progressBar;
   }

   public TextView getQuestionTitleView() {
      return this.questionTitleView;
   }

   public ListView getOptionsListView() {
      return this.optionsListView;
   }

   public Question getQuestion() {
      return this.question;
   }

   private class OptionAdapter extends BaseAdapter {

      private Context context;
      private OptionList options;

      public OptionAdapter(Context context, OptionList opts) {
         this.context = context;
         this.options = opts;
      }

      public void setData(OptionList opts) {
         this.options = opts;
      }

      @Override
      public int getCount() {
         return this.options != null ? this.options.getCount() : 0;
      }

      @Override
      public Option getItem(int position) {
         try {
            return this.options.getList().get(position);
         } catch (IndexOutOfBoundsException e) {
            return null;
         }
      }

      @Override
      public long getItemId(int position) {
         return getItem(position).hashCode();
      }

      @Override
      public View getView(int position, View convertView, ViewGroup parent) {
         if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.ex_option, null);
         }

         Option opt = getItem(position);
         if (getCount() <= 0 || opt == null) {
            return convertView;
         }

         TextView icoView = (TextView) convertView
               .findViewById(R.id.option_ico);
         if (icoView != null) {
            icoView.setText(opt.getName());
         }
         TextView titleView = (TextView) convertView
               .findViewById(R.id.option_title);
         if (titleView != null) {
            titleView.setText(opt.getTitle());
         }

         convertView.setTag(opt);
         return convertView;
      }

   }

}
