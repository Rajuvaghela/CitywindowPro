package com.citywindowpro;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.citywindowpro.Adapter.FiveMainProductSubcategory1Adapter;
import com.citywindowpro.Adapter.ProductDetailAdapter;
import com.citywindowpro.Adapter.SliderAdapter;
import com.citywindowpro.Adapter.ViewPagerAdapter;
import com.citywindowpro.Model.FiveMainsubcatgory;
import com.citywindowpro.Utils.Constants;
import com.citywindowpro.Utils.FileDownloader;
import com.citywindowpro.Utils.RecyclerItemClickListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class YummyFoodsMoreDetailsActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    RecyclerView recycler_view_other_product;
    RecyclerView.Adapter listAdapter;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.LayoutManager layoutManager2;

    ViewPager viewPager_slider_images;
    CirclePageIndicator yindicator;
    public ArrayList<Integer> imgArray = new ArrayList<Integer>();
    public Runnable mRun;
    private ViewPagerAdapter mAdapter;
    public int pos = 0;
    private final Handler handler = new Handler();
    TextView textView_address, textView_company_name, textView_contact, textView_email, tv_more_information;

    List<SliderAdapter> imagelist = new ArrayList<>();
    List<FiveMainsubcatgory> rv_list = new ArrayList<>();
    LinearLayout ll_product_detail, ll_similar_product, ll_contact_call, ll_direction, ll_download_menu;
    String contact_number;
    String file_url;
    String fileName, last_page_description;
    ProgressDialog dialog;
    private ProgressDialog pDialog;
    ImageView my_image;
    public static final int progress_bar_type = 0;
    String maplink;
    String d_latitude, d_longitude;
    LinearLayout ll_product_slider, ll_product_name, ll_more_information;
    boolean map;
    boolean menu;
    TextView tv_catlog_pdf, tv_menu_pdf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yummy_foods_more_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recycler_view_other_product = (RecyclerView) findViewById(R.id.recycler_view_other_product);
        ll_product_slider = (LinearLayout) findViewById(R.id.ll_product_slider);
        ll_product_name = (LinearLayout) findViewById(R.id.ll_product_name);
        ll_more_information = (LinearLayout) findViewById(R.id.ll_more_information);
        ll_product_detail = (LinearLayout) findViewById(R.id.ll_product_detail);
        ll_similar_product = (LinearLayout) findViewById(R.id.ll_similar_product);
        ll_contact_call = (LinearLayout) findViewById(R.id.ll_contact_call);
        ll_direction = (LinearLayout) findViewById(R.id.ll_direction);
        ll_download_menu = (LinearLayout) findViewById(R.id.ll_download_menu);
        ScrollView v = (ScrollView) findViewById(R.id.scrollProfile);
        tv_more_information = (TextView) findViewById(R.id.tv_more_information);
        tv_menu_pdf = (TextView) findViewById(R.id.tv_menu_pdf);
        tv_catlog_pdf = (TextView) findViewById(R.id.tv_catlog_pdf);
        v.requestFocus();
        ll_contact_call.setOnClickListener(this);

        viewPager_slider_images = (ViewPager) findViewById(R.id.viewPager_slider_images);
        yindicator = (CirclePageIndicator) findViewById(R.id.yindicator);
        textView_email = (TextView) findViewById(R.id.textView_email);
        textView_address = (TextView) findViewById(R.id.textView_address);
        textView_company_name = (TextView) findViewById(R.id.textView_company_name);
        textView_contact = (TextView) findViewById(R.id.textView_contact);
        textView_contact.setOnClickListener(this);
        ll_download_menu.setOnClickListener(this);
        ll_direction.setOnClickListener(this);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        if (Constants.cat_id.equals("1")) {
            tv_menu_pdf.setVisibility(View.VISIBLE);
        } else {
            tv_catlog_pdf.setVisibility(View.VISIBLE);
        }

        //recyclerview1();
