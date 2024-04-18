package com.campusrecruitmentsystem.student.presentation.fragment_modules.notification;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.campusrecruitmentsystem.R;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private List<Notification> NotificationList;
    private static Context context;
    String profilePic;
    private OnItemClickListener onItemClickListener;


    public NotificationAdapter(Context context) {
        this.context = context;
    }

    public void setJobList(List<Notification> NotificationList) {
        this.NotificationList = NotificationList;
        notifyDataSetChanged();
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    // ViewHolder class and other methods

    // Interface for item click listener
    public interface OnItemClickListener {
        void onItemClick(View view, int position, Notification job, String text);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_listgooglewantto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notification notification = NotificationList.get(position);

        holder.companyNameTextView.setText(notification.getCompanyName());
        holder.tv_notification_text.setText(notification.getnotification_text());
        profilePic = notification.getCompanyProfilePic();


        String notifStatus = notification.getnotification_status();
        if (notifStatus.equalsIgnoreCase("new")){

            // Set bold style for tv_notification_text
            holder.companyNameTextView.setTypeface(null, Typeface.BOLD);
            holder.tv_notification_text.setTypeface(null, Typeface.BOLD);
            holder.txtTime.setTypeface(null, Typeface.BOLD);
            holder.viewEllipse783.setVisibility(View.VISIBLE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = holder.getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION && onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, adapterPosition, NotificationList.get(adapterPosition),"new");
                    }
                }
            });
        }else if (notifStatus.equalsIgnoreCase("new_non_click")){
            holder.companyNameTextView.setTypeface(null, Typeface.BOLD);
            holder.tv_notification_text.setTypeface(null, Typeface.BOLD);
            holder.txtTime.setTypeface(null, Typeface.BOLD);
            holder.viewEllipse783.setVisibility(View.VISIBLE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = holder.getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION && onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, adapterPosition, NotificationList.get(adapterPosition),"new_non_click");
                    }
                }
            });
        }else {
            holder.viewEllipse783.setVisibility(View.GONE);

        }


        String postedTime= notification.getcurrent_date_time();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date specificDate = dateFormat.parse(postedTime);

            // Get the current time
            Date currentDate = new Date();

            // Calculate the time difference in milliseconds
            long timeDifferenceMillis = currentDate.getTime() - specificDate.getTime();

            // Convert milliseconds to minutes, hours, or weeks
            long minutesDifference = timeDifferenceMillis / (60 * 1000);
            long hoursDifference = minutesDifference / 60;
            long daysDifference = hoursDifference / 24;
            long weeksDifference = daysDifference / 7;

            // Format the time difference string
            String timeDifferenceText;
            if (weeksDifference > 0) {
                timeDifferenceText = weeksDifference + " weeks, " + (daysDifference % 7) + " days, and " + (minutesDifference % 60) + " minutes";
            } else if (daysDifference > 0) {
                timeDifferenceText = daysDifference + " days and " + (minutesDifference % 60) + " minutes";
            } else if (hoursDifference > 0) {
                timeDifferenceText = hoursDifference + " hours and " + (minutesDifference % 60) + " minutes";
            } else {
                timeDifferenceText = minutesDifference + " minutes";
            }

            // Set the time difference text to the TextView
            holder.txtTime.setText(timeDifferenceText+" Ago");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.showImage(profilePic);
        // Set other views as needed

    }

    @Override
    public int getItemCount() {
        return NotificationList == null ? 0 : NotificationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView companyNameTextView,tv_notification_text,txtTime;
        View viewEllipse783;
        CircleImageView img_company;
        // Other views as needed

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_notification_text = itemView.findViewById(R.id.tv_notification_text);
            companyNameTextView = itemView.findViewById(R.id.tv_company_name);
            txtTime = itemView.findViewById(R.id.txtTime);
            img_company = itemView.findViewById(R.id.img_company_2);
            viewEllipse783 = itemView.findViewById(R.id.viewEllipse783);
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


