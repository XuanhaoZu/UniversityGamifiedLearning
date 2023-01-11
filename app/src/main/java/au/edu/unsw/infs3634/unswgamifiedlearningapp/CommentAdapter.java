package au.edu.unsw.infs3634.unswgamifiedlearningapp;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;
import java.util.concurrent.Executors;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private final List<Comment> mComments;
    private final RecyclerViewClickListener mListener;
    public Context mContext;
    private AppDatabase mDb;

    //constructor
    public CommentAdapter(List<Comment> comments, RecyclerViewClickListener listener){
        mComments = comments;
        mListener = listener;
    }


    // ClickListener interface
    public interface RecyclerViewClickListener {
        void onCommentClick(View view, String cid);
    }

    @NonNull
    @Override
    public CommentAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_row, parent, false);
        mContext = parent.getContext();
        return new CommentViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.CommentViewHolder holder, int position) {
        // Retrieve the comment by it's position in filtered list
        Comment comment = mComments.get(position);
        holder.comment.setText(comment.getContent());

        // Instantiate a CountryDatabase object for "country-database"
        mDb = Room.databaseBuilder(mContext, AppDatabase.class, "app-database").build();

        //Retrieve userName and userImage by userId
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                User user = mDb.userDao().getUser(comment.getUserId());

                ((Activity)mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        holder.userName.setText(user.getName());
                        holder.userImage.setImageResource(user.getImageId());
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() { return mComments.size(); }

    // Extend the signature of CommentViewHolder to implement a click listener
    public static class CommentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView comment;
        private final TextView userName;
        private final ImageView userImage;
        private final RecyclerViewClickListener listener;

        // A constructor method for CommentViewHolder class
        public CommentViewHolder(@NonNull View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            this.listener = listener;
            itemView.setOnClickListener(this);
            comment = itemView.findViewById(R.id.tvComment);
            userName = itemView.findViewById(R.id.tvCusername);
            userImage = itemView.findViewById(R.id.ivCuserimage);
        }

        @Override
        public void onClick(View v) {
            listener.onCommentClick(v, (String) v.getTag());
        }

    }


    //Update comments if user add new comment
    public void setComments(List<Comment> data){
        mComments.clear();
        mComments.addAll(data);
        notifyDataSetChanged();
    }

}

