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

import sunfloweradmin.ara.com.admin.Models.SoApprovalModel;
import sunfloweradmin.ara.com.admin.R;

public class SoapprovalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    List<SoApprovalModel> data = Collections.emptyList();
    SoApprovalModel current;
    int currentPos = 0;

    public SoapprovalAdapter(Context context, List<SoApprovalModel> soApprovalModelList_arrayList) {

        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = soApprovalModelList_arrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycleritemssoapproval, parent, false);
        MyHolderSo holder = new MyHolderSo(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolderSo myHolder = (MyHolderSo) holder;
        SoApprovalModel current = data.get(position);
        myHolder.soentryNo_recycleritem.setText("No : "+current.getSo_entry_no());
        myHolder.soentryDate_recycleritem.setText("Date : "+current.getSo_entry_date());
        myHolder.soentryCustomerName_recycleritem.setText("Name : "+current.getCustomer_name_so());

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
class MyHolderSo extends RecyclerView.ViewHolder {

    TextView soentryNo_recycleritem,soentryDate_recycleritem,soentryCustomerName_recycleritem;

    public MyHolderSo(View itemView) {
        super(itemView);
        soentryNo_recycleritem = (TextView) itemView.findViewById(R.id.soentryNo_recycleritem);
        soentryDate_recycleritem = (TextView) itemView.findViewById(R.id.soentryDate_recycleritem);
        soentryCustomerName_recycleritem = (TextView) itemView.findViewById(R.id.soentryCustomerName_recycleritem);
    }
}
