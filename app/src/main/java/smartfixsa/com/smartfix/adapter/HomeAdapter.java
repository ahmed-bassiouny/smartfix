package smartfixsa.com.smartfix.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import smartfixsa.com.smartfix.R;
import smartfixsa.com.smartfix.interfaces.ClickListener;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private int[] image = {R.drawable.center,R.drawable.store,R.drawable.register,R.drawable.maintenace};
    private int[] text = {R.string.center,R.string.store,R.string.register,R.string.maintenance};
    private Activity activity;
    private ClickListener clickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgIcon;
        public TextView txtIcon;

        public MyViewHolder(View view) {
            super(view);
            imgIcon = view.findViewById(R.id.img_icon);
            txtIcon = view.findViewById(R.id.txt_icon);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onClick(getAdapterPosition());
                }
            });

        }
    }

    public HomeAdapter(Activity activity) {
        this.activity = activity;
        this.clickListener = (ClickListener) activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.imgIcon.setImageDrawable(activity.getResources().getDrawable(image[position]));
        holder.txtIcon.setText(activity.getResources().getString(text[position]));
    }

    @Override
    public int getItemCount() {
        return image.length;
    }
}