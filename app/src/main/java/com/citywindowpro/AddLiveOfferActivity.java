package com.citywindowpro;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

public class AddLiveOfferActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    String[] items = {"--Select category--", "YUMMY FOODS", "FASHIONISTA", "MALL SUPER MARKET", "HEALTH PERSONAL CARE", "EDUCATION", "HOME APPLIANCE"};
    String[] sub_items = {"--Select sub category--", "YUMMY FOODS", "FASHIONISTA", "MALL SUPER MARKET", "HEALTH PERSONAL CARE", "EDUCATION", "HOME APPLIANCE"};
    ImageView imageview_photo1, imageview_photo2;
    Button btn_submit_offer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_live_offer);

        imageview_photo1 = (ImageView) findViewById(R.id.imageview_photo1);

        imageview_photo1.setOnClickListener(this);

        Spinner spinner_category = (Spinner) findViewById(R.id.spinner_category);
        spinner_category.setOnItemSelectedListener(this);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_category.setAdapter(dataAdapter);

        Spinner spinner_sub_category = (Spinner) findViewById(R.id.spinner_sub_category);
        spinner_sub_category.setOnItemSelectedListener(this);
        ArrayAdapter<String> arrayAdapter_sub_category = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sub_items);
        arrayAdapter_sub_category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_sub_category.setAdapter(arrayAdapter_sub_category);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageview_photo1:
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    Uri uri1 = data.getData();
                    imageview_photo1.setImageURI(uri1);
                    break;


            }
        }

    }
}
