package sunfloweradmin.ara.com.admin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import sunfloweradmin.ara.com.admin.Utils.AppConstants;

import static sunfloweradmin.ara.com.admin.Utils.AppConstants.LOGIN_PREFERENCE;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.MODE;
import static sunfloweradmin.ara.com.admin.Utils.AppConstants.PREFERENCE;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.constrainmain)
    ConstraintLayout constraintLayout;
    @BindView(R.id.dashBoard_card)
    CardView dashBoard;
    @BindView(R.id.collectiontracking_card)
    CardView collectiontracking;
    @BindView(R.id.deliveryTracking_card)
    CardView deliveryTracking;
    @BindView(R.id.reports_card)
    CardView reports;
    @BindView(R.id.soApproval_card)
    CardView soApproval;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews();
        if (!isNetworkAvailable()) {
            showSnackbar("please Check Your Network Connection");
        }
    }
   /* private void setDrawableForLogin() {
        final Drawable drawableUser = getResources().getDrawable(R.drawable.dash);
        drawableUser.setBounds(0, 0, 85, 80);

        dashBoard.setCompoundDrawables(drawableUser, null, null, null);


    }*/

    private void initViews() {

        dashBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, DashBoard.class));
                finish();
            }
        });

        collectiontracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, CollectionTrackingList.class));
                finish();
            }
        });
        deliveryTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, DeliveryTrackingList.class));
                finish();
            }
        });

        reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(MainActivity.this, Reports.class));
                finish();
            }
        });
        soApproval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, SoApprovalList.class));
                finish();
            }
        });

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logut_menu, menu);
        return true;
    }

    private void logout() {

        SharedPreferences sharedPreferences = getSharedPreferences(LOGIN_PREFERENCE,MODE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        startActivity(new Intent(MainActivity.this,LoginScreen.class));
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_logout_id:
                logout();
                break;

            default:
                break;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
       finish();
    }
}


