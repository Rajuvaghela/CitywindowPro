package com.citywindowpro.Adapter;

/**
 * Created by raju on 31-07-2017.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.citywindowpro.Model.FiveMainsubcatgory;
import com.citywindowpro.R;
import com.citywindowpro.YummyFoodsMoreDetailsActivity;

import java.util.ArrayList;
import java.util.List;


public class OfferDetailAdapter extends RecyclerView.Adapter<OfferDetailAdapter.MyViewHolder> {
    Context context;
    List<FiveMainsubcatgory> rv_list = new ArrayList<>();

    public OfferDetailAdapter(Context context, List<FiveMainsubcatgory> rv_list) {
        this.context = context;
        this.rv_list = rv_list;


    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_product_list, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.tv_product_list_price.setText(rv_list.get(position).getOffer_lastpage_rv_product_price());
        holder.tv_product_list_name.setText(rv_list.get(position).getOffer_lastpage_rv_product_name());
        Glide.with(context).load(rv_list.get(position).getOffer_lastpage_rv_product_image_name()).into(holder.iv_product_list_image);
    }


    @Override
    public int getItemCount() {
        return rv_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_product_list_name, tv_product_list_price;
        ImageView iv_product_list_image;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_product_list_price = (TextView) itemView.findViewById(R.id.tv_product_list_price);
            tv_product_list_name = (TextView) itemView.findViewById(R.id.tv_product_list_name);
            iv_product_list_image = (ImageView) itemView.findViewById(R.id.iv_product_list_image);


        }
    }
}