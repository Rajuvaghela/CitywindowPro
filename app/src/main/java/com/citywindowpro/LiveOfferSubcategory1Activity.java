package com.citywindowpro;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.citywindowpro.Adapter.FiveMainProductSubcategory1Adapter;
import com.citywindowpro.Adapter.LiveOfferAdapter1;
import com.citywindowpro.Model.FiveMainsubcatgory;
import com.citywindowpro.Model.LiveOfferModel;
import com.citywindowpro.Utils.Constants;
import com.citywindowpro.Utils.RecyclerItemClickListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class LiveOfferSubcategory1Activity extends AppCompatActivity {
    RecyclerView rv_sub_product1;
    RecyclerView.Adapter rv_listAdapter;
    RecyclerView.LayoutManager rv_layoutManager;
    ProgressDialog dialog;
    List<LiveOfferModel> list_model = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_offer_subcategory1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rv_sub_product1 = (RecyclerView) findViewById(R.id.rv_sub_product1);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);

        if (Constants.offer_cat_id.equals("2")) {
            Fashinistaload();
        } else if (Constants.offer_cat_id.equals("8")) {
            RealEstateloadcategory();

        } else {
            subcategorylistload();
        }


    }

    private void RealEstateloadcategory() {
        if (isNetworkAvailable()) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
            // params.add("sub_id", Constants.sub_id);
            params.add("cat_id", Constants.offer_cat_id);
            client.post(Constants.liveuri + "live_offer_list_child.php", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {
                        list_model.clear();

                        try {
                            Log.e("resfas", responseString);
                            //Toast.makeText(getContext().getApplicationContext(), res.toString(), Toast.LENGTH_SHORT).show();
                            JSONObject jsonObj = new JSONObject(responseString);
                            JSONArray resultsarray = jsonObj.getJSONArray("response");
                            // looping through All Contacts
                            for (int i = 0; i < resultsarray.length(); i++) {
                                LiveOfferModel temp = new LiveOfferModel();
                                temp.setLv_id(resultsarray.getJSONObject(i).getString("lv_id"));
                                temp.setDescription(resultsarray.getJSONObject(i).getString("description"));
                                temp.setPro_name(resultsarray.getJSONObject(i).getString("pro_name"));
                                temp.setL_id(resultsarray.getJSONObject(i).getString("l_id"));
                                temp.setCat_id(resultsarray.getJSONObject(i).getString("cat_id"));
                                temp.setOff_price(resultsarray.getJSONObject(i).getString("off_price"));
                                temp.setPro_price(resultsarray.getJSONObject(i).getString("pro_price"));
                                temp.setProduct_image(Constants.liveimageuri + resultsarray.getJSONObject(i).getString("pro_img"));
                                list_model.add(temp);
                                dialog.dismiss();
                            }
                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }
                        rv_sub_product1.setHasFixedSize(true);
                        rv_layoutManager = new LinearLayoutManager(LiveOfferSubcategory1Activity.this, LinearLayoutManager.VERTICAL, false);
                        rv_sub_product1.setLayoutManager(rv_layoutManager);
                        rv_listAdapter = new LiveOfferAdapter1(LiveOfferSubcategory1Activity.this, list_model);
                        rv_sub_product1.setAdapter(rv_listAdapter);
                        rv_sub_product1.addOnItemTouchListener(new RecyclerItemClickListener(LiveOfferSubcategory1Activity.this, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Constants.lv_id = list_model.get(position).getLv_id();
                                Constants.l_id = list_model.get(position).getL_id();
                                Constants.cat_id = list_model.get(position).getCat_id();
                                startActivity(new Intent(getApplicationContext(), LiveOfferDetailActivity.class));
                            }
                        }));
                    }
                }
            });
        } else {
            Toast.makeText(LiveOfferSubcategory1Activity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void Fashinistaload() {
        if (isNetworkAvailable()) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
            // params.add("sub_id", Constants.sub_id);
            params.add("cat_id", Constants.offer_cat_id);
            client.post(Constants.liveuri + "live_offer_fashion.php", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {
                        list_model.clear();

                        try {
                            Log.e("res", responseString);
                            //Toast.makeText(getContext().getApplicationContext(), res.toString(), Toast.LENGTH_SHORT).show();
                            JSONObject jsonObj = new JSONObject(responseString);
                            JSONArray resultsarray = jsonObj.getJSONArray("response");
                            // looping through All Contacts
                            for (int i = 0; i < resultsarray.length(); i++) {
                                LiveOfferModel temp = new LiveOfferModel();
                                temp.setLv_id(resultsarray.getJSONObject(i).getString("lv_id"));
                                temp.setDescription(resultsarray.getJSONObject(i).getString("description"));
                                temp.setPro_name(resultsarray.getJSONObject(i).getString("pro_name"));
                                temp.setL_id(resultsarray.getJSONObject(i).getString("l_id"));
                                temp.setCat_id(resultsarray.getJSONObject(i).getString("cat_id"));
                                temp.setOff_price(resultsarray.getJSONObject(i).getString("off_price"));
                                temp.setPro_price(resultsarray.getJSONObject(i).getString("pro_price"));
                                temp.setProduct_image(Constants.liveimageuri + resultsarray.getJSONObject(i).getString("pro_img"));
                                list_model.add(temp);
                                dialog.dismiss();
                            }
                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }
                        rv_sub_product1.setHasFixedSize(true);
                        rv_layoutManager = new LinearLayoutManager(LiveOfferSubcategory1Activity.this, LinearLayoutManager.VERTICAL, false);
                        rv_sub_product1.setLayoutManager(rv_layoutManager);
                        rv_listAdapter = new LiveOfferAdapter1(LiveOfferSubcategory1Activity.this, list_model);
                        rv_sub_product1.setAdapter(rv_listAdapter);
                        rv_sub_product1.addOnItemTouchListener(new RecyclerItemClickListener(LiveOfferSubcategory1Activity.this, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Constants.lv_id = list_model.get(position).getLv_id();
                                Constants.l_id = list_model.get(position).getL_id();
                                Constants.cat_id = list_model.get(position).getCat_id();
                                startActivity(new Intent(getApplicationContext(), LiveOfferDetailActivity.class));
                            }
                        }));
                    }
                }
            });
        } else {
            Toast.makeText(LiveOfferSubcategory1Activity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void subcategorylistload() {
        if (isNetworkAvailable()) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
            // params.add("sub_id", Constants.sub_id);
            params.add("cat_id", Constants.offer_cat_id);
            client.post(Constants.liveuri + "live_offer_list.php", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {
                        list_model.clear();

                        try {
                            Log.e("res", responseString);
                            //Toast.makeText(getContext().getApplicationContext(), res.toString(), Toast.LENGTH_SHORT).show();
                            JSONObject jsonObj = new JSONObject(responseString);
                            JSONArray resultsarray = jsonObj.getJSONArray("response");
                            // looping through All Contacts
                            for (int i = 0; i < resultsarray.length(); i++) {
                                LiveOfferModel temp = new LiveOfferModel();
                                temp.setLv_id(resultsarray.getJSONObject(i).getString("lv_id"));
                                temp.setDescription(resultsarray.getJSONObject(i).getString("description"));
                                temp.setPro_name(resultsarray.getJSONObject(i).getString("pro_name"));
                                temp.setL_id(resultsarray.getJSONObject(i).getString("l_id"));
                                temp.setCat_id(resultsarray.getJSONObject(i).getString("cat_id"));
                                temp.setOff_price(resultsarray.getJSONObject(i).getString("off_price"));
                                temp.setPro_price(resultsarray.getJSONObject(i).getString("pro_price"));

                                temp.setProduct_image(Constants.liveimageuri + resultsarray.getJSONObject(i).getString("pro_img"));
                                list_model.add(temp);
                                dialog.dismiss();
                            }
                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }
                        rv_sub_product1.setHasFixedSize(true);
                        rv_layoutManager = new LinearLayoutManager(LiveOfferSubcategory1Activity.this, LinearLayoutManager.VERTICAL, false);
                        rv_sub_product1.setLayoutManager(rv_layoutManager);
                        rv_listAdapter = new LiveOfferAdapter1(LiveOfferSubcategory1Activity.this, list_model);
                        rv_sub_product1.setAdapter(rv_listAdapter);
                        rv_sub_product1.addOnItemTouchListener(new RecyclerItemClickListener(LiveOfferSubcategory1Activity.this, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Constants.lv_id = list_model.get(position).getLv_id();
                                Constants.l_id = list_model.get(position).getL_id();
                                Constants.cat_id = list_model.get(position).getCat_id();
                                startActivity(new Intent(getApplicationContext(), LiveOfferDetailActivity.class));
                            }
                        }));
                    }
                }
            });
        } else {
            Toast.makeText(LiveOfferSubcategory1Activity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
