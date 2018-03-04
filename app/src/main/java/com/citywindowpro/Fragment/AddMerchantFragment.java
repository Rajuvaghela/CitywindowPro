package com.citywindowpro.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.citywindowpro.GetRegistrationPhotoActivity;
import com.citywindowpro.R;

import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddMerchantFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddMerchantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddMerchantFragment extends Fragment implements
       View.OnClickListener {
    String[] items = {"--Select category--", "YUMMY FOODS", "FASHIONISTA", "MALL SUPER MARKET", "HEALTH PERSONAL CARE", "EDUCATION", "HOME APPLIANCE"};
    String[] sub_items = {"--Select sub category--", "YUMMY FOODS", "FASHIONISTA", "MALL SUPER MARKET", "HEALTH PERSONAL CARE", "EDUCATION", "HOME APPLIANCE"};
    EditText editText_company_name, editText_person_name, editText_address, editText_contact1, editText_contact2, editText_email, editText_pass1, editText_pass2;
    String str_company_name, str_persion_name, str_address, str_contact1, str_contact2, str_email, str_pass1, str_pass2;
 //   ImageView imageview_photo1, imageview_photo2, imageview_photo3;
    TextView textView;
    EditText editText;
    String s;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AddMerchantFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddMerchantFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddMerchantFragment newInstance(String param1, String param2) {
        AddMerchantFragment fragment = new AddMerchantFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_merchant, container, false);
/*        Spinner spinner_category = (Spinner) rootView.findViewById(R.id.spinner_category);
        spinner_category.setOnItemSelectedListener(this);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_category.setAdapter(dataAdapter);

        Spinner spinner_sub_category = (Spinner) rootView.findViewById(R.id.spinner_sub_category);
        spinner_sub_category.setOnItemSelectedListener(this);
        ArrayAdapter<String> arrayAdapter_sub_category = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, sub_items);
        arrayAdapter_sub_category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_sub_category.setAdapter(arrayAdapter_sub_category);*/


        Button button_submit = (Button) rootView.findViewById(R.id.button_submit);
        editText_company_name = (EditText) rootView.findViewById(R.id.editText_company_name);
        editText_person_name = (EditText) rootView.findViewById(R.id.et_person_name);
        editText_address = (EditText) rootView.findViewById(R.id.et_address);
        editText_contact1 = (EditText) rootView.findViewById(R.id.editText_contact_no1);
        editText_contact2 = (EditText) rootView.findViewById(R.id.editText_contact_no2);
        editText_email = (EditText) rootView.findViewById(R.id.editText_email);
        editText_pass1 = (EditText) rootView.findViewById(R.id.editText_pass1);
        editText_pass2 = (EditText) rootView.findViewById(R.id.editText_pass2);

/*
        imageview_photo1 = (ImageView)rootView.findViewById(R.id.imageview_photo1);
        imageview_photo2 = (ImageView)rootView. findViewById(R.id.imageview_photo2);
        imageview_photo3 = (ImageView) rootView.findViewById(R.id.imageview_photo3);
        imageview_photo1.setOnClickListener(this);
        imageview_photo2.setOnClickListener(this);
        imageview_photo3.setOnClickListener(this);*/

        button_submit.setOnClickListener(this);
        return rootView;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

/*    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
      //   Toast.makeText(getContext(), items[position], Toast.LENGTH_LONG).show();
     //   Toast.makeText(getContext(), sub_items[position], Toast.LENGTH_LONG).show();
    }*/

/*    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_submit:
                str_company_name = editText_company_name.getText().toString();
                str_address = editText_address.getText().toString();
                str_persion_name = editText_person_name.getText().toString();
                Log.e("str_persion_name", "" + str_persion_name);
                str_contact1 = editText_contact1.getText().toString();
                str_contact2 = editText_contact2.getText().toString();
                str_email = editText_email.getText().toString();
                str_pass1 = editText_pass1.getText().toString();
                str_pass2 = editText_pass2.getText().toString();


                Log.e("str_address", "" + str_address);
                Log.e("str_contact1", "" + str_contact1);
                Log.e("str_contact2", "" + str_contact2);
                Log.e("str_email", "" + str_email);
                Log.e("email", "" + str_email);
                /*if ( isValidCompany(editText_company_name.getText().toString())) {

                    if ( isValidName(editText_person_name.getText().toString())){
                            if(! str_address.isEmpty()){
                                if ((str_contact1.matches("^[7-9][0-9]{9}$"))) {
                                    if(isValidEmail(editText_email.getText().toString())){

                                        if (str_pass1.contentEquals(str_pass2))
                                        {
                                            if(isValidPassword(str_pass1)){
                                            //    startActivity(new Intent(getContext().getApplicationContext(), GetRegistrationPhotoActivity.class));
                                            }else {
                                                Toast.makeText(getContext().getApplicationContext(), "Enter valid Password!", Toast.LENGTH_LONG).show();

                                            }

                                        }else {
                                            Toast.makeText(getContext().getApplicationContext(), "Password does not match!", Toast.LENGTH_LONG).show();
                                        }


                                    }else{
                                        Toast.makeText(getContext().getApplicationContext(), "Enter Email!", Toast.LENGTH_LONG).show();
                                    }


                                } else {
                                    Toast.makeText(getContext().getApplicationContext(), "Enter mobile number!", Toast.LENGTH_LONG).show();
                                }
                            }else {
                                Toast.makeText(getContext().getApplicationContext(), "Enter Address!", Toast.LENGTH_LONG).show();
                            }


                    }else {
                        Toast.makeText(getContext().getApplicationContext(), "Enter Person Name!", Toast.LENGTH_LONG).show();
                    }


                }else {
                    Toast.makeText(getContext().getApplicationContext(), "Enter Company Name!", Toast.LENGTH_LONG).show();
                }*/








            startActivity(new Intent(getContext().getApplicationContext(), GetRegistrationPhotoActivity.class));
            break;

/*            case R.id.imageview_photo1:
                Intent intent1 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent1, 1);
                break;

            case R.id.imageview_photo2:
                Intent intent2 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent2, 2);
                break;
            case R.id.imageview_photo3:
                Intent intent3 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent3, 3);
                break;*/

        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
                "[a-zA-Z0-9$]{2,24}"
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
}
