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
import sunfloweradmin.ara.com.admin.Adapter.CollectionTrackingAdapter;
import sunfloweradmin.ara.com.admin.Adapter.SoapprovalAdapter;
import sunfloweradmin.ara.com.admin.Models.CollectionTrackingModel;
import sunfloweradmin.ara.com.admin.Models.SoApprovalModel;
import sunfloweradmin.ara.com.admin.Utils.MySingleton;
import sunfloweradmin.ara.com.admin.listeneres.RecyclerTouchListener;

import static sunfloweradmin.ara.com.admin.Utils.AppConstants.COLLECTION_TRACKING_API;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.CT_FROM_DATE;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.CT_TO_DATE;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.CT_USER_ID;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.POST;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.PREFERENCE;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.SALES_REPORT_API;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.SR_FROM_DATE;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.SR_TO_DATE;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.SR_USER_ID;

public class SoApprovalList extends AppCompatActivity {

    public static final String TAG = "So APProval List";
    @BindView(R.id.soapprovalLayout)
    ConstraintLayout constraintLayout;
    @BindView(R.id.fromdate_so)
    TextView startingDate;
    @BindView(R.id.todate_so)
    TextView closingDate;
    @BindView(R.id.onclickbtn_so)
    ImageButton onclickImage;
    @BindView(R.id.swipetoSo)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerSo)
    RecyclerView recyclerView_so;
    SoapprovalAdapter adapter;
    DatePickerDialog datePickerDialog;
    boolean sDateClicked = false, cDateClicked = false;
    ArrayList<SoApprovalModel> soApprovalModelArrayList = new ArrayList<SoApprovalModel>();
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_so_approval);
        ButterKnife.bind(this);
        if (!isNetworkAvailable()) {
            showSnackbar("please check your Network Connection");
        }

       // -----------------------------------------------------------------------------------------
        sharedPreferences = getSharedPreferences(PREFERENCE, MODE_PRIVATE);

        String sdate = sharedPreferences.getString("sdateSo", "");
        String cdate = sharedPreferences.getString("cdateSo", "");
        if (sdate != "" && cdate != "") {
            startingDate.setText(sdate);
            closingDate.setText(cdate);
            soApprovalModelArrayList.clear();
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

                soApprovalModelArrayList.clear();
                intializeRecylcerView();
            }
        });
        recyclerView_so.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView_so, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                SoApprovalModel soApprovalModel = soApprovalModelArrayList.get(position);
                sharedPreferences = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String so_entry_no = soApprovalModel.getSo_entry_no();
                String so_entry_date = soApprovalModel.getSo_entry_date();
                String customer_name_so = soApprovalModel.getCustomer_name_so();
                String customer_code_so = soApprovalModel.getCustomer_code_so();
                String product_code_so = soApprovalModel.getProduct_code_so();
                String product_name_so = soApprovalModel.getProduct_name_so();
                String so_entry_product_details_rate = soApprovalModel.getSo_entry_product_details_rate();
                String so_entry_product_details_amount = soApprovalModel.getSo_entry_product_details_amount();
                String so_entry_product_details_qty = soApprovalModel.getSo_entry_product_details_qty();
                String product_uom_name = soApprovalModel.getProduct_uom_name();


                editor.putString("so_entry_no", so_entry_no);
                editor.putString("so_entry_date", so_entry_date);
                editor.putString("customer_name_so", customer_name_so);
                editor.putString("customer_code_so", customer_code_so);
                editor.putString("product_code_so", product_code_so);
                editor.putString("product_name_so", product_name_so);
                editor.putString("so_entry_product_details_rate", so_entry_product_details_rate);
                editor.putString("so_entry_product_details_amount", so_entry_product_details_amount);
                editor.putString("so_entry_product_details_qty", so_entry_product_details_qty);
                editor.putString("product_uom_name", product_uom_name);
                editor.putString("sdateSo", startingDate.getText().toString());
                editor.putString("cdateSo", closingDate.getText().toString());
                editor.commit();
                startActivity(new Intent(SoApprovalList.this, SoApprovalView.class));
                finish();

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
                soApprovalModelArrayList.clear();
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

        datePickerDialog = new DatePickerDialog(SoApprovalList.this, R.style.DatePickerDialogTheme,
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

        datePickerDialog = new DatePickerDialog(SoApprovalList.this, R.style.DatePickerDialogTheme,
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
        final ProgressDialog progressDialog = new ProgressDialog(SoApprovalList.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(POST, SALES_REPORT_API, new Response.Listener<String>() {
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
                        String so_entry_no = jsonObject.getString("so_entry_no");
                        String so_entry_date = jsonObject.getString("so_entry_date");
                        String customer_name_so = jsonObject.getString("customer_name");
                        String customer_code_so = jsonObject.getString("customer_code");
                        String product_code_so = jsonObject.getString("product_code");
                        String product_name_so = jsonObject.getString("product_name");
                        String so_entry_product_details_rate = jsonObject.getString("so_entry_product_details_rate");
                        String so_entry_product_details_amount = jsonObject.getString("so_entry_product_details_amount");
                        String so_entry_product_details_qty = jsonObject.getString("so_entry_product_details_qty");
                        String product_uom_name = jsonObject.getString("product_uom_name");

                        SoApprovalModel soApprovalModel = new SoApprovalModel();
                        soApprovalModel.setSo_entry_no(so_entry_no);
                        soApprovalModel.setSo_entry_date(so_entry_date);
                        soApprovalModel.setCustomer_name_so(customer_name_so);
                        soApprovalModel.setCustomer_code_so(customer_code_so);
                        soApprovalModel.setProduct_code_so(product_code_so);
                        soApprovalModel.setProduct_name_so(product_name_so);
                        soApprovalModel.setSo_entry_product_details_rate(so_entry_product_details_rate);
                        soApprovalModel.setSo_entry_product_details_amount(so_entry_product_details_amount);
                        soApprovalModel.setSo_entry_product_details_qty(so_entry_product_details_qty);
                        soApprovalModel.setProduct_uom_name(product_uom_name);
                        soApprovalModelArrayList.add(soApprovalModel);

                    }
                    adapter = new SoapprovalAdapter(SoApprovalList.this, soApprovalModelArrayList);
                    recyclerView_so.setAdapter(adapter);
                    recyclerView_so.setHasFixedSize(true);
                    recyclerView_so.setLayoutManager(new LinearLayoutManager(SoApprovalList.this));
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
                map.put(SR_FROM_DATE, startingDate.getText().toString());
                map.put(SR_TO_DATE, closingDate.getText().toString());
                map.put(SR_USER_ID,"");
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
            soApprovalModelArrayList.clear();
            valid =false;
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
               soApprovalModelArrayList.clear();
            }
        } catch (ParseException parse) {
            Log.e(TAG, "Parse Exception -" + parse);
        }
        return ;
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
        startActivity(new Intent(SoApprovalList.this,MainActivity.class));
        finish();
    }
}
