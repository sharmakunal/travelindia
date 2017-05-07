package com.travelindia.kunalsharma.travelindia.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.travelindia.kunalsharma.travelindia.PojoClasses.Place;
import com.travelindia.kunalsharma.travelindia.R;

import java.util.List;

/**
 * Created by kunal sharma on 07-May-17.
 */

public class PlaceGridViewAdapter extends BaseAdapter {

    private static final String TAG = "PlaceGridViewAdapter";
    List<Place> mphotosList;
    private Context Context;

    private static LayoutInflater inflater=null;
    public PlaceGridViewAdapter(Context context, List<Place> photosList) {
        this.Context=context;
        this.mphotosList=photosList;
        this.inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return mphotosList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Place getItem(int position) {
        return mphotosList.get(position);
    }

    public void loadNewData(List<Place> newPhotos) {
        mphotosList = newPhotos;
        this.notifyDataSetChanged();
    }


    public class Holder
    {
        TextView thubnail_title;
        ImageView thumbnail_images;
        int pos;

        public Holder(View convertView,  int pos) {
            thubnail_title =(TextView) convertView.findViewById(R.id.thubnail_title);
            thumbnail_images =(ImageView) convertView.findViewById(R.id.thumbnail_images);
            this.pos=pos;
        }

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(convertView==null){
            convertView=LayoutInflater.from(Context).inflate(R.layout.gridlayout,parent,false);
            holder= new Holder(convertView,position);
            convertView.setTag(holder);
        }else{
            holder=(Holder)convertView.getTag();
        }

        final Place photoItem = mphotosList.get(position);

        holder.thubnail_title.setText(photoItem.getPname());
        Picasso.with(Context).load(photoItem.getPthumbnail())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.thumbnail_images);

        return convertView;
    }
}
