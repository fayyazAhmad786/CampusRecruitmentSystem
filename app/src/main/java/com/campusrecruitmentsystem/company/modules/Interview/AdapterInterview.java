package com.campusrecruitmentsystem.company.modules.Interview;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.campusrecruitmentsystem.R;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.util.Calendar;
import java.util.List;

public class AdapterInterview extends RecyclerView.Adapter<AdapterInterview.ViewHolder> {
    private static final float MARGIN_END_LAS_ELEMENT = 0;
    private List<Interview> jobList;
    private static Context context;
    String profilePic;
    private static final int MARGIN_END_LAST_ELEMENT = 75; // 16dp
    private static final int MARGIN_BETWEEN_ITEMS = 8; // 8dp



    public AdapterInterview(Context context) {
        this.context = context;
    }

    public void setJobList(List<Interview> jobList) {
        this.jobList = jobList;
        notifyDataSetChanged();
    }
    private AdapterInterview.OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(AdapterInterview.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    // ViewHolder class and other methods

    // Interface for item click listener
    public interface OnItemClickListener {
        void onItemClick(View view, int position, Interview job, String test, String joining_date, Integer id_pk);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_interview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // Set other views as needed
        if (position == getItemCount() - 1) {
            // Adjust spacing for the last item
            Resources r = context.getResources();
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MARGIN_END_LAST_ELEMENT, r.getDisplayMetrics());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 0, (int) px); // Set bottom margin for the last item
            holder.itemView.setLayoutParams(layoutParams);
        } else {
            // Set the margin for non-last elements here
            Resources r = context.getResources();
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MARGIN_BETWEEN_ITEMS, r.getDisplayMetrics());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 0, (int) px); // Set bottom margin for non-last items
            holder.itemView.setLayoutParams(layoutParams);
        }


        Interview Interview = jobList.get(position);
        holder.tv_interview_salery_range.setText(Interview.getSalleryRange());
        holder.tv_joining_date.setText(Interview.getjoining_date());
        holder.tv_interview_job_title.setText(Interview.getJobTitle());
        profilePic = Interview.getstudentProfilePic();
        holder.showImage(profilePic);
        String hired = Interview.gethired();
        Integer id_pk = Interview.get_id_pk();
