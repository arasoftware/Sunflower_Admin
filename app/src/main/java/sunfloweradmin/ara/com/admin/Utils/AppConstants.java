package sunfloweradmin.ara.com.admin.Utils;

import com.android.volley.Request;

import static android.content.Context.MODE_PRIVATE;

public class AppConstants {


    //Login Api
    public  static  final  String LOGINAPI = "http://sunflower.sunflowergroups.com/app/sun_android_app.php?action=login&";
    public static final String PARAM_USERID = "user_id";
    public static final String PARAM_PASSWORD = "password";
    public static final String LOGIN_RESPONSE  = "login";
    public static final String RESPONSE_USERID = "user_id";
    public static final String RESPONSE_USERNAME  = "user_name";
    public static final String RESPONSE_BRANCHID  = "user_branch_ids";
    public static final String LOGINMESSAGE =  "success";

    //Request Type----------------------------------------------------------------------------------

    public static final int POST = Request.Method.POST;
    public static final int GET = Request.Method.GET;

    //collection tracking api ---------------------------------------------------------------------------

    public static final String COLLECTION_TRACKING_API = "http://sunflower.sunflowergroups.com/app/sun_android_app.php?action=collection_report&";
    public static final String CT_FROM_DATE = "from_date";
    public static final String CT_TO_DATE = "to_date";
    public static final String CT_USER_ID = "user_id";

    //Preference  ------------------------------------------------------------------------------
    public static final String PREFERENCE = "myPref";
    public static final String LOGIN_PREFERENCE = "myPrefLogin";
    public static final int MODE = MODE_PRIVATE;
    public static final String USERIDPREF = "useridpref";
    public static final String USERNAMEPREF = "usernamepref";
    public static final String BRANCHIDPREF = "branchidpref";


    //SALES REPORT api  ------------------------------------------------------------------------------

    public static final String SALES_REPORT_API = "http://sunflower.sunflowergroups.com/app/sun_android_app.php?action=sales_report&";
    public static final String SR_FROM_DATE = "from_date";
    public static final String SR_TO_DATE = "to_date";
    public static final String SR_USER_ID= "user_id";


    //Delivery REPORT api  ------------------------------------------------------------------------------

    public static final String DELIVERY_REPORT_API = "http://sunflower.sunflowergroups.com/app/sun_android_app.php?action=delivery_report&";
    public static final String DR_FROM_DATE = "from_date";
    public static final String DR_TO_DATE = "to_date";
    public static final String DR_USER_ID = "user_id";

    //DASH board Api ------------------------------------------------------------------------------------
    public static final String DASH_BOARD_API = "http://sunflower.sunflowergroups.com/app/sun_android_app.php?action=dashborad";

}
