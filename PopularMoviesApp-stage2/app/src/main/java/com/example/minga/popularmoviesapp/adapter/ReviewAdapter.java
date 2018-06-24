package com.example.minga.popularmoviesapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.minga.popularmoviesapp.R;
import com.example.minga.popularmoviesapp.model.Review;

import java.util.ArrayList;

/**
 * Created by minga on 6/8/2018.
 */

public class ReviewAdapter extends ArrayAdapter<Review> {
    private static final String LOG_TAG= ReviewAdapter.class.getSimpleName();

    public ReviewAdapter(Context context, ArrayList<Review> reviews){
        super(context, 0, reviews);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Review review = getItem(position);

        if (convertView == null) {
            // List item layout
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_review, parent, false);
        }

        TextView authorTv = (TextView ) convertView.findViewById(R.id.author_tv);
        authorTv.setText (review.getAuthor () + "  wrote:");
        TextView contentTv = (TextView ) convertView.findViewById(R.id.content_tv);
        contentTv.setText(review.getContent ());

        return convertView;
    }
}
