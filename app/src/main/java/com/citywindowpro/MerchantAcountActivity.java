package com.citywindowpro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.citywindowpro.Utils.Constants;

public class MerchantAcountActivity extends AppCompatActivity implements View.OnClickListener {
TextView tv_upload_gallary_photo,tv_upload_profile_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_acount);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv_upload_gallary_photo=(TextView)findViewById(R.id.tv_upload_gallary_photo);
        tv_upload_profile_image=(TextView)findViewById(R.id.tv_upload_profile_image);
        tv_upload_profile_image.setOnClickListener(this);
        tv_upload_gallary_photo.setOnClickListener(this);

        boolean isloggedin = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isLoggedIn", false);
        if (!isloggedin) {
            Constants.login_id="setting";
            startActivity(new Intent(MerchantAcountActivity.this, LoginActivity.class));
            finish();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_upload_gallary_photo:
                startActivity(new Intent(MerchantAcountActivity.this, UploadGallaryPhotoActivity.class));
                break;
            case R.id.tv_upload_profile_image:
                startActivity(new Intent(MerchantAcountActivity.this, UploadProfilePhotoActivity.class));
                break;
        }
    }
}
