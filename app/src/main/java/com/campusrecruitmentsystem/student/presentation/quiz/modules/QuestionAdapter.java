package com.campusrecruitmentsystem.student.presentation.quiz.modules;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.campusrecruitmentsystem.R;
import com.campusrecruitmentsystem.student.modules.Question;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {
    private List<Question> questionList;

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
        holder.bind(question);
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView questionTextView;
        RadioGroup optionsRadioGroup;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.questionTextView);
            optionsRadioGroup = itemView.findViewById(R.id.optionsRadioGroup);
        }

        public void bind(Question question) {
            // Set question text
            questionTextView.setText(question.getQuestion());

            // Clear existing radio buttons
            optionsRadioGroup.removeAllViews();

            // Add radio buttons for each option
            for (String option : question.getOptions()) {
                RadioButton radioButton = new RadioButton(itemView.getContext());
                radioButton.setText(option);
                optionsRadioGroup.addView(radioButton);

                // Set the selected state based on the question's selected option
                radioButton.setChecked(option.equals(question.getSelectedOption()));

                // Set click listener to update the selected option for the question
                radioButton.setOnClickListener(view -> {
                    // Update the selected option for the question
                    question.setSelectedOption(option);
                    // Update UI to reflect the change
                    notifyDataSetChanged();
                });
            }
        }
    }
}


