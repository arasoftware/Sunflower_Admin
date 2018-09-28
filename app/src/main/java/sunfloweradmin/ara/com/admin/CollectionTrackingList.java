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
import sunfloweradmin.ara.com.admin.Models.CollectionTrackingModel;
import sunfloweradmin.ara.com.admin.Utils.MySingleton;
import sunfloweradmin.ara.com.admin.listeneres.RecyclerTouchListener;

import static sunfloweradmin.ara.com.admin.Utils.AppConstants.COLLECTION_TRACKING_API;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.CT_FROM_DATE;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.CT_TO_DATE;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.CT_USER_ID;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.POST;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.PREFERENCE;

public class CollectionTrackingList extends AppCompatActivity {

    public static final String TAG = "CollectionTrackingList";
    @BindView(R.id.collectiontrackinglistlayout)
    ConstraintLayout constraintLayout;
    @BindView(R.id.startingDate)
    TextView startingDate;
    @BindView(R.id.endingDate)
    TextView closingDate;
    @BindView(R.id.onclickbtn)
    ImageButton onclickImage;
    @BindView(R.id.swipetoCollection)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerCollcectionTracking)
    RecyclerView recyclerViewCollectiontracking;
    CollectionTrackingAdapter adapter;
    DatePickerDialog datePickerDialog;
    boolean sDateClicked = false, cDateClicked = false;
    ArrayList<CollectionTrackingModel> collectionTracking_ArrayList = new ArrayList<CollectionTrackingModel>();
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_tracking);
        ButterKnife.bind(this);

        if (isNetworkAvailable()) {

        } else {
            showSnackbar("please Check Your Network Connection");
        }
        // -----------------------------------------------------------------------------------------
        sharedPreferences = getSharedPreferences(PREFERENCE, MODE_PRIVATE);

        String sdate = sharedPreferences.getString("sdate", "");
        String cdate = sharedPreferences.getString("cdate", "");
        if (sdate != "" && cdate != "") {
            startingDate.setText(sdate);
            closingDate.setText(cdate);
            collectionTracking_ArrayList.clear();
            intializeRecylcerView();
        } else {
            startingDate.setText("Starting Date");
            closingDate.setText("Closing Date");
        }
        //        -----------------------------------------------------------------------------------------
        onclickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collectionTracking_ArrayList.clear();
                intializeRecylcerView();
            }
        });

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

        recyclerViewCollectiontracking.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerViewCollectiontracking, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                CollectionTrackingModel collectionTrackingModel = collectionTracking_ArrayList.get(position);
                sharedPreferences = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String collectionEntryNo = collectionTrackingModel.getCollection_entryNo();
                String collectionEntryDate = collectionTrackingModel.getCollection_entryDate();
                String CustomerName = collectionTrackingModel.getCustomer_name();
                String collectionEntryDetailsAmount = collectionTrackingModel.getCollectionEntryDetails_amount();
                String invooiceEntryNo = collectionTrackingModel.getInvoiceEntry_No();
                String customerCode = collectionTrackingModel.getCustomer_code();
                String invoiceDate = collectionTrackingModel.getInvioce_entryDate();

                editor.putString("collectionEntryNo", collectionEntryNo);
                editor.putString("collectionEntryDate", collectionEntryDate);
                editor.putString("CustomerName", CustomerName);
                editor.putString("collectionEntryDetailsAmount", collectionEntryDetailsAmount);
                editor.putString("invooiceEntryNo", invooiceEntryNo);
                editor.putString("customerCode", customerCode);
                editor.putString("invoiceDate", invoiceDate);
                editor.putString("sdate", startingDate.getText().toString());
                editor.putString("cdate", closingDate.getText().toString());
                editor.commit();


                startActivity(new Intent(CollectionTrackingList.this, CollectionTrackingView.class));
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
                collectionTracking_ArrayList.clear();
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

        datePickerDialog = new DatePickerDialog(CollectionTrackingList.this, R.style.DatePickerDialogTheme,
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

        datePickerDialog = new DatePickerDialog(CollectionTrackingList.this, R.style.DatePickerDialogTheme,
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
        final ProgressDialog progressDialog = new ProgressDialog(CollectionTrackingList.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(POST, COLLECTION_TRACKING_API, new Response.Listener<String>() {
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
                        String collection_entry_no = jsonObject.getString("collection_entry_no");
                        String collection_entry_date = jsonObject.getString("collection_entry_date");
                        String customer_name = jsonObject.getString("customer_name");
                        String customer_code = jsonObject.getString("customer_code");
                        String invoice_entry_no = jsonObject.getString("invoice_entry_no");
                        String invoice_entry_date = jsonObject.getString("invoice_entry_date");
                        String collection_entry_details_amount = jsonObject.getString("collection_entry_details_amount");
                        CollectionTrackingModel collectionTrackingModel = new CollectionTrackingModel();
                        collectionTrackingModel.setCollection_entryNo(collection_entry_no);
                        collectionTrackingModel.setCollection_entryDate(collection_entry_date);
                        collectionTrackingModel.setCustomer_name(customer_name);
                        collectionTrackingModel.setCustomer_code(customer_code);
                        collectionTrackingModel.setInvoiceEntry_No(invoice_entry_no);
                        collectionTrackingModel.setInvioce_entryDate(invoice_entry_date);
                        collectionTrackingModel.setCollectionEntryDetails_amount(collection_entry_details_amount);
                        collectionTracking_ArrayList.add(collectionTrackingModel);
                    }
                    adapter = new CollectionTrackingAdapter(CollectionTrackingList.this, collectionTracking_ArrayList);
                    recyclerViewCollectiontracking.setAdapter(adapter);
                    recyclerViewCollectiontracking.setHasFixedSize(true);
                    recyclerViewCollectiontracking.setLayoutManager(new LinearLayoutManager(CollectionTrackingList.this));
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
                map.put(CT_FROM_DATE, startingDate.getText().toString());
                map.put(CT_TO_DATE, closingDate.getText().toString());
                map.put(CT_USER_ID, "");
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
            collectionTracking_ArrayList.clear();
            valid = false;
        }

        return valid;
    }

    private void dateValidation() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yy");

        Date d1 = null;
        Date d2 = null;
        try {
            d1 = simpleDateFormat.parse(startingDate.getText().toString());
            d2 = simpleDateFormat.parse(closingDate.getText().toString());
            if (d1.after(d2)) {
                showSnackbar("Please Enter Valid  Date");
                collectionTracking_ArrayList.clear();
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
        startActivity(new Intent(CollectionTrackingList.this, MainActivity.class));
        finish();
    }
}
