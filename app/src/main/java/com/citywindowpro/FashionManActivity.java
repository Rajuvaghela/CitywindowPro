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

public class FashionManActivity extends AppCompatActivity {
    RecyclerView rv_fashionista_sub3;
    RecyclerView.Adapter listAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    ProgressDialog dialog;
    List<FiveMainsubcatgory> listuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fashionista_subcategory3);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        rv_fashionista_sub3 = (RecyclerView) findViewById(R.id.rv_fashionista_sub3);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        listuser = new ArrayList<>();
        load_fashinista_subcategory2();
    }

    public void load_fashinista_subcategory2() {
        if (isNetworkAvailable()) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
            params.add("child_id", Constants.child_id);
            params.add("ctc_id", Constants.ctc_id);
            Log.e("Constants.ctc_id", "" + Constants.ctc_id);
            client.post(Constants.liveuri + "sel_fashion_list.php", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {
                        listuser.clear();

                        try {
                            Log.e("Activity3", responseString);
                            JSONObject jsonObj = new JSONObject(responseString);
                            JSONArray resultsarray = jsonObj.getJSONArray("response");
                            for (int i = 0; i < resultsarray.length(); i++) {
                                FiveMainsubcatgory temp = new FiveMainsubcatgory();
                                temp.setSubid(resultsarray.getJSONObject(i).getString("child_id"));
                                temp.setSubname(resultsarray.getJSONObject(i).getString("l_name"));
                                temp.setL_id(resultsarray.getJSONObject(i).getString("l_id"));
                                temp.setImg(Constants.liveimageuri + resultsarray.getJSONObject(i).getString("l_img"));
                                temp.setCtc_id(resultsarray.getJSONObject(i).getString("ctc_id"));
                                listuser.add(temp);
                                dialog.dismiss();
                            }
                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }
                        rv_fashionista_sub3.setHasFixedSize(true);
                        mLayoutManager = new LinearLayoutManager(FashionManActivity.this, LinearLayoutManager.VERTICAL, false);
                        rv_fashionista_sub3.setLayoutManager(mLayoutManager);
                        listAdapter = new FiveMainProductSubcategory1Adapter(FashionManActivity.this, listuser);
                        rv_fashionista_sub3.setAdapter(listAdapter);
                        rv_fashionista_sub3.addOnItemTouchListener(new RecyclerItemClickListener(FashionManActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Constants.l_id = listuser.get(position).getL_id();
                                Constants.fashionista_lid = listuser.get(position).getL_id();
                                Constants.fiveproducat_lid = null;
                                Constants.realestate_lid = null;
                                Constants.id = "2";
                                startActivity(new Intent(getApplicationContext(), YummyFoodsMoreDetailsActivity.class));
                            }
                        }));
                    }


                }
            });
        } else {
            Toast.makeText(FashionManActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
}
