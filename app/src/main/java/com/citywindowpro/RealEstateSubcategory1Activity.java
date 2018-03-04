package com.citywindowpro;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.citywindowpro.Adapter.FiveMainProductSubcategory1Adapter;
import com.citywindowpro.Model.FiveMainsubcatgory;
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

public class RealEstateSubcategory1Activity extends AppCompatActivity {
    RecyclerView rv_realestste_subcategory1_main;
    RecyclerView.Adapter listAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    ProgressDialog dialog;
    List<FiveMainsubcatgory> listuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_estate_subcategory1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rv_realestste_subcategory1_main = (RecyclerView) findViewById(R.id.rv_realestste_subcategory1_main);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        listuser=new ArrayList<>();
        subcategorylistload();
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



    public void subcategorylistload(){
        if (isNetworkAvailable()) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
            params.add("sub_id", Constants.sub_id);
            client.post(Constants.liveuri+"sel_child_sub.php",params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {
                        listuser.clear();

                        try {
                            Log.d("res",responseString);
                            //Toast.makeText(getContext().getApplicationContext(), res.toString(), Toast.LENGTH_SHORT).show();
                            JSONObject jsonObj = new JSONObject(responseString);
                            JSONArray resultsarray = jsonObj.getJSONArray("response");
                            // looping through All Contacts
                            for (int i = 0; i < resultsarray.length(); i++) {
                                FiveMainsubcatgory temp = new FiveMainsubcatgory();
                                temp.setSubid( resultsarray.getJSONObject(i).getString("child_id"));
                                temp.setSubname(resultsarray.getJSONObject(i).getString("child_name"));
                                temp.setImg(Constants.liveimageuri+resultsarray.getJSONObject(i).getString("c_img"));
                                listuser.add(temp);
                                dialog.dismiss();
                            }
                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }
                        rv_realestste_subcategory1_main.setHasFixedSize(true);
                        mLayoutManager = new LinearLayoutManager(RealEstateSubcategory1Activity.this, LinearLayoutManager.VERTICAL, false);
                        rv_realestste_subcategory1_main.setLayoutManager(mLayoutManager);
                        listAdapter = new FiveMainProductSubcategory1Adapter(RealEstateSubcategory1Activity.this,listuser);
                        rv_realestste_subcategory1_main.setAdapter(listAdapter);
                        rv_realestste_subcategory1_main.addOnItemTouchListener(new RecyclerItemClickListener(RealEstateSubcategory1Activity.this, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Constants.child_id=listuser.get(position).getSubid();
                                startActivity(new Intent(getApplicationContext(), RealEstateSubcategory2Activity.class));
                            }
                        }));
                    }
                }
            });
        }else {
            Toast.makeText(RealEstateSubcategory1Activity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }




}
