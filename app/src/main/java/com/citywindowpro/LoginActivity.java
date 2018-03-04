package com.citywindowpro;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.citywindowpro.Model.FiveMainsubcatgory;
import com.citywindowpro.Utils.Constants;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button button_login;
    EditText editText_email, editText_pass;
    TextView textView_forgot_pass;
    ProgressDialog dialog;
    boolean isloggeddin;
    public static final String MY_PREFS_NAME = "Raju";
    String username,password;
    String l_id, cat_id, sub_id, child_id, ctc_id, l_name, l_img, d_contact, d_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textView_forgot_pass = (TextView) findViewById(R.id.textView_forgot_pass);
        button_login = (Button) findViewById(R.id.button_login);
        editText_email = (EditText) findViewById(R.id.editText_email);
        editText_pass = (EditText) findViewById(R.id.editText_pass);
        textView_forgot_pass.setOnClickListener(this);
        button_login.setOnClickListener(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading..");
        dialog.setCancelable(true);

        isloggeddin = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isLoggedIn", false);
        if (isloggeddin) {
            Toast.makeText(LoginActivity.this, "You have already login!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this, HomemenuDisplayActivity.class));
            finish();
        }  /*{
            dialog = new ProgressDialog(this);
            dialog.setMessage("Loading..");
            dialog.setCancelable(false);

            if (cat_id.equals("2")) {
                FashionistaProductList();
            } else if (cat_id.equals("8")) {
                RealEstateProductList();
            } else {
                FiveProductList();
            }
        }*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login:


                if (isValidEmail(editText_email.getText().toString()) == true && isValidPassword(editText_pass.getText().toString()) == true) {
                    Login();
                } else {
                    Toast.makeText(LoginActivity.this, "Enter correct email or pass!", Toast.LENGTH_SHORT).show();
                }

                //startActivity(new Intent(getApplicationContext(), HomemenuDisplayActivity.class));
                break;
            case R.id.textView_forgot_pass:
                Showcomplaindialogue();
                break;
        }
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static boolean isValidPassword(String s) {
        Pattern PASSWORD_PATTERN
                = Pattern.compile(
                "[a-zA-Z0-9$]{4,24}"
        );
        return !TextUtils.isEmpty(s) && PASSWORD_PATTERN.matcher(s).matches();
    }

    private void Login() {

        if (isNetworkAvailable()) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
            // Complainmodel temp1 = new Complainmodel();
            //  int user = temp1.getUser();
            //Log.e("user1", "" + user);

            params.put("username", editText_email.getText().toString());
            params.put("password", editText_pass.getText().toString());

            client.post(Constants.liveuri + "login_list.php", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {
                        String str = null;
                        String test = null;

                        JSONArray resultsarray = null;
                        Log.e("responseString:", "" + responseString);


                        try {
                            JSONObject jsonObj = new JSONObject(responseString);
                            str = jsonObj.getString("success");

                            JSONObject obj = new JSONObject(responseString);
                            resultsarray = jsonObj.getJSONArray("details");

                            Log.e("details", "" + jsonObj.getString("details"));
                            Log.e("resultsarray", "" + resultsarray);


                            l_id = resultsarray.getJSONObject(0).getString("l_id");
                            Log.e("log_cat_id", "" + resultsarray.getJSONObject(0).getString("cat_id"));
                            cat_id = resultsarray.getJSONObject(0).getString("cat_id");
                            sub_id = resultsarray.getJSONObject(0).getString("sub_id");

                            if (resultsarray.getJSONObject(0).getString("cat_id").equals("8")) {
                                child_id = resultsarray.getJSONObject(0).getString("child_id");
                            } else if (resultsarray.getJSONObject(0).getString("cat_id").equals("2")) {
                                child_id = resultsarray.getJSONObject(0).getString("child_id");
                                ctc_id = resultsarray.getJSONObject(0).getString("ctc_id");
                            }


                            l_name = resultsarray.getJSONObject(0).getString("l_name");
                            l_img = resultsarray.getJSONObject(0).getString("l_img");
                            d_contact = resultsarray.getJSONObject(0).getString("d_contact");
                            d_email = resultsarray.getJSONObject(0).getString("d_email");

                            dialog.dismiss();
/*                            for (int i = 0; i < resultsarray.length(); i++) {
                                FiveMainsubcatgory temp = new FiveMainsubcatgory();
                                temp.setSubid(resultsarray.getJSONObject(i).getString("child_id"));
                                temp.setSubname(resultsarray.getJSONObject(i).getString("child_name"));
                                temp.setImg(Constants.liveimageuri + resultsarray.getJSONObject(i).getString("c_img"));
                                //listuser.add(temp);
                                dialog.dismiss();
                            }*/


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                                .edit()
                                .putString("cat_id", cat_id)
                                .putString("l_id", l_id)
                                .putString("sub_id", sub_id)
                                .putString("child_id", child_id)
                                .putString("ctc_id", ctc_id)
                                .putString("l_name", l_name)
                                .putString("l_img", l_img)
                                .putString("d_contact", d_contact)
                                .putString("d_email", d_email)
                                .apply();
                        getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                                .edit()
                                .putBoolean("isLoggedIn", true)
                                .apply();

                        if (str.equals("true")) {
                            //Constants.user_mob = et_Register_Mobile.getText().toString();
                            Toast.makeText(LoginActivity.this, "Login successfully!", Toast.LENGTH_SHORT).show();

                            if (Constants.login.equals("1")) {

                                startActivity(new Intent(LoginActivity.this, HomemenuDisplayActivity.class));
                            } else if (Constants.login.equals("2")) {
                                startActivity(new Intent(LoginActivity.this, GetRegistrationPhotoActivity.class));
                            } else {
                                startActivity(new Intent(LoginActivity.this, MerchantAcountActivity.class));
                            }


                            dialog.dismiss();
                            finish();
                        } else if (str.equals("false")) {
                            dialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Enter correct email or pass!", Toast.LENGTH_SHORT).show();
                        } else {
                            dialog.dismiss();
                        }


                    }


                }
            });
        } else {
            dialog.dismiss();
            Toast.makeText(LoginActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void ForgotPassword(String username,String password) {

        if (isNetworkAvailable()) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
            // Complainmodel temp1 = new Complainmodel();
            //  int user = temp1.getUser();
            //Log.e("user1", "" + user);

            params.put("username", username);
            params.put("password",password);

            client.post(Constants.liveuri + "forgat_pass.php", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {
                        String str = null;
                        Log.e("responseString:", "" + responseString);
                        try {
                            JSONObject jsonObj = new JSONObject(responseString);
                            str = jsonObj.getString("success");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (str.equals("true")) {
                            Toast.makeText(LoginActivity.this, "Forgot password successfully!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                          finish();
                        } else if (str.equals("false")) {
                            dialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Enter correct email / username", Toast.LENGTH_SHORT).show();
                        } else {
                            dialog.dismiss();
                        }


                    }

                }
            });
        } else {
            dialog.dismiss();
            Toast.makeText(LoginActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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

    public void Showcomplaindialogue() {
        LayoutInflater li = LayoutInflater.from(LoginActivity.this);
        View vi = li.inflate(R.layout.custom_dialog_mobile_verify, null);


        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
        alertDialogBuilder.setView(vi);
        alertDialogBuilder.setCancelable(false);
        final EditText tv_message_email_id = (EditText) vi.findViewById(R.id.tv_message_email_id);
        final EditText tv_dialog_password = (EditText) vi.findViewById(R.id.tv_dialog_password);
        final EditText tv_dialog_conform_pass = (EditText) vi.findViewById(R.id.tv_dialog_conform_pass);
        Button btn_ok = (Button) vi.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidEmail(tv_message_email_id.getText().toString()) == true) {
                    ForgotPassword(tv_message_email_id.getText().toString(),tv_dialog_conform_pass.getText().toString());
                } else {
                    Toast.makeText(LoginActivity.this, "Enter correct email / username!", Toast.LENGTH_SHORT).show();
                }

            }
        });


        Button btn_close = (Button) vi.findViewById(R.id.btn_close);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    /*private void ForgotPassword(String email) {

        if (isNetworkAvailable()) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
            params.put("username", email);
            client.post(Constants.liveuri + "forgat_pass.php", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {
                        String str = null;
                        Log.e("responseString:", "" + responseString);
                        try {
                            JSONObject jsonObj = new JSONObject(responseString);
                            str = jsonObj.getString("success");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (str.equals("true")) {
                            Toast.makeText(LoginActivity.this, "Forgot password successfully!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            finish();
                        } else if (str.equals("false")) {
                            dialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Enter correct email / username", Toast.LENGTH_SHORT).show();
                        } else {
                            dialog.dismiss();
                        }


                    }

                }
            });
        } else {
            dialog.dismiss();
            Toast.makeText(LoginActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }*/
}
