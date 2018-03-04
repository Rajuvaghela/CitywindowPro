package com.citywindowpro;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AboutusActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView tv_call = (TextView) findViewById(R.id.tv_call);
        tv_call.setOnClickListener(this);
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
        switch (v.getId()) {
            case R.id.tv_call:

                if (checkPermission()) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "+918238915015"));
                    startActivity(intent);
                }else {
                    requestPermission();
                }
                break;








        }
    }
    private void    requestPermission() {
        ActivityCompat.requestPermissions(AboutusActivity.this, new
                String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE}, 1);
    }

    public boolean checkPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = ContextCompat.checkSelfPermission(AboutusActivity.this,
                    android.Manifest.permission.CALL_PHONE);
            return result == PackageManager.PERMISSION_GRANTED;
        }else {
            return true;
        }
    }

}
