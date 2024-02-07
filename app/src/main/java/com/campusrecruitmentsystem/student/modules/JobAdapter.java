package com.campusrecruitmentsystem.student.modules;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.campusrecruitmentsystem.R;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewHolder> {
    private List<Job> jobList;
    private static Context context;
    String profilePic;
    private OnItemClickListener onItemClickListener;


    public JobAdapter(Context context) {
        this.context = context;
    }

    public void setJobList(List<Job> jobList) {
        this.jobList = jobList;
        notifyDataSetChanged();
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    // ViewHolder class and other methods

    // Interface for item click listener
    public interface OnItemClickListener {
        void onItemClick(View view, int position, Job job);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_jobs_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Job job = jobList.get(position);
        holder.tv_salery_range.setText(job.getSalleryRange());
        holder.jobTitleTextView.setText(job.getJobTitle());
        holder.companyNameTextView.setText(job.getCompanyName());
        holder.tv_location.setText(job.getCompanyLocation());
        profilePic = job.getCompanyProfilePic();
        holder.showImage(profilePic);
        // Set other views as needed
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
            tv_salery_range = itemView.findViewById(R.id.tv_salery_range);
            jobTitleTextView = itemView.findViewById(R.id.tv_Job_title);
            companyNameTextView = itemView.findViewById(R.id.tv_company_name);
            tv_location = itemView.findViewById(R.id.tv_location);
            img_company = itemView.findViewById(R.id.img_company);
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


