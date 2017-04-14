package com.travelindia.kunalsharma.travelindia;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by kunal sharma on 02-Apr-17.
 */

class PlaceRecyclerViewAdapter extends RecyclerView.Adapter<PlaceRecyclerViewAdapter.PlaceImageViewHolder> {
    private static final String TAG = "PlaceRecyclerViewAdapt";
    private List<Place> mPlaceList;
    private Context mContext;

    public PlaceRecyclerViewAdapter(Context context, List<Place> photosList) {
        mContext = context;
        mPlaceList = photosList;
    }

    @Override
    public PlaceImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Called by the layout manager when it needs a new view
        Log.d(TAG, "onCreateViewHolder: new view requested");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse, parent, false);
        return new PlaceImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlaceImageViewHolder holder, int position) {
        // Called by the layout manager when it wants new data in an existing row

        Place photoItem = mPlaceList.get(position);

        Log.d(TAG, "onBindViewHolder: " + photoItem.getPname() + " --> " + position);
        Picasso.with(mContext).load(photoItem.getPthumbnail())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.thumbnail);

        holder.title.setText(photoItem.getPname());
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: called");
        return ((mPlaceList != null) && (mPlaceList.size() !=0) ? mPlaceList.size() : 0);
    }

    void loadNewData(List<Place> newPhotos) {
        mPlaceList = newPhotos;
        notifyDataSetChanged();
    }

    public Place getPhoto(int position) {
        return ((mPlaceList != null) && (mPlaceList.size() !=0) ? mPlaceList.get(position) : null);
    }

    static class PlaceImageViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "PlaceImageViewHolder";
        ImageView thumbnail = null;
        TextView title = null;

        public PlaceImageViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "PlaceImageViewHolder: starts");
            this.thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            this.title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}