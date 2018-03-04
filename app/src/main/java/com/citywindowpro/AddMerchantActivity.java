package com.citywindowpro;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.citywindowpro.Utils.Constants;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

public class AddMerchantActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editText_company_name, editText_description,  editText_contact1, editText_contact2, editText_email;
    String str_company_name, str_persion_name, str_address, str_contact1, str_contact2, str_email, str_pass1, str_pass2;
    ImageView imageview_photo1, imageview_photo2, imageview_photo3;
    TextView textView;
    EditText editText;
    String s;
    File file;
    Bitmap photo = null;
    String citywindow;
    String RootDir;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_merchant);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button button_submit = (Button) findViewById(R.id.button_submit);
        editText_company_name = (EditText) findViewById(R.id.editText_company_name);
        editText_description = (EditText) findViewById(R.id.editText_description);
        editText_contact1 = (EditText) findViewById(R.id.editText_contact_no1);
        editText_contact2 = (EditText) findViewById(R.id.editText_contact_no2);
        editText_email = (EditText) findViewById(R.id.editText_email);



        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        button_submit.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_submit:
                str_company_name = editText_company_name.getText().toString();
                str_contact1 = editText_contact1.getText().toString();
                str_email = editText_email.getText().toString();


                if ( isValidCompany(editText_company_name.getText().toString())) {



                                if ((str_contact1.matches("^[7-9][0-9]{9}$"))) {
                                    registerData();
                /*                    if(isValidEmail(editText_email.getText().toString())){

                                        registerData();


                                    }else{
                                        Toast.makeText(getApplicationContext(), "Enter valid Email!", Toast.LENGTH_LONG).show();
                                    }
*/

                                } else {
                                    Toast.makeText(getApplicationContext(), "Enter valid mobile number!", Toast.LENGTH_LONG).show();
                                }






                }else {
                    Toast.makeText(getApplicationContext(), "Enter valid Company Name!", Toast.LENGTH_LONG).show();
                }


                // startActivity(new Intent(getApplicationContext(), GetRegistrationPhotoActivity.class));
                break;


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

    public static boolean isValidName(String fname) {
        Pattern FNAME_PATTERN
                = Pattern.compile(
                "[a-zA-Z$]{2,24}"
        );
        return (!TextUtils.isEmpty(fname) && FNAME_PATTERN.matcher(fname).matches());
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

    public static boolean isValidPassword(String s) {
        Pattern PASSWORD_PATTERN
                = Pattern.compile(
                "[a-zA-Z0-9$]{4,24}"
        );
        return !TextUtils.isEmpty(s) && PASSWORD_PATTERN.matcher(s).matches();
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    Uri uri1 = data.getData();
                    imageview_photo1.setImageURI(uri1);

                    try {
                        photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                        saveImage(photo);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;
                case 2:
                    Uri uri2 = data.getData();
                    imageview_photo2.setImageURI(uri2);
                    try {
                        photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                        saveImage(photo);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;
                case 3:
                    Uri uri3 = data.getData();
                    imageview_photo3.setImageURI(uri3);
                    try {
                        photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                        saveImage(photo);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;

            }
        }

    }*/

    private void registerData() {
        if (isNetworkAvailable()) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
            params.put("i_name", editText_company_name.getText().toString());
            params.put("i_contact1", editText_contact1.getText().toString());
            params.put("i_contact2", editText_contact2.getText().toString());
            params.put("i_email", editText_email.getText().toString());
            params.put("i_description", editText_description.getText().toString());
           /* params.put("d_password", editText_pass2.getText().toString());

            params.put("child_id", "1");
            params.put("ctc_id", "1");
            params.put("d_latitude", "120.0");
            params.put("d_longitude", "120.");*/

/*            try {
                params.put("img", file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }*/

            client.post(Constants.liveuri + "add_inquiry.php", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {
                        Log.e("responseString:", "" + responseString);
                        String str = null;
                        Log.e("responseString:", "" + responseString);
                        try {
                            JSONObject jsonObj = new JSONObject(responseString);
                            str = jsonObj.getString("success");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (str.equals("true")) {
                            // Constants.user_mob = et_Register_Mobile.getText().toString();
                            Toast.makeText(AddMerchantActivity.this, "Your Inquiry Taken successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddMerchantActivity.this, HomemenuDisplayActivity.class));
                            dialog.dismiss();
                            finish();
                        } else if (str.equals("false")) {

                            Toast.makeText(AddMerchantActivity.this, "User Allready Registered", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            dialog.dismiss();
                        }

                    }
                }
            });
        } else {
            dialog.dismiss();
            Toast.makeText(AddMerchantActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }


    /*void saveImage(Bitmap img) {
        RootDir = Environment.getExternalStorageDirectory()
                + File.separator + "citywindow";
        File myDir = new File(RootDir);
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        citywindow = "Image-" + n + ".jpg";
        file = new File(myDir, citywindow);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            img.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        Toast.makeText(MainActivity.this, "Image saved to 'txt_imgs' folder", Toast.LENGTH_LONG).show();
    }*/

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
