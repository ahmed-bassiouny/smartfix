package smartfixsa.com.smartfix.acitivities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import smartfixsa.com.smartfix.R;

public class MaintenanceCenterDetailsActivity extends AppCompatActivity {
    TextView Name, Address, Services;
    DatabaseReference centerDetails;
    Bundle data;
    String Value,  latitude, Longitude, name, address, services;
    Button gotoLocation;
    ProgressBar progrss_mcdl;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_center_details);
        Name = (TextView) findViewById(R.id.tv_name_mcd);
        Address = (TextView) findViewById(R.id.tv_address_mcd);
        Services = (TextView) findViewById(R.id.tv_services_mcd);
        gotoLocation=(Button)findViewById(R.id.btn_gotolocation);
        progrss_mcdl=(ProgressBar) findViewById(R.id.progress_mcl);
        data = getIntent().getExtras();
        if (data != null) {
            Value = data.getString("maintennaceCenter");
        }
        progrss_mcdl.setVisibility(View.VISIBLE);
        centerDetails = FirebaseDatabase.getInstance().getReference("Official Center").child(Value);
        centerDetails.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild("name")) {
                    name=dataSnapshot.child("name").getValue().toString();
                    Name.setText(name);

                    address=dataSnapshot.child("address").getValue().toString();
                    Address.setText(address);

                    services=dataSnapshot.child("services").getValue().toString();
                    Services.setText(services);
                    
                    progrss_mcdl.setVisibility(View.GONE);
                    if (dataSnapshot.hasChild("lat")) {
                        latitude=dataSnapshot.child("lat").getValue().toString();
                        Longitude=dataSnapshot.child("lng").getValue().toString();

                       gotoLocation.setOnClickListener(new View.OnClickListener() {
                           @Override
                            public void onClick(View v) {
                               gotoLocation.setVisibility(View.VISIBLE);
                               /*String geoUri = "http://maps.google.com/maps?q=loc:" + latitude + "," + Longitude + " (" + ""+ ")";
                               Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                               startActivity(intent);*/
                               Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:<lat>,<long>?q=<"+latitude+">,<"+Longitude+">()"));
                               startActivity(intent);
                            }
                        });

                    } else {
                        gotoLocation.setVisibility(View.GONE);
                    //   Toast.makeText(MaintenanceCenterDetailsActivity.this,"there is no Longitude or latitude for this location ",Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MaintenanceCenterDetailsActivity.this, "nothing connection", Toast.LENGTH_LONG).show();
                }
               /*                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    MaintenanceCenterModel detail = postSnapshot.getValue(MaintenanceCenterModel.class);
                    Log.e("hamo",detail.getName());
                    Log.e("hamo",detail.getAddress());
                    Log.e("hamo",detail.getServices());
                    Log.e("hamo",detail.getLng());



                    String name = detail.getName();
                    String address = detail.getAddress();
                    String service = detail.getServices();
                    String lat=detail.getLat();
                    String log=detail.getLng();


                    Name.setText(name);
                    Address.setText(address);
                    Services.setText(service);
                    latitude.setText(lat);
                    logitude.setText(log);
                                                   }
*/

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
