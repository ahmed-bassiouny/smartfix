package smartfixsa.com.smartfix.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import smartfixsa.com.smartfix.R;
import smartfixsa.com.smartfix.acitivities.MaintenanceCenterDetailsActivity;
import smartfixsa.com.smartfix.models.StoreListModel;

/**
 * Created by Adham on 05/06/2018.
 */
public class StoreListAdapter extends RecyclerView.Adapter<StoreListAdapter.ViewHolder> {
    private Context context;
    private List<StoreListModel> products;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView centername;
        TextView productname;
        TextView companyname;

        public ViewHolder(View itemView) {
            super(itemView);
            centername = itemView.findViewById(R.id.textView2);
            productname = itemView.findViewById(R.id.textView3);
            companyname = itemView.findViewById(R.id.textView4);
        }
    }

    public StoreListAdapter(Context c, List<StoreListModel> productlist) {
        this.context = c;
        products = productlist;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.store_listadapter, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final String mycenter;

        StoreListModel slm = products.get(position);
        holder.productname.setText(slm.getProduct());
        holder.companyname.setText(slm.getCompany());
        mycenter = slm.getCenter();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), MaintenanceCenterDetailsActivity.class);
                intent.putExtra("center_name", mycenter);
                view.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}

