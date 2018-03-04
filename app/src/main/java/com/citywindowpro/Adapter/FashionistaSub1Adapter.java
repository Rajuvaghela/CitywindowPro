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
import com.citywindowpro.Model.FiveMainsubcatgory;
import com.citywindowpro.R;

import java.util.ArrayList;
import java.util.List;




public class FashionistaSub1Adapter extends RecyclerView.Adapter<FashionistaSub1Adapter.MyViewHolder> {

    Context context;
    List<FiveMainsubcatgory> listuser = new ArrayList<>();

    public FashionistaSub1Adapter(Context context, List<FiveMainsubcatgory> listuser) {
        this.context = context;
        this.listuser = listuser;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_fashion_subcategory1, parent, false);
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

               holder.tv_item_name.setText(listuser.get(position).getSubname());
        Glide.with(context).load(listuser.get(position).getImg()).into(holder.iv_subcategory1_image);

    }


    @Override
    public int getItemCount() {
        return listuser.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_item_name;
        ImageView iv_subcategory1_image;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_item_name = (TextView) itemView.findViewById(R.id.tv_item_name);
            iv_subcategory1_image = (ImageView) itemView.findViewById(R.id.iv_subcategory1_image);


        }
    }
}