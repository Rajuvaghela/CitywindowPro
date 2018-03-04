package com.citywindowpro.Adapter;

/**
 * Created by raju on 06-09-2017.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.citywindowpro.DeleteGalleryPhotoActivity;
import com.citywindowpro.DeleteProfilePhotoActivity;
import com.citywindowpro.Model.FiveMainsubcatgory;
import com.citywindowpro.R;
import com.citywindowpro.Utils.Constants;
import com.citywindowpro.YummyFoodsMoreDetailsActivity;

import java.util.ArrayList;
import java.util.List;


public class UploadGallaryPhotoAdapter extends RecyclerView.Adapter<UploadGallaryPhotoAdapter.MyViewHolder> {
    Context context;
    List<FiveMainsubcatgory> rv_list = new ArrayList<>();

    public UploadGallaryPhotoAdapter(Context context, List<FiveMainsubcatgory> rv_list) {
        this.context = context;
        this.rv_list = rv_list;


    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_gallary_edit, parent, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(lp);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv_product_price.setText(rv_list.get(position).getLastpage_recyclerview_product_price());
        holder.tv_company_name.setText(rv_list.get(position).getLastpage_recyclerview_product_name());
        Glide.with(context).load(rv_list.get(position).getLastpage_recyclerview_product_image()).into(holder.iv_profile_image);
        // Constants.delete_p_id = rv_list.get(position).getP_id();
        holder.tv_delete_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(context.getApplicationContext(), DeleteGalleryPhotoActivity.class);
                i.putExtra("p_id", rv_list.get(position).getP_id());
                rv_list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, rv_list.size());
                context.startActivity(i);

                // context.startActivity(new Intent(context.getApplicationContext(), DeleteGalleryPhotoActivity.class));
            }
        });

      /*  holder.tv_product_list_price.setText(rv_list.get(position).getLastpage_recyclerview_product_price());
        holder.tv_product_list_name.setText(rv_list.get(position).getLastpage_recyclerview_product_name());
        Glide.with(context).load(rv_list.get(position).getLastpage_recyclerview_product_image()).into(holder.iv_product_list_image);
   */
    }


    @Override
    public int getItemCount() {
        return rv_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_profile_image;
        TextView tv_company_name, tv_product_price;
        TextView tv_delete_product;


        public MyViewHolder(View itemView) {
            super(itemView);
            tv_delete_product = (TextView) itemView.findViewById(R.id.tv_delete_product);
            iv_profile_image = (ImageView) itemView.findViewById(R.id.iv_profile_image);
            tv_company_name = (TextView) itemView.findViewById(R.id.tv_company_name);
            tv_product_price = (TextView) itemView.findViewById(R.id.tv_product_price);


        }
    }
}