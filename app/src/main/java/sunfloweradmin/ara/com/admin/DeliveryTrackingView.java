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

import static sunfloweradmin.ara.com.admin.Utils.AppConstants.PREFERENCE;

public class DeliveryTrackingView extends AppCompatActivity {


    @BindView(R.id.constraindeliverytrackingviewlayout)
    ConstraintLayout constraintLayout;
    @BindView(R.id.customerNameDT)
    TextView customerName;
    @BindView(R.id.customerCodeDT)
    TextView customerCode;
    @BindView(R.id.dtNo)
    TextView entryNo;
    @BindView(R.id.dtDate)
    TextView entryDate;
    @BindView(R.id.productCodeDT)
    TextView productCode;
    @BindView(R.id.productNameDT)
    TextView productName;
    @BindView(R.id.dtProductQty)
    TextView productquantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_tracking_view);
        ButterKnife.bind(this);

        if (!isNetworkAvailable()) {
            showSnackbar("Please Check Your Network Connection");
        }
        initializeView();

    }

    private void initializeView() {

        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
       String entryUniqId =  sharedPreferences.getString("dc_entry_uniq_id","");
        String customercode = sharedPreferences.getString("customer_code","");
        String entrydate= sharedPreferences.getString("dc_entry_date","");
        String productname = sharedPreferences.getString("product_name","");
        String customername = sharedPreferences.getString("customer_name","");
        String entryno = sharedPreferences.getString("dc_entry_no","");
        String productqty = sharedPreferences.getString("dc_entry_product_details_qty","");
        String entryid = sharedPreferences.getString("dc_entry_id","");
        String productcode = sharedPreferences.getString("product_code","");

        customerCode.setText("Code : "+customercode);
        entryDate.setText("Date : "+entrydate);
        productName.setText("Product Name : "+productname);
        customerName.setText("Name : "+customername);
        entryNo.setText("No : "+entryno);
        productquantity.setText("Product Quantity : "+productqty);
        productCode.setText("Product Code : "+productcode);
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

        startActivity(new Intent(DeliveryTrackingView.this, DeliveryTrackingList.class));
        finish();
    }
}
