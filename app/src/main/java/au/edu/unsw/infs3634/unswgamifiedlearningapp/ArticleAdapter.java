package au.edu.unsw.infs3634.unswgamifiedlearningapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;


public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.MyViewHolder> {
    private final ArrayList<Article> mArticle;
    private final RecyclerViewClickListener mListener;
    public Context mContext;

    // A constructor method for the adapter class
    ArticleAdapter(ArrayList<Article> Article, RecyclerViewClickListener listener) {
        this.mArticle = Article;
        this.mListener = listener;
    }


    // ClickListener interface
    public interface RecyclerViewClickListener {
        void onArticleClick(View view, String url);
    }

    // Create a view and return it
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_row, parent, false);
        mContext = parent.getContext();
        return new MyViewHolder(view, mListener);
    }

    // Associate the data with the view holder for a given position in the RecyclerView
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Retrieve the country by it's position in filtered list
        Article article = mArticle.get(position);

        //Avoid null image reference
        try {
            holder.title.setText(article.getTitle());
            Glide.with(holder.itemView)
                    .load(article.getUrlToImage().toLowerCase())
                    .apply(new RequestOptions().override(100,100))
                    .into(holder.imageId);
            holder.itemView.setTag(article.getUrl());

        } catch (Exception e) {
            holder.imageId.setVisibility(View.GONE);
            holder.title.setPadding(20, 20, 20, 20);
        }

    }


    // Return the number of data items available to display
    @Override
    public int getItemCount() {
        return mArticle.size();
    }

    // Extend the signature of CountryViewHolder to implement a click listener
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public ImageView imageId;
        private final RecyclerViewClickListener listener;

        // A constructor method for CountryViewHolder class
        public MyViewHolder(@NonNull View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            this.listener = listener;
            itemView.setOnClickListener(MyViewHolder.this);
            title = itemView.findViewById(R.id.tvRecTitle);
            imageId = itemView.findViewById(R.id.ivRecImage);
        }

        @Override
        public void onClick(View v) {
            listener.onArticleClick(v, (String) v.getTag());

        }
    }

    //Update and push latest news
    public void setArticle(ArrayList<Article> data) {
        mArticle.clear();
        mArticle.addAll(data);
        notifyDataSetChanged();
    }


}



