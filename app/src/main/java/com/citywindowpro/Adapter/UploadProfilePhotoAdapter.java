package com.citywindowpro.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.citywindowpro.DeleteGalleryPhotoActivity;
import com.citywindowpro.DeleteProfilePhotoActivity;
import com.citywindowpro.MerchantAcountActivity;
import com.citywindowpro.Model.FiveMainsubcatgory;
import com.citywindowpro.R;
import com.citywindowpro.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raju on 08-09-2017.
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.citywindowpro.DeleteProfilePhotoActivity;
import com.citywindowpro.Model.FiveMainsubcatgory;
import com.citywindowpro.R;
import com.citywindowpro.Utils.Constants;
import com.citywindowpro.YummyFoodsMoreDetailsActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class UploadProfilePhotoAdapter extends RecyclerView.Adapter<UploadProfilePhotoAdapter.MyViewHolder> {
    Context context;
    List<FiveMainsubcatgory> rv_list = new ArrayList<>();
    ProgressDialog dialog;
    String g_id;
    String l_id, cat_id, sub_id, child_id, ctc_id, l_name, l_img, d_contact, d_email;


    public UploadProfilePhotoAdapter(Context context, List<FiveMainsubcatgory> rv_list) {
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
        //holder.tv_product_price.setText(rv_list.get(position).getLastpage_recyclerview_product_price());
        //holder.tv_company_name.setText(rv_list.get(position).getLastpage_recyclerview_product_name());
        Glide.with(context).load(rv_list.get(position).getLastpage_recyclerview_product_image()).into(holder.iv_profile_image);
        //   Constants.delete_g_id = rv_list.get(position).getG_id();
        holder.tv_delete_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context.getApplicationContext(), DeleteProfilePhotoActivity.class);
                i.putExtra("g_id", rv_list.get(position).getG_id());
                rv_list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, rv_list.size());
                context.startActivity(i);


                //context.startActivity(new Intent(context.getApplicationContext(), DeleteProfilePhotoActivity.class));
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

    /*private void DeleteProfileRealEstate() {
        if (isNetworkAvailable()) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
            params.add("g_id", g_id);
            client.post(Constants.liveuri + "gallery_list_child_delete.php", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {

                        String str = null;
                        Log.e("res_del:", "" + responseString);
                        try {
                            JSONObject jsonObj = new JSONObject(responseString);
                            str = jsonObj.getString("success");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (str.equals("true")) {
                            Toast.makeText(context.getApplicationContext(), " Profile delete successfully!", Toast.LENGTH_SHORT).show();

                            dialog.dismiss();
                        } else if (str.equals("false")) {
                            dialog.dismiss();
                            Toast.makeText(context.getApplicationContext(), "Try Again!", Toast.LENGTH_SHORT).show();
                        } else {
                            dialog.dismiss();
                        }


                    }

                }
            });
        } else {
            Toast.makeText(context.getApplicationContext(), "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void DeleteProfileFashionista() {
        if (isNetworkAvailable()) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();

            params.add("g_id", g_id);
            client.post(Constants.liveuri + "gallery_fashion_delete.php", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {

                        String str = null;
                        Log.e("res_del:", "" + responseString);
                        try {
                            JSONObject jsonObj = new JSONObject(responseString);
                            str = jsonObj.getString("success");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (str.equals("true")) {
                            Toast.makeText(context.getApplicationContext(), " Profile delete successfully!", Toast.LENGTH_SHORT).show();

                            dialog.dismiss();
                        } else if (str.equals("false")) {
                            dialog.dismiss();
                            Toast.makeText(context.getApplicationContext(), "Try Again!", Toast.LENGTH_SHORT).show();
                        } else {
                            dialog.dismiss();
                        }


                    }

                }
            });
        } else {
            Toast.makeText(context.getApplicationContext(), "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }


    public void DeleteProfileFiveProduct() {
        if (isNetworkAvailable()) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
            params.add("g_id", g_id);
            client.post(Constants.liveuri + "gallery_delete.php", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {

                        String str = null;
                        Log.e("res_del:", "" + responseString);
                        try {
                            JSONObject jsonObj = new JSONObject(responseString);
                            str = jsonObj.getString("success");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (str.equals("true")) {
                            Toast.makeText(context.getApplicationContext(), " Profile delete successfully!", Toast.LENGTH_SHORT).show();

                            dialog.dismiss();

                        } else if (str.equals("false")) {
                            dialog.dismiss();
                            Toast.makeText(context.getApplicationContext(), "Try Again!", Toast.LENGTH_SHORT).show();
                        } else {
                            dialog.dismiss();
                        }


                    }

                }
            });
        } else {
            Toast.makeText(context.getApplicationContext(), "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }*/
}