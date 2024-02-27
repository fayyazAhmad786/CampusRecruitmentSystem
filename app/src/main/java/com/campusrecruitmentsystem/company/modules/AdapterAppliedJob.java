package com.campusrecruitmentsystem.company.modules;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.campusrecruitmentsystem.R;
import com.campusrecruitmentsystem.student.modules.Job;
import com.campusrecruitmentsystem.student.modules.JobAdapter;

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
        void onItemClick(View view, int position, AppliedJobs job);
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION && onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, adapterPosition, jobList.get(adapterPosition));
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
        // Other views as needed

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_salery_range = itemView.findViewById(R.id.tv_salery_range_2);
            jobTitleTextView = itemView.findViewById(R.id.tv_Job_title_2);
            companyNameTextView = itemView.findViewById(R.id.tv_company_name_2);
            tv_location = itemView.findViewById(R.id.tv_location_2);
            img_company = itemView.findViewById(R.id.img_company_2);
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

    }
}



