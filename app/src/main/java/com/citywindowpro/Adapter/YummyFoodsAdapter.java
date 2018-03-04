package com.citywindowpro.Adapter;

/**
 * Created by raju on 20-07-2017.
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

import com.citywindowpro.R;
import com.citywindowpro.YummyFoodsMoreDetailsActivity;


public class YummyFoodsAdapter extends RecyclerView.Adapter<YummyFoodsAdapter.MyViewHolder> {
    private Typeface tf;
    Context context;

    public YummyFoodsAdapter(Context context) {
        this.context = context;
        this.tf= Typeface.createFromAsset(context.getAssets(),
                "refsan.TTF");

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_yummy_detail, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // set the data in items

/*        Typeface face = Typeface.createFromAsset(context.getAssets(),
                "REFSAN.ttf");*/
        holder.tv.setTypeface(this.tf);
       // holder.tv.setText("DOSA.COM");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open another activity on item click
                Intent intent = new Intent(context, YummyFoodsMoreDetailsActivity.class);
                //intent.putExtra("image", itemImages.get(position)); // put image data in Intent

               // Log.e("itemImages.get",""+ itemImages.get(position));
                context.startActivity(intent); // start Intent
            }
        });
    }


    @Override
    public int getItemCount() {
        return 10;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
           tv = (TextView) itemView.findViewById(R.id.tv_item_name);



        }
    }
}