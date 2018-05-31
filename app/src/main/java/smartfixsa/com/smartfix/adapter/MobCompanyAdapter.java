package smartfixsa.com.smartfix.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import smartfixsa.com.smartfix.R;
import smartfixsa.com.smartfix.models.CompanyMobileModel;

public class MobCompanyAdapter extends RecyclerView.Adapter <MobCompanyAdapter.CompanyMobHolder>{
List<CompanyMobileModel>CompanymobList;

    public MobCompanyAdapter(List<CompanyMobileModel> companymobList) {
        CompanymobList = companymobList;
    }

    @NonNull
    @Override
    public CompanyMobHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
View row= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mobcompany,parent,false);
CompanyMobHolder holder=new CompanyMobHolder(row);
return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyMobHolder holder, int position) {
CompanyMobileModel model=CompanymobList.get(position);
holder.img.setImageResource(model.getImg_company());
holder.text.setText(model.getCompanymobile_name());
    }

    @Override
    public int getItemCount() {
        return CompanymobList.size();
    }
    public class CompanyMobHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView text;
        public CompanyMobHolder(View itemView) {
            super(itemView);
            img=(ImageView)itemView.findViewById(R.id.imgcompanymobile_icon);
            text=(TextView)itemView.findViewById(R.id.txtmobilecompany_icon);

        }
    }
}
