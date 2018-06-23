package com.example.minga.popularmoviesapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minga.popularmoviesapp.R;
import com.example.minga.popularmoviesapp.model.Trailer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

/**
 * Created by minga on 6/6/2018.
 */

public class TrailerAdapter extends ArrayAdapter<Trailer> {
    private static final String LOG_TAG= TrailerAdapter.class.getSimpleName();

    public TrailerAdapter(Context context, ArrayList<Trailer> trailers){
        super(context, 0,trailers);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Trailer trailer = getItem(position);

        if (convertView == null) {
            // List item layout
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_trailer, parent, false);
        }

        ImageView iconView = (ImageView ) convertView.findViewById(R.id.playIcon_iv);
        //iconView.setImageResource(R.drawable.icons8_play_50);
        Picasso.with (getContext ())
                .load (R.drawable.icons8_play_50)
                .error (R.drawable.ic_android_black_24dp)
                .fit ()
                .into (iconView);

        TextView trailerName = (TextView ) convertView.findViewById(R.id.trailerName_tv);
        trailerName.setText(trailer.getName ());


        return convertView;
    }
}
