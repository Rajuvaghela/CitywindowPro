package com.citywindowpro.Adapter;

/**
 * Created by raju on 24-08-2017.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.citywindowpro.LiveOfferDetailActivity;
import com.citywindowpro.LiveOfferSubcategory1Activity;
import com.citywindowpro.Model.LiveOfferModel;
import com.citywindowpro.R;

import java.util.ArrayList;
import java.util.List;


public class LiveOfferAdapter1 extends RecyclerView.Adapter<LiveOfferAdapter1.MyViewHolder> {


    ArrayList<String> itemNames;
    List<LiveOfferModel> list_model;
    Context context;

    public LiveOfferAdapter1(Context context, List<LiveOfferModel> list_model) {
        this.context = context;
        this.list_model = list_model;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_live_offer, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.textView_description.setText(list_model.get(position).getDescription());
        holder.tv_product_name.setText(list_model.get(position).getPro_name());
        holder.tv_new_price.setText(list_model.get(position).getOff_price());
        holder.tv_old_price.setText(list_model.get(position).getPro_price());
        holder.tv_old_price.setPaintFlags(holder.tv_old_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        Glide.with(context).load(list_model.get(position).getProduct_image()).into(holder.iv_product_image);
        //holder.image.setImageResource(itemImages.get(position));
/*

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, LiveOfferDetailActivity.class);
                intent.putExtra("image", itemImages.get(position));
                context.startActivity(intent);
            }
        });*/

    }


    @Override
    public int getItemCount() {
        return list_model.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView_description, tv_product_name, tv_new_price, tv_old_price;
        ImageView iv_product_image;

        public MyViewHolder(View itemView) {
            super(itemView);


            textView_description = (TextView) itemView.findViewById(R.id.textView_description);
            tv_product_name = (TextView) itemView.findViewById(R.id.tv_product_name);
            tv_new_price = (TextView) itemView.findViewById(R.id.tv_new_price);
            tv_old_price = (TextView) itemView.findViewById(R.id.tv_old_price);

            iv_product_image = (ImageView) itemView.findViewById(R.id.iv_product_image);

        }
    }
}