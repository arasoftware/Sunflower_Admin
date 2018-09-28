package sunfloweradmin.ara.com.admin.Models;

public class DeliveyTrackingModel {
    private String dc_entry_uniq_id;

    private String customer_code;

    private String dc_entry_date;

    private String product_name;

    private String customer_name;

    private String dc_entry_no;


    private String dc_entry_product_details_qty;


    private String dc_entry_id;


    private String product_code;


    public String getDc_entry_uniq_id() {
        return dc_entry_uniq_id;
    }

    public void setDc_entry_uniq_id(String dc_entry_uniq_id) {
        this.dc_entry_uniq_id = dc_entry_uniq_id;
    }

    public String getCustomer_code() {
        return customer_code;
    }

    public void setCustomer_code(String customer_code) {
        this.customer_code = customer_code;
    }

    public String getDc_entry_date() {
        return dc_entry_date;
    }

    public void setDc_entry_date(String dc_entry_date) {
        this.dc_entry_date = dc_entry_date;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getDc_entry_no() {
        return dc_entry_no;
    }

    public void setDc_entry_no(String dc_entry_no) {
        this.dc_entry_no = dc_entry_no;
    }


    public String getDc_entry_product_details_qty() {
        return dc_entry_product_details_qty;
    }

    public void setDc_entry_product_details_qty(String dc_entry_product_details_qty) {
        this.dc_entry_product_details_qty = dc_entry_product_details_qty;
    }


    public String getDc_entry_id() {
        return dc_entry_id;
    }

    public void setDc_entry_id(String dc_entry_id) {
        this.dc_entry_id = dc_entry_id;
    }


    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }


    @Override
    public String toString() {
        return "ClassPojo [dc_entry_uniq_id = " + dc_entry_uniq_id + ", customer_code = " + customer_code + ", dc_entry_date = " + dc_entry_date + ", product_name = " + product_name + ", customer_name = " + customer_name + ", dc_entry_no = " + dc_entry_no + ", dc_entry_product_details_qty = " + dc_entry_product_details_qty + ", dc_entry_id = " + dc_entry_id + ", product_code = " + product_code + "]";
    }
}
