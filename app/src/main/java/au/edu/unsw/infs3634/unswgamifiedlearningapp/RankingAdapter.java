package au.edu.unsw.infs3634.unswgamifiedlearningapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.Executors;


// A constructor method for the adapter class
public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.RankingViewHolder> {
    private final ArrayList<Plan> mPlans;
    private final RecyclerViewClickListener mListener;
    private AppDatabase mDb;
    public Context mContext;
    public static final int SORT_METHOD_PROGRESS = 1;

    // A constructor method for the adapter class
    public RankingAdapter(ArrayList<Plan> plans, RecyclerViewClickListener listener) {
        mPlans = plans;
        mListener = listener;
    }

    // ClickListener interface
    public interface RecyclerViewClickListener {
        void onPlanClick(View view, String planId);
    }


    // Create a view and return it
    @NonNull
    @Override
    public RankingAdapter.RankingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ranking_row, parent, false);
        mContext = parent.getContext();
        return new RankingViewHolder(v, mListener);
    }

    // Associate the data with the view holder for a given position in the RecyclerView
    @Override
    public void onBindViewHolder(@NonNull RankingAdapter.RankingViewHolder holder, int position) {
        Plan plan = mPlans.get(position);
        mDb = Room.databaseBuilder(mContext, AppDatabase.class, "app-database").build();

        //Retrieve other information of user by userId
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                User user = mDb.userDao().getUser(plan.getUserId());
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        holder.userImage.setImageResource(user.getImageId());
                        holder.userName.setText(user.getName());

                    }
                });
            }
        });

        holder.progress.setText(plan.getProgress() + " / " + plan.getDays());
        holder.progressBar.setProgress((int) (Double.valueOf(plan.getProgress()) * 100 / Double.valueOf(plan.getDays())));
        holder.number.setText(String.valueOf(position + 1));
        holder.itemView.setTag(plan.getPlanId());

    }

    // Return the number of data items available to display
    @Override
    public int getItemCount() {
        return mPlans.size();
    }

    // Extend the signature of RankingViewHolder to implement a click listener
    public static class RankingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView userName;
        private final TextView progress;
        private final TextView number;
        private final ProgressBar progressBar;
        private final ImageView userImage;
        private final RecyclerViewClickListener listener;

        // A constructor method for RankingViewHolder class
        public RankingViewHolder(@NonNull View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            this.listener = listener;
            itemView.setOnClickListener(this);
            userName = itemView.findViewById(R.id.tvRUser);
            progress = itemView.findViewById(R.id.tvRProgress);
            progressBar = itemView.findViewById(R.id.progressBar2);
            userImage = itemView.findViewById(R.id.ivRImage);
            number = itemView.findViewById(R.id.tvPnumber);

        }

        @Override
        public void onClick(View v) {
            listener.onPlanClick(v, String.valueOf(v.getTag()));
        }
    }

    // Use the Java Collection.sort() and Comparator methods to sort the list
    public void sort(final int sortMethod) {
        if (mPlans.size() > 0) {

            Collections.sort(mPlans, new Comparator<Plan>() {
                @Override
                public int compare(Plan o1, Plan o2) {
                    if (sortMethod == SORT_METHOD_PROGRESS) {
                        // Sort by new cases
                        return -o2.getProgress().compareTo(o1.getProgress());
                    }
                    return o2.getProgress().compareTo(o1.getProgress());
                }
            });
        }
        notifyDataSetChanged();
    }
}

