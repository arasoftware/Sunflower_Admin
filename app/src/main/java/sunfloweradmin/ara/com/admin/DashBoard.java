package sunfloweradmin.ara.com.admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import sunfloweradmin.ara.com.admin.Adapter.SoapprovalAdapter;
import sunfloweradmin.ara.com.admin.Models.DashBoardModel;
import sunfloweradmin.ara.com.admin.Models.SoApprovalModel;
import sunfloweradmin.ara.com.admin.Utils.MySingleton;

import static sunfloweradmin.ara.com.admin.Utils.AppConstants.DASH_BOARD_API;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.GET;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.POST;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.PREFERENCE;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.SALES_REPORT_API;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.SR_FROM_DATE;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.SR_TO_DATE;

public class DashBoard extends AppCompatActivity {

    public static final String TAG = "DASH BOARD";
    @BindView(R.id.dashboardLayout)
    ConstraintLayout constraintLayout;
    @BindView(R.id.soappdashboard)
    TextView soWaitingApproval;
    @BindView(R.id.noofcollectiondashboard)
    TextView noofCollection;
    @BindView(R.id.noofsomadedashboard)
    TextView noofSoMadeToday;
    @BindView(R.id.noofdeliverymadedashboard)
    TextView noofDeliveryMadeToday;
    ArrayList<DashBoardModel> dashBoardModelArrayList = new ArrayList<DashBoardModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        ButterKnife.bind(this);
        if (!isNetworkAvailable()) {
            showSnackbar("please Check Your Network Connection");
        }
        initializeView();
    }

    private void initializeView() {
        final ProgressDialog progressDialog = new ProgressDialog(DashBoard.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(GET, DASH_BOARD_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                Log.e(TAG, "Response = " + response);

                JSONArray jsonArray = null;
                JSONObject jsonObject = null;

                try {
                    jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        String dc_total = jsonObject.getString("dc_total");
                        String so_approvel = jsonObject.getString("so_approvel");
                        String collection_total = jsonObject.getString("collection_total");
                        String so_total = jsonObject.getString("so_total");
                        DashBoardModel dashBoardModel = new DashBoardModel();
                        dashBoardModel.setDc_total(dc_total);
                        dashBoardModel.setSo_approvel(so_approvel);
                        dashBoardModel.setCollection_total(collection_total);
                        dashBoardModel.setSo_total(so_total);
                        dashBoardModelArrayList.add(dashBoardModel);
                        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("dc_total", dc_total);
                        editor.putString("so_approvel", so_approvel);
                        editor.putString("collection_total", collection_total);
                        editor.putString("so_total", so_total);
                        editor.commit();
                        soWaitingApproval.setText("Sales Order Waiting For Approval : "+so_approvel);
                        noofCollection.setText("No of Collection Order Made Today : "+collection_total);
                        noofDeliveryMadeToday.setText("No of Delivery Made Today : "+dc_total);
                        noofSoMadeToday.setText("No of Sales Order Made Today : "+so_total);

                    }

                } catch (JSONException e) {
                    Log.e(TAG, "Json Exception  - " + e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

            }
        });
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
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
        startActivity(new Intent(DashBoard.this, MainActivity.class));
        finish();
    }
}
