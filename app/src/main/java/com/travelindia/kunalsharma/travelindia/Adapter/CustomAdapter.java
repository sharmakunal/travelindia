package com.travelindia.kunalsharma.travelindia.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.travelindia.kunalsharma.travelindia.Activities.PlaceAccordingToStateActivity;
import com.travelindia.kunalsharma.travelindia.PojoClasses.State;
import com.travelindia.kunalsharma.travelindia.R;
import java.util.List;

public class CustomAdapter extends BaseAdapter{


    private static final String TAG = "StateViewAdapater";
    List<State> mphotosList;
    private Context Context;

    private static LayoutInflater inflater=null;
    public CustomAdapter(Context context, List<State> photosList) {
        // TODO Auto-generated constructor stub

        this.Context=context;
        this.mphotosList=photosList;
        this.inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    public State getItem(int position) {
        // TODO Auto-generated method stub
        return mphotosList.get(position);
    }

    public void loadNewData(List<State> newPhotos) {
        mphotosList = newPhotos;
        this.notifyDataSetChanged();
    }

    public class Holder
    {
        TextView os_text;
        ImageView os_img;
        int pos;

        public Holder(View convertView,  int pos) {
            os_text =(TextView) convertView.findViewById(R.id.os_texts1);
            os_img =(ImageView) convertView.findViewById(R.id.os_images);
            this.pos=pos;

             /*final State c = (State) getItem(pos);
              int a = c.getStateid();
              final int stateid = a;

            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                  //  Log.d(TAG, String.valueOf(pos));
                    Toast.makeText(v.getContext(), "You Clicked "+ stateid, Toast.LENGTH_SHORT).show();
                    Intent comment_page=new Intent(v.getContext(),PlaceAccordingToStateActivity.class);
                    comment_page.putExtra("position",c);
                    v.getContext().startActivity(comment_page);
                }
            });*/
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

}