package sunfloweradmin.ara.com.admin;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import sunfloweradmin.ara.com.admin.Adapter.DelieveryTrackingAdapter;
import sunfloweradmin.ara.com.admin.Adapter.SoapprovalAdapter;
import sunfloweradmin.ara.com.admin.Models.CollectionTrackingModel;
import sunfloweradmin.ara.com.admin.Models.DeliveyTrackingModel;
import sunfloweradmin.ara.com.admin.Models.SoApprovalModel;
import sunfloweradmin.ara.com.admin.Utils.MySingleton;
import sunfloweradmin.ara.com.admin.listeneres.RecyclerTouchListener;

import static sunfloweradmin.ara.com.admin.Utils.AppConstants.DELIVERY_REPORT_API;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.DR_FROM_DATE;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.DR_TO_DATE;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.DR_USER_ID;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.POST;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.PREFERENCE;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.SALES_REPORT_API;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.SR_FROM_DATE;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.SR_TO_DATE;

public class DeliveryTrackingList extends AppCompatActivity {

    public static final String TAG = "Delivery List";
    @BindView(R.id.deliverytarackingLayout)
    ConstraintLayout constraintLayout;
    @BindView(R.id.fromdate_DT)
    TextView startingDate;
    @BindView(R.id.todate_DT)
    TextView closingDate;
    @BindView(R.id.swipetodelivery)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.onclickbtn_DT)
    ImageButton onclickImage;
    @BindView(R.id.recyclerDT)
    RecyclerView recyclerView_DT;
    DelieveryTrackingAdapter adapter;
    DatePickerDialog datePickerDialog;
    boolean sDateClicked = false, cDateClicked = false;
    ArrayList<DeliveyTrackingModel> deliveyTrackingModelArrayList = new ArrayList<DeliveyTrackingModel>();
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_tracking);
        ButterKnife.bind(this);
        if (!isNetworkAvailable()) {
            showSnackbar("Please Check Your Network Connection");
        }
        // -----------------------------------------------------------------------------------------
        sharedPreferences = getSharedPreferences(PREFERENCE, MODE_PRIVATE);

        String sdate = sharedPreferences.getString("sdateDt", "");
        String cdate = sharedPreferences.getString("cdateDt", "");
        if (sdate != "" && cdate != "") {
            startingDate.setText(sdate);
            closingDate.setText(cdate);
            deliveyTrackingModelArrayList.clear();
            intializeRecylcerView();
        } else {
            startingDate.setText("Starting Date");
            closingDate.setText("Closing Date");
        }
        //        -----------------------------------------------------------------------------------------
        startingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startDatePicker();
            }
        });
        closingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                closingDatePicker();
            }
        });
        onclickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deliveyTrackingModelArrayList.clear();
                intializeRecylcerView();
            }
        });
        recyclerView_DT.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView_DT, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                DeliveyTrackingModel deliveyTrackingModel = deliveyTrackingModelArrayList.get(position);
                sharedPreferences = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String dc_entry_uniq_id = deliveyTrackingModel.getDc_entry_uniq_id();
                String customer_code = deliveyTrackingModel.getCustomer_code();
                String dc_entry_date = deliveyTrackingModel.getDc_entry_date();
                String product_name = deliveyTrackingModel.getProduct_name();
                String customer_name = deliveyTrackingModel.getCustomer_name();
                String dc_entry_no = deliveyTrackingModel.getDc_entry_no();
                String dc_entry_product_details_qty = deliveyTrackingModel.getDc_entry_product_details_qty();
                String dc_entry_id = deliveyTrackingModel.getDc_entry_id();
                String product_code = deliveyTrackingModel.getProduct_code();

                editor.putString("dc_entry_uniq_id", dc_entry_uniq_id);
                editor.putString("customer_code", customer_code);
                editor.putString("dc_entry_date", dc_entry_date);
                editor.putString("product_name", product_name);
                editor.putString("customer_name", customer_name);
                editor.putString("dc_entry_no", dc_entry_no);
                editor.putString("dc_entry_product_details_qty", dc_entry_product_details_qty);
                editor.putString("dc_entry_id", dc_entry_id);
                editor.putString("product_code", product_code);
                editor.putString("sdateDt", startingDate.getText().toString());
                editor.putString("cdateDt", closingDate.getText().toString());
                editor.commit();

                startActivity(new Intent(DeliveryTrackingList.this, DeliveryTrackingView.class));
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isNetworkAvailable()) {
                    showSnackbar("Please check your network connection");
                }
                deliveyTrackingModelArrayList.clear();
                intializeRecylcerView();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    public void startDatePicker() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

        datePickerDialog = new DatePickerDialog(DeliveryTrackingList.this, R.style.DatePickerDialogTheme,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String dstart = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        startingDate.setText(dstart);
                        sDateClicked = true;
                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.show();
    }

    public void closingDatePicker() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

        datePickerDialog = new DatePickerDialog(DeliveryTrackingList.this, R.style.DatePickerDialogTheme,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String dstart = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        closingDate.setText(dstart);
                        cDateClicked = true;

                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.show();
    }

    private void intializeRecylcerView() {

        if (!formValid()) {
            return;
        }
        dateValidation();
        final ProgressDialog progressDialog = new ProgressDialog(DeliveryTrackingList.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(POST, DELIVERY_REPORT_API, new Response.Listener<String>() {
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
                        String dc_entry_uniq_id = jsonObject.getString("dc_entry_uniq_id");
                        String customer_code = jsonObject.getString("customer_code");
                        String dc_entry_date = jsonObject.getString("dc_entry_date");
                        String product_name = jsonObject.getString("product_name");
                        String customer_name = jsonObject.getString("customer_name");
                        String dc_entry_no = jsonObject.getString("dc_entry_no");
                        String dc_entry_product_details_qty = jsonObject.getString("dc_entry_product_details_qty");
                        String dc_entry_id = jsonObject.getString("dc_entry_id");
                        String product_code = jsonObject.getString("product_code");

                        DeliveyTrackingModel deliveyTrackingModel = new DeliveyTrackingModel();
                        deliveyTrackingModel.setDc_entry_uniq_id(dc_entry_uniq_id);
                        deliveyTrackingModel.setCustomer_code(customer_code);
                        deliveyTrackingModel.setDc_entry_date(dc_entry_date);
                        deliveyTrackingModel.setProduct_name(product_name);
                        deliveyTrackingModel.setCustomer_name(customer_name);
                        deliveyTrackingModel.setDc_entry_no(dc_entry_no);
                        deliveyTrackingModel.setDc_entry_product_details_qty(dc_entry_product_details_qty);
                        deliveyTrackingModel.setDc_entry_id(dc_entry_id);
                        deliveyTrackingModel.setProduct_code(product_code);
                        deliveyTrackingModelArrayList.add(deliveyTrackingModel);

                    }
                    adapter = new DelieveryTrackingAdapter(DeliveryTrackingList.this, deliveyTrackingModelArrayList);
                    recyclerView_DT.setAdapter(adapter);
                    recyclerView_DT.setHasFixedSize(true);
                    recyclerView_DT.setLayoutManager(new LinearLayoutManager(DeliveryTrackingList.this));
                } catch (JSONException e) {
                    Log.e(TAG, "Json Exception  - " + e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map map = new HashMap();
                map.put(DR_FROM_DATE, startingDate.getText().toString());
                map.put(DR_TO_DATE, closingDate.getText().toString());
                map.put(DR_USER_ID, "");
                return map;
            }

        };
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private boolean formValid() {
        boolean valid = true;

        if (startingDate.getText().toString().equalsIgnoreCase("Starting Date")) {
            showSnackbar("Starting Date not Updated");
            valid = false;
        }
        if (closingDate.getText().toString().equalsIgnoreCase("Closing Date")) {
            showSnackbar("Closing Date not Updated");
            valid = false;
        }
        if (startingDate.getText().toString().equalsIgnoreCase(closingDate.getText().toString())) {
            showSnackbar("Date should be not equal");
            deliveyTrackingModelArrayList.clear();
            valid = false;
        }

        return valid;
    }

    private void dateValidation() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yy");

        Date d1 = null;
        Date d2 = null;
        try {
            d1 = simpleDateFormat.parse(startingDate.getText().toString());
            d2 = simpleDateFormat.parse(closingDate.getText().toString());
            if (d1.after(d2)) {
                showSnackbar("Please Enter Valid  Date");

                deliveyTrackingModelArrayList.clear();
            }
        } catch (ParseException parse) {
            Log.e(TAG, "Parse Exception -" + parse);
        }
        return;
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
        sharedPreferences = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        startActivity(new Intent(DeliveryTrackingList.this, MainActivity.class));
        finish();
    }
}
