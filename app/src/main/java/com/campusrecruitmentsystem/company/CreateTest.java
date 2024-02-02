package com.campusrecruitmentsystem.company;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.BoringLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.campusrecruitmentsystem.R;
import com.campusrecruitmentsystem.helperClases.ViewDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CreateTest extends AppCompatActivity {

    private EditText editTextOption,et_complaint_writing_pin;
    private Button buttonAddOption,btnPostIt;
    private RecyclerView recyclerView;
    private List<String> options;
    private OptionAdapter adapter;
    private Boolean allOk= false;
    JSONArray jsonArray;
    JSONObject jsonObject;
    Context context;
    TextView tv_question_no;
    ImageView imageArrowleft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_test);
        findViews();
        context= this;
        jsonArray = new JSONArray();


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        options = new ArrayList<>();
        adapter = new OptionAdapter(options);
        recyclerView.setAdapter(adapter);

        buttonAddOption.setOnClickListener(v -> {
            String question = et_complaint_writing_pin.getText().toString().trim();
            String newOption = editTextOption.getText().toString().trim();
            if (jsonArray.length() <=10){
                if (!question.isEmpty()){
                    if (!newOption.isEmpty()) {
                        if (options.size() < 4) { // Check if less than four options are added
                            options.add(newOption);
                            adapter.notifyDataSetChanged();
                            editTextOption.getText().clear();
                            if (options.size() == 4){
                                allOk=true;
                            }
                        } else {
                            // Display a message indicating that no more options can be added
                            ViewDialog alert = new ViewDialog();
                            alert.showDialog(CreateTest.this,"Maximum four options Allowed");
                        }
                    } else {
                        // Display a message indicating that no more options can be added
                        ViewDialog alert = new ViewDialog();
                        alert.showDialog(CreateTest.this,"Please Enter Something to add Option");
                    }
                }else {
                    // Display a message indicating that no more options can be added
                    ViewDialog alert = new ViewDialog();
                    alert.showDialog(CreateTest.this,"Please Write Question First to add Options");
                }
            }else {
                ViewDialog alert = new ViewDialog();
                alert.showDialog(CreateTest.this,"You can add only 10 Questions Minimum");
            }


        });

        btnPostIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allOk){
                    try {
                        jsonObject = new JSONObject();
                        String  question = et_complaint_writing_pin.getText().toString().trim();
                        jsonObject.put("first_question",question);
                        jsonObject.put("first_question_options",options);
                        jsonArray.put(jsonObject);
                        int size= 0;
                        size = jsonArray.length();
                        String sizeAsString = String.valueOf(size);
                        tv_question_no.setText(sizeAsString);
                        options.clear();
                        et_complaint_writing_pin.setText("");
                        editTextOption.setText("");
                        adapter.notifyDataSetChanged();
                        allOk=false;

                    }catch (Exception e){
                        Toast.makeText(context,"Error"+e.getMessage(),Toast.LENGTH_SHORT).show();
                        System.out.println("error= "+e.getMessage());
                    }

                }else {
                    ViewDialog alert = new ViewDialog();
                    alert.showDialog(CreateTest.this,"Please Add Question and 4 Options First");
                }
            }
        });

        imageArrowleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (jsonArray.length()>=1){
                    if (jsonArray.length() >= 5) {
                        Intent intent = new Intent(context, PostJob.class);
                        intent.putExtra("jsonArray", jsonArray.toString()); // Convert JSON array to string and pass it as an extra
                        startActivity(intent);
                        finish();
                    } else {
                        showDialogBack(CreateTest.this,"Please Add 5 Questions Otherwise Your data will be lost");
                    }
                }else {
                    Intent intent = new Intent(context, PostJob.class);
                    intent.putExtra("jsonArray", "empty"); // Convert JSON array to string and pass it as an extra
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
    @Override
    public void onBackPressed() {
        if (jsonArray.length()>=1){
            if (jsonArray.length() >= 5) {
                Intent intent = new Intent(context, PostJob.class);
                intent.putExtra("jsonArray", jsonArray.toString()); // Convert JSON array to string and pass it as an extra
                startActivity(intent);
                finish();
            } else {
                showDialogBack(CreateTest.this,"Please Add 5 Questions Otherwise Your data will be lost");
            }
        }else {
            Intent intent = new Intent(context, PostJob.class);
            intent.putExtra("jsonArray", "empty"); // Convert JSON array to string and pass it as an extra
            startActivity(intent);
            finish();
        }

    }
    public void showDialogBack(Activity activity, String msg){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog_back);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog_cancel);
        Button dialogOK = (Button) dialog.findViewById(R.id.btn_dialog_ok);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialogOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PostJob.class);
                intent.putExtra("jsonArray", "empty"); // Convert JSON array to string and pass it as an extra
                startActivity(intent);
                finish();
                dialog.dismiss();


            }
        });

        dialog.show();

    }
    private void findViews() {
        et_complaint_writing_pin = findViewById(R.id.et_complaint_writing_pin);
        editTextOption = findViewById(R.id.editTextOption);
        buttonAddOption = findViewById(R.id.buttonAddOption);
        recyclerView = findViewById(R.id.recyclerView);
        btnPostIt = findViewById(R.id.btnPostIt);
        tv_question_no = findViewById(R.id.tv_question_no);
        imageArrowleft = findViewById(R.id.imageArrowleft);
    }

    private class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.OptionViewHolder> {

        private List<String> options;

        OptionAdapter(List<String> options) {
            this.options = options;
        }


        @NonNull
        @Override
        public OptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checkbox, parent, false);
            return new OptionViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull OptionViewHolder holder, int position) {
            String option = options.get(position);
            holder.textView.setText(option);

            holder.checkBox.setOnCheckedChangeListener(null); // Reset listener to prevent unwanted changes
            holder.checkBox.setChecked(false); // Clear previous selection

            holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    // Handle selection
//                    Toast.makeText(CreateTest.this, "Selected: " + option, Toast.LENGTH_SHORT).show();
                } else {
                    // Handle deselection
                }
            });
        }

        @Override
        public int getItemCount() {
            return options.size();
        }

        class OptionViewHolder extends RecyclerView.ViewHolder {
            CheckBox checkBox;
            TextView textView;

            OptionViewHolder(View itemView) {
                super(itemView);
                checkBox = itemView.findViewById(R.id.checkBox);
                textView = itemView.findViewById(R.id.textView);
            }
        }


    }
}