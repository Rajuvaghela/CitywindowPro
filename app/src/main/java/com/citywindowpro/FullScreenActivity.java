package com.citywindowpro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.citywindowpro.Model.FiveMainsubcatgory;
import com.citywindowpro.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class FullScreenActivity extends AppCompatActivity {
    List<FiveMainsubcatgory> rv_list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
       ImageView iv_product_list_image = (ImageView) findViewById(R.id.iv_product_list_image);
        Glide.with(Constants.context).load(rv_list.get(Constants.possition).getLastpage_recyclerview_product_image()).into(iv_product_list_image);

    }
}
