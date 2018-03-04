package com.citywindowpro;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.citywindowpro.Adapter.FiveMainProductAdapter;
import com.citywindowpro.Model.FiveMainsubcatgory;
import com.citywindowpro.Utils.Constants;
import com.citywindowpro.Utils.RecyclerItemClickListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

public class LiveOfferActivity extends AppCompatActivity implements View.OnClickListener {
    CircleImageView iv_yummyfoods, iv_fashionfesta, iv_electronics, iv_supermarket, iv_health, iv_education, iv_gadjets, iv_realesttae;
    CirclePageIndicator indicator;
    LinearLayout ll_electronics, ll_malls, ll_health, ll_education, ll_home_appliance, ll_real_estate, ll_fashionista;
    ProgressDialog dialog;

/*    RecyclerView rv_live_offer;
    ProgressDialog dialog;
    List<FiveMainsubcatgory> listuser;*/


/*    ArrayList<String> itemNames = new ArrayList<>(Arrays.asList("South",
            "Panjabi",
            "Gurati",
            "Pizza",
            "South",
            "Panjabi",
            "Gurati",
            "Pizza",
            "South",
            "Panjabi"));
    ArrayList<Integer> itemImages = new ArrayList<>(Arrays.asList(R.drawable.south, R.drawable.panjabi, R.drawable.gujrati, R.drawable.pizza, R.drawable.south, R.drawable.panjabi, R.drawable.gujrati, R.drawable.pizza, R.drawable.south, R.drawable.panjabi
    ));*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_offer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        iv_yummyfoods = (CircleImageView) findViewById(R.id.iv_yummyfoods);
        LinearLayout ll_yummyfoods = (LinearLayout) findViewById(R.id.ll_yummyfoods);
        ll_electronics = (LinearLayout) findViewById(R.id.ll_electronics);
        ll_malls = (LinearLayout) findViewById(R.id.ll_malls);
        ll_health = (LinearLayout) findViewById(R.id.ll_health);
        ll_education = (LinearLayout) findViewById(R.id.ll_education);
        ll_home_appliance = (LinearLayout) findViewById(R.id.ll_home_appliance);
        ll_real_estate = (LinearLayout) findViewById(R.id.ll_real_estate);
        ll_fashionista = (LinearLayout) findViewById(R.id.ll_fashionista);


        iv_fashionfesta = (CircleImageView) findViewById(R.id.iv_fashionfesta);
        iv_electronics = (CircleImageView) findViewById(R.id.iv_electronics);
        iv_supermarket = (CircleImageView) findViewById(R.id.iv_supermarket);
        iv_health = (CircleImageView) findViewById(R.id.iv_health);
        iv_education = (CircleImageView) findViewById(R.id.iv_education);
        iv_gadjets = (CircleImageView) findViewById(R.id.iv_gadjets);
        iv_realesttae = (CircleImageView) findViewById(R.id.iv_realesttae);


        Glide.with(this).load(getImage("restauranty")).into(iv_yummyfoods);
        Glide.with(this).load(getImage("shopping")).into(iv_fashionfesta);
        Glide.with(this).load(getImage("electronics1")).into(iv_electronics);
        Glide.with(this).load(getImage("mallimg")).into(iv_supermarket);
        Glide.with(this).load(getImage("health")).into(iv_health);
        Glide.with(this).load(getImage("education")).into(iv_education);
        Glide.with(this).load(getImage("home_decor1")).into(iv_gadjets);
        Glide.with(this).load(getImage("real_estate")).into(iv_realesttae);


        ll_yummyfoods.setOnClickListener(this);
        ll_electronics.setOnClickListener(this);
        ll_malls.setOnClickListener(this);
        ll_health.setOnClickListener(this);
        ll_education.setOnClickListener(this);
        ll_home_appliance.setOnClickListener(this);
        ll_real_estate.setOnClickListener(this);
        ll_fashionista.setOnClickListener(this);

       /* rv_live_offer = (RecyclerView) findViewById(R.id.rv_live_offer);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        listuser = new ArrayList<>();
        load_live_offer();
*/

    }

    public int getImage(String imageName) {
        int drawableResourceId = this.getResources().getIdentifier(imageName, "drawable", this.getPackageName());
        return drawableResourceId;
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

   /* public void load_live_offer() {
        if (isNetworkAvailable()) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
           // params.add("cat_id", Constants.cat_id);
            params.add("cat_id", "1");
            client.post(Constants.liveuri + "sel_sub_category.php", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {
                        listuser.clear();

                        try {
                            Log.d("res", responseString);
                            //Toast.makeText(getContext().getApplicationContext(), res.toString(), Toast.LENGTH_SHORT).show();
                            JSONObject jsonObj = new JSONObject(responseString);
                            JSONArray resultsarray = jsonObj.getJSONArray("response");
                            // looping through All Contacts
                            for (int i = 0; i < resultsarray.length(); i++) {
                                FiveMainsubcatgory temp = new FiveMainsubcatgory();
                                temp.setSubid(resultsarray.getJSONObject(i).getString("sub_id"));
                                temp.setSubname(resultsarray.getJSONObject(i).getString("sub_name"));
                                temp.setImg(Constants.liveimageuri + resultsarray.getJSONObject(i).getString("img"));
                                listuser.add(temp);
                                dialog.dismiss();
                            }
                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                        rv_live_offer.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                        //  call the constructor of FashionistaMainAdapter to send the reference and data to Adapter
                        FiveMainProductAdapter customAdapter = new FiveMainProductAdapter(LiveOfferActivity.this, listuser);
                        rv_live_offer.setAdapter(customAdapter);
                        rv_live_offer.addOnItemTouchListener(new RecyclerItemClickListener(LiveOfferActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Constants.sub_id = listuser.get(position).getSubid();
                                startActivity(new Intent(getApplicationContext(), LiveOfferSubcategory1Activity.class));
                            }
                        }));
                    }
                }
            });
        } else {
            Toast.makeText(LiveOfferActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }*/

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_yummyfoods:
                Constants.offer_cat_id = "1";
                startActivity(new Intent(getApplicationContext(), LiveOfferSubcategory1Activity.class));
                break;
            case R.id.ll_electronics:
                Constants.offer_cat_id = "3";
                startActivity(new Intent(getApplicationContext(), LiveOfferSubcategory1Activity.class));
                break;
            case R.id.ll_malls:
                Constants.offer_cat_id = "4";
                startActivity(new Intent(getApplicationContext(), LiveOfferSubcategory1Activity.class));
                break;
            case R.id.ll_health:
                Constants.offer_cat_id = "6";
                startActivity(new Intent(getApplicationContext(), LiveOfferSubcategory1Activity.class));
                break;
            case R.id.ll_education:
                Constants.offer_cat_id = "7";
                startActivity(new Intent(getApplicationContext(), LiveOfferSubcategory1Activity.class));
                break;
            case R.id.ll_home_appliance:
                Constants.offer_cat_id = "5";
                startActivity(new Intent(getApplicationContext(), LiveOfferSubcategory1Activity.class));
                break;
            case R.id.ll_real_estate:
                Constants.offer_cat_id = "8";
                startActivity(new Intent(getApplicationContext(), LiveOfferSubcategory1Activity.class));
                break;
            case R.id.ll_fashionista:
                Constants.offer_cat_id = "2";
                startActivity(new Intent(getApplicationContext(), LiveOfferSubcategory1Activity.class));
                break;

        }

    }
}
