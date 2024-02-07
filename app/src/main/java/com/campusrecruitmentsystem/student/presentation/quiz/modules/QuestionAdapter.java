package com.campusrecruitmentsystem.student.presentation.quiz.modules;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.campusrecruitmentsystem.R;
import com.campusrecruitmentsystem.student.modules.Question;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {
    private List<Question> questionList;
    private boolean isEnabled = true; // Flag to enable/disable items


    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
        notifyDataSetChanged(); // Refresh RecyclerView after changing the state
    }
    public QuestionAdapter(List<Question> questionList) {
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Question question = questionList.get(position);
        holder.questionTextView.setText(question.getQuestion());

        // Clear existing radio buttons
        holder.optionsRadioGroup.removeAllViews();

        // Add radio buttons for each option
        for (String option : question.getOptions()) {
            RadioButton radioButton = new RadioButton(holder.itemView.getContext());
            radioButton.setText(option);
            holder.optionsRadioGroup.addView(radioButton);

        }
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView questionTextView;
        RadioGroup optionsRadioGroup;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.questionTextView);
            optionsRadioGroup = itemView.findViewById(R.id.optionsRadioGroup);
        }
    }
}



