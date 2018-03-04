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

import java.io.FileNotFoundException;

import cz.msebera.android.httpclient.Header;

public class DeleteProfilePhotoActivity extends AppCompatActivity {
    ProgressDialog dialog;
    String g_id;
    String l_id, cat_id, sub_id, child_id, ctc_id, l_name, l_img, d_contact, d_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_profile_photo);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);

        g_id = getIntent().getExtras().getString("g_id");

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
            DeleteProfileFashionista();
        } else if (cat_id.equals("8")) {
            DeleteProfileRealEstate();
        } else {
            DeleteProfileFiveProduct();

        }


    }

    private void DeleteProfileRealEstate() {
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
                            Toast.makeText(DeleteProfilePhotoActivity.this, " Profile delete successfully!", Toast.LENGTH_SHORT).show();

                            dialog.dismiss();
                            finish();
                        } else if (str.equals("false")) {
                            dialog.dismiss();
                            Toast.makeText(DeleteProfilePhotoActivity.this, "Try Again!", Toast.LENGTH_SHORT).show();
                        } else {
                            dialog.dismiss();
                        }


                    }

                }
            });
        } else {
            Toast.makeText(DeleteProfilePhotoActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(DeleteProfilePhotoActivity.this, " Profile delete successfully!", Toast.LENGTH_SHORT).show();

                            dialog.dismiss();
                            startActivity(new Intent(DeleteProfilePhotoActivity.this, MerchantAcountActivity.class));
                        } else if (str.equals("false")) {
                            dialog.dismiss();
                            Toast.makeText(DeleteProfilePhotoActivity.this, "Try Again!", Toast.LENGTH_SHORT).show();
                        } else {
                            dialog.dismiss();
                        }


                    }

                }
            });
        } else {
            Toast.makeText(DeleteProfilePhotoActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(DeleteProfilePhotoActivity.this, " Profile delete successfully!", Toast.LENGTH_SHORT).show();

                            dialog.dismiss();
                            finish();
                        } else if (str.equals("false")) {
                            dialog.dismiss();
                            Toast.makeText(DeleteProfilePhotoActivity.this, "Try Again!", Toast.LENGTH_SHORT).show();
                        } else {
                            dialog.dismiss();
                        }


                    }

                }
            });
        } else {
            Toast.makeText(DeleteProfilePhotoActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
