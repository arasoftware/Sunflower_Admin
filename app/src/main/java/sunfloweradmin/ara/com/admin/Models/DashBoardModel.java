package sunfloweradmin.ara.com.admin.Models;

public class DashBoardModel {
    private String dc_total;

    private String so_approvel;

    private String collection_total;

    private String so_total;

    public String getDc_total ()
    {
        return dc_total;
    }

    public void setDc_total (String dc_total)
    {
        this.dc_total = dc_total;
    }

    public String getSo_approvel ()
    {
        return so_approvel;
    }

    public void setSo_approvel (String so_approvel)
    {
        this.so_approvel = so_approvel;
    }

    public String getCollection_total ()
    {
        return collection_total;
    }

    public void setCollection_total (String collection_total)
    {
        this.collection_total = collection_total;
    }

    public String getSo_total ()
    {
        return so_total;
    }

    public void setSo_total (String so_total)
    {
        this.so_total = so_total;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [dc_total = "+dc_total+", so_approvel = "+so_approvel+", collection_total = "+collection_total+", so_total = "+so_total+"]";
    }
}
