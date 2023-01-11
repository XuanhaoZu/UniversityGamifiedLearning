package au.edu.unsw.infs3634.unswgamifiedlearningapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> implements Filterable {
    private final ArrayList<Course> mCourses;
    private ArrayList<Course> mCourseFiltered;
    private final RecyclerViewClickListener mListener;
    public static final int SORT_METHOD_NAME = 1;
    public static final int SORT_METHOD_DIFFICULTY = 2;
    public Context mContext;

    // A constructor method for the adapter class
    public CourseAdapter(ArrayList<Course> courses, RecyclerViewClickListener listener) {
        mCourses = courses;
        mCourseFiltered = courses;
        mListener = listener;
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    mCourseFiltered = mCourses;
                } else {
                    ArrayList<Course> filteredList = new ArrayList<>();
                    for (Course courses : mCourses) {
                        if (courses.getCourseName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(courses);
                        }
                    }
                    mCourseFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mCourseFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {

                mCourseFiltered = (ArrayList<Course>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    // ClickListener interface
    public interface RecyclerViewClickListener {
        void onCourseClick(View view, String CourseId);
    }

    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_row, parent, false);
        mContext = parent.getContext();
        return new CourseViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        Course courses = mCourseFiltered.get(position);
        holder.name.setText(courses.getCourseName());
        holder.type.setText(courses.getCourseType());
        holder.duration.setText(courses.getCourseDuration() + "min");
        holder.equipment.setText(courses.getCourseEquipment());
        holder.difficulty.setText(courses.getCourseDifficulty());
        holder.calorie.setText(courses.getCourseCalorie());

        holder.itemView.setTag(courses.getCourseId());
    }

    @Override
    public int getItemCount() {
        return mCourseFiltered.size();
    }

    // Extend the signature of CountryViewHolder to implement a click listener
    public static class CourseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView name;
        private final TextView type;
        private final TextView duration;
        private final TextView equipment;
        private final TextView difficulty;
        private final TextView calorie;
        private final RecyclerViewClickListener listener;

        // A constructor method for CourseViewHolder class
        public CourseViewHolder(@NonNull View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            this.listener = listener;
            itemView.setOnClickListener(this);
            name = itemView.findViewById(R.id.tvCourseName);
            type = itemView.findViewById(R.id.tvType);
            duration = itemView.findViewById(R.id.tvDuration);
            equipment = itemView.findViewById(R.id.tvEquipment);
            difficulty = itemView.findViewById(R.id.tvDifficulty);
            calorie = itemView.findViewById(R.id.tvCalorie);

        }

        @Override
        public void onClick(View v) {
            listener.onCourseClick(v, (String) v.getTag());
        }
    }

    // Use the Java Collection.sort() and Comparator methods to sort the list - SORT BY NAME & DIFFICULTY
    public void sort(final int sortMethod) {
        if (mCourseFiltered.size() > 0) {
            Collections.sort(mCourseFiltered, new Comparator<Course>() {
                @Override
                public int compare(Course o1, Course o2) {
                    if (sortMethod == SORT_METHOD_NAME) {
                        // Sort by new cases
                        return -o2.getCourseName().compareTo(o1.getCourseName());
                    } else if (sortMethod == SORT_METHOD_DIFFICULTY) {
                        // Sort by total cases
                        return -o2.getCourseDifficulty().compareTo(o1.getCourseDifficulty());
                    }
                    return o2.getCourseName().compareTo(o1.getCourseName());
                }
            });
        }
        notifyDataSetChanged();
    }

    public void setCourse(ArrayList<Course> data) {
        mCourseFiltered.clear();
        mCourseFiltered.addAll(data);
        notifyDataSetChanged();
    }


}
