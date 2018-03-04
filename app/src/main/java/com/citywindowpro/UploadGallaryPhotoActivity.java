package com.citywindowpro;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.citywindowpro.Adapter.FiveMainProductSubcategory1Adapter;
import com.citywindowpro.Adapter.ProductDetailAdapter;
import com.citywindowpro.Adapter.SliderAdapter;
import com.citywindowpro.Adapter.UploadGallaryPhotoAdapter;
import com.citywindowpro.Model.FiveMainsubcatgory;
import com.citywindowpro.Utils.Constants;
import com.citywindowpro.Utils.RecyclerItemClickListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cz.msebera.android.httpclient.Header;

public class UploadGallaryPhotoActivity extends AppCompatActivity implements View.OnClickListener {

    List<SliderAdapter> imagelist = new ArrayList<>();


    RecyclerView recyclerView_gallary;
    RecyclerView.Adapter listAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    ProgressDialog dialog;
    List<FiveMainsubcatgory> listuser = new ArrayList<>();

    EditText editText_product_name, editText_Product_price;
    LinearLayout ll_product_image;
    ImageView imageview_photo1;
    Bitmap photo;
    String citywindow;
    String dir;
    boolean isloggedin;
    String l_id, cat_id, sub_id, child_id, ctc_id, l_name, l_img, d_contact, d_email;
    Button btn_upload_gallay_img;
    File file;
    Uri uri1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_gallary_photo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imageview_photo1 = (ImageView) findViewById(R.id.imageview_photo1);
        recyclerView_gallary = (RecyclerView) findViewById(R.id.recyclerView_gallary);
        btn_upload_gallay_img = (Button) findViewById(R.id.btn_upload_gallay_img);
        ll_product_image = (LinearLayout) findViewById(R.id.ll_product_image);
        editText_product_name = (EditText) findViewById(R.id.editText_product_name);
        editText_Product_price = (EditText) findViewById(R.id.editText_Product_price);

        ll_product_image.setOnClickListener(this);
        btn_upload_gallay_img.setOnClickListener(this);


        l_id = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("l_id", null);
        cat_id = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("cat_id", null);
        child_id = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("child_id", null);
        ctc_id = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("ctc_id", null);
        l_name = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("l_name", null);
        l_img = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("l_img", null);
        d_contact = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("d_contact", null);
        d_email = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("d_email", null);


