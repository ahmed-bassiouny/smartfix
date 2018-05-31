package smartfixsa.com.smartfix.acitivities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import smartfixsa.com.smartfix.R;
import smartfixsa.com.smartfix.adapter.MobCompanyAdapter;
import smartfixsa.com.smartfix.models.CompanyMobileModel;

public class MobileCompanyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_company);
        // todo show all mobile brand
        // todo grid view ( img + name )
        ArrayList<CompanyMobileModel> CompanyMob=new ArrayList<>();
         int[]ImgCompanymob={R.drawable.apple_icon,R.drawable.huawei_icon,R.drawable.htc_icon,R.drawable.nokia_icon,R.drawable.sony_icon,R.drawable.lg_icon,R.drawable.sam_icon};
         String[] NameComapnymob={"APPLE","HUAWEI","HTC","NOKIA","SONY","LG","SAMSUNG"};
        for (int i = 0; i <ImgCompanymob.length ; i++) {
            CompanyMobileModel model=new CompanyMobileModel(ImgCompanymob[i],NameComapnymob[i]);
            CompanyMob.add(model);
            RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recyclercompanymob);
            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
            MobCompanyAdapter adapter=new MobCompanyAdapter(CompanyMob);
            recyclerView.setAdapter(adapter);

        }

    }
}
