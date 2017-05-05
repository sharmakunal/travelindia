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
import com.travelindia.kunalsharma.travelindia.PojoClasses.Category;
import com.travelindia.kunalsharma.travelindia.R;

import java.util.List;

public class CategoryRecylerViewAdapter extends RecyclerView.Adapter<CategoryRecylerViewAdapter.CategoryImageViewHolder> {

    private static final String TAG = "CategoryRecylerViewAdapter";
    private List<Category> mPlaceList;
    private Context mContext;
    private List<Category> list ;

    public CategoryRecylerViewAdapter(Context context, List<Category> photosList) {
        mContext = context;
        mPlaceList = photosList;
    }

    @Override
    public CategoryImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Called by the layout manager when it needs a new view

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categorybrowse, parent, false);
        return new CategoryImageViewHolder(view);
    }

  @Override
    public void onBindViewHolder(CategoryRecylerViewAdapter.CategoryImageViewHolder holder, final int position) {
        // Called by the layout manager when it wants new data in an existing row

        final Category photoItem = mPlaceList.get(position);

        Log.d(TAG, "onBindViewHolder: " + photoItem.getCatName() + " --> " + position);
        Picasso.with(mContext).load(photoItem.getCatthumbnail())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.thumbnail);

        holder.title.setText(photoItem.getCatName());
}

    @Override
    public int getItemCount() {

        Log.d(TAG, "getItemCount: called");
        return ((mPlaceList != null) && (mPlaceList.size() !=0) ? mPlaceList.size() : 0);
    }

    public void loadNewData(List<Category> newPhotos) {
        mPlaceList = newPhotos;
        notifyDataSetChanged();
    }

    public Category getPhoto(int position) {
        return ((mPlaceList != null) && (mPlaceList.size() !=0) ? mPlaceList.get(position) : null);
    }

    static class CategoryImageViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "CategoryImageViewHolder";
        ImageView thumbnail = null;
        TextView title = null;

        public CategoryImageViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "PlaceImageViewHolder: starts");
            this.thumbnail = (ImageView) itemView.findViewById(R.id.categorythumbnail);
            this.title = (TextView) itemView.findViewById(R.id.categorytitle);
        }
    }
}