        isloggedin = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isLoggedIn", false);
        if (!isloggedin) {
            startActivity(new Intent(UploadGallaryPhotoActivity.this, LoginActivity.class));
            finish();
        } else {
            dialog = new ProgressDialog(this);
            dialog.setMessage("Loading..");
            dialog.setCancelable(false);
            Log.e("cat_id", "" + cat_id);
            if (cat_id.equals("2")) {
                // FashionistaProductList();
                FashinistaloadgallaryPhoto();
            } else if (cat_id.equals("8")) {
                //RealEstateProductList();
                ReasEstateloadgallaryPhoto();
            } else {
                //FiveProductList();
                FiveCategoryloadgallaryPhoto();
            }
        }


    }

    private void FashinistaloadgallaryPhoto() {
        if (isNetworkAvailable()) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
            params.add("l_id", l_id);

            client.post(Constants.liveuri + "product_fashion.php", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {
                        listuser.clear();

                        try {
                            Log.e("rv_response", responseString);

                            JSONObject jsonObj = new JSONObject(responseString);
                            JSONArray resultsarray = jsonObj.getJSONArray("response");
                            for (int i = 0; i < resultsarray.length(); i++) {
                                FiveMainsubcatgory temp = new FiveMainsubcatgory();
                                temp.setP_id(resultsarray.getJSONObject(i).getString("p_id"));
                                temp.setLastpage_recyclerview_product_name(resultsarray.getJSONObject(i).getString("pro_name"));
                                temp.setLastpage_recyclerview_product_price(resultsarray.getJSONObject(i).getString("pro_price"));
                                temp.setLastpage_recyclerview_product_image(Constants.liveimageuri + resultsarray.getJSONObject(i).getString("pro_img"));

                                listuser.add(temp);
                                dialog.dismiss();
                            }
                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }

                        recyclerView_gallary.setHasFixedSize(true);
                        mLayoutManager = new LinearLayoutManager(UploadGallaryPhotoActivity.this, LinearLayoutManager.VERTICAL, false);
                        recyclerView_gallary.setLayoutManager(mLayoutManager);
                        listAdapter = new UploadGallaryPhotoAdapter(UploadGallaryPhotoActivity.this, listuser);
                        recyclerView_gallary.setAdapter(listAdapter);

                    }

                }
            });
        } else {
            Toast.makeText(UploadGallaryPhotoActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void ReasEstateloadgallaryPhoto() {
        if (isNetworkAvailable()) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
            params.add("l_id", l_id);

            client.post(Constants.liveuri + "product_list_child.php", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {
                        listuser.clear();

                        try {
                            Log.e("rv_response", responseString);

                            JSONObject jsonObj = new JSONObject(responseString);
                            JSONArray resultsarray = jsonObj.getJSONArray("response");
                            for (int i = 0; i < resultsarray.length(); i++) {
                                FiveMainsubcatgory temp = new FiveMainsubcatgory();
                                temp.setP_id(resultsarray.getJSONObject(i).getString("p_id"));
                                temp.setLastpage_recyclerview_product_name(resultsarray.getJSONObject(i).getString("pro_name"));
                                temp.setLastpage_recyclerview_product_price(resultsarray.getJSONObject(i).getString("pro_price"));
                                temp.setLastpage_recyclerview_product_image(Constants.liveimageuri + resultsarray.getJSONObject(i).getString("pro_img"));

                                listuser.add(temp);
                                dialog.dismiss();
                            }
                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }

                        recyclerView_gallary.setHasFixedSize(true);
                        mLayoutManager = new LinearLayoutManager(UploadGallaryPhotoActivity.this, LinearLayoutManager.VERTICAL, false);
                        recyclerView_gallary.setLayoutManager(mLayoutManager);
                        // Constants.delete_Parent_id="2";
                        listAdapter = new UploadGallaryPhotoAdapter(UploadGallaryPhotoActivity.this, listuser);
                        recyclerView_gallary.setAdapter(listAdapter);

                    }

                }
            });
        } else {
            Toast.makeText(UploadGallaryPhotoActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
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

    public void FiveCategoryloadgallaryPhoto() {
        if (isNetworkAvailable()) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
            params.add("l_id", l_id);

            client.post(Constants.liveuri + "product_list.php", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {
                        listuser.clear();

                        try {
                            Log.e("rv_response", responseString);

                            JSONObject jsonObj = new JSONObject(responseString);
                            JSONArray resultsarray = jsonObj.getJSONArray("response");
                            for (int i = 0; i < resultsarray.length(); i++) {
                                FiveMainsubcatgory temp = new FiveMainsubcatgory();
                                temp.setP_id(resultsarray.getJSONObject(i).getString("p_id"));
                                temp.setLastpage_recyclerview_product_name(resultsarray.getJSONObject(i).getString("pro_name"));
                                temp.setLastpage_recyclerview_product_price(resultsarray.getJSONObject(i).getString("pro_price"));
                                temp.setLastpage_recyclerview_product_image(Constants.liveimageuri + resultsarray.getJSONObject(i).getString("pro_img"));

                                listuser.add(temp);
                                dialog.dismiss();
                            }
                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }

                        recyclerView_gallary.setHasFixedSize(true);
                        mLayoutManager = new LinearLayoutManager(UploadGallaryPhotoActivity.this, LinearLayoutManager.VERTICAL, false);
                        recyclerView_gallary.setLayoutManager(mLayoutManager);
                        listAdapter = new UploadGallaryPhotoAdapter(UploadGallaryPhotoActivity.this, listuser);
                        recyclerView_gallary.setAdapter(listAdapter);

                    }

                }
            });
        } else {
            Toast.makeText(UploadGallaryPhotoActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    /*public void loadgallaryPhoto() {
        if (isNetworkAvailable()) {
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
                        listuser.clear();

                        try {
                            Log.d("res", responseString);
                            //Toast.makeText(getContext().getApplicationContext(), res.toString(), Toast.LENGTH_SHORT).show();
                            JSONObject jsonObj = new JSONObject(responseString);
                            JSONArray resultsarray = jsonObj.getJSONArray("response");
                            // looping through All Contacts
                            for (int i = 0; i < resultsarray.length(); i++) {
                                FiveMainsubcatgory temp = new FiveMainsubcatgory();
                                temp.setSubid(resultsarray.getJSONObject(i).getString("child_id"));
                                temp.setSubname(resultsarray.getJSONObject(i).getString("child_name"));
                                temp.setImg(Constants.liveimageuri + resultsarray.getJSONObject(i).getString("c_img"));
                                listuser.add(temp);
                                dialog.dismiss();
                            }
                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }
                        recyclerView_gallary.setHasFixedSize(true);
                        mLayoutManager = new LinearLayoutManager(UploadGallaryPhotoActivity.this, LinearLayoutManager.VERTICAL, false);
                        recyclerView_gallary.setLayoutManager(mLayoutManager);
                        listAdapter = new FiveMainProductSubcategory1Adapter(UploadGallaryPhotoActivity.this, listuser);
                        recyclerView_gallary.setAdapter(listAdapter);
     *//*                   recyclerView_gallary.addOnItemTouchListener(new RecyclerItemClickListener(RealEstateSubcategory1Activity.this, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Constants.child_id=listuser.get(position).getSubid();
                                startActivity(new Intent(getApplicationContext(), RealEstateSubcategory2Activity.class));
                            }
                        }));*//*
                    }
                }
            });
        } else {
            Toast.makeText(UploadGallaryPhotoActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }*/

    /*public void loadgallaryPhoto() {
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


                    }

                }
            });
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
            case R.id.btn_upload_gallay_img:

                if (editText_product_name.getText().toString().isEmpty()) {
                    Toast.makeText(UploadGallaryPhotoActivity.this, "Enter Product Name!", Toast.LENGTH_LONG).show();
                } else if (editText_Product_price.getText().toString().isEmpty()) {
                    Toast.makeText(UploadGallaryPhotoActivity.this, "Enter Product Price!", Toast.LENGTH_LONG).show();
                } else if (uri1 == null) {
                    Toast.makeText(UploadGallaryPhotoActivity.this, "Select Image!", Toast.LENGTH_LONG).show();
                } else {
                    Log.e("cat_id", "" + cat_id);
                    if (cat_id.equals("2")) {
                        FashinistaUploadGallary(l_id, editText_product_name.getText().toString(), editText_Product_price.getText().toString());
                    } else if (cat_id.equals("8")) {
                        RealEstateUploadGallary(l_id, editText_product_name.getText().toString(), editText_Product_price.getText().toString());
                    } else {

                        FiveCategoryUploadGallary(l_id);
                    }
                }

                break;
            case R.id.ll_product_image:

                if (checkPermission()) {
                    Intent intent1 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent1, 1);
                } else {
                    requestPermission();
                }
                break;


        }
    }

    private void FashinistaUploadGallary(String id, String name, String Price) {
        if (isNetworkAvailable()) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
            params.add("l_id", id);
            params.add("pro_name", name);
            params.add("pro_price", Price);
            try {
                params.put("img", file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            client.post(Constants.liveuri + "add_product_fashion.php", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {
                        String str = null;
                        Log.e("add_pro_res", "" + responseString);
                        try {
                            JSONObject jsonObj = new JSONObject(responseString);
                            str = jsonObj.getString("success");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (str.equals("true")) {
                            //Constants.user_mob = et_Register_Mobile.getText().toString();
                            Toast.makeText(UploadGallaryPhotoActivity.this, "Profile picture upload successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(UploadGallaryPhotoActivity.this, MerchantAcountActivity.class));
                            dialog.dismiss();
                            finish();
                        } else if (str.equals("false")) {
                            dialog.dismiss();
                            Toast.makeText(UploadGallaryPhotoActivity.this, "Try again!", Toast.LENGTH_LONG).show();
                        } else {
                            dialog.dismiss();
                        }


                    }

                }
            });
        } else {
            Toast.makeText(UploadGallaryPhotoActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }

    }

    private void RealEstateUploadGallary(String id, String name, String Price) {
        if (isNetworkAvailable()) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
            params.add("l_id", id);
            params.add("pro_name", name);
            params.add("pro_rice", Price);
            try {
                params.put("img", file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            client.post(Constants.liveuri + "add_product_list_child.php", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {
                        String str = null;
                        Log.e("add_pro_res", "" + responseString);
                        try {
                            JSONObject jsonObj = new JSONObject(responseString);
                            str = jsonObj.getString("success");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (str.equals("true")) {
                            //Constants.user_mob = et_Register_Mobile.getText().toString();
                            Toast.makeText(UploadGallaryPhotoActivity.this, "Profile picture upload successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(UploadGallaryPhotoActivity.this, MerchantAcountActivity.class));
                            dialog.dismiss();
                            finish();
                        } else if (str.equals("false")) {
                            dialog.dismiss();
                            Toast.makeText(UploadGallaryPhotoActivity.this, "Try again!", Toast.LENGTH_LONG).show();
                        } else {
                            dialog.dismiss();
                        }


                    }

                }
            });
        } else {
            Toast.makeText(UploadGallaryPhotoActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }

    }

    public void FiveCategoryUploadGallary(String id) {
        if (isNetworkAvailable()) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
            params.add("l_id", id);
            params.add("pro_name", editText_product_name.getText().toString());
            params.add("pro_price", editText_Product_price.getText().toString());
            try {
                params.put("img", file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            client.post(Constants.liveuri + "add_product.php", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {
                        String str = null;
                        Log.e("add_pro_res", "" + responseString);
                        try {
                            JSONObject jsonObj = new JSONObject(responseString);
                            str = jsonObj.getString("success");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (str.equals("true")) {
                            //Constants.user_mob = et_Register_Mobile.getText().toString();
                            Toast.makeText(UploadGallaryPhotoActivity.this, "Profile picture upload successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(UploadGallaryPhotoActivity.this, MerchantAcountActivity.class));
                            dialog.dismiss();
                            finish();
                        } else if (str.equals("false")) {
                            dialog.dismiss();
                            Toast.makeText(UploadGallaryPhotoActivity.this, "Try again!", Toast.LENGTH_LONG).show();
                        } else {
                            dialog.dismiss();
                        }


                    }

                }
            });
        } else {
            Toast.makeText(UploadGallaryPhotoActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    uri1 = data.getData();

                    imageview_photo1.setImageURI(uri1);
                    try {
                        photo = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                        imageview_photo1.setImageBitmap(photo);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    saveImage(photo);
                    break;
            }
        }

    }

    void saveImage(Bitmap img) {
        dir = Environment.getExternalStorageDirectory()
                + File.separator + "pankajmehta";
        File myDir = new File(dir);
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        citywindow = "Image-" + n + ".jpg";
        file = new File(myDir, citywindow);
        long length = file.length();
        Log.e("length", "" + length);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            img.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void requestPermission() {
        ActivityCompat.requestPermissions(UploadGallaryPhotoActivity.this, new
                String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }

    public boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = ContextCompat.checkSelfPermission(UploadGallaryPhotoActivity.this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }

}
