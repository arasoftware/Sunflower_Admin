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

public class SoApprovalView extends AppCompatActivity {

    @BindView(R.id.constrainSo)
    ConstraintLayout constraintLayout;
    @BindView(R.id.customerCodeSo)
    TextView cuustomerCodeSo;
    @BindView(R.id.customerNameSo)
    TextView customerName;
    @BindView(R.id.soNo)
    TextView entryNo;
    @BindView(R.id.soDate)
    TextView entrydate;
    @BindView(R.id.productCodeSo)
    TextView productCode;
    @BindView(R.id.productUomSo)
    TextView productUom;
    @BindView(R.id.productNameSo)
    TextView productName;
    @BindView(R.id.soProductrate)
    TextView productRate;
    @BindView(R.id.soProductAmount)
    TextView productAmount;
    @BindView(R.id.soProductQty)
    TextView productQty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_so_approval_view);
        ButterKnife.bind(this);
        if (!isNetworkAvailable()) {
            showSnackbar("Please Check Your Internet Connection");
        }
        initializeView();

    }

    private void initializeView() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE, MODE_PRIVATE);

        String so_entry_no = sharedPreferences.getString("so_entry_no", "");
        String so_entry_date = sharedPreferences.getString("so_entry_date", "");
        String customer_name_so = sharedPreferences.getString("customer_name_so", "");
        String customer_code_so = sharedPreferences.getString("customer_code_so", "");
        String product_code_so = sharedPreferences.getString("product_code_so", "");
        String product_name_so = sharedPreferences.getString("product_name_so", "");
        String so_entry_product_details_rate = sharedPreferences.getString("so_entry_product_details_rate", "");
        String so_entry_product_details_amount = sharedPreferences.getString("so_entry_product_details_amount", "");
        String so_entry_product_details_qty = sharedPreferences.getString("so_entry_product_details_qty", "");
        String product_uom_name = sharedPreferences.getString("product_uom_name", "");
        String sdateSo = sharedPreferences.getString("sdateSo", "");
        String cdateSo = sharedPreferences.getString("cdateSo", "");

        entryNo.setText("No : "+so_entry_no);
        entrydate.setText("Date : "+so_entry_date);
        customerName.setText("Name : "+customer_name_so);
        cuustomerCodeSo.setText("Code : "+customer_code_so);
        productCode.setText("Product Code : "+product_code_so);
        productName.setText("Product Name : "+product_name_so);
        productRate.setText("Product Rate : "+so_entry_product_details_rate);
        productAmount.setText("Product Amount : "+so_entry_product_details_amount);
        productQty.setText("Product Quantity : "+so_entry_product_details_qty);
        productUom.setText("Product UOM Name : "+product_uom_name);
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

        startActivity(new Intent(SoApprovalView.this, SoApprovalList.class));
        finish();
    }
}
