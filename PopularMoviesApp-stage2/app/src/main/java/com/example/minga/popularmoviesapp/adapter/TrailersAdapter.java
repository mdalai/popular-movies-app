package com.example.minga.popularmoviesapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minga.popularmoviesapp.R;
import com.example.minga.popularmoviesapp.model.Trailer;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

/**
 * Created by minga on 6/22/2018.
 */

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.ViewHolder>{

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

        View trailerView = inflater.inflate (R.layout.item_trailer, parent, false);

        ViewHolder viewHolder = new ViewHolder (trailerView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrailersAdapter.ViewHolder holder, int position) {
        Trailer trailer = mTrailers.get (position);

        TextView textView = holder.trailerNameTv;
        textView.setText (trailer.getName ());
        ImageView imageView = holder.trailerPlayIv;

        Picasso.with (holder.itemView.getContext ())
                .load (R.drawable.icons8_play_50)
                .error (R.drawable.ic_android_black_24dp)
                .fit ()
                .into (imageView);
    }

    @Override
    public int getItemCount() {
        return mTrailers.size();
    }

}
