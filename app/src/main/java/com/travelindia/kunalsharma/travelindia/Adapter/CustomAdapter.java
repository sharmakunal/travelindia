package com.travelindia.kunalsharma.travelindia.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.travelindia.kunalsharma.travelindia.PogoClasses.State;
import com.travelindia.kunalsharma.travelindia.R;
import java.util.List;

public class CustomAdapter extends BaseAdapter{

    List<State> mphotosList;
    private Context Context;
    private StateClickListener mStateClickListener;

    private static LayoutInflater inflater=null;
    public CustomAdapter(Context context, List<State> photosList,StateClickListener stateClickListener) {
        // TODO Auto-generated constructor stub

        this.Context=context;
        this.mphotosList=photosList;
        this.inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mStateClickListener=stateClickListener;

    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mphotosList.size();
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mphotosList.get(position);
    }

    public void loadNewData(List<State> newPhotos) {
        mphotosList = newPhotos;
        this.notifyDataSetChanged();
    }

    public class Holder implements View.OnClickListener
    {
        TextView os_text;
        ImageView os_img;
        int pos;

        public Holder(View convertView,int pos) {
            os_text =(TextView) convertView.findViewById(R.id.os_texts1);
            os_img =(ImageView) convertView.findViewById(R.id.os_images);
            this.pos=pos;
            convertView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mStateClickListener.onStateClick(pos);
        }
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
       // Holder holder=new Holder(convertView);
       // View rowView;

        Holder holder;// = new Holder(convertView);

        if(convertView==null){
            convertView=LayoutInflater.from(Context).inflate(R.layout.gridlayout,parent,false);
            holder= new Holder(convertView,position);
            convertView.setTag(holder);
        }else{
            holder=(Holder)convertView.getTag();
        }


        final State photoItem = mphotosList.get(position);
      //  rowView = inflater.inflate(R.layout.gridlayout, null);


        holder.os_text.setText(photoItem.getStateName());
        Picasso.with(Context).load(photoItem.getStatethumbnail())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.os_img);

        return convertView;
    }
    public interface StateClickListener{
         void onStateClick(int pos);
    }

}