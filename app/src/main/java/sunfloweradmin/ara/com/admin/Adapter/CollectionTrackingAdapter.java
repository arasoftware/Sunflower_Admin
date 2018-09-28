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

import sunfloweradmin.ara.com.admin.Models.CollectionTrackingModel;
import sunfloweradmin.ara.com.admin.R;

public class CollectionTrackingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    List<CollectionTrackingModel> data = Collections.emptyList();
    CollectionTrackingModel current;
    int currentPos = 0;

    public CollectionTrackingAdapter(Context context, List<CollectionTrackingModel> collectionTracking_arrayList) {

        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = collectionTracking_arrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycleritemscollectiontracking, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        CollectionTrackingModel current = data.get(position);
        myHolder.collectionEntryNo_rowlayout.setText("No : " + current.getCollection_entryNo());
        myHolder.collectionCustomerName_rowlayout.setText("Customer Name : " + current.getCustomer_name());
        myHolder.collectonEntryDate_rowlayout.setText("Date : " + current.getCollection_entryDate());
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

class MyHolder extends RecyclerView.ViewHolder {

    TextView collectionEntryNo_rowlayout, collectonEntryDate_rowlayout, collectionCustomerName_rowlayout;

    public MyHolder(View itemView) {
        super(itemView);
        collectionEntryNo_rowlayout = (TextView) itemView.findViewById(R.id.collectionEntryNo_rowlayout);
        collectonEntryDate_rowlayout = (TextView) itemView.findViewById(R.id.collectonEntryDate_rowlayout);
        collectionCustomerName_rowlayout = (TextView) itemView.findViewById(R.id.collectionCustomerName_rowlayout);

    }
}
