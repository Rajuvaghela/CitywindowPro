package com.citywindowpro;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.citywindowpro.Utils.Constants;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.citywindowpro.Utils.Constants;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;

import cz.msebera.android.httpclient.Header;

public class DeleteGalleryPhotoActivity extends AppCompatActivity {
    ProgressDialog dialog;
    String p_id;
    String l_id, cat_id, sub_id, child_id, ctc_id, l_name, l_img, d_contact, d_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_gallery_photo);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        p_id=getIntent().getExtras().getString("p_id");

        l_id = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("l_id", null);
        cat_id = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("cat_id", null);
        child_id = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("child_id", null);
        ctc_id = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("ctc_id", null);
        l_name = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("l_name", null);
        l_img = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("l_img", null);
        d_contact = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("d_contact", null);
        d_email = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("d_email", null);


        Log.e("cat_id", "" + cat_id);
        if (cat_id.equals("2")) {
            DeleteGalleryFashionista();
        } else if (cat_id.equals("8")) {
            DeleteProfileRealeEstate();
        } else {
            DeleteGalleryFiveProduct();

        }


    }

    private void DeleteGalleryFashionista() {
        if (isNetworkAvailable()) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();

            params.add("p_id", p_id);
            client.post(Constants.liveuri + "product_fashion_delete.php", params, new TextHttpResponseHandler() {
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
                            Toast.makeText(DeleteGalleryPhotoActivity.this, "Delete successfully!", Toast.LENGTH_SHORT).show();

                            dialog.dismiss();
                            startActivity(new Intent(DeleteGalleryPhotoActivity.this, MerchantAcountActivity.class));
                        } else if (str.equals("false")) {
                            dialog.dismiss();
                            Toast.makeText(DeleteGalleryPhotoActivity.this, "Try Again!", Toast.LENGTH_SHORT).show();
                        } else {
                            dialog.dismiss();
                        }


                    }

                }
            });
        } else {
            Toast.makeText(DeleteGalleryPhotoActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void DeleteProfileRealeEstate() {
        if (isNetworkAvailable()) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
            params.add("p_id", p_id);
            client.post(Constants.liveuri + "product_list_child_delete.php", params, new TextHttpResponseHandler() {
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
                            Toast.makeText(DeleteGalleryPhotoActivity.this, "Delete successfully!", Toast.LENGTH_SHORT).show();

                            dialog.dismiss();
                            finish();
                        } else if (str.equals("false")) {
                            dialog.dismiss();
                            Toast.makeText(DeleteGalleryPhotoActivity.this, "Try Again!", Toast.LENGTH_SHORT).show();
                        } else {
                            dialog.dismiss();
                        }


                    }

                }
            });
        } else {
            Toast.makeText(DeleteGalleryPhotoActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }


    public void DeleteGalleryFiveProduct() {
        if (isNetworkAvailable()) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
            params.add("p_id", p_id);
            client.post(Constants.liveuri + "product_delete.php", params, new TextHttpResponseHandler() {
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
                            Toast.makeText(DeleteGalleryPhotoActivity.this, "Delete successfully!", Toast.LENGTH_SHORT).show();

                            dialog.dismiss();
                            finish();
                        } else if (str.equals("false")) {
                            dialog.dismiss();
                            Toast.makeText(DeleteGalleryPhotoActivity.this, "Try Again!", Toast.LENGTH_SHORT).show();
                        } else {
                            dialog.dismiss();
                        }


                    }

                }
            });
        } else {
            Toast.makeText(DeleteGalleryPhotoActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
