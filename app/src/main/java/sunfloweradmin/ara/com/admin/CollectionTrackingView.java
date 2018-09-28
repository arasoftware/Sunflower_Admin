package sunfloweradmin.ara.com.admin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sunfloweradmin.ara.com.admin.Utils.AppConstants;

import static sunfloweradmin.ara.com.admin.Utils.AppConstants.PREFERENCE;

public class CollectionTrackingView extends AppCompatActivity {

    @BindView(R.id.collectiontrckingviewlayout)
    ConstraintLayout constraintLayout;
    @BindView(R.id.collectionentryNo)
    TextView collectionEntryNo;
    @BindView(R.id.customerName)
    TextView customerName;
    @BindView(R.id.customerCode)
    TextView customerCode;
    @BindView(R.id.collectionEntryDate)
    TextView collectionEntryDate;
    @BindView(R.id.collectionEntryAmount)
    TextView collectionEntryAmount;
    @BindView(R.id.invoiceEntryNo)
    TextView invoiceEntryNo;
    @BindView(R.id.invoiceEntryDate)
    TextView invoiceEntryDate;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_tracking_view);
        ButterKnife.bind(this);

        if (!isNetworkAvailable()) {
            showSnackbar("Please Check Your Internet Connection");
        }
        initiazeView();

    }

    private void initiazeView() {
        sharedPreferences = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        String collectionentryno = sharedPreferences.getString("collectionEntryNo", "");
        String collectionentrydate = sharedPreferences.getString("collectionEntryDate", "");
        String customername = sharedPreferences.getString("CustomerName", "");
        String collectionentrydetailsamount = sharedPreferences.getString("collectionEntryDetailsAmount", "");
        String invoiceentryno = sharedPreferences.getString("invooiceEntryNo", "");
        String customercode = sharedPreferences.getString("customerCode", "");
        String invoicedate = sharedPreferences.getString("invoiceDate", "");
        String startingdate = sharedPreferences.getString("sdate", "");
        String closingdate = sharedPreferences.getString("cdate", "");

        collectionEntryNo.setText("No : " + collectionentryno);
        collectionEntryDate.setText("Date : " + collectionentrydate);
        collectionEntryAmount.setText("Amount : " + collectionentrydetailsamount);
        customerCode.setText("Code : " + customercode);
        customerName.setText("Name : " + customername);
        invoiceEntryNo.setText("No : " + invoiceentryno);
        invoiceEntryDate.setText("Date : " + invoicedate);
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

        startActivity(new Intent(CollectionTrackingView.this, CollectionTrackingList.class));
        finish();
    }
}
