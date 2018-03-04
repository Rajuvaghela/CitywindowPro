package com.citywindowpro;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.citywindowpro.Adapter.SearchAdapter;
import com.citywindowpro.Fragment.DashboardFragment;
import com.citywindowpro.Model.SearchModel;
import com.citywindowpro.Utils.Constants;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class HomemenuDisplayActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private DrawerLayout mDrawer;
    public NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    boolean isloggeddin;
    TextView nav_textview_header_name, nav_marchent_email;
    String l_name, d_email, l_img;
    AutoCompleteTextView editText_search;
    ProgressDialog dialog;
    ImageView search_cancel, nav_imageView_profile;
    SearchAdapter adapter;
    int pos;
    List<SearchModel> sersch_model_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homemenu_display);
        getSupportActionBar().hide();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        editText_search = (AutoCompleteTextView) findViewById(R.id.editText_search);
        search_cancel = (ImageView) findViewById(R.id.search_cancel);
        editText_search.setOnItemClickListener(this);
        search_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_search.setText("");
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        });
        navigationView = (NavigationView) findViewById(R.id.nvView);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        View view = navigationView.getHeaderView(0);
        nav_imageView_profile = (ImageView) view.findViewById(R.id.nav_imageView_profile);

        nav_textview_header_name = (TextView) view.findViewById(R.id.nav_textview_header_name);
        //  nav_marchent_email = (TextView) view.findViewById(R.id.nav_marchent_email);
       /* l_name = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("l_name", null);
        String image_url=getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("l_img", null);*/


        //  d_email = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("d_email", null);
        // nav_textview_header_email.setText(l_name);
        // nav_marchent_email.setText(d_email);
        dialog = new ProgressDialog(HomemenuDisplayActivity.this);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);


        initNavigationDrawer();
        DashboardFragment fragment = new DashboardFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flContent, fragment);
        fragmentTransaction.commit();
        editText_search.setThreshold(1);
        search();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        l_name = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("l_name", null);
        l_img = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("l_img", null);
        d_email = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("d_email", null);


        Log.e("l_name", "" + l_name);
        Log.e("l_img", "" + l_img);

        if (l_name == null) {
            nav_textview_header_name.setText("Username");

        } else {
            nav_textview_header_name.setText(l_name);
            Glide.with(getApplicationContext()).load(Constants.liveimageuri + l_img).into(nav_imageView_profile);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


        l_name = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("l_name", null);
        l_img = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("l_img", null);
        d_email = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("d_email", null);


        Log.e("resume_l_name", "" + l_name);
        Log.e("resume_l_img", "" + l_img);

        if (l_name == null) {
            nav_textview_header_name.setText("Citywindow");
            nav_imageView_profile.setImageResource(R.mipmap.ic_launcher);

        } else {
            nav_textview_header_name.setText(l_name);
            Glide.with(getApplicationContext()).load(Constants.liveimageuri + l_img).into(nav_imageView_profile);
        }

    }

    public void search() {
        if (isNetworkAvailable()) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(50000);
            client.post(Constants.liveuri + "search.php", new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {
                        sersch_model_list.clear();
                        try {
                            Log.e("serch", responseString);
                            JSONObject jsonObj = new JSONObject(responseString);
                            JSONArray resultsarray = jsonObj.getJSONArray("response");


                            for (int i = 0; i < resultsarray.length(); i++) {
                                SearchModel temp = new SearchModel();
                                temp.setL_id(resultsarray.getJSONObject(i).getString("l_id"));
                                temp.setCat_id(resultsarray.getJSONObject(i).getString("cat_id"));
                                temp.setSearch(resultsarray.getJSONObject(i).getString("search"));
                                sersch_model_list.add(temp);
                                dialog.dismiss();
                            }
                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }
                        //   Toast.makeText(SubReligionActivity.this, responseString, Toast.LENGTH_SHORT).show();
                    }
                    adapter = new SearchAdapter(HomemenuDisplayActivity.this, sersch_model_list);
                    editText_search.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }

    public void initNavigationDrawer() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                mDrawer.closeDrawers();
                switch (id) {
                    case R.id.nav_camera:
                        DashboardFragment fragment = new DashboardFragment();
                        fragmentTransaction.replace(R.id.flContent, fragment);
                        fragmentTransaction.commit();
                        mDrawer.closeDrawers();
                        break;
                    case R.id.nav_add_marchent:
                       /* AddMerchantFragment addMerchantFragment = new AddMerchantFragment();
                        fragmentTransaction.replace(R.id.flContent, addMerchantFragment);
                        fragmentTransaction.commit();*/
                        startActivity(new Intent(HomemenuDisplayActivity.this, AddMerchantActivity.class));
                        mDrawer.closeDrawers();
                        break;
    /*                case R.id.nav_login_marchent:
                        Constants.login = "1";
                        isloggeddin = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isLoggedIn", false);
                        if (isloggeddin) {

                        } else {
                            startActivity(new Intent(HomemenuDisplayActivity.this, LoginActivity.class));
                            mDrawer.closeDrawers();
                        }

                        break;*/
                    case R.id.nav_slideshow:
                        startActivity(new Intent(HomemenuDisplayActivity.this, AboutusActivity.class));
                        mDrawer.closeDrawers();
                        break;
                    case R.id.nav_add_offer:
                        Constants.login = "2";
                        startActivity(new Intent(HomemenuDisplayActivity.this, GetRegistrationPhotoActivity.class));
                        mDrawer.closeDrawers();
                        break;
                    case R.id.nav_logout_marchent:

                        isloggeddin = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isLoggedIn", false);
                        if (isloggeddin) {
                            getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                                    .edit()
                                    .putString("cat_id", null)
                                    .putString("l_id", null)
                                    .putString("sub_id", null)
                                    .putString("child_id", null)
                                    .putString("ctc_id", null)
                                    .putString("l_name", null)
                                    .putString("l_img", null)
                                    .putString("d_contact", null)
                                    .putString("d_email", null).clear()
                                    .apply();
                            getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                                    .edit()
                                    .putBoolean("isLoggedIn", false).clear()
                                    .apply();
                            Toast.makeText(HomemenuDisplayActivity.this, "Logout successfully!", Toast.LENGTH_SHORT).show();

                            Menu nav_Menu = navigationView.getMenu();
                            nav_Menu.findItem(R.id.nav_logout_marchent).setVisible(false);

                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                            mDrawer.closeDrawers();
                        }


                        break;
                    case R.id.nav_manage:
                        startActivity(new Intent(HomemenuDisplayActivity.this, PrivacyPolicyActivity.class));
                        mDrawer.closeDrawers();
                        break;

                    case R.id.nav_share:
/*                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("text/plain");
                        i.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL");
                        i.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.pankajmehtanewdemo");
                        startActivity(Intent.createChooser(i, "Share URL"));*/
                        mDrawer.closeDrawers();
                        break;
                    case R.id.nav_acount:
                        Constants.login = "3";
                        startActivity(new Intent(HomemenuDisplayActivity.this, MerchantAcountActivity.class));
                        mDrawer.closeDrawers();
                        break;

                }
                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
                super.onDrawerOpened(v);
            }
        };
        mDrawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)

                .setTitle("Confirm exit!")
                .setMessage("Are you sure you want to close this Application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String selection = (String) parent.getItemAtPosition(position);
        for (int i = 0; i < sersch_model_list.size(); i++) {

            if (sersch_model_list.get(i).getSearch().equals(selection)) {
                pos = i;
                break;
            }
        }
        Log.d("position", String.valueOf(pos));
        Log.d("listid", sersch_model_list.get(pos).getCat_id());
        if (sersch_model_list.get(pos).getCat_id().toString().equals("1")) {
            Constants.l_id = sersch_model_list.get(pos).getL_id();
            Constants.fiveproducat_lid = sersch_model_list.get(pos).getL_id();
            Constants.fashionista_lid = null;
            Constants.realestate_lid = null;
            Constants.id = "1";
            Constants.cat_id = "1";
            startActivity(new Intent(getApplicationContext(), YummyFoodsMoreDetailsActivity.class));
            editText_search.setText("");
        } else if (sersch_model_list.get(pos).getCat_id().toString().equals("2")) {
            Constants.l_id = sersch_model_list.get(pos).getL_id();
            Constants.fashionista_lid = sersch_model_list.get(pos).getL_id();
            Constants.fiveproducat_lid = null;
            Constants.realestate_lid = null;
            Constants.id = "2";
            Constants.cat_id = "2";
            startActivity(new Intent(getApplicationContext(), YummyFoodsMoreDetailsActivity.class).putExtra("title", selection));
            editText_search.setText("");
        } else if (sersch_model_list.get(pos).getCat_id().toString().equals("3")) {
            Constants.l_id = sersch_model_list.get(pos).getL_id();
            Constants.fiveproducat_lid = sersch_model_list.get(pos).getL_id();
            Constants.fashionista_lid = null;
            Constants.realestate_lid = null;
            Constants.id = "1";
            Constants.cat_id = "3";
            startActivity(new Intent(getApplicationContext(), YummyFoodsMoreDetailsActivity.class));
            editText_search.setText("");

        } else if (sersch_model_list.get(pos).getCat_id().toString().equals("4")) {
            Constants.l_id = sersch_model_list.get(pos).getL_id();
            Constants.fiveproducat_lid = sersch_model_list.get(pos).getL_id();
            Constants.fashionista_lid = null;
            Constants.realestate_lid = null;
            Constants.id = "1";
            Constants.cat_id = "4";
            startActivity(new Intent(getApplicationContext(), YummyFoodsMoreDetailsActivity.class));
            editText_search.setText("");

        } else if (sersch_model_list.get(pos).getCat_id().toString().equals("5")) {
            Constants.l_id = sersch_model_list.get(pos).getL_id();
            Constants.fiveproducat_lid = sersch_model_list.get(pos).getL_id();
            Constants.fashionista_lid = null;
            Constants.realestate_lid = null;
            Constants.id = "1";
            Constants.cat_id = "5";
            startActivity(new Intent(getApplicationContext(), YummyFoodsMoreDetailsActivity.class));
            editText_search.setText("");

        } else if (sersch_model_list.get(pos).getCat_id().toString().equals("6")) {
            Constants.l_id = sersch_model_list.get(pos).getL_id();
            Constants.fiveproducat_lid = sersch_model_list.get(pos).getL_id();
            Constants.fashionista_lid = null;
            Constants.realestate_lid = null;
            Constants.id = "1";
            Constants.cat_id = "6";
            startActivity(new Intent(getApplicationContext(), YummyFoodsMoreDetailsActivity.class));
            editText_search.setText("");

        } else if (sersch_model_list.get(pos).getCat_id().toString().equals("7")) {
            Constants.l_id = sersch_model_list.get(pos).getL_id();
            Constants.fiveproducat_lid = sersch_model_list.get(pos).getL_id();
            Constants.fashionista_lid = null;
            Constants.realestate_lid = null;
            Constants.id = "1";
            Constants.cat_id = "7";
            startActivity(new Intent(getApplicationContext(), YummyFoodsMoreDetailsActivity.class));
            editText_search.setText("");

        } else if (sersch_model_list.get(pos).getCat_id().toString().equals("8")) {
            Constants.l_id = sersch_model_list.get(pos).getL_id();
            Constants.realestate_lid = sersch_model_list.get(pos).getL_id();
            Constants.fashionista_lid = null;
            Constants.fiveproducat_lid = null;
            Constants.id = "3";
            Constants.cat_id = "8";
            startActivity(new Intent(getApplicationContext(), YummyFoodsMoreDetailsActivity.class));
            editText_search.setText("");
        }/*else if (sersch_model_list.get(pos).getType().toString().equals("3")){
            startActivity(new Intent(getApplicationContext(), SubCategoryDisplayActivity.class).putExtra("ganlibtitle",selection));
            EditText_dashboard_search.setText("");
        }else if (sersch_model_list.get(pos).getType().toString().equals("4")){
            EditText_dashboard_search.setText("");
            startActivity(new Intent(getApplicationContext(),SubcategoryfulldescActivity.class).putExtra("name",sersch_model_list.get(pos).getId()));
        }*/
        /*else if (listuserslider1.get(posi).getType().toString().equals("2")){
            startActivity(new Intent(getApplicationContext(),SubCategoryDisplayActivity.class).putExtra("category",selection));
            EditText_dashboard_search.setText("");
        }else if (listuserslider1.get(posi).getType().toString().equals("3")){
            startActivity(new Intent(getApplicationContext(), SubCategoryDisplayActivity.class).putExtra("ganlibtitle",selection));
            EditText_dashboard_search.setText("");
        }else if (listuserslider1.get(posi).getType().toString().equals("4")){
            EditText_dashboard_search.setText("");
            startActivity(new Intent(getApplicationContext(),SubcategoryfulldescActivity.class).putExtra("name",listuserslider1.get(posi).getId()));
        }else if (listuserslider1.get(posi).getType().toString().equals("5")){
            startActivity(new Intent(getApplicationContext(),SubcategoryfulldescActivity.class).putExtra("gandhilibnm",selection));
            EditText_dashboard_search.setText("");
        }else if (listuserslider1.get(posi).getType().toString().equals("6")){
            startActivity(new Intent(getApplicationContext(),EventActivity.class).putExtra("eventnamename",selection));
            EditText_dashboard_search.setText("");
        }else if (listuserslider1.get(posi).getType().toString().equals("7")){
            EditText_dashboard_search.setText("");
            startActivity(new Intent(getApplicationContext(),SubCategoryDisplayActivity.class).putExtra("homemenycategoryname",listuserslider1.get(posi).getId()));
        }else if (listuserslider1.get(posi).getType().toString().equals("8")){
            EditText_dashboard_search.setText("");
            startActivity(new Intent(getApplicationContext(),HomemenuActivity.class).putExtra("homemenucategoryname",listuserslider1.get(posi).getId()));
        }else if (listuserslider1.get(posi).getType().toString().equals("10")){
            EditText_dashboard_search.setText("");
            startActivity(new Intent(getApplicationContext(),MoviesListActivity.class).putExtra("cinemaname",selection));
        }else if (listuserslider1.get(posi).getType().toString().equals("12")){
            EditText_dashboard_search.setText("");
            startActivity(new Intent(getApplicationContext(),AstroDisplayActivity.class).putExtra("astroname",selection));
        }else if (listuserslider1.get(posi).getType().toString().equals("15")){
            startActivity(new Intent(getApplicationContext(), QuckRefrenceActivity.class).putExtra("quickrefchildlist",selection));
            EditText_dashboard_search.setText("");
        }else if (listuserslider1.get(posi).getType().toString().equals("16")){
            startActivity(new Intent(getApplicationContext(), QuckRefrenceActivity.class).putExtra("quickrefsublist",selection));
            EditText_dashboard_search.setText("");
        }else if (listuserslider1.get(posi).getType().toString().equals("21")){
            startActivity(new Intent(getApplicationContext(), DealsOfferListActivity.class).putExtra("Delascatname",selection));
            EditText_dashboard_search.setText("");
        }else if (listuserslider1.get(posi).getType().toString().equals("22")){
            EditText_dashboard_search.setText("");
            startActivity(new Intent(getApplicationContext(), DealsofferfulllistDisplayActivity.class).putExtra("Delasoffername",selection));
        }else if (listuserslider1.get(posi).getType().toString().equals("23")){
            EditText_dashboard_search.setText("");
            startActivity(new Intent(getApplicationContext(),ImportantwebsiteActivity.class).putExtra("importantwensitetitle",selection));
        }else if (listuserslider1.get(posi).getType().toString().equals("24")){
            EditText_dashboard_search.setText("");
            startActivity(new Intent(getApplicationContext(),ImportantwebsitewebviewActivity.class).putExtra("iwebsitetile",selection));
        }*/
        else {
            Toast.makeText(HomemenuDisplayActivity.this, "No Data Available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.search_menu, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }
}
