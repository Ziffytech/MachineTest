package com.ziffytech.machinetest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.ziffytech.machinetest.network.VolleyNetworkUtil;
import com.ziffytech.machinetest.network.interfaces.RegisterUserAPICallback;
import com.ziffytech.machinetest.utility.ApiParams;
import com.ziffytech.machinetest.utility.MyUtility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, RegisterUserAPICallback {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private EditText edt_Email, edt_address;
    private EditText edt_Fullname, edt_lastname, edt_pass, editconf_pass;
    private EditText phone, txtcode;
    private Button btn_sign_up;
    private String firstname, lastname, email, myphone, code, password, c_password, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        bindViews();
        setListeners();
        getValuesFromIntents();
    }

    private void bindViews() {
        edt_Email = findViewById(R.id.txtEmail);
        edt_address = findViewById(R.id.edt_address);
        edt_Fullname = findViewById(R.id.txtFirstname);
        edt_lastname = findViewById(R.id.txtlastname);
        edt_pass = findViewById(R.id.edt_password);
        editconf_pass = findViewById(R.id.edt__pass);
        phone = findViewById(R.id.txtPhone);
        txtcode = findViewById(R.id.txtcode);
        txtcode.setOnClickListener(this);
        btn_sign_up = findViewById(R.id.btn_sign_up);

    }

    private void setListeners() {
        btn_sign_up.setOnClickListener(this);
    }

    private void getValuesFromIntents() {
    }

    public void register() {
        firstname = edt_Fullname.getText().toString().trim();
        lastname = edt_lastname.getText().toString().trim();
        email = edt_Email.getText().toString().trim();
        myphone = phone.getText().toString().trim();
        code = txtcode.getText().toString().trim();
        password = edt_pass.getText().toString().trim();
        c_password = editconf_pass.getText().toString().trim();
        address = edt_address.getText().toString().trim();
        if (isValidForm(myphone, code, email)) {
            if (getIntent().hasExtra("phone")) {
                APICALL(getIntent().getStringExtra("phone"), getIntent().getStringExtra("code"));
            } else {
                APICALL(myphone, code);
            }
        }

    }

    private boolean isValidForm(String myphone, String code, String email) {
        if (!MyUtility.isConnected(this)) {
            MyUtility.showAlertMessage(this, MyUtility.INTERNET_ERROR);
            return false;
        } else if (!MyUtility.isValidString(myphone)) {
            phone.setError("Error");
            phone.setFocusable(true);
            return false;
        } else if (!MyUtility.isValidString(code)) {
            txtcode.setError("Error");
            txtcode.setFocusable(true);
            return false;
        } else if (!MyUtility.isValidEmail(email)) {
            txtcode.setError("Error");
            txtcode.setFocusable(true);
            return false;
        }
        return true;
    }

    private void APICALL(String myphone, String code) {
        String url = ApiParams.REGISTER_URL;
        HashMap<String, String> params = new HashMap<>();
        params.put("name", firstname);
        params.put("last_name", lastname);
        params.put("password", password);
        params.put("c_password", c_password);
        params.put("phone_no", myphone);
        params.put("email", email);
        params.put("address", address);
        Log.e(TAG, "PARAMS: " + params.toString());
        String requestTag = "register";
        VolleyNetworkUtil.registerUserAPICall(this, url, params, requestTag, this);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_sign_up:
                register();
                break;
        }
    }


    @Override
    public void registerUserAPISuccessCallback(String response) {
        Log.e("Register response", response);
        try {
            JSONObject userdata = new JSONObject(response);
            if (userdata.getInt("responce") == 1) {
                JSONObject data = userdata.getJSONObject("data");
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            } else {
                MyUtility.showAlertMessage(RegisterActivity.this, "Provided email or mobile number is already Registered with us,If you forgotten your password then reset your password.");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registerUserAPIFailureCallback(VolleyError error) {
        MyUtility.showAlertMessage(RegisterActivity.this, error.toString());
    }

}