/*        if (Constants.cat_id == "1") {
            iv_download_menu.setVisibility(View.VISIBLE);
            ll_download_menu.setVisibility(View.VISIBLE);
        }*/
        if (Constants.id == "3") {


            load_real_estate_final();
        } else if (Constants.id == "1") {

            loadfinalpage();
        } else if (Constants.id == "2") {
            load_fashionista_page();
        }
        Log.e("fiveproducat_lid", "" + Constants.fiveproducat_lid);
        Log.e("fashionista_lid", "" + Constants.fashionista_lid);
        Log.e("l_id ", "" + Constants.l_id);
        Log.e("realestate_lid", "" + Constants.realestate_lid);

        //real_estate_slider();
        if (Constants.fiveproducat_lid == Constants.l_id) {
            Constants.fashionista_lid = null;
            Constants.realestate_lid = null;
            loadtopslider();
            FiveProductLoadBottomRecyclerView();
        } else if (Constants.l_id.equals(Constants.fashionista_lid)) {
            Constants.realestate_lid = null;
            Constants.fiveproducat_lid = null;
            fashionista_slider();
            FashionLoadBottomRecyclerView();

        } else if (Constants.l_id == Constants.realestate_lid) {
            Constants.fashionista_lid = null;
            Constants.fiveproducat_lid = null;
            real_estate_slider();
            RealEstateLoadBottomRecyclerView();
        }


    }

    public void RealEstateLoadBottomRecyclerView() {
        if (isNetworkAvailable()) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
            params.add("l_id", Constants.l_id);

            client.post(Constants.liveuri + "product_list_child.php", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {
                        rv_list.clear();

                        try {
                            Log.e("rv_response", responseString);

                            JSONObject jsonObj = new JSONObject(responseString);
                            JSONArray resultsarray = jsonObj.getJSONArray("response");
                            for (int i = 0; i < resultsarray.length(); i++) {
                                FiveMainsubcatgory temp = new FiveMainsubcatgory();
                                temp.setLastpage_recyclerview_product_name(resultsarray.getJSONObject(i).getString("pro_name"));
                                temp.setLastpage_recyclerview_product_price(resultsarray.getJSONObject(i).getString("pro_price"));
                                temp.setLastpage_recyclerview_product_image(Constants.liveimageuri + resultsarray.getJSONObject(i).getString("pro_img"));

                                rv_list.add(temp);
                                dialog.dismiss();
                            }
                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }
                        ll_similar_product.setVisibility(View.VISIBLE);
                        recycler_view_other_product.setHasFixedSize(true);
                        layoutManager2 = new LinearLayoutManager(YummyFoodsMoreDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false);
                        recycler_view_other_product.setLayoutManager(layoutManager2);
                        listAdapter = new ProductDetailAdapter(YummyFoodsMoreDetailsActivity.this, rv_list);
                        recycler_view_other_product.setAdapter(listAdapter);
          /*              recycler_view_other_product.addOnItemTouchListener(new RecyclerItemClickListener(YummyFoodsMoreDetailsActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                startActivity(new Intent(getApplicationContext(), YummyFoodsMoreDetailsActivity.class));
                            }
                        }));*/
                    }
                    if (responseString.equals("[]")) {
                        ll_similar_product.setVisibility(View.GONE);
                    }
                }
            });
        } else {
            Toast.makeText(YummyFoodsMoreDetailsActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void FiveProductLoadBottomRecyclerView() {
        if (isNetworkAvailable()) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
            params.add("l_id", Constants.l_id);

            client.post(Constants.liveuri + "product_list.php", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {
                        rv_list.clear();

                        try {
                            Log.e("rv_response", responseString);

                            JSONObject jsonObj = new JSONObject(responseString);
                            JSONArray resultsarray = jsonObj.getJSONArray("response");
                            for (int i = 0; i < resultsarray.length(); i++) {
                                FiveMainsubcatgory temp = new FiveMainsubcatgory();
                                temp.setLastpage_recyclerview_product_name(resultsarray.getJSONObject(i).getString("pro_name"));
                                temp.setLastpage_recyclerview_product_price(resultsarray.getJSONObject(i).getString("pro_price"));
                                temp.setLastpage_recyclerview_product_image(Constants.liveimageuri + resultsarray.getJSONObject(i).getString("pro_img"));

                                rv_list.add(temp);
                                dialog.dismiss();
                            }
                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }
                        ll_similar_product.setVisibility(View.VISIBLE);
                        recycler_view_other_product.setHasFixedSize(true);
                        layoutManager2 = new LinearLayoutManager(YummyFoodsMoreDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false);
                        recycler_view_other_product.setLayoutManager(layoutManager2);
                        listAdapter = new ProductDetailAdapter(YummyFoodsMoreDetailsActivity.this, rv_list);
                        recycler_view_other_product.setAdapter(listAdapter);

                    }
                    if (responseString.equals("[]")) {
                        ll_similar_product.setVisibility(View.GONE);
                    }
                }
            });
        } else {
            Toast.makeText(YummyFoodsMoreDetailsActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void FashionLoadBottomRecyclerView() {
        if (isNetworkAvailable()) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
            params.add("l_id", Constants.l_id);

            client.post(Constants.liveuri + "product_fashion.php", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {
                        rv_list.clear();

                        try {
                            Log.e("rv_response", responseString);

                            JSONObject jsonObj = new JSONObject(responseString);
                            JSONArray resultsarray = jsonObj.getJSONArray("response");
                            for (int i = 0; i < resultsarray.length(); i++) {
                                FiveMainsubcatgory temp = new FiveMainsubcatgory();
                                temp.setLastpage_recyclerview_product_name(resultsarray.getJSONObject(i).getString("pro_name"));
                                temp.setLastpage_recyclerview_product_price(resultsarray.getJSONObject(i).getString("pro_price"));
                                temp.setLastpage_recyclerview_product_image(Constants.liveimageuri + resultsarray.getJSONObject(i).getString("pro_img"));

                                rv_list.add(temp);
                                dialog.dismiss();
                            }
                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }
                        ll_similar_product.setVisibility(View.VISIBLE);
                        recycler_view_other_product.setHasFixedSize(true);
                        layoutManager2 = new LinearLayoutManager(YummyFoodsMoreDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false);
                        recycler_view_other_product.setLayoutManager(layoutManager2);
                        listAdapter = new ProductDetailAdapter(YummyFoodsMoreDetailsActivity.this, rv_list);
                        recycler_view_other_product.setAdapter(listAdapter);
                    /*    recycler_view_other_product.addOnItemTouchListener(new RecyclerItemClickListener(YummyFoodsMoreDetailsActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Constants.l_id = rv_list.get(position).getL_id();
                                startActivity(new Intent(getApplicationContext(), YummyFoodsMoreDetailsActivity.class));
                            }
                        }));*/
                    }
                    if (responseString.equals("[]")) {
                        ll_similar_product.setVisibility(View.GONE);
                    }
                }
            });
        } else {
            Toast.makeText(YummyFoodsMoreDetailsActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void fashionista_slider() {
        if (!isNetworkAvailable()) {
            Toast.makeText(getApplicationContext(), "No Internet connection", Toast.LENGTH_SHORT).show();
        } else {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
            params.add("l_id", Constants.l_id);
            client.post(Constants.liveuri + "gallery_fashion.php", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {
                        imagelist.clear();

                        try {
                            Log.d("fashion_slider", responseString);
                            JSONObject jsonObj = new JSONObject(responseString);
                            JSONArray resultsarray = jsonObj.getJSONArray("response");
                            for (int i = 0; i < resultsarray.length(); i++) {
                                SliderAdapter temp = new SliderAdapter();
                                temp.setSlider_image(Constants.liveimageuri + resultsarray.getJSONObject(i).getString("g_img"));
                                imagelist.add(temp);
                                dialog.dismiss();
                            }
                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }
                        ll_product_slider.setVisibility(View.VISIBLE);
                        setTopSlider();
                    }
                    if (responseString.equals("[]")) {
                        ll_product_slider.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    private void real_estate_slider() {
        if (!isNetworkAvailable()) {
            Toast.makeText(getApplicationContext(), "No Internet connection", Toast.LENGTH_SHORT).show();
        } else {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
            params.add("l_id", Constants.l_id);
            client.post(Constants.liveuri + "gallery_list_child.php", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {
                        imagelist.clear();

                        try {
                            Log.d("realestaete_slider", responseString);
                            JSONObject jsonObj = new JSONObject(responseString);
                            JSONArray resultsarray = jsonObj.getJSONArray("response");
                            for (int i = 0; i < resultsarray.length(); i++) {
                                SliderAdapter temp = new SliderAdapter();
                                temp.setSlider_image(Constants.liveimageuri + resultsarray.getJSONObject(i).getString("g_img"));
                                imagelist.add(temp);
                                dialog.dismiss();
                            }
                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }
                        ll_product_slider.setVisibility(View.VISIBLE);
                        setTopSlider();
                    }
                    if (responseString.equals("[]")) {
                        ll_product_slider.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    public void load_fashionista_page() {

        if (isNetworkAvailable()) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
            params.add("l_id", Constants.l_id);
            params.add("ctc_id", Constants.ctc_id);
            params.add("child_id", Constants.child_id);
            params.add("sub_id", Constants.sub_id);

            client.post(Constants.liveuri + "sel_fashion_detail.php", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {
                        try {
                            Log.d("res", responseString);
                            JSONObject jsonObj = new JSONObject(responseString);
                            JSONArray resultsarray = jsonObj.getJSONArray("response");
                            ll_product_detail.setVisibility(View.VISIBLE);
                            ll_product_name.setVisibility(View.VISIBLE);
                            textView_company_name.setText(resultsarray.getJSONObject(0).getString("l_name"));
                            textView_address.setText(resultsarray.getJSONObject(0).getString("d_address"));
                            textView_email.setText(resultsarray.getJSONObject(0).getString("d_email"));
                            textView_contact.setText(resultsarray.getJSONObject(0).getString("d_contact"));
                            tv_more_information.setText(Html.fromHtml(resultsarray.getJSONObject(0).getString("l_more_info")));
                            if (resultsarray.getJSONObject(0).getString("l_more_info").equals("")) {
                                ll_more_information.setVisibility(View.GONE);
                            } else {
                                ll_more_information.setVisibility(View.VISIBLE);
                            }
                            contact_number = resultsarray.getJSONObject(0).getString("d_contact");
                            Constants.pdf_menu_path = resultsarray.getJSONObject(0).getString("l_path");
                            if (resultsarray.getJSONObject(0).getString("l_path").equals("")) {
                                menu = true;
                            }
                            if (resultsarray.getJSONObject(0).getString("d_latitude").equals("") && resultsarray.getJSONObject(0).getString("d_longitude").equals("")) {
                                map = true;
                            }
                            fileName = resultsarray.getJSONObject(0).getString("l_name");
                            d_latitude = resultsarray.getJSONObject(0).getString("d_latitude");
                            d_longitude = resultsarray.getJSONObject(0).getString("d_longitude");

                            dialog.dismiss();

                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }

                    }
                }
            });
        } else {
            Toast.makeText(YummyFoodsMoreDetailsActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }

    }

    public void load_real_estate_final() {

        if (isNetworkAvailable()) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
            params.add("l_id", Constants.l_id);
            client.post(Constants.liveuri + "sel_detail_list_child.php", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {
                        try {
                            Log.d("res", responseString);
                            JSONObject jsonObj = new JSONObject(responseString);
                            JSONArray resultsarray = jsonObj.getJSONArray("response");
                            ll_product_detail.setVisibility(View.VISIBLE);
                            ll_product_name.setVisibility(View.VISIBLE);
                            textView_company_name.setText(resultsarray.getJSONObject(0).getString("l_name"));
                            textView_address.setText(resultsarray.getJSONObject(0).getString("d_address"));
                            textView_email.setText(resultsarray.getJSONObject(0).getString("d_email"));
                            textView_contact.setText(resultsarray.getJSONObject(0).getString("d_contact"));
                            tv_more_information.setText(Html.fromHtml(resultsarray.getJSONObject(0).getString("l_more_info")));
                            if (resultsarray.getJSONObject(0).getString("l_more_info").equals("")) {
                                ll_more_information.setVisibility(View.GONE);
                            } else {
                                ll_more_information.setVisibility(View.VISIBLE);
                            }
                            contact_number = resultsarray.getJSONObject(0).getString("d_contact");
                            Constants.pdf_menu_path = resultsarray.getJSONObject(0).getString("l_path");
                            if (resultsarray.getJSONObject(0).getString("l_path").equals("")) {
                                menu = true;
                            }
                            if (resultsarray.getJSONObject(0).getString("d_latitude").equals("") && resultsarray.getJSONObject(0).getString("d_longitude").equals("")) {
                                map = true;
                            }
                            fileName = resultsarray.getJSONObject(0).getString("l_name");
                            d_latitude = resultsarray.getJSONObject(0).getString("d_latitude");
                            d_longitude = resultsarray.getJSONObject(0).getString("d_longitude");
                            Log.e(" d_latitude", "" + d_latitude);
                            Log.e("d_longitude", "" + d_longitude);

                            dialog.dismiss();

                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }
/*                        rv_sub_product1.setHasFixedSize(true);
                        rv_layoutManager = new LinearLayoutManager(FiveMainProductSubcategory1Activity.this, LinearLayoutManager.VERTICAL, false);
                        rv_sub_product1.setLayoutManager(rv_layoutManager);
                        rv_listAdapter = new FiveMainProductSubcategory1Adapter(FiveMainProductSubcategory1Activity.this,listuser);
                        rv_sub_product1.setAdapter(rv_listAdapter);
                        rv_sub_product1.addOnItemTouchListener(new RecyclerItemClickListener(FiveMainProductSubcategory1Activity.this, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Constants.l_id=listuser.get(position).getL_id();
                                startActivity(new Intent(getApplicationContext(), YummyFoodsMoreDetailsActivity.class));
                            }
                        }));*/
                    }
                }
            });
        } else {
            Toast.makeText(YummyFoodsMoreDetailsActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
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

    public void loadtopslider() {
        if (!isNetworkAvailable()) {
            Toast.makeText(getApplicationContext(), "No Internet connection", Toast.LENGTH_SHORT).show();
        } else {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
            params.add("l_id", Constants.l_id);
            client.post(Constants.liveuri + "gallery_list.php", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {

                        imagelist.clear();

                        try {
                            Log.d("five_product_slider", responseString);
                            JSONObject jsonObj = new JSONObject(responseString);
                            JSONArray resultsarray = jsonObj.getJSONArray("response");
                            for (int i = 0; i < resultsarray.length(); i++) {
                                SliderAdapter temp = new SliderAdapter();
                                temp.setSlider_image(Constants.liveimageuri + resultsarray.getJSONObject(i).getString("g_img"));
                                imagelist.add(temp);
                                dialog.dismiss();
                            }
                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }
                        ll_product_slider.setVisibility(View.VISIBLE);
                        setTopSlider();
                    }
                    if (responseString.equals("[]")) {
                        ll_product_slider.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    private void setTopSlider() {

        mAdapter = new ViewPagerAdapter(this, imagelist);
        viewPager_slider_images.setAdapter(mAdapter);
        viewPager_slider_images.setCurrentItem(0);
        viewPager_slider_images.setOnPageChangeListener(this);
        viewPager_slider_images.setCurrentItem(pos, true);
        yindicator.setViewPager(viewPager_slider_images);


        mRun = new Runnable() {
            @Override
            public void run() {
                if (pos < mAdapter.getCount()) {
                    viewPager_slider_images.setCurrentItem(pos, true);
                    handler.postDelayed(this, 3000);
                    yindicator.setViewPager(viewPager_slider_images);
                    pos++;
                } else {
                    pos = 0;
                    viewPager_slider_images.setCurrentItem(pos, true);
                    handler.postDelayed(this, 3000);
                    yindicator.setViewPager(viewPager_slider_images);
                    pos++;
                }
            }
        };
        handler.post(mRun);


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void loadfinalpage() {
        if (isNetworkAvailable()) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
            params.add("l_id", Constants.l_id);
            client.post(Constants.liveuri + "sel_list_detail.php", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {
                        try {
                            Log.d("res", responseString);
                            //Toast.makeText(getContext().getApplicationContext(), res.toString(), Toast.LENGTH_SHORT).show();
                            JSONObject jsonObj = new JSONObject(responseString);
                            JSONArray resultsarray = jsonObj.getJSONArray("response");
                            // looping through All Contacts
                            ll_product_name.setVisibility(View.VISIBLE);
                            ll_product_detail.setVisibility(View.VISIBLE);

                            textView_company_name.setText(resultsarray.getJSONObject(0).getString("l_name"));
                            textView_address.setText(resultsarray.getJSONObject(0).getString("d_address"));
                            textView_email.setText(resultsarray.getJSONObject(0).getString("d_email"));
                            textView_contact.setText(resultsarray.getJSONObject(0).getString("d_contact"));
                            tv_more_information.setText(Html.fromHtml(resultsarray.getJSONObject(0).getString("l_more_info")));
                            if (resultsarray.getJSONObject(0).getString("l_more_info").equals("")) {
                                ll_more_information.setVisibility(View.GONE);
                            } else {
                                ll_more_information.setVisibility(View.VISIBLE);
                            }
                            contact_number = resultsarray.getJSONObject(0).getString("d_contact");
                            Constants.pdf_menu_path = resultsarray.getJSONObject(0).getString("l_path");
                            if (resultsarray.getJSONObject(0).getString("l_path").equals("")) {
                                menu = true;
                            }
                            if (resultsarray.getJSONObject(0).getString("d_latitude").equals("") && resultsarray.getJSONObject(0).getString("d_longitude").equals("")) {
                                map = true;
                            }
                            fileName = resultsarray.getJSONObject(0).getString("l_name");
                            d_latitude = resultsarray.getJSONObject(0).getString("d_latitude");
                            d_longitude = resultsarray.getJSONObject(0).getString("d_longitude");
                            Log.e(" d_latitude", "" + d_latitude);
                            Log.e("d_longitude", "" + d_longitude);
                            // textView_company_name.setText(resultsarray.getJSONObject(0).getString("l_name"));

                            dialog.dismiss();
/*                            for (int i = 0; i < resultsarray.length(); i++) {
                                FiveMainsubcatgory temp = new FiveMainsubcatgory();
                                temp.setL_id(resultsarray.getJSONObject(i).getString("l_id"));
                                temp.setSubname(resultsarray.getJSONObject(i).getString("l_name"));
                                temp.setImg(Constants.liveimageuri + resultsarray.getJSONObject(i).getString("l_img"));
                                dialog.dismiss();
                            }*/
                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }
/*                        rv_sub_product1.setHasFixedSize(true);
                        rv_layoutManager = new LinearLayoutManager(FiveMainProductSubcategory1Activity.this, LinearLayoutManager.VERTICAL, false);
                        rv_sub_product1.setLayoutManager(rv_layoutManager);
                        rv_listAdapter = new FiveMainProductSubcategory1Adapter(FiveMainProductSubcategory1Activity.this,listuser);
                        rv_sub_product1.setAdapter(rv_listAdapter);
                        rv_sub_product1.addOnItemTouchListener(new RecyclerItemClickListener(FiveMainProductSubcategory1Activity.this, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Constants.l_id=listuser.get(position).getL_id();
                                startActivity(new Intent(getApplicationContext(), YummyFoodsMoreDetailsActivity.class));
                            }
                        }));*/
                    }


                }
            });
        } else {
            Toast.makeText(YummyFoodsMoreDetailsActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(YummyFoodsMoreDetailsActivity.this, new
                String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE}, 1);
    }

    public boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = ContextCompat.checkSelfPermission(YummyFoodsMoreDetailsActivity.this,
                    android.Manifest.permission.CALL_PHONE);
            return result == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_contact_call:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + contact_number));
                startActivity(intent);
                break;

            case R.id.ll_download_menu:
                if (menu) {
                    Toast.makeText(getApplicationContext(), "File not available", Toast.LENGTH_LONG).show();

                } else {
                    file_url = Constants.livepdfuri + Constants.pdf_menu_path;

                    if (checkPermission()) {
                        new DownloadFileFromURL().execute(file_url);
                    } else {
                        requestPermission();
                    }
                }


                break;
            case R.id.ll_direction:
                if (map) {
                    Toast.makeText(getApplicationContext(), "Location not available", Toast.LENGTH_LONG).show();
                } else {
                    Intent ii = new Intent(this, MapsActivity.class);
                    // ii.putExtra("pkgName", B2MAppsPKGName);

                    ii.putExtra("d_latitude", d_latitude);
                    ii.putExtra("d_longitude", d_longitude);
                    ii.putExtra("l_name", fileName);


                    ii.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(ii);
                }
                break;

        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case progress_bar_type: // we set this to 0
                pDialog = new ProgressDialog(this);
                pDialog.setMessage("Downloading file. Please wait...");
                pDialog.setIndeterminate(false);
                pDialog.setMax(100);
                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pDialog.setCancelable(false);
                pDialog.show();
                return pDialog;
            default:
                return null;
        }
    }

    /**
     * Background Async Task to download file
     */
    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(progress_bar_type);
        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                // this will be useful so that you can show a tipical 0-100% progress bar
                int lenghtOfFile = conection.getContentLength();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                // Output stream
                OutputStream output = new FileOutputStream("/sdcard/menu.pdf");

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        /**
         * After completing background task
         * Dismiss the progress dialog
         **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            dismissDialog(progress_bar_type);

            // Displaying downloaded image into image view
            // Reading image path from sdcard
            //   String imagePath = Environment.getExternalStorageDirectory().toString() + "/menu.pdf";
            view();
            // setting downloaded into image view
            //  my_image.setImageDrawable(Drawable.createFromPath(imagePath));
            // startActivity(new Intent(getApplicationContext(),SecondActivity.class));
        }

    }

    public void view() {
        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/menu.pdf");  // -> filename = maven.pdf
        Uri path = Uri.fromFile(pdfFile);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(YummyFoodsMoreDetailsActivity.this, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
        }
    }
   /* public void download() {
        new DownloadFile().execute("http://citywindow.deckoidsolution.website/menu_pdf/43832DoctorDoctor.pdf", "maven.pdf");
    }

    public void view() {
        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/testthreepdf/" + "maven.pdf");  // -> filename = maven.pdf
        Uri path = Uri.fromFile(pdfFile);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(YummyFoodsMoreDetailsActivity.this, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
        }
    }

    private class DownloadFile extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
*//*            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf*//*
            String str = Constants.liveuri;
            fileUrl = Constants.livepdfuri+Constants.pdf_menu_path;
            fileName = "path.pdf";
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "Download");
            folder.mkdir();

            File pdfFile = new File(folder, fileName);

            try {
                pdfFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            FileDownloader.downloadFile(fileUrl, pdfFile);

            return null;
        }
    }*/

}
