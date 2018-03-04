package com.citywindowpro.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.citywindowpro.FashionistaSubcategory1Activity;
import com.citywindowpro.R;

/**
 * Created by raju on 20-07-2017.
 */


public class FashionistaSub2Adapter extends RecyclerView.Adapter<FashionistaSub2Adapter.MyViewHolder> {
    Context context;

    public FashionistaSub2Adapter(Context context) {
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_yummy_detail, parent, false);
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.startActivity(new Intent(context,FashionistaSubcategory1Activity.class));
            }
        });
    }


    @Override
    public int getItemCount() {
        return 10;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            //tv = (TextView) itemView.findViewById(R.id.tv_item_name);


        }
    }
}