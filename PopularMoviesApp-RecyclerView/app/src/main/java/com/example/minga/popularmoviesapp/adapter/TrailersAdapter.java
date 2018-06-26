package com.example.minga.popularmoviesapp.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minga.popularmoviesapp.R;
import com.example.minga.popularmoviesapp.model.Trailer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by minga on 6/24/2018.
 */

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.ViewHolder> {
    private Context context;

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView trailerNameTv;
        public ImageView trailerPlayIv;
        public ViewHolder(View itemView){
            super(itemView);
            trailerNameTv=(TextView)itemView.findViewById (R.id.trailerName_tv);
            trailerPlayIv=(ImageView)itemView.findViewById (R.id.playIcon_iv);
        }
    }

    private ArrayList<Trailer> mTrailers;
    public TrailersAdapter(Context c, ArrayList<Trailer> trailers){
        this.context = c;
        mTrailers = trailers;
    }

    @Override
    public TrailersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext ();
        LayoutInflater inflater = LayoutInflater.from (context);

        View trailerView = inflater.inflate (R.layout.list_item_trailer, parent, false);

        ViewHolder viewHolder = new ViewHolder (trailerView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrailersAdapter.ViewHolder holder, int position) {
        final Trailer trailer = mTrailers.get (position);

        TextView textView = holder.trailerNameTv;
        textView.setText (trailer.getName ());
        ImageView imageView = holder.trailerPlayIv;

        Picasso.with (holder.itemView.getContext ())
                .load (R.drawable.icons8_play_50)
                .error (R.drawable.ic_android_black_24dp)
                .fit ()
                .into (imageView);

        holder.itemView.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                openTrailerVideo (context, trailer.getYoutubeId ());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTrailers.size();
    }

    public void updateRvAdapter(ArrayList<Trailer> jTrailers){
        if(mTrailers != null){
            mTrailers.clear ();
            mTrailers.addAll(jTrailers);
        } else{
            mTrailers = jTrailers;
        }
        notifyDataSetChanged ();
    }

    public void openTrailerVideo(Context context, String youtube_id){
        Intent appIntent = new Intent (Intent.ACTION_VIEW, Uri.parse ("vnd.youtube:"+youtube_id));
        Intent webIntent = new Intent (Intent.ACTION_VIEW, Uri.parse ("http://www.youtube.com/watch?v=" + youtube_id));
        try {
            context.startActivity (appIntent);
        } catch (ActivityNotFoundException ex){
            if(webIntent.resolveActivity(context.getPackageManager())!=null){
                context.startActivity (webIntent);
            }
        }
    }

}
