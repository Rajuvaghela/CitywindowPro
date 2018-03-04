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
import android.widget.TextView;

import com.citywindowpro.FiveMainProductSubcategory1Activity;
import com.citywindowpro.R;
import com.citywindowpro.YummyFoodsMoreDetailsActivity;


public class RealEstateSubcategory2Adapter extends RecyclerView.Adapter<RealEstateSubcategory2Adapter.MyViewHolder> {

    Context context;

    public RealEstateSubcategory2Adapter(Context context) {
        this.context = context;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_five_main_product_subcategory1, parent, false);
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, YummyFoodsMoreDetailsActivity.class);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return 30;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        public MyViewHolder(View itemView) {
            super(itemView);


        }
    }
}