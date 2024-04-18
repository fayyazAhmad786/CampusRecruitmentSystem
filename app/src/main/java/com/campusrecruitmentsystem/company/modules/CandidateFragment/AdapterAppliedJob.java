package com.campusrecruitmentsystem.company.modules.CandidateFragment;


import android.app.Activity;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.campusrecruitmentsystem.R;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterAppliedJob extends RecyclerView.Adapter<AdapterAppliedJob.ViewHolder> {
    private static final float MARGIN_END_LAS_ELEMENT = 0;
    private List<AppliedJobs> jobList;
    private static Context context;
    String profilePic;
    private static final int MARGIN_END_LAST_ELEMENT = 75; // 16dp
    private static final int MARGIN_BETWEEN_ITEMS = 8; // 8dp



    public AdapterAppliedJob(Context context) {
        this.context = context;
    }

    public void setJobList(List<AppliedJobs> jobList) {
        this.jobList = jobList;
        notifyDataSetChanged();
    }
    private AdapterAppliedJob.OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(AdapterAppliedJob.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    // ViewHolder class and other methods

    // Interface for item click listener
    public interface OnItemClickListener {
        void onItemClick(View view, int position, AppliedJobs job, String test);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_applied_jobs_list, parent, false);
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


        AppliedJobs AppliedJobs = jobList.get(position);
        holder.tv_salery_range.setText(AppliedJobs.getSalleryRange());
        holder.jobTitleTextView.setText(AppliedJobs.getStudent_full_name());
        holder.companyNameTextView.setText(AppliedJobs.getJobTitle());
        holder.tv_location.setText(AppliedJobs.getCompany_location());
        profilePic = AppliedJobs.getCompanyProfilePic();
        holder.showImage(profilePic);
        String test = AppliedJobs.gettest_assigned();
//        Toast.makeText(context,"Value= "+test,Toast.LENGTH_SHORT).show();
        if (test!= null){
            if (test.equalsIgnoreCase("No")){
                holder.img_assign_test.setBackgroundResource(R.drawable.ic_cancel_two);
                holder.img_assign_test.setEnabled(true);

                holder.ll_view_test.setVisibility(View.GONE);
                holder.ll_asign_test.setVisibility(View.GONE);
            }else {
                holder.img_assign_test.setBackgroundResource(R.drawable.ic_tick);
                holder.img_assign_test.setEnabled(false);
                holder.ll_view_test.setVisibility(View.VISIBLE);
                holder.ll_asign_test.setVisibility(View.VISIBLE);

            }
        }

//        String short_listed = AppliedJobs.getshort_listed();
//        if (short_listed!= null){
//            if (short_listed.equalsIgnoreCase("No") || short_listed.isEmpty()){
//
//                holder.img_shortlist.setBackgroundResource(R.drawable.ic_cancel_two);
//            }else {
//
//                holder.img_shortlist.setBackgroundResource(R.drawable.ic_tick);
//
//            }
//        }

        String test_result = AppliedJobs.gettest_result();
        if (test_result!= null){
            if (test_result.equalsIgnoreCase("No") ){

                holder.img_test_result.setBackgroundResource(R.drawable.ic_cancel_two);
            }else {

                holder.img_test_result.setBackgroundResource(R.drawable.ic_test_result_icon);
                holder.ll_view_test.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int adapterPosition = holder.getAdapterPosition();
                        if (adapterPosition != RecyclerView.NO_POSITION && onItemClickListener != null) {
                            final Dialog dialog = new Dialog(context);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCancelable(false);
                            dialog.setContentView(R.layout.custome_dialog_yes_no);
                            TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
                            text.setText("Do you want to View Test Result?");


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
                                    holder.img_assign_test.setBackgroundResource(R.drawable.ic_tick);
                                    holder.ll_view_test.setVisibility(View.VISIBLE);
                                    holder.ll_asign_test.setVisibility(View.VISIBLE);
                                    dialog.dismiss();
                                    onItemClickListener.onItemClick(v, adapterPosition, jobList.get(adapterPosition),"test_result");

                                }
                            });

                            dialog.show();
                        }
                    }
                });



            }
        }

        String short_list = AppliedJobs.getshort_listed();
        System.out.println("short_listsdsd= "+short_list);

        if (short_list != null){
            if (short_list.equalsIgnoreCase("no")){
                holder.img_shortlist.setBackgroundResource(R.drawable.ic_cancel_two);
                holder.img_shortlist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int adapterPosition = holder.getAdapterPosition();
                        if (adapterPosition != RecyclerView.NO_POSITION && onItemClickListener != null) {
                            final Dialog dialog = new Dialog(context);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCancelable(false);
                            dialog.setContentView(R.layout.custome_dialog_yes_no);
                            TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
                            text.setText("Do you want to Short List This Candidate?");


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
                                    holder.img_shortlist.setBackgroundResource(R.drawable.ic_short_list);
                                    dialog.dismiss();
                                    onItemClickListener.onItemClick(v, adapterPosition, jobList.get(adapterPosition),"short_list");

                                }
                            });

                            dialog.show();
                        }
                    }
                });

            }else {
                holder.img_shortlist.setEnabled(false);

            }
        }


        holder.img_download_resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION && onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, adapterPosition, jobList.get(adapterPosition), "resume");
                }
            }
        });
        holder.img_assign_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION && onItemClickListener != null) {
                    final Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.custome_dialog_yes_no);
                    TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
                    text.setText("Do you want to Assign Test to this Applicant");


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
                            holder.img_assign_test.setBackgroundResource(R.drawable.ic_tick);
                            holder.ll_view_test.setVisibility(View.VISIBLE);
                            holder.ll_asign_test.setVisibility(View.VISIBLE);
                            dialog.dismiss();
                            onItemClickListener.onItemClick(v, adapterPosition, jobList.get(adapterPosition),"test");

                        }
                    });

                    dialog.show();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return jobList == null ? 0 : jobList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_salery_range;
        TextView jobTitleTextView;
        TextView companyNameTextView;
        TextView tv_location;
        CircleImageView img_company;
        ImageView img_download_resume,img_assign_test,img_test_result,img_shortlist;
        LinearLayout ll_view_test,ll_asign_test;
        // Other views as needed

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_salery_range = itemView.findViewById(R.id.tv_salery_range_2);
            jobTitleTextView = itemView.findViewById(R.id.tv_Job_title_2);
            companyNameTextView = itemView.findViewById(R.id.tv_company_name_2);
            tv_location = itemView.findViewById(R.id.tv_location_2);
            img_company = itemView.findViewById(R.id.img_company_2);
            img_download_resume = itemView.findViewById(R.id.img_download_resume);
            img_assign_test = itemView.findViewById(R.id.img_assign_test);
            img_test_result = itemView.findViewById(R.id.img_test_result);
            img_shortlist = itemView.findViewById(R.id.img_shortlist);
            ll_view_test = itemView.findViewById(R.id.ll_view_test);
            ll_asign_test = itemView.findViewById(R.id.ll_asign_test);
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
                    img_company.setImageBitmap(b1);
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



