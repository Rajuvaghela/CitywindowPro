package com.citywindowpro.Adapter;

/**
 * Created by raju on 20-07-2017.
 */

import android.content.Context;
import android.content.Intent;
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
import com.citywindowpro.YummyFoodsDetailActivity;

import java.util.ArrayList;
import java.util.List;


public class FiveMainProductAdapter extends RecyclerView.Adapter<FiveMainProductAdapter.MyViewHolder> {

    private final Typeface tf;
    List<FiveMainsubcatgory> listuser=new ArrayList<>();
    Context context;

    public FiveMainProductAdapter(Context context,List<FiveMainsubcatgory> listuser) {
        this.context = context;
        this.listuser = listuser;
        this.tf= Typeface.createFromAsset(context.getAssets(),
                "refsan.TTF");
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_yummy_foods, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // set the data in items
        holder.name.setTypeface(this.tf);
        holder.name.setText(listuser.get(position).getSubname());
        Glide.with(context).load(listuser.get(position).getImg()).into(holder.image);





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