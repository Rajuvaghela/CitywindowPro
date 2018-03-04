package com.citywindowpro.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.citywindowpro.Adapter.FiveMainProductAdapter;
import com.citywindowpro.Adapter.SliderAdapter;
import com.citywindowpro.Adapter.ViewPagerAdapter;
import com.citywindowpro.Adapter.ViewPagerAdapter2;
import com.citywindowpro.FashionistaMainActivity;
import com.citywindowpro.FiveMainProductActivity;
import com.citywindowpro.LiveOfferActivity;
import com.citywindowpro.Model.FiveMainsubcatgory;
import com.citywindowpro.R;
import com.citywindowpro.RealEstateMainActivity;
import com.citywindowpro.RealEstateSubcategory1Activity;
import com.citywindowpro.Utils.Constants;
import com.citywindowpro.Utils.RecyclerItemClickListener;
import com.citywindowpro.YummyFoodsActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;


public class DashboardFragment extends Fragment implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private ViewPager intro_images, second_slider;
    public ArrayList<Integer> imgArray = new ArrayList<Integer>();
    public Runnable mRun;
    public Runnable mRun2;
    private ViewPagerAdapter mAdapter;
    private ViewPagerAdapter2 mAdapter2;
    public int pos = 0;
    public int pos2 = 0;
    private final Handler handler = new Handler();
    ImageView spcl_offer;
    CircleImageView iv_yummyfoods, iv_fashionfesta, iv_electronics, iv_supermarket, iv_health, iv_education, iv_gadjets, iv_realesttae;
    CirclePageIndicator indicator;
    LinearLayout ll_electronics, ll_malls, ll_health, ll_education, ll_home_appliance, ll_real_estate, ll_fashionista;
    ProgressDialog dialog;
    List<SliderAdapter> imagelist;
    List<SliderAdapter> imagelist2;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragement_dashboard, container, false);
        intro_images = (ViewPager) rootView.findViewById(R.id.intro_images);
        second_slider = (ViewPager) rootView.findViewById(R.id.second_slider);
        spcl_offer = (ImageView) rootView.findViewById(R.id.spcl_offer);
        iv_yummyfoods = (CircleImageView) rootView.findViewById(R.id.iv_yummyfoods);
        LinearLayout ll_yummyfoods = (LinearLayout) rootView.findViewById(R.id.ll_yummyfoods);
        ll_electronics = (LinearLayout) rootView.findViewById(R.id.ll_electronics);
        ll_malls = (LinearLayout) rootView.findViewById(R.id.ll_malls);
        ll_health = (LinearLayout) rootView.findViewById(R.id.ll_health);
        ll_education = (LinearLayout) rootView.findViewById(R.id.ll_education);
        ll_home_appliance = (LinearLayout) rootView.findViewById(R.id.ll_home_appliance);
        ll_real_estate = (LinearLayout) rootView.findViewById(R.id.ll_real_estate);
        ll_fashionista = (LinearLayout) rootView.findViewById(R.id.ll_fashionista);


        Animation startAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.anim);
        spcl_offer.startAnimation(startAnimation);

        spcl_offer.setOnClickListener(this);
        ll_yummyfoods.setOnClickListener(this);
        ll_electronics.setOnClickListener(this);
        ll_malls.setOnClickListener(this);
        ll_health.setOnClickListener(this);
        ll_education.setOnClickListener(this);
        ll_home_appliance.setOnClickListener(this);
        ll_real_estate.setOnClickListener(this);
        ll_fashionista.setOnClickListener(this);
        indicator = (CirclePageIndicator) rootView.findViewById(R.id.indicator);


        iv_fashionfesta = (CircleImageView) rootView.findViewById(R.id.iv_fashionfesta);
        iv_electronics = (CircleImageView) rootView.findViewById(R.id.iv_electronics);
        iv_supermarket = (CircleImageView) rootView.findViewById(R.id.iv_supermarket);
        iv_health = (CircleImageView) rootView.findViewById(R.id.iv_health);
        iv_education = (CircleImageView) rootView.findViewById(R.id.iv_education);
        iv_gadjets = (CircleImageView) rootView.findViewById(R.id.iv_gadjets);
        iv_realesttae = (CircleImageView) rootView.findViewById(R.id.iv_realesttae);

        Glide.with(getActivity()).load(getImage("restauranty")).into(iv_yummyfoods);
        Glide.with(getActivity()).load(getImage("shopping")).into(iv_fashionfesta);
        Glide.with(getActivity()).load(getImage("electronics1")).into(iv_electronics);
        Glide.with(getActivity()).load(getImage("mallimg")).into(iv_supermarket);
        Glide.with(getActivity()).load(getImage("health")).into(iv_health);
        Glide.with(getActivity()).load(getImage("education")).into(iv_education);
        Glide.with(getActivity()).load(getImage("home_decor1")).into(iv_gadjets);
        Glide.with(getActivity()).load(getImage("real_estate")).into(iv_realesttae);

     /*   final Animation animation = new AlphaAnimation(1, 0);
        animation.setDuration(1000);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        spcl_offer.startAnimation(animation);*/
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        imagelist = new ArrayList<>();
        imagelist2 = new ArrayList<>();
        loadtopslider();
        loadbottomslider();

        return rootView;
    }

    private void loadbottomslider() {
        if (!isNetworkAvailable()) {
            Toast.makeText(getContext().getApplicationContext(), "No Internet connection", Toast.LENGTH_SHORT).show();
        } else {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
            params.add("s_no", "2");
            client.post(Constants.liveuri + "slider.php", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {
                        imagelist2.clear();

                        try {
                            Log.e("res_bottom_slider", responseString);
                            JSONObject jsonObj = new JSONObject(responseString);
                            JSONArray resultsarray = jsonObj.getJSONArray("response");
                            for (int i = 0; i < resultsarray.length(); i++) {
                                SliderAdapter temp = new SliderAdapter();
                                temp.setBottom_slider_image(Constants.liveimageuri + resultsarray.getJSONObject(i).getString("s_img"));
                                imagelist2.add(temp);
                                dialog.dismiss();
                            }
                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }
                        setbottomSlider();
                    }
                }
            });
        }
    }

    private void setbottomSlider() {
        mAdapter2 = new ViewPagerAdapter2(getContext().getApplicationContext(), imagelist2);
        second_slider.setAdapter(mAdapter2);
        second_slider.setCurrentItem(0);
        second_slider.setOnPageChangeListener(this);
        second_slider.setCurrentItem(pos2, true);

        mRun2 = new Runnable() {
            @Override
            public void run() {
                if (pos2 < mAdapter2.getCount()) {
                    second_slider.setCurrentItem(pos2, true);
                    handler.postDelayed(this, 3000);
                    pos2++;
                } else {
                    pos2 = 0;
                    second_slider.setCurrentItem(pos2, true);
                    handler.postDelayed(this, 3000);
                    //indicator.setViewPager(second_slider);
                    pos2++;
                }
            }
        };
        handler.post(mRun2);

    }

    private void initializeVariable() {
    }

    public void loadtopslider() {
        if (!isNetworkAvailable()) {
            Toast.makeText(getContext().getApplicationContext(), "No Internet connection", Toast.LENGTH_SHORT).show();
        } else {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
            params.add("s_no", "1");
            client.post(Constants.liveuri + "slider.php", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {
                        imagelist.clear();

                        try {
                            Log.e("res_top_slider", responseString);
                            JSONObject jsonObj = new JSONObject(responseString);
                            JSONArray resultsarray = jsonObj.getJSONArray("response");
                            for (int i = 0; i < resultsarray.length(); i++) {
                                SliderAdapter temp = new SliderAdapter();
                                temp.setSlider_image(Constants.liveimageuri + resultsarray.getJSONObject(i).getString("s_img"));
                                imagelist.add(temp);
                                dialog.dismiss();
                            }
                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }
                        setTopSlider();
                    }
                }
            });
        }
    }


    private void setTopSlider() {
        mAdapter = new ViewPagerAdapter(getContext().getApplicationContext(), imagelist);
        intro_images.setAdapter(mAdapter);
        intro_images.setCurrentItem(0);
        intro_images.setOnPageChangeListener(this);
        intro_images.setCurrentItem(pos, true);
        indicator.setViewPager(intro_images);

        mRun = new Runnable() {
            @Override
            public void run() {
                if (pos < mAdapter.getCount()) {
                    intro_images.setCurrentItem(pos, true);
                    handler.postDelayed(this, 3000);
                    pos++;
                } else {
                    pos = 0;
                    intro_images.setCurrentItem(pos, true);
                    handler.postDelayed(this, 3000);
                    indicator.setViewPager(intro_images);
                    pos++;
                }
            }
        };
        handler.post(mRun);


    }




   /* public void slideroneloaddata(){
        if (isNetworkAvailable()){
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
            params.add("s_no","1");
            client.post(Constants.liveuri+"slider.php", new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }
                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {
                        listuserslider1.clear();
                        try {
                            Log.d("res",responseString);
                            //Toast.makeText(getContext().getApplicationContext(), res.toString(), Toast.LENGTH_SHORT).show();
                            JSONObject jsonObj = new JSONObject(responseString);
                            JSONArray resultsarray = jsonObj.getJSONArray("response");

                            // looping through All Contacts
                            for (int i = 0; i < resultsarray.length(); i++) {
                                Categorymodel temp = new Categorymodel();
                                String defaulturi =constants.imageurl_key;
                                // temp.setImageuri("http://gradlesol.com/dayro/getImageById.php?id="+resultsarray.getJSONObject(i).getString("list_image"));
                                temp.setImageuri(defaulturi+resultsarray.getJSONObject(i).getString("slider_image"));
                                temp.setImageid(resultsarray.getJSONObject(i).getString("slider_id"));
                                listuserslider1.add(temp);
                                dialog.dismiss();
                            }
                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }
                        //   Toast.makeText(SubReligionActivity.this, responseString, Toast.LENGTH_SHORT).show();
                    }
                    mAdapter = new ViewPagerAdapter(getActivity(),imgArray);
                    intro_images.setAdapter(mAdapter);
                    intro_images.setCurrentItem(0);
                    indicator.setViewPager(intro_images);
                    intro_images.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {

                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });
                    intro_images.setCurrentItem(pos, true);
                    mRun = new Runnable() {
                        @Override
                        public void run() {
                            if (pos < mAdapter.getCount()) {
                                intro_images.setCurrentItem(pos, true);
                                handler.postDelayed(this, 5000);
                                indicator.setViewPager(intro_images);
                                pos++;

                            } else {
                                pos = 0;
                                intro_images.setCurrentItem(pos, true);
                                handler.postDelayed(this, 5000);
                                indicator.setViewPager(intro_images);
                                pos++;
                            }
                        }
                    };
                    handler.post(mRun);

                }
            });
        }else {
            Toast.makeText(getContext().getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }


    public void slidertwoloaddata(){
        if (isNetworkAvailable()){
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
            params.add("s_no","1");
            client.post(Constants.liveuri+"slider.php", new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }
                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {
                        listuserslider1.clear();
                        try {
                            Log.d("res",responseString);
                            //Toast.makeText(getContext().getApplicationContext(), res.toString(), Toast.LENGTH_SHORT).show();
                            JSONObject jsonObj = new JSONObject(responseString);
                            JSONArray resultsarray = jsonObj.getJSONArray("response");

                            // looping through All Contacts
                            for (int i = 0; i < resultsarray.length(); i++) {
                                Categorymodel temp = new Categorymodel();
                                String defaulturi =constants.imageurl_key;
                                // temp.setImageuri("http://gradlesol.com/dayro/getImageById.php?id="+resultsarray.getJSONObject(i).getString("list_image"));
                                temp.setImageuri(defaulturi+resultsarray.getJSONObject(i).getString("slider_image"));
                                temp.setImageid(resultsarray.getJSONObject(i).getString("slider_id"));
                                listuserslider1.add(temp);
                                dialog.dismiss();
                            }
                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }
                        //   Toast.makeText(SubReligionActivity.this, responseString, Toast.LENGTH_SHORT).show();
                    }
                    mAdapter = new ViewPagerAdapter(getActivity(),imgArray);
                    intro_images.setAdapter(mAdapter);
                    intro_images.setCurrentItem(0);
                    indicator.setViewPager(intro_images);
                    intro_images.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {

                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });
                    intro_images.setCurrentItem(pos, true);
                    mRun = new Runnable() {
                        @Override
                        public void run() {
                            if (pos < mAdapter.getCount()) {
                                intro_images.setCurrentItem(pos, true);
                                handler.postDelayed(this, 5000);
                                indicator.setViewPager(intro_images);
                                pos++;

                            } else {
                                pos = 0;
                                intro_images.setCurrentItem(pos, true);
                                handler.postDelayed(this, 5000);
                                indicator.setViewPager(intro_images);
                                pos++;
                            }
                        }
                    };
                    handler.post(mRun);

                }
            });
        }else {
            Toast.makeText(getContext().getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }*/


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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

    public int getImage(String imageName) {
        int drawableResourceId = this.getResources().getIdentifier(imageName, "drawable", getActivity().getPackageName());
        return drawableResourceId;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_yummyfoods:
                Constants.cat_id = "1";
                startActivity(new Intent(getContext().getApplicationContext(), YummyFoodsActivity.class));
                break;
            case R.id.ll_electronics:
                Constants.cat_id = "3";
                startActivity(new Intent(getContext().getApplicationContext(), FiveMainProductActivity.class));
                break;
            case R.id.ll_malls:
                Constants.cat_id = "4";
                startActivity(new Intent(getContext().getApplicationContext(), FiveMainProductActivity.class));
                break;
            case R.id.ll_health:
                Constants.cat_id = "6";
                startActivity(new Intent(getContext().getApplicationContext(), FiveMainProductActivity.class));
                break;
            case R.id.ll_education:
                Constants.cat_id = "7";
                startActivity(new Intent(getContext().getApplicationContext(), FiveMainProductActivity.class));
                break;
            case R.id.ll_home_appliance:
                Constants.cat_id = "5";
                startActivity(new Intent(getContext().getApplicationContext(), FiveMainProductActivity.class));
                break;
            case R.id.ll_real_estate:
                Constants.cat_id = "8";
                startActivity(new Intent(getContext().getApplicationContext(), RealEstateMainActivity.class));
                break;
            case R.id.ll_fashionista:
                Constants.cat_id = "2";
                startActivity(new Intent(getContext().getApplicationContext(), FashionistaMainActivity.class));
                break;
            case R.id.spcl_offer:
                // Constants.cat_id="2";
                startActivity(new Intent(getContext().getApplicationContext(), LiveOfferActivity.class));
                break;

        }
    }
}