//        String join = Interview.get_id_pk();
        Toast.makeText(context,"Value999= "+hired,Toast.LENGTH_SHORT).show();
        if (hired!= null){
            if (hired.equalsIgnoreCase("No")){


                holder.ll_joining_date.setVisibility(View.GONE);
                holder.tv_hired_reject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int adapterPosition = holder.getAdapterPosition();
                        if (adapterPosition != RecyclerView.NO_POSITION && onItemClickListener != null) {
                            final Dialog dialog = new Dialog(context);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCancelable(false);
                            dialog.setContentView(R.layout.custome_dialog_yes_no);
                            TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
                            text.setText("Do you want to Reject This Candidate?");


                            Button btn_dialog_no = (Button) dialog.findViewById(R.id.btn_dialog_no);
                            Button btn_dialog_yes = (Button) dialog.findViewById(R.id.btn_dialog_yes);
                            btn_dialog_no.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });  btn_dialog_yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    holder.tv_hired_reject.setText("Rejected");
                                    holder.tv_hired_reject.setEnabled(false);
                                    float initialWeightReject = 1.5f;
                                    float initialWeightAccept = 1.5f;
                                    holder.tv_hired_accept.setVisibility(View.GONE);
                                    float newWeightReject = initialWeightReject + initialWeightAccept;
                                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.tv_hired_reject.getLayoutParams();
                                    params.weight = newWeightReject;
                                    holder.tv_hired_reject.setLayoutParams(params);
                                    dialog.dismiss();
                                    onItemClickListener.onItemClick(v, adapterPosition, jobList.get(adapterPosition),"reject", "joining_date", id_pk);

                                }
                            });

                            dialog.show();
                        }
                    }
                });
                holder.tv_hired_accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int adapterPosition = holder.getAdapterPosition();
                        if (adapterPosition != RecyclerView.NO_POSITION && onItemClickListener != null) {
                            final Dialog dialog = new Dialog(context);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCancelable(false);
                            dialog.setContentView(R.layout.custome_dialog_with_joining_date);
                            TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
                            text.setText("Do you want to Hire This Candidate?");


                            Button btn_dialog_no = (Button) dialog.findViewById(R.id.btn_dialog_no);
                            Button btn_dialog_yes = (Button) dialog.findViewById(R.id.btn_dialog_yes);
                            TextInputLayout til_joining_date = (TextInputLayout) dialog.findViewById(R.id.til_joining_date);
                            EditText et_joining_date = (EditText) dialog.findViewById(R.id.et_joining_date);
                            et_joining_date.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                    if (imm != null) {
                                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);  // hide the soft keyboard
                                    }

                                    // Get current date
                                    final Calendar c = Calendar.getInstance();
                                    int mYear = c.get(Calendar.YEAR);
                                    int mMonth = c.get(Calendar.MONTH);
                                    int mDay = c.get(Calendar.DAY_OF_MONTH);

                                    // Create DatePickerDialog and set minimum date
                                    DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                                            new DatePickerDialog.OnDateSetListener() {
                                                @Override
                                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                                    et_joining_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                                }
                                            }, mYear, mMonth, mDay);

                                    // Set minimum date (current date)
                                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

                                    // Show the dialog
                                    datePickerDialog.show();
                                }
                            });
                            btn_dialog_no.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });  btn_dialog_yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String joining_date = et_joining_date.getText().toString();

                                    if (!joining_date.equalsIgnoreCase("")){
                                        holder.tv_hired_accept.setText("Accepted");
                                        holder.tv_hired_accept.setEnabled(false);
                                        holder.tv_hired_reject.setVisibility(View.GONE);

                                        float initialWeightReject = 1.5f;
                                        float initialWeightAccept = 1.5f;
                                        float newWeightReject = initialWeightReject + initialWeightAccept;
                                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.tv_hired_accept.getLayoutParams();
                                        params.weight = newWeightReject;
                                        holder.tv_hired_accept.setLayoutParams(params);


                                        dialog.dismiss();
                                        onItemClickListener.onItemClick(v, adapterPosition, jobList.get(adapterPosition),"accept",joining_date,id_pk);

                                    }else {
                                        Toast.makeText(context,"Please Enter Joining Date",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                            dialog.show();
                        }
                    }
                });


            }
            else if (hired.equalsIgnoreCase("rejected")){
                holder.tv_hired_reject.setText("Rejected");
                holder.tv_hired_reject.setEnabled(false);
                float initialWeightReject = 1.5f;
                float initialWeightAccept = 1.5f;
                holder.tv_hired_accept.setVisibility(View.GONE);
                float newWeightReject = initialWeightReject + initialWeightAccept;
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.tv_hired_reject.getLayoutParams();
                params.weight = newWeightReject;
                holder.tv_hired_reject.setLayoutParams(params);

                holder.ll_joining_date.setVisibility(View.GONE);
                holder.tv_hired_reject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int adapterPosition = holder.getAdapterPosition();
                        if (adapterPosition != RecyclerView.NO_POSITION && onItemClickListener != null) {
                            final Dialog dialog = new Dialog(context);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCancelable(false);
                            dialog.setContentView(R.layout.custome_dialog_yes_no);
                            TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
                            text.setText("Do you want to Reject This Candidate?");


                            Button btn_dialog_no = (Button) dialog.findViewById(R.id.btn_dialog_no);
                            Button btn_dialog_yes = (Button) dialog.findViewById(R.id.btn_dialog_yes);
                            btn_dialog_no.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });  btn_dialog_yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    holder.tv_hired_accept.setVisibility(View.GONE);
                                    holder.tv_hired_reject.setText("Rejected");
                                    holder.tv_hired_reject.setEnabled(false);
                                    dialog.dismiss();
                                    onItemClickListener.onItemClick(v, adapterPosition, jobList.get(adapterPosition),"reject", "joining_date", id_pk);

                                }
                            });

                            dialog.show();
                        }
                    }
                });


            }
            else if (hired.equalsIgnoreCase("accepted")){
                holder.tv_hired_reject.setVisibility(View.GONE);

                holder.tv_hired_accept.setText("Accepted");
                holder.tv_hired_accept.setEnabled(false);

                holder.ll_joining_date.setVisibility(View.VISIBLE);
                float initialWeightReject = 1.5f;
                float initialWeightAccept = 1.5f;
                float newWeightReject = initialWeightReject + initialWeightAccept;
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.tv_hired_accept.getLayoutParams();
                params.weight = newWeightReject;
                holder.tv_hired_accept.setLayoutParams(params);


                holder.tv_hired_accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int adapterPosition = holder.getAdapterPosition();
                        if (adapterPosition != RecyclerView.NO_POSITION && onItemClickListener != null) {
                            final Dialog dialog = new Dialog(context);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCancelable(false);
                            dialog.setContentView(R.layout.custome_dialog_with_joining_date);
                            TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
                            text.setText("Do you want to Hire This Candidate?");


                            Button btn_dialog_no = (Button) dialog.findViewById(R.id.btn_dialog_no);
                            Button btn_dialog_yes = (Button) dialog.findViewById(R.id.btn_dialog_yes);
                            TextInputLayout til_joining_date = (TextInputLayout) dialog.findViewById(R.id.til_joining_date);
                            EditText et_joining_date = (EditText) dialog.findViewById(R.id.et_joining_date);
                            et_joining_date.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                    if (imm != null) {
                                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);  // hide the soft keyboard
                                    }

                                    // Get current date
                                    final Calendar c = Calendar.getInstance();
                                    int mYear = c.get(Calendar.YEAR);
                                    int mMonth = c.get(Calendar.MONTH);
                                    int mDay = c.get(Calendar.DAY_OF_MONTH);

                                    // Create DatePickerDialog and set minimum date
                                    DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                                            new DatePickerDialog.OnDateSetListener() {
                                                @Override
                                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                                    et_joining_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                                }
                                            }, mYear, mMonth, mDay);

                                    // Set minimum date (current date)
                                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

                                    // Show the dialog
                                    datePickerDialog.show();
                                }
                            });
                            btn_dialog_no.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });  btn_dialog_yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String joining_date = et_joining_date.getText().toString();
                                    if (!joining_date.equalsIgnoreCase("")){
                                        holder.tv_hired_reject.setVisibility(View.GONE);

                                        holder.tv_hired_accept.setText("Accepted");
                                        holder.tv_hired_accept.setEnabled(false);

                                        float initialWeightReject = 1.5f;
                                        float initialWeightAccept = 1.5f;
                                        float newWeightReject = initialWeightReject + initialWeightAccept;
                                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.tv_hired_accept.getLayoutParams();
                                        params.weight = newWeightReject;
                                        holder.tv_hired_accept.setLayoutParams(params);


                                        dialog.dismiss();
                                        onItemClickListener.onItemClick(v, adapterPosition, jobList.get(adapterPosition),"accept", joining_date, id_pk);

                                    }else {
                                        Toast.makeText(context,"Please Enter Joining Date",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                            dialog.show();
                        }
                    }
                });

            }

        }
        holder.img_interview_send_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_email =  Interview.getuser_applied();

                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION && onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, adapterPosition, jobList.get(adapterPosition),"email", user_email, id_pk);

                }
            }
        });





    }

    @Override
    public int getItemCount() {
        return jobList == null ? 0 : jobList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_interview_job_title,tv_interview_salery_range,tv_hired_reject,tv_hired_accept,tv_joining_date;

        ImageView img_interview_send_email,img_candidate;
        LinearLayout ll_joining_date;
        // Other views as needed

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_interview_job_title = itemView.findViewById(R.id.tv_interview_job_title);
            tv_interview_salery_range = itemView.findViewById(R.id.tv_interview_salery_range);
            img_interview_send_email = itemView.findViewById(R.id.img_interview_send_email);
            tv_hired_reject = itemView.findViewById(R.id.tv_hired_reject);
            tv_hired_accept = itemView.findViewById(R.id.tv_hired_accept);
            tv_joining_date = itemView.findViewById(R.id.tv_joining_date);
            img_candidate = itemView.findViewById(R.id.img_candidate);
            ll_joining_date = itemView.findViewById(R.id.ll_joining_date);

            // Initialize other views
        }
        private void showImage(String imageName){
            try {
                Bitmap b = null;
                String imageFile = "";
                String strFolder = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + File.separator  + context.getString(R.string.db_folder_images) + "/";
                imageFile = strFolder + imageName;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 5;
//            if (!UploaderGui.path[id].equalsIgnoreCase(""))
                b = BitmapFactory.decodeFile(imageFile, options);
//            b = BitmapFactory.decodeFile(imageFile);
                if (b != null) {
                    Bitmap b1 = Bitmap.createScaledBitmap(b, 60, 60, false);
                    img_candidate.setImageBitmap(b1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public void DialogAlert(Activity activity, String msg){
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.custom_dialog);

            TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
            text.setText(msg);

            Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();

        }

    }
}



