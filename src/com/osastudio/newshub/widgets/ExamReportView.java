package com.osastudio.newshub.widgets;

import com.huadi.azker_phone.R;
import com.osastudio.newshub.data.exam.ExamReport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class ExamReportView extends FrameLayout {

   private Context context;
   private ViewGroup layout;
   private TextView scoreView, timeView, conclusionView;
   private ExamReport report;

   public ExamReportView(Context context) {
      super(context);

      this.context = context;
      LayoutInflater inflater = LayoutInflater.from(context);
      this.layout = (ViewGroup) inflater
            .inflate(R.layout.ex_report, this, true);

      findViews();
   }

   private void findViews() {
      this.scoreView = (TextView) this.layout.findViewById(R.id.report_score);
      this.timeView = (TextView) this.layout.findViewById(R.id.report_time);
      this.conclusionView = (TextView) this.layout
            .findViewById(R.id.report_conclusion);
   }

   public void setContent(ExamReport report) {
      this.report = report;
      if (this.scoreView != null) {
         this.scoreView.setText(report != null ? String.valueOf(report
               .getScore()) : "");
      }
      if (this.timeView != null) {
         this.timeView.setText(this.context.getString(R.string.ex_report_time,
               report != null ? report.getTime() : ""));
      }
      if (this.conclusionView != null) {
         this.conclusionView.setText(report != null ? report.getConclusion()
               : "");
      }
   }

   public TextView getScoreView() {
      return this.scoreView;
   }

   public TextView getTimeView() {
      return this.timeView;
   }

   public TextView getConclusionView() {
      return this.conclusionView;
   }

   public ExamReport getReport() {
      return this.report;
   }

}
