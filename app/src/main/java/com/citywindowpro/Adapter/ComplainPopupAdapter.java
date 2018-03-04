package com.citywindowpro.Adapter;

/**
 * Created by raju on 20-07-2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.citywindowpro.Model.LiveOfferModel;
import com.citywindowpro.R;


import java.util.ArrayList;
import java.util.List;


public class ComplainPopupAdapter extends RecyclerView.Adapter<ComplainPopupAdapter.MyViewHolder> {

    List<LiveOfferModel> listuser = new ArrayList<>();
    Context context;

    public ComplainPopupAdapter(Context context, List<LiveOfferModel> listuser) {
        this.context = context;
        this.listuser = listuser;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_popup_comlain, parent, false);
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.name.setText(listuser.get(position).getPro_name());
        Glide.with(context).load(listuser.get(position).getProduct_image()).into(holder.image);


    }


    @Override
    public int getItemCount() {
        return listuser.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView name;
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);

            // get the reference of item view's
            name = (TextView) itemView.findViewById(R.id.tv_item_name);
            image = (ImageView) itemView.findViewById(R.id.iv_item_image);

        }
    }
}