package com.osastudio.newshub.widgets;

import java.util.ArrayList;
import java.util.List;

import com.huadi.azker_phone.R;
import com.osastudio.newshub.data.exam.Option;
import com.osastudio.newshub.data.exam.OptionList;
import com.osastudio.newshub.data.exam.Question;
import com.osastudio.newshub.data.exam.QuestionAnswer;
import com.osastudio.newshub.utils.UIUtils;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ExamView extends FrameLayout {

   private Context context;
   private ViewGroup layout;
   private TextView currProgressView, totalProgressView;
   private ProgressBar progressBar;
   private TextView questionTitleView;
   private OptionAdapter optionAdapter;
   private Question question;
   private QuestionAnswer answer;
   private ViewGroup optionsLayout;

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
      this.optionsLayout = (ViewGroup) this.layout
            .findViewById(R.id.options_list);
   }

   public void setContent(Question question) {
      this.question = question;
      this.answer = new QuestionAnswer();
      this.answer.setQuestionId(question.getId());
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
         this.questionTitleView.setText(question != null ? Html
               .fromHtml(question.getTitle()) : "");
      }
      if (this.optionsLayout != null) {
         this.optionsLayout.removeAllViews();
         this.optionAdapter = new OptionAdapter(this.context,
               question != null ? question.getOptions() : null);
         View view = null;
         for (int i = 0; i < question.getOptions().getCount(); i++) {
            view = this.optionAdapter.getView(i, null, null);
            view.setOnClickListener(new OnClickListener() {
               public void onClick(View v) {
                  if (v.getTag() != null) {
                     Option opt = (Option) v.getTag();
                     if (!opt.isSelected()) {
                        selectOption(opt);
                     } else {
                        deselectOption(opt);
                     }
                  }
               }
            });
            this.optionsLayout.addView(view, i);
         }
      }
   }

   public void selectOption(Option option) {
      OptionList options = this.question.getOptions();
      if (this.question.isSingleChoice()) {
         for (int i = 0; i < options.getCount(); i++) {
            options.getList().get(i).setSelected(false);
         }
         option.setSelected(true);
         this.answer.clearAllOptionIds();
         this.answer.addOptionId(option.getId());
         this.optionAdapter.notifyDataSetChanged();
      } else if (this.question.isMultipleChoice()) {
         int count = 0;
         for (int i = 0; i < options.getCount(); i++) {
            if (options.getList().get(i).isSelected()) {
               count++;
            }
         }
         if (count < this.question.getMaxChoices()) {
            option.setSelected(true);
            this.answer.addOptionId(option.getId());
            this.optionAdapter.notifyDataSetChanged();
         } else {
            UIUtils.showToast(
                  this.context,
                  this.context.getString(R.string.ex_max_choices,
                        this.question.getMaxChoices()));
         }
      }
   }

   public void deselectOption(Option option) {
      option.setSelected(false);
      this.answer.removeOptionId(option.getId());
      this.optionAdapter.notifyDataSetChanged();
   }

   public boolean hasAnswered() {
      return this.answer.getOptionIds().size() > 0;
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

   public Question getQuestion() {
      return this.question;
   }

   public QuestionAnswer getAnswer() {
      return this.answer;
   }

   private class OptionAdapter extends BaseAdapter {

      private Context context;
      private OptionList options;
      private List<View> itemViews = new ArrayList<View>();

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
            return this.options != null ? this.options.getList().get(position)
                  : null;
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
            convertView = new ExamOptionView(this.context);
            this.itemViews.add(convertView);
         }

         Option opt = getItem(position);
         if (getCount() <= 0 || opt == null) {
            return convertView;
         }

         ((ExamOptionView) convertView).setContent(opt);
         convertView.setTag(opt);
         return convertView;
      }

      @Override
      public void notifyDataSetChanged() {
         View view = null;
         for (int i = 0; i < this.itemViews.size(); i++) {
            view = this.itemViews.get(i);
            if (view != null) {
               Option opt = (Option) view.getTag();
               view.setSelected(opt.isSelected());
               view.invalidate();
            }
         }
      }

   }

}
