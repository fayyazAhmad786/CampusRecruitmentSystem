package com.campusrecruitmentsystem.student.presentation.job_applied_module;



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
import com.campusrecruitmentsystem.student.presentation.fragment_modules.home_module.Job;
import com.campusrecruitmentsystem.student.presentation.fragment_modules.home_module.JobAdapter;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class AppliedJobAdapter extends RecyclerView.Adapter<AppliedJobAdapter.ViewHolder> {
    private List<GettersetterAppliedJobs> jobList;
    private static Context context;
    String profilePic;
    private AppliedJobAdapter.OnItemClickListener onItemClickListener;
    private static final int MARGIN_END_LAST_ELEMENT = 75; // 16dp
    private static final int MARGIN_BETWEEN_ITEMS = 8; // 8dp

    public AppliedJobAdapter(Context context) {
        this.context = context;
    }

    public void setJobList(List<GettersetterAppliedJobs> jobList) {
        this.jobList = jobList;
        notifyDataSetChanged();
    }
    public void setOnItemClickListener(AppliedJobAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    // ViewHolder class and other methods

    // Interface for item click listener
    public interface OnItemClickListener {
        void onItemClick(View view, int position, GettersetterAppliedJobs job,int _id_pk);
    }
    @NonNull
    @Override
    public AppliedJobAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_applied, parent, false);
        return new AppliedJobAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppliedJobAdapter.ViewHolder holder, int position) {
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
        GettersetterAppliedJobs job = jobList.get(position);
        holder.txtJobTitle.setText(job.getTxtJobTitle());
        holder.txtlocation.setText(job.getTxtlocation());
        holder.txtcompany.setText(job.getCompanyName());
        holder.txtdeadline.setText(job.getTxtdeadline());
        profilePic = job.getCompanyProfilePic();
        System.out.println("profilePic= "+profilePic);
        holder.showImage(profilePic);
        // Set other views as needed
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION && onItemClickListener != null) {
                    int _id_pk = job.get_id_pk();

                    onItemClickListener.onItemClick(v, adapterPosition, jobList.get(adapterPosition),_id_pk);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobList == null ? 0 : jobList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtJobTitle;
        TextView txtlocation;
        TextView txtcompany;
        TextView txtdeadline;
        CircleImageView img_company;
        LinearLayout linearRowthumbsup;
        // Other views as needed

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtJobTitle = itemView.findViewById(R.id.txtJobTitle);
            txtlocation = itemView.findViewById(R.id.txtlocation);
            txtcompany = itemView.findViewById(R.id.txtcompany);
            txtdeadline = itemView.findViewById(R.id.txtdeadline);
            img_company = itemView.findViewById(R.id.img_appliedCompanyIcon);
            linearRowthumbsup = itemView.findViewById(R.id.linearRowthumbsup);
            // Initialize other views
        }
        private void showImage(String imageName){
            try {
                Bitmap b = null;
                String imageFile = "";
                String strFolder = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + File.separator  + context.getString(R.string.db_folder_images) + "/";
                imageFile = strFolder + imageName;
                System.out.println("imageFile1= "+imageFile);

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







