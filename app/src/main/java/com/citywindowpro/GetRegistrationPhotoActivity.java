package com.citywindowpro;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.citywindowpro.Adapter.ComplainPopupAdapter;
import com.citywindowpro.Model.LiveOfferModel;
import com.citywindowpro.Model.SpinerModel;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

public class GetRegistrationPhotoActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    EditText editText_price1, editText_price3, editText_price4, editText_price5;
    ImageView imageview_photo1/*, imageview_photo2, imageview_photo3, imageview_photo4, imageview_photo5*/;
    Button btn_live_offer;
    // ProgressDialog dialog;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    // Spinner spinner_sub_category, spinner_child_category, spinner_ctc_category, spinner_ctc_category1;
    Spinner spinner_category;
    Button btn_start_date, btn_end_time, btn_end_date, btn_start_time;
    private int hour;
    private int minute;
    ProgressDialog dialog;
    TextView tv_select_productname, editText_productprice, editText_productname;
    List<LiveOfferModel> rv_list = new ArrayList<>();
    RecyclerView recyclerView_popup;
    EditText editText_Offer_price, editText_description;
    String start_date, end_date, start_time, end_time;
    boolean isloggedin;
    String l_id, cat_id, sub_id, child_id, ctc_id, l_name, l_img, d_contact, d_email;
    Bitmap photo;
    String p_name, p_price;
    String citywindow;
    String dir;
    String date_first, date_second;
    String time_first, time_second;

    File file;


    List<SpinerModel> list_category_type = new ArrayList<>();
    ArrayList<String> category_typearray = new ArrayList<>();

    List<SpinerModel> list_sub_category_type = new ArrayList<>();
    ArrayList<String> sub_category_typearray = new ArrayList<>();

    List<SpinerModel> list_child_category_type = new ArrayList<>();
    ArrayList<String> child_category_typearray = new ArrayList<>();

    List<SpinerModel> list_child_category_type1 = new ArrayList<>();
    ArrayList<String> child_category_typearray1 = new ArrayList<>();

    List<SpinerModel> list_child_category_type2 = new ArrayList<>();
    ArrayList<String> child_category_typearray2 = new ArrayList<>();

    ArrayAdapter<String> spinnerArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_registration_photo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        editText_Offer_price = (EditText) findViewById(R.id.editText_Offer_price);
        editText_description = (EditText) findViewById(R.id.editText_description);


        btn_live_offer = (Button) findViewById(R.id.btn_live_offer);
        btn_start_date = (Button) findViewById(R.id.btn_start_date);
        btn_start_time = (Button) findViewById(R.id.btn_start_time);
        btn_end_date = (Button) findViewById(R.id.btn_end_date);
        btn_end_time = (Button) findViewById(R.id.btn_end_time);
        editText_productname = (TextView) findViewById(R.id.editText_productname);
        editText_productprice = (TextView) findViewById(R.id.editText_productprice);
        tv_select_productname = (TextView) findViewById(R.id.tv_select_productname);

        tv_select_productname.setOnClickListener(this);
        btn_start_date.setOnClickListener(this);
        btn_start_time.setOnClickListener(this);
        btn_end_date.setOnClickListener(this);
        btn_end_time.setOnClickListener(this);

        imageview_photo1 = (ImageView) findViewById(R.id.imageview_photo1);

        imageview_photo1.setOnClickListener(this);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);


        btn_live_offer.setOnClickListener(this);

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
            startActivity(new Intent(GetRegistrationPhotoActivity.this, LoginActivity.class));
            finish();
        } else {
            dialog = new ProgressDialog(this);
            dialog.setMessage("Loading..");
            dialog.setCancelable(false);
            Log.e("cat_id", "" + cat_id);
            if (cat_id.equals("2")) {
                FashionistaProductList();
            } else if (cat_id.equals("8")) {
                RealEstateProductList();
            } else {
                FiveProductList();
            }
        }

    }

    public void RealEstateProductList() {
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
                        rv_list.clear();

                        try {
                            Log.e("reaelestet_res", responseString);

                            JSONObject jsonObj = new JSONObject(responseString);
                            JSONArray resultsarray = jsonObj.getJSONArray("response");
                            for (int i = 0; i < resultsarray.length(); i++) {
                                LiveOfferModel temp = new LiveOfferModel();
                                temp.setPro_name(resultsarray.getJSONObject(i).getString("pro_name"));
                                temp.setPro_price(resultsarray.getJSONObject(i).getString("pro_price"));
                                temp.setL_id(resultsarray.getJSONObject(i).getString("l_id"));
                                temp.setP_id(resultsarray.getJSONObject(i).getString("p_id"));

                                temp.setProduct_image(Constants.liveimageuri + resultsarray.getJSONObject(i).getString("pro_img"));


                                rv_list.add(temp);
                                dialog.dismiss();
                            }
                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(GetRegistrationPhotoActivity.this, "data is not available!", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        } else {
            Toast.makeText(GetRegistrationPhotoActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }

    }

    public void FashionistaProductList() {
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
                        rv_list.clear();

                        try {
                            Log.e("product_fashion", responseString);

                            JSONObject jsonObj = new JSONObject(responseString);
                            JSONArray resultsarray = jsonObj.getJSONArray("response");
                            for (int i = 0; i < resultsarray.length(); i++) {
                                LiveOfferModel temp = new LiveOfferModel();
                                temp.setPro_name(resultsarray.getJSONObject(i).getString("pro_name"));
                                temp.setPro_price(resultsarray.getJSONObject(i).getString("pro_price"));
                                temp.setL_id(resultsarray.getJSONObject(i).getString("l_id"));
                                temp.setP_id(resultsarray.getJSONObject(i).getString("p_id"));


                                temp.setProduct_image(Constants.liveimageuri + resultsarray.getJSONObject(i).getString("pro_img"));

                                rv_list.add(temp);
                                dialog.dismiss();
                            }
                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(GetRegistrationPhotoActivity.this, "data is not available!", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        } else {
            Toast.makeText(GetRegistrationPhotoActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void popupWindow() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popupwindow_layout, null);
        popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
        popupWindow.showAtLocation(popupView, Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        popupWindow.setOutsideTouchable(true);

        popupWindow.setIgnoreCheekPress();

        ImageView btnCancel = (ImageView) popupView.findViewById(R.id.btnCancel);
        TextView textView_popup = (TextView) popupView.findViewById(R.id.textView_popup);
        if (rv_list == null) {
            textView_popup.setVisibility(View.VISIBLE);
        }
        recyclerView_popup = (RecyclerView) popupView.findViewById(R.id.recyclerView_popup);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView_popup.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        ComplainPopupAdapter customAdapter = new ComplainPopupAdapter(GetRegistrationPhotoActivity.this, rv_list);
        recyclerView_popup.setAdapter(customAdapter);
        recyclerView_popup.addOnItemTouchListener(new RecyclerItemClickListener(GetRegistrationPhotoActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                editText_productprice.setVisibility(View.VISIBLE);
                editText_productname.setVisibility(View.VISIBLE);
                editText_productprice.setText(rv_list.get(position).getPro_price());
                editText_productname.setText(rv_list.get(position).getPro_name());
                p_name = rv_list.get(position).getPro_name();
                p_price = rv_list.get(position).getPro_price();
                Log.e("price", "" + rv_list.get(position).getPro_price());
                Constants.p_id = rv_list.get(position).getP_id();
                popupWindow.dismiss();


            }
        }));

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 3:
                return new TimePickerDialog(this, timePickerListener, hour, minute,
                        false);
            case 0:
                return new DatePickerDialog(this, datePickerListener, /*year, month, day*/year, month, day);
            case 1:
                return new DatePickerDialog(this, datePickerListener, /*year, month, day*/year, month, day);
            case 4:
                return new TimePickerDialog(this, timePickerListener, hour, minute,
                        false);

        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            Log.e("selectedYear", "" + selectedYear);
            Log.e("selectedMonth", "" + selectedMonth);
            Log.e("selectedDay", "" + selectedDay);
            int month = selectedMonth + 1;


            if (Constants.date.equals("1")) {
                String date = selectedYear + "-" + month + "-" + selectedDay;

                date_first = selectedDay + "-" + month + "-" + selectedYear;

                start_date = date;
                btn_start_date.setText(date);
            }
            if (Constants.date.equals("2")) {
                String date = selectedYear + "-" + month + "-" + selectedDay;
                date_second = selectedDay + "-" + month + "-" + selectedYear;
                end_date = date;
                btn_end_date.setText(date);
            }


        }
    };


    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
            // TODO Auto-generated method stub
            hour = hourOfDay;
            minute = minutes;

            convertTo12HourFormat(hour, minute);


        }

    };


    // To convert in AM PM format
    private void convertTo12HourFormat(int hours, int mins) {

        /*String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";


        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);*/


        String finalTime = new StringBuilder().append(hours).append(':')
                .append(mins).append(" ")/*.append(timeSet)*/.toString();
        if (Constants.date.equals("3")) {
            start_time = finalTime;
            btn_start_time.setText(finalTime);
        }
        if (Constants.date.equals("4")) {
            end_time = finalTime;
            btn_end_time.setText(finalTime);
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
        switch (v.getId()) {
            case R.id.imageview_photo1:
                Intent intent1 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent1, 1);
                break;
            case R.id.btn_start_date:
                Constants.date = "1";
                showDialog(0);
                break;
            case R.id.btn_end_date:
                Constants.date = "2";
                showDialog(0);
                break;
            case R.id.btn_start_time:
                Constants.date = "3";
                showDialog(3);
                break;
            case R.id.btn_end_time:
                Constants.date = "4";
                showDialog(4);
                break;
            case R.id.tv_select_productname:
                popupWindow();
                break;


            case R.id.btn_live_offer:
                if (p_name == null && p_price == null) {
                    tv_select_productname.setError("Select Your Product");
                    Toast.makeText(getApplicationContext(), "Select Product", Toast.LENGTH_LONG).show();
                } else {
                    if (editText_Offer_price.getText().toString().matches("^\\d{0,8}(\\.\\d{1,4})?$") && !(editText_Offer_price.getText().toString().matches(""))) {
                        if (start_date == null) {
                            Toast.makeText(getApplicationContext(), "Select offer start date", Toast.LENGTH_LONG).show();
                        } else if (start_time == null) {

                            Toast.makeText(getApplicationContext(), "Select offer start time", Toast.LENGTH_LONG).show();
                        } else if (end_time == null) {

                            Toast.makeText(getApplicationContext(), "Select offer end time", Toast.LENGTH_LONG).show();
                        } else if (end_time == null) {

                            Toast.makeText(getApplicationContext(), "Select offer end time", Toast.LENGTH_LONG).show();
                        } else {

                            if (CheckDates(date_first, date_second)) {
                                if (cat_id.equals("2")) {
                                    SubmitLiveOfferFashionista();
                                } else if (cat_id.equals("8")) {
                                    SubmitLiveOfferRealEstatet();
                                } else {
                                    SubmitLiveOffer();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Select Proper date", Toast.LENGTH_LONG).show();
                            }


                        }


                    } else {
                        Toast.makeText(getApplicationContext(), "Enter valid Offer Price", Toast.LENGTH_LONG).show();
                    }
                }


                break;


        }

    }


    public static boolean CheckDates(String startDate, String endDate) {
        boolean b = false;
        try {

            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");


            String str1 = startDate;
            Date date1 = formatter.parse(str1);

            String str2 = endDate;
            Date date2 = formatter.parse(str2);
            Log.e("date", "" + date1.compareTo(date2));

            if (date1.compareTo(date2) < 0) {
                System.out.println("date2 is Greater than my date1");
                b = true;
            }

        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        return b;
       /* SimpleDateFormat dfDate = new SimpleDateFormat("dd-MM-yyyy");

        boolean b = false;

        try {
            if (dfDate.parse(startDate).before(dfDate.parse(endDate))) {
                b = true;  // If start date is before end date.
            } else if (dfDate.parse(startDate).equals(dfDate.parse(endDate))) {
                b = true;  // If two dates are equal.
            } else {
                b = false; // If start date is after the end date.
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return b;*/
    }

    private void SubmitLiveOfferFashionista() {
        if (isNetworkAvailable()) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();

            params.add("sub_id", sub_id);
            params.add("l_id", l_id);
            params.add("child_id", child_id);
            params.add("p_id", Constants.p_id);
            params.add("off_price", editText_Offer_price.getText().toString());
            params.add("description", editText_description.getText().toString());
            params.add("start_date", start_date);//start_date
            params.add("start_time", start_time);
            params.add("end_date", end_date);
            params.add("end_time", end_time);
            params.add("cat_id", cat_id);

            try {
                params.put("img", file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            client.post(Constants.liveuri + "add_live_offer_fashion.php", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {
                        rv_list.clear();
                        String str = null;
                        String test = null;
                        JSONArray resultsarray = null;
                        Log.e("responseString:", "" + responseString);


                        try {
                            JSONObject jsonObj = new JSONObject(responseString);
                            str = jsonObj.getString("success");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (str.equals("true")) {
                            //Constants.user_mob = et_Register_Mobile.getText().toString();
                            Toast.makeText(GetRegistrationPhotoActivity.this, "Offer Uoload successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(GetRegistrationPhotoActivity.this, HomemenuDisplayActivity.class));
                            dialog.dismiss();
                            finish();
                        } else if (str.equals("false")) {
                            dialog.dismiss();
                            Toast.makeText(GetRegistrationPhotoActivity.this, "Please Try Again!!", Toast.LENGTH_SHORT).show();
                        } else {
                            dialog.dismiss();
                        }

                    } else {
                        Toast.makeText(GetRegistrationPhotoActivity.this, "data is not available!", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        } else {
            Toast.makeText(GetRegistrationPhotoActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void SubmitLiveOfferRealEstatet() {
        if (isNetworkAvailable()) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();

            params.add("sub_id", sub_id);
            params.add("l_id", l_id);
            params.add("child_id", child_id);
            params.add("p_id", Constants.p_id);
            params.add("off_price", editText_Offer_price.getText().toString());
            params.add("description", editText_description.getText().toString());
            params.add("start_date", start_date);//start_date
            params.add("start_time", start_time);
            params.add("end_date", end_date);
            params.add("end_time", end_time);
            params.add("cat_id", cat_id);

            try {
                params.put("img", file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            client.post(Constants.liveuri + "add_live_offer_list_child.php", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {
                        rv_list.clear();
                        String str = null;
                        String test = null;
                        JSONArray resultsarray = null;
                        Log.e("responseString:", "" + responseString);


                        try {
                            JSONObject jsonObj = new JSONObject(responseString);
                            str = jsonObj.getString("success");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (str.equals("true")) {
                            //Constants.user_mob = et_Register_Mobile.getText().toString();
                            Toast.makeText(GetRegistrationPhotoActivity.this, "Offer Uoload successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(GetRegistrationPhotoActivity.this, HomemenuDisplayActivity.class));
                            dialog.dismiss();
                            finish();
                        } else if (str.equals("false")) {
                            dialog.dismiss();
                            Toast.makeText(GetRegistrationPhotoActivity.this, "Please Try Again!!", Toast.LENGTH_SHORT).show();
                        } else {
                            dialog.dismiss();
                        }

                    } else {
                        Toast.makeText(GetRegistrationPhotoActivity.this, "data is not available!", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        } else {
            Toast.makeText(GetRegistrationPhotoActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }


    public void FiveProductList() {
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
                        rv_list.clear();

                        try {
                            Log.e("Product_res", responseString);

                            JSONObject jsonObj = new JSONObject(responseString);
                            JSONArray resultsarray = jsonObj.getJSONArray("response");
                            for (int i = 0; i < resultsarray.length(); i++) {
                                LiveOfferModel temp = new LiveOfferModel();
                                temp.setPro_name(resultsarray.getJSONObject(i).getString("pro_name"));
                                temp.setPro_price(resultsarray.getJSONObject(i).getString("pro_price"));
                                temp.setL_id(resultsarray.getJSONObject(i).getString("l_id"));
                                temp.setP_id(resultsarray.getJSONObject(i).getString("p_id"));
                                temp.setProduct_image(Constants.liveimageuri + resultsarray.getJSONObject(i).getString("pro_img"));

                                rv_list.add(temp);
                                dialog.dismiss();
                            }
                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(GetRegistrationPhotoActivity.this, "data is not available!", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        } else {
            Toast.makeText(GetRegistrationPhotoActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void SubmitLiveOffer() {
        if (isNetworkAvailable()) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();

            params.add("sub_id", sub_id);
            params.add("l_id", l_id);
            params.add("p_id", Constants.p_id);
            params.add("off_price", editText_Offer_price.getText().toString());
            params.add("description", editText_description.getText().toString());
            params.add("start_date", start_date);//start_date
            params.add("start_time", start_time);
            params.add("end_date", end_date);
            params.add("end_time", end_time);
            params.add("cat_id", cat_id);

            try {
                params.put("img", file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            client.post(Constants.liveuri + "add_live_offer_list.php", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {
                        rv_list.clear();
                        String str = null;
                        String test = null;
                        JSONArray resultsarray = null;
                        Log.e("responseString:", "" + responseString);


                        try {
                            JSONObject jsonObj = new JSONObject(responseString);
                            str = jsonObj.getString("success");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (str.equals("true")) {
                            //Constants.user_mob = et_Register_Mobile.getText().toString();
                            Toast.makeText(GetRegistrationPhotoActivity.this, "Offer Uoload successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(GetRegistrationPhotoActivity.this, HomemenuDisplayActivity.class));
                            dialog.dismiss();
                            finish();
                        } else if (str.equals("false")) {
                            dialog.dismiss();
                            Toast.makeText(GetRegistrationPhotoActivity.this, "Please Try Again!!", Toast.LENGTH_SHORT).show();
                        } else {
                            dialog.dismiss();
                        }

                    } else {
                        Toast.makeText(GetRegistrationPhotoActivity.this, "data is not available!", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        } else {
            Toast.makeText(GetRegistrationPhotoActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public static boolean isValidCompany(String fname) {
        Pattern FNAME_PATTERN
                = Pattern.compile(
                "[\\sa-zA-Z0-9\\\\\\.n°/]*"
        );
        return (!TextUtils.isEmpty(fname) && FNAME_PATTERN.matcher(fname).matches());
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
