package com.citywindowpro.Adapter;

/**
 * Created by raju on 31-07-2017.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.citywindowpro.FiveMainProductSubcategory1Activity;
import com.citywindowpro.Model.FiveMainsubcatgory;
import com.citywindowpro.R;
import com.citywindowpro.YummyFoodsMoreDetailsActivity;

import java.util.ArrayList;
import java.util.List;


public class FiveMainProductSubcategory1Adapter extends RecyclerView.Adapter<FiveMainProductSubcategory1Adapter.MyViewHolder> {
    private Typeface tf;
    Context context;
    List<FiveMainsubcatgory> listuser = new ArrayList<>();

    public FiveMainProductSubcategory1Adapter(Context context, List<FiveMainsubcatgory> listuser) {
        this.context = context;
        this.listuser = listuser;
        this.tf = Typeface.createFromAsset(context.getAssets(),
                "refsan.TTF");

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_five_main_product_subcategory1, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.tv_subcategory1_name.setTypeface(this.tf);
        holder.tv_subcategory1_name.setText(listuser.get(position).getSubname());
        Glide.with(context).load(listuser.get(position).getImg()).into(holder.iv_subcategory1_image);
/*        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, YummyFoodsMoreDetailsActivity.class);
                context.startActivity(intent);
            }
        });*/
    }


    @Override
    public int getItemCount() {
        return listuser.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView tv_subcategory1_name;
        ImageView iv_subcategory1_image;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_subcategory1_name = (TextView) itemView.findViewById(R.id.tv_subcategory1_name);
            iv_subcategory1_image = (ImageView) itemView.findViewById(R.id.iv_subcategory1_image);


        }
    }
}