package smartfixsa.com.smartfix.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import smartfixsa.com.smartfix.R;
import smartfixsa.com.smartfix.interfaces.ClickListener;

public class OfficialCentersAdapter extends RecyclerView.Adapter<OfficialCentersAdapter.MyViewHolder> {

    private List<String> centers;
    private ClickListener clickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtCenter;

        public MyViewHolder(View view) {
            super(view);
            txtCenter = view.findViewById(R.id.txt_center);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onClick(getAdapterPosition());
                }
            });

        }
    }

    public OfficialCentersAdapter(Activity activity, List<String> centers) {
        this.clickListener = (ClickListener) activity;
        this.centers = centers;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_official_center, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.txtCenter.setText(centers.get(position));
    }

    @Override
    public int getItemCount() {
        return centers.size();
    }
}