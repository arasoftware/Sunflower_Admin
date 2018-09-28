package sunfloweradmin.ara.com.admin.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import sunfloweradmin.ara.com.admin.Models.DeliveyTrackingModel;
import sunfloweradmin.ara.com.admin.Models.SoApprovalModel;
import sunfloweradmin.ara.com.admin.R;

public class DelieveryTrackingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    List<DeliveyTrackingModel> data = Collections.emptyList();
    DeliveyTrackingModel current;
    int currentPos = 0;

    public DelieveryTrackingAdapter(Context context, List<DeliveyTrackingModel> dtApprovalModelList_arrayList) {

        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = dtApprovalModelList_arrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycleritemdeliverytracking, parent, false);
        MyHolderdt holder = new MyHolderdt(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolderdt myHolder = (MyHolderdt) holder;
        DeliveyTrackingModel current = data.get(position);

        myHolder.dtentryNo_recycleritem.setText("No : " + current.getDc_entry_no());
        myHolder.dtentryCustomerName_recycleritem.setText("Customer Name : " + current.getCustomer_name());

        myHolder.dtentryDate_recycleritem.setText("Date : " + current.getDc_entry_date());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}

class MyHolderdt extends RecyclerView.ViewHolder {


    TextView dtentryNo_recycleritem, dtentryDate_recycleritem, dtentryCustomerName_recycleritem;

    public MyHolderdt(View itemView) {
        super(itemView);
        dtentryNo_recycleritem = (TextView) itemView.findViewById(R.id.dtentryNo_recycleritem);
        dtentryDate_recycleritem = (TextView) itemView.findViewById(R.id.dtentryDate_recycleritem);
        dtentryCustomerName_recycleritem = (TextView) itemView.findViewById(R.id.dtentryCustomerName_recycleritem);

    }
}
