package com.citywindowpro.Adapter;

/**
 * Created by raju on 20-07-2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.citywindowpro.LiveOfferDetailActivity;
import com.citywindowpro.R;

import java.util.ArrayList;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {


    ArrayList<String> itemNames;
    ArrayList<Integer> itemImages;
    Context context;

    public CustomAdapter(Context context, ArrayList<String> personNames, ArrayList<Integer> personImages) {
        this.context = context;
        this.itemNames = personNames;
        this.itemImages = personImages;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
          View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_live_offer, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
            //holder.name.setText(itemNames.get(position));
        //holder.image.setImageResource(itemImages.get(position));


           holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, LiveOfferDetailActivity.class);
                intent.putExtra("image", itemImages.get(position));
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return itemNames.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);


           // name = (TextView) itemView.findViewById(R.id.tv_item_name);
           // image = (ImageView) itemView.findViewById(R.id.iv_item_image);

        }
    }
}