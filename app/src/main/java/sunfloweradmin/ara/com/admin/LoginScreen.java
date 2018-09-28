package sunfloweradmin.ara.com.admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import sunfloweradmin.ara.com.admin.Utils.MySingleton;

import static sunfloweradmin.ara.com.admin.Utils.AppConstants.BRANCHIDPREF;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.LOGINAPI;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.LOGINMESSAGE;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.LOGIN_PREFERENCE;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.LOGIN_RESPONSE;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.MODE;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.PARAM_PASSWORD;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.PARAM_USERID;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.POST;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.PREFERENCE;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.RESPONSE_BRANCHID;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.RESPONSE_USERID;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.RESPONSE_USERNAME;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.USERIDPREF;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.USERNAMEPREF;

public class LoginScreen extends AppCompatActivity {

    @BindView(R.id.loginscreenConstraint)
    ConstraintLayout constraintLayout;
    @BindView(R.id.loginusername)
    EditText username;
    @BindView(R.id.loginpassword)
    EditText password;
    @BindView(R.id.loginbutton)
    Button loginbutton;
    String loginResponse, resUserid, resUsername, resBranchid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        ButterKnife.bind(this);

        setDrawableForLogin();
        if (!isNetworkAvailable()) {
            showSnackbar("Please Check your Internet Connection");
        }
        SharedPreferences sharedPreferences = getSharedPreferences(LOGIN_PREFERENCE, MODE);
        String userid = sharedPreferences.getString(USERIDPREF, "");
        if (userid != "") {
            startActivity(new Intent(LoginScreen.this, MainActivity.class));
            finish();
        }

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initializeLogin();

            }
        });
    }

    private void initializeLogin() {

        if (!isNetworkAvailable()) {
            showSnackbar("PLease check your mobile network connection");
        }
        if (!validate()) {
            return;
        }
        final ProgressDialog progressDialog = new ProgressDialog(LoginScreen.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(POST, LOGINAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("KUMAR LOG : ", "login Response : " + response);

                progressDialog.dismiss();

                try {
                    progressDialog.dismiss();/*
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {*/
//                        JSONObject jsonObject = jsonArray.getJSONObject(i)
                    JSONObject jsonObject = new JSONObject(response);
                    loginResponse = jsonObject.getString(LOGIN_RESPONSE);
                    resUserid = jsonObject.getString(RESPONSE_USERID);
                    resUsername = jsonObject.getString(RESPONSE_USERNAME);
                    resBranchid = jsonObject.getString(RESPONSE_BRANCHID);

//                    }
                    if (loginResponse.contains(LOGINMESSAGE)) {


                        SharedPreferences sharedPreferences = getSharedPreferences(LOGIN_PREFERENCE, MODE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(USERIDPREF, resUserid);
                        editor.putString(USERNAMEPREF, resUsername);
                        editor.putString(BRANCHIDPREF, resBranchid);
                        editor.commit();
                        Toast.makeText(LoginScreen.this, "Login Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginScreen.this, MainActivity.class));
                        finish();
                    } else {
                        progressDialog.dismiss();
                        showSnackbar("Please check your username password ");
                    }
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    progressDialog.dismiss();
                    showSnackbar("PLease check Your Your username    and Password ");
                    password.requestFocus();
                    Log.e("kumar LOG : ", "JSOn exception : " + e);


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("KUMAR LOG : ", "error Response : " + error);
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map map = new HashMap();
                map.put(PARAM_USERID, username.getText().toString());
                map.put(PARAM_PASSWORD, password.getText().toString());
                return map;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

        /*startActivity(new Intent(LoginScreen.this, MainActivity.class));
        finish();*/
    }

    public boolean validate() {
        boolean valid = true;

        String User = username.getText().toString();
        String Pass = password.getText().toString();

        if (User.isEmpty()) {
            username.setError("Enter the Valid Name");
            valid = false;
        } else {
            username.setError(null);
        }

        if (Pass.isEmpty()) {
            password.setError("Between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            password.setError(null);
        }

        return valid;
    }

    private void setDrawableForLogin() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        constraintLayout.setFocusableInTouchMode(true);
        constraintLayout.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);

        final Drawable drawableUser = getResources().getDrawable(R.drawable.bitmapuser);
        final Drawable drawablePass = getResources().getDrawable(R.drawable.bitmaplock);
        drawableUser.setBounds(0, 0, 45, 40);
        drawablePass.setBounds(0, 0, 45, 40);
        username.setCompoundDrawables(drawableUser, null, null, null);
        password.setCompoundDrawables(drawablePass, null, null, null);

    }

    private boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }

    private void showSnackbar(String message) {
        final Snackbar snackbar = Snackbar.make(constraintLayout, message,
                Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Ok", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
