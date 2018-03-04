package com.citywindowpro;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.citywindowpro.Adapter.LiveOfferAdapter1;
import com.citywindowpro.Adapter.OfferDetailAdapter;
import com.citywindowpro.Adapter.ProductDetailAdapter;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.citywindowpro.R.id.iv_subcategory1_image;

public class LiveOfferDetailActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recycler_view_other_product;
    RecyclerView.Adapter listAdapter;
    RecyclerView.LayoutManager layoutManager;
    TextView tv_company_name, tv_product_name, tv_start_date, tv_start_time;
    TextView tv_end_date, tv_end_time, tv_price, tv_sale_price;
    TextView tv_description, tv_contact, tv_email, tv_address;
    ImageView iv_live_offer_image;
    ProgressDialog dialog;
    RecyclerView.LayoutManager layoutManager2;
    List<FiveMainsubcatgory> rv_list = new ArrayList<>();
    String contact_number;
    LinearLayout ll_live_offer_detail, ll_similar_product;
    String data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_offer_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recycler_view_other_product = (RecyclerView) findViewById(R.id.recycler_view_other_product);
        ll_live_offer_detail = (LinearLayout) findViewById(R.id.ll_live_offer_detail);
        ll_similar_product = (LinearLayout) findViewById(R.id.ll_similar_product);
        tv_company_name = (TextView) findViewById(R.id.tv_company_name);
        tv_product_name = (TextView) findViewById(R.id.tv_product_name);
        tv_start_date = (TextView) findViewById(R.id.tv_start_date);
        tv_start_time = (TextView) findViewById(R.id.tv_start_time);
        tv_end_date = (TextView) findViewById(R.id.tv_end_date);
        tv_end_time = (TextView) findViewById(R.id.tv_end_time);
        tv_price = (TextView) findViewById(R.id.tv_price);
        tv_sale_price = (TextView) findViewById(R.id.tv_sale_price);
        tv_description = (TextView) findViewById(R.id.tv_description);
        tv_contact = (TextView) findViewById(R.id.tv_contact);
        tv_contact.setOnClickListener(this);
        tv_email = (TextView) findViewById(R.id.tv_email);
        tv_address = (TextView) findViewById(R.id.tv_address);
        iv_live_offer_image = (ImageView) findViewById(R.id.iv_live_offer_image);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);



        if (Constants.offer_cat_id.equals("2")) {
            FashinistaLoadOffer();
            FashionistaLoadBottomRecyclerView();

        } else if (Constants.offer_cat_id.equals("8")) {
            RealEstateloadCategoryLoadOffer();
            RealEstateLoadBottomRecyclerView();

        } else {
            FiveCategoryLoadOffer();
            FiveCatLoadBottomRecyclerView();
        }
        //RealEstateLoadBottomRecyclerView();

    }

    public void FiveCatLoadBottomRecyclerView() {
        if (isNetworkAvailable()) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
            Log.e("l_id",""+Constants.l_id);
            Log.e("cat_id",""+Constants.cat_id);
            Log.e("lv_id",""+Constants.lv_id);
            params.add("l_id", Constants.l_id);
            params.add("cat_id", Constants.cat_id);
            params.add("lv_id", Constants.lv_id);

            client.post(Constants.liveuri + "live_offer_list_product.php", params, new TextHttpResponseHandler() {
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
                                temp.setOffer_lastpage_rv_product_name(resultsarray.getJSONObject(i).getString("pro_name"));
                                temp.setOffer_lastpage_rv_product_price(resultsarray.getJSONObject(i).getString("off_price"));
                                temp.setLv_id(resultsarray.getJSONObject(i).getString("lv_id"));
                                temp.setOffer_lastpage_rv_product_image_name(Constants.liveimageuri + resultsarray.getJSONObject(i).getString("pro_img"));

                                rv_list.add(temp);
                                dialog.dismiss();
                            }
                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }
                        ll_similar_product.setVisibility(View.VISIBLE);
                        recycler_view_other_product.setHasFixedSize(true);
                        layoutManager2 = new LinearLayoutManager(LiveOfferDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
                        recycler_view_other_product.setLayoutManager(layoutManager2);
                        listAdapter = new OfferDetailAdapter(LiveOfferDetailActivity.this, rv_list);
                        recycler_view_other_product.setAdapter(listAdapter);
                        recycler_view_other_product.addOnItemTouchListener(new RecyclerItemClickListener(LiveOfferDetailActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Constants.lv_id = rv_list.get(position).getLv_id();

                                startActivity(new Intent(getApplicationContext(), LiveOfferDetailActivity.class));
                                finish();
                            }
                        }));
                    }
                    if (responseString.equals("[]")) {
                        ll_similar_product.setVisibility(View.GONE);
                    }

                }
            });
        } else {
            Toast.makeText(LiveOfferDetailActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void FashionistaLoadBottomRecyclerView() {
        if (isNetworkAvailable()) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
            params.add("l_id", Constants.l_id);
            params.add("cat_id", Constants.cat_id);
            params.add("lv_id", Constants.lv_id);

            client.post(Constants.liveuri + "live_offer_fashion_product.php", params, new TextHttpResponseHandler() {
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
                                temp.setOffer_lastpage_rv_product_name(resultsarray.getJSONObject(i).getString("pro_name"));
                                temp.setOffer_lastpage_rv_product_price(resultsarray.getJSONObject(i).getString("off_price"));
                                temp.setLv_id(resultsarray.getJSONObject(i).getString("lv_id"));
                                temp.setOffer_lastpage_rv_product_image_name(Constants.liveimageuri + resultsarray.getJSONObject(i).getString("pro_img"));

                                rv_list.add(temp);
                                dialog.dismiss();
                            }
                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }

                        ll_similar_product.setVisibility(View.VISIBLE);

                        recycler_view_other_product.setHasFixedSize(true);
                        layoutManager2 = new LinearLayoutManager(LiveOfferDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
                        recycler_view_other_product.setLayoutManager(layoutManager2);
                        listAdapter = new OfferDetailAdapter(LiveOfferDetailActivity.this, rv_list);
                        recycler_view_other_product.setAdapter(listAdapter);
                        recycler_view_other_product.addOnItemTouchListener(new RecyclerItemClickListener(LiveOfferDetailActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Constants.lv_id = rv_list.get(position).getLv_id();
                                startActivity(new Intent(getApplicationContext(), LiveOfferDetailActivity.class));
                                finish();
                            }
                        }));
                    }

                    if (responseString.equals("[]")) {
                        ll_similar_product.setVisibility(View.GONE);
                    }
                }
            });
        } else {
            Toast.makeText(LiveOfferDetailActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void RealEstateLoadBottomRecyclerView() {
        if (isNetworkAvailable()) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
            Log.e("l_id",""+Constants.l_id);
            Log.e("cat_id",""+Constants.cat_id);
            Log.e("lv_id",""+Constants.lv_id);
            params.add("l_id", Constants.l_id);
            params.add("cat_id", Constants.cat_id);
            params.add("lv_id", Constants.lv_id);

            client.post(Constants.liveuri + "live_offer_list_child_product.php", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {
                        rv_list.clear();

                        try {
                            Log.e("real_rv_res", responseString);

                            JSONObject jsonObj = new JSONObject(responseString);
                            JSONArray resultsarray = jsonObj.getJSONArray("response");
                            for (int i = 0; i < resultsarray.length(); i++) {
                                FiveMainsubcatgory temp = new FiveMainsubcatgory();
                                temp.setOffer_lastpage_rv_product_name(resultsarray.getJSONObject(i).getString("pro_name"));
                                temp.setOffer_lastpage_rv_product_price(resultsarray.getJSONObject(i).getString("off_price"));
                                temp.setLv_id(resultsarray.getJSONObject(i).getString("lv_id"));
                                temp.setOffer_lastpage_rv_product_image_name(Constants.liveimageuri + resultsarray.getJSONObject(i).getString("pro_img"));

                                rv_list.add(temp);
                                dialog.dismiss();
                            }
                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }

                        ll_similar_product.setVisibility(View.VISIBLE);

                        recycler_view_other_product.setHasFixedSize(true);
                        layoutManager2 = new LinearLayoutManager(LiveOfferDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
                        recycler_view_other_product.setLayoutManager(layoutManager2);
                        listAdapter = new OfferDetailAdapter(LiveOfferDetailActivity.this, rv_list);
                        recycler_view_other_product.setAdapter(listAdapter);
                        recycler_view_other_product.addOnItemTouchListener(new RecyclerItemClickListener(LiveOfferDetailActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Constants.lv_id = rv_list.get(position).getLv_id();
                                startActivity(new Intent(getApplicationContext(), LiveOfferDetailActivity.class));
                                finish();
                            }
                        }));
                    }
                    if (responseString.equals("[]")) {
                        ll_similar_product.setVisibility(View.GONE);
                    }
                }
            });
        } else {
            Toast.makeText(LiveOfferDetailActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void FashinistaLoadOffer() {
        if (isNetworkAvailable()) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
            params.add("lv_id", Constants.lv_id);
            client.post(Constants.liveuri + "live_offer_fashion_detail.php", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {
                        try {
                            Log.e("res", responseString);
                            JSONObject jsonObj = new JSONObject(responseString);
                            JSONArray resultsarray = jsonObj.getJSONArray("response");

                            ll_live_offer_detail.setVisibility(View.VISIBLE);

                            tv_company_name.setText(resultsarray.getJSONObject(0).getString("l_name"));
                            tv_product_name.setText(resultsarray.getJSONObject(0).getString("pro_name"));
                          //  tv_start_date.setText(resultsarray.getJSONObject(0).getString("start_date"));
                            tv_start_date.setText(parseDateToddMMyyyy(resultsarray.getJSONObject(0).getString("start_date")));
                            tv_end_date.setText(parseDateToddMMyyyy(resultsarray.getJSONObject(0).getString("end_date")));

                            String st = convert24hoursto12hors(resultsarray.getJSONObject(0).getString("start_time"));
                            String et = convert24hoursto12hors(resultsarray.getJSONObject(0).getString("end_time"));
                            tv_start_time.setText(st);
                            tv_end_time.setText(et);
                           // tv_end_date.setText(resultsarray.getJSONObject(0).getString("end_date"));

                            tv_price.setText(resultsarray.getJSONObject(0).getString("pro_price"));
                            tv_price.setPaintFlags(tv_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                            tv_sale_price.setText(resultsarray.getJSONObject(0).getString("off_price"));
                            tv_description.setText(resultsarray.getJSONObject(0).getString("description"));
                            contact_number = resultsarray.getJSONObject(0).getString("d_contact");
                            tv_contact.setText(resultsarray.getJSONObject(0).getString("d_contact"));
                            tv_email.setText(resultsarray.getJSONObject(0).getString("d_email"));
                            tv_address.setText(resultsarray.getJSONObject(0).getString("d_address"));
                            Log.e("img", "" + resultsarray.getJSONObject(0).getString("l_img"));
                            Glide.with(LiveOfferDetailActivity.this).load(Constants.liveimageuri + resultsarray.getJSONObject(0).getString("pro_img")).into(iv_live_offer_image);
                            dialog.dismiss();

                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(LiveOfferDetailActivity.this, "data is not available!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(LiveOfferDetailActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void RealEstateloadCategoryLoadOffer() {
        if (isNetworkAvailable()) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
            params.add("lv_id", Constants.lv_id);
            client.post(Constants.liveuri + "live_offer_list_child_detail.php", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {
                        try {
                            Log.e("res", responseString);
                            JSONObject jsonObj = new JSONObject(responseString);
                            JSONArray resultsarray = jsonObj.getJSONArray("response");

                            ll_live_offer_detail.setVisibility(View.VISIBLE);

                            tv_company_name.setText(resultsarray.getJSONObject(0).getString("l_name"));
                            tv_product_name.setText(resultsarray.getJSONObject(0).getString("pro_name"));
                           // tv_start_date.setText(resultsarray.getJSONObject(0).getString("start_date"));
                            tv_start_date.setText(parseDateToddMMyyyy(resultsarray.getJSONObject(0).getString("start_date")));
                            tv_end_date.setText(parseDateToddMMyyyy(resultsarray.getJSONObject(0).getString("end_date")));
                            String st = convert24hoursto12hors(resultsarray.getJSONObject(0).getString("start_time"));
                            String et = convert24hoursto12hors(resultsarray.getJSONObject(0).getString("end_time"));
                            tv_start_time.setText(st);
                            tv_end_time.setText(et);
                           // tv_end_date.setText(resultsarray.getJSONObject(0).getString("end_date"));

                            tv_price.setText(resultsarray.getJSONObject(0).getString("pro_price"));

                            tv_price.setPaintFlags(tv_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                            tv_sale_price.setText(resultsarray.getJSONObject(0).getString("off_price"));
                            tv_description.setText(resultsarray.getJSONObject(0).getString("description"));
                            contact_number = resultsarray.getJSONObject(0).getString("d_contact");
                            tv_contact.setText(resultsarray.getJSONObject(0).getString("d_contact"));
                            tv_email.setText(resultsarray.getJSONObject(0).getString("d_email"));
                            tv_address.setText(resultsarray.getJSONObject(0).getString("d_address"));
                            Log.e("img", "" + resultsarray.getJSONObject(0).getString("l_img"));
                            Glide.with(LiveOfferDetailActivity.this).load(Constants.liveimageuri + resultsarray.getJSONObject(0).getString("pro_img")).into(iv_live_offer_image);
                            dialog.dismiss();

                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(LiveOfferDetailActivity.this, "data is not available!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(LiveOfferDetailActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }


    public void FiveCategoryLoadOffer() {
        if (isNetworkAvailable()) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
            params.add("lv_id", Constants.lv_id);
            client.post(Constants.liveuri + "live_offer_list_detail.php", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {
                        try {
                            Log.e("res", responseString);
                            JSONObject jsonObj = new JSONObject(responseString);
                            JSONArray resultsarray = jsonObj.getJSONArray("response");

                            ll_live_offer_detail.setVisibility(View.VISIBLE);

                            tv_company_name.setText(resultsarray.getJSONObject(0).getString("l_name"));
                            tv_product_name.setText(resultsarray.getJSONObject(0).getString("pro_name"));
                            tv_start_date.setText(parseDateToddMMyyyy(resultsarray.getJSONObject(0).getString("start_date")));
                            tv_end_date.setText(parseDateToddMMyyyy(resultsarray.getJSONObject(0).getString("end_date")));
                            tv_price.setText(resultsarray.getJSONObject(0).getString("pro_price"));

                            String st = convert24hoursto12hors(resultsarray.getJSONObject(0).getString("start_time"));
                            String et = convert24hoursto12hors(resultsarray.getJSONObject(0).getString("end_time"));
                            tv_start_time.setText(st);
                            tv_end_time.setText(et);
                            tv_price.setPaintFlags(tv_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                            tv_sale_price.setText(resultsarray.getJSONObject(0).getString("off_price"));
                            tv_description.setText(resultsarray.getJSONObject(0).getString("description"));
                            contact_number = resultsarray.getJSONObject(0).getString("d_contact");
                            tv_contact.setText(resultsarray.getJSONObject(0).getString("d_contact"));
                            tv_email.setText(resultsarray.getJSONObject(0).getString("d_email"));
                            tv_address.setText(resultsarray.getJSONObject(0).getString("d_address"));
                            Log.e("img", "" + resultsarray.getJSONObject(0).getString("l_img"));
                            Glide.with(LiveOfferDetailActivity.this).load(Constants.liveimageuri + resultsarray.getJSONObject(0).getString("pro_img")).into(iv_live_offer_image);
                            dialog.dismiss();

                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(LiveOfferDetailActivity.this, "data is not available!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(LiveOfferDetailActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
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

    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd-MM-yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }


    public String convert24hoursto12hors(String time) {
        String t = null;
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
            final Date dateObj = sdf.parse(time);
            System.out.println(dateObj);
            System.out.println(new SimpleDateFormat("K:mm:a").format(dateObj));
            t = new SimpleDateFormat("K:mm:a").format(dateObj);
            return new SimpleDateFormat("K:mm:a").format(dateObj);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return t;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_contact:

                if (checkPermission()) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + contact_number));
                    startActivity(intent);
                } else {
                    requestPermission();
                }
                break;
            case R.id.tv_email:
                if (checkPermission()) {
                    sendEmail(getApplicationContext(), new String[]{"rajuvaghela1485@gmail.com"}, "Sending Email",
                            "Test Email", "I am body");
                } else {
                    requestPermission();
                }
                break;
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(LiveOfferDetailActivity.this, new
                String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE}, 1);
    }

    public boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = ContextCompat.checkSelfPermission(LiveOfferDetailActivity.this,
                    android.Manifest.permission.CALL_PHONE);
            return result == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }

    public static void sendEmail(Context context, String[] recipientList,
                                 String title, String subject, String body) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, recipientList);
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);
        context.startActivity(Intent.createChooser(emailIntent, title));
    }
}


