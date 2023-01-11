package au.edu.unsw.infs3634.unswgamifiedlearningapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;


// A constructor method for the adapter class
public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.PlanViewHolder> {
    private final ArrayList<Plan> mPlans;
    private final RecyclerViewClickListener mListener;

    // A constructor method for the adapter class
    public PlanAdapter(ArrayList<Plan> plans, RecyclerViewClickListener listener) {
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
    public PlanAdapter.PlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_row, parent, false);
        return new PlanViewHolder(v, mListener);
    }

    // Associate the data with the view holder for a given position in the RecyclerView
    @Override
    public void onBindViewHolder(@NonNull PlanAdapter.PlanViewHolder holder, int position) {
        Plan plan = mPlans.get(position);
        holder.course.setText(plan.getCourseName());
        holder.progress.setText(plan.getProgress());
        holder.days.setText(plan.getDays());
        holder.progressBar.setProgress((int) (Double.valueOf(plan.getProgress()) * 100 / Double.valueOf(plan.getDays())));
        holder.itemView.setTag(plan.getPlanId());
    }

    // Return the number of data items available to display
    @Override
    public int getItemCount() {
        return mPlans.size();
    }

    // Extend the signature of PlanViewHolder to implement a click listener
    public static class PlanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView course;
        private final TextView days;
        private final TextView progress;
        private final ProgressBar progressBar;
        private final RecyclerViewClickListener listener;

        // A constructor method for PlanViewHolder class
        public PlanViewHolder(@NonNull View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            this.listener = listener;
            itemView.setOnClickListener(this);
            course = itemView.findViewById(R.id.tvPname);
            days = itemView.findViewById(R.id.tvPTotal);
            progress = itemView.findViewById(R.id.tvPProgress);
            progressBar = itemView.findViewById(R.id.progressBar);
        }

        @Override
        public void onClick(View v) {
            listener.onPlanClick(v, String.valueOf(v.getTag()));
        }
    }
}