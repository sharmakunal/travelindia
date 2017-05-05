package com.travelindia.kunalsharma.travelindia.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.travelindia.kunalsharma.travelindia.PojoClasses.Place;
import com.travelindia.kunalsharma.travelindia.R;

import java.util.List;


public class PlaceRecyclerViewAdapter extends RecyclerView.Adapter<PlaceRecyclerViewAdapter.PlaceImageViewHolder> {
    private static final String TAG = "PlaceRecyclerViewAdapt";
    private List<Place> mPlaceList;
    private Context mContext;
    private List<Place> mFilteredList;

    public PlaceRecyclerViewAdapter(Context context, List<Place> photosList) {
        mContext = context;
        mPlaceList = photosList;
        mFilteredList = photosList;
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

        Place photoItem = mFilteredList.get(position);

        Log.d(TAG, "onBindViewHolder: " + photoItem.getPname() + " --> " + position);
        Picasso.with(mContext).load(photoItem.getPthumbnail())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.thumbnail);

        holder.title.setText(photoItem.getPname());
        holder.state.setText(photoItem.getPState());
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: called");
        return ((mFilteredList != null) && (mFilteredList.size() != 0) ? mFilteredList.size() : 0);
    }

    public void loadNewData(List<Place> newPhotos) {
        mFilteredList = newPhotos;
        notifyDataSetChanged();
    }

    public Place getPhoto(int position) {
        return ((mFilteredList != null) && (mFilteredList.size() != 0) ? mFilteredList.get(position) : null);
    }


/*    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = mPlaceList;
                } else {

                    List<Place> filteredList = new ArrayList<>();

                    for (Place place : mPlaceList) {

                        if (place.getPcity().toLowerCase().contains(charString) || place.getPname().toLowerCase().contains(charString) || place.getPState().toLowerCase().contains(charString)) {

                            filteredList.add(place);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (List<Place>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

*/
    static class PlaceImageViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "PlaceImageViewHolder";
        ImageView thumbnail = null;
        TextView title = null;
        TextView state = null;

        public PlaceImageViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "PlaceImageViewHolder: starts");
            this.thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            this.title = (TextView) itemView.findViewById(R.id.title);
            this.state = (TextView) itemView.findViewById(R.id.state);
        }
    }
}