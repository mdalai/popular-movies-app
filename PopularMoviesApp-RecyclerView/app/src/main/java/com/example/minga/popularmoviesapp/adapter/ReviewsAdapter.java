package com.example.minga.popularmoviesapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minga.popularmoviesapp.R;
import com.example.minga.popularmoviesapp.model.Review;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by minga on 6/24/2018.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {
    private Context context;

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView authorTv;
        public TextView contentTv;
        public ViewHolder(View itemView){
            super(itemView);
            authorTv=(TextView)itemView.findViewById (R.id.author_tv);
            contentTv=(TextView)itemView.findViewById (R.id.content_tv);
        }
    }

    private ArrayList<Review> mReviews;
    public ReviewsAdapter(Context c, ArrayList<Review> reviews){
        this.context = c;
        mReviews = reviews;
    }

    @Override
    public ReviewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext ();
        LayoutInflater inflater = LayoutInflater.from (context);

        View reviewView = inflater.inflate (R.layout.list_item_review, parent, false);

        ViewHolder viewHolder = new ViewHolder (reviewView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewsAdapter.ViewHolder holder, int position) {
        final Review review = mReviews.get (position);

        TextView tv_author = holder.authorTv;
        tv_author.setText (review.getAuthor () + "  wrote:");
        TextView tv_content = holder.contentTv;
        tv_content.setText (review.getContent ());
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    public void updateRvAdapter(ArrayList<Review> jReviews){
        if(mReviews != null){
            mReviews.clear ();
            mReviews.addAll(jReviews);
        } else{
            mReviews = jReviews;
        }
        notifyDataSetChanged ();
    }
}
