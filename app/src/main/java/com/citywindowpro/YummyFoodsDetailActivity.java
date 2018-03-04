package com.citywindowpro;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.citywindowpro.Adapter.YummyFoodsAdapter;

public class YummyFoodsDetailActivity extends AppCompatActivity  {
    RecyclerView rv_yummydetaail;
    RecyclerView.Adapter listAdapter;
    RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yummy_food_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rv_yummydetaail = (RecyclerView) findViewById(R.id.rv_yummydetaail);


        rv_yummydetaail.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(YummyFoodsDetailActivity.this, LinearLayoutManager.VERTICAL, false);
        rv_yummydetaail.setLayoutManager(mLayoutManager);
        listAdapter = new YummyFoodsAdapter(YummyFoodsDetailActivity.this);
        rv_yummydetaail.setAdapter(listAdapter);


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


}
