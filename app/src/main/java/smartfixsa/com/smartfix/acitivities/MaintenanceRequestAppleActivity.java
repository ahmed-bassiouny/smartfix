package smartfixsa.com.smartfix.acitivities;

import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import smartfixsa.com.smartfix.LocationManager;
import android.Manifest;
import smartfixsa.com.smartfix.R;
import smartfixsa.com.smartfix.models.OfficialCenterModel;

public class MaintenanceRequestAppleActivity extends AppCompatActivity implements LocationListener {
    Spinner ModelType;
    Spinner ChangeType;
    TextView Price;
    TextView SaveTime;
    Button getdatalocation;
    ProgressBar progress_getddata;


    String typeofmobileselected,typeofitemchangeselected  // items selected from spinner
            , Longitude, Latitude; // items to get location user
    DatabaseReference databaseApple,databaseItem,DatabasePriceTime,
            officialcenterreference; //Data reference for all data of Official Center
    // Object of Officail center and array list of it
    // list of Officail Center will used in aonther Class
    OfficialCenterModel officialCenterModel=new OfficialCenterModel();
    ArrayList<OfficialCenterModel> officialcenter = new ArrayList<>();

    final List<String> mobiletypeList = new ArrayList<String>();// list of mobiles type to fill modelType spinner
    final List<String> itemchangeList = new ArrayList<String>();// list of change type to fill  changetype Spinner

    private LocationManager locationManager;
    private int requestLocationPermission = 125;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_request_apple);
        ModelType=(Spinner)findViewById(R.id.spinner_modeltype);
        ChangeType=(Spinner)findViewById(R.id.spinner_changetype);
        Price=(TextView)findViewById(R.id.tv_price);
        SaveTime=(TextView) findViewById(R.id.tv_savetime);
        getdatalocation=(Button)findViewById(R.id.btn_gotolocation);
        progress_getddata=(ProgressBar)findViewById(R.id.progress_getdatalocation);
        // get first spinner data and Display it


        databaseApple= FirebaseDatabase.getInstance().getReference("Apple");
        databaseApple.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    String result=data.getKey();
                    mobiletypeList.add(result);
                }
                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(MaintenanceRequestAppleActivity.this, android.R.layout.simple_spinner_item, mobiletypeList);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                ModelType.setAdapter(areasAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
        ModelType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeofmobileselected=parent.getItemAtPosition(position).toString();
// get second spinner and display it
                databaseItem=databaseApple.child(typeofmobileselected);
                databaseItem.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        itemchangeList.clear();
                        for(DataSnapshot dataitem:dataSnapshot.getChildren()){
                            String result=dataitem.getKey();
                            itemchangeList.add(result);
                        }
                        ArrayAdapter<String> areaitem = new ArrayAdapter<String>(MaintenanceRequestAppleActivity.this, android.R.layout.simple_spinner_item, itemchangeList);

                        areaitem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        ChangeType.setAdapter(areaitem);
                        ChangeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            // when select Spinner 1 and 2 will display
                            // price and save time of model type
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                typeofitemchangeselected=parent.getItemAtPosition(position).toString();
                                DatabasePriceTime=databaseItem.child(typeofitemchangeselected);
                                DatabasePriceTime.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        System.out.println(dataSnapshot.getValue());
                                        if (dataSnapshot.hasChild("price")) {
                                            Price.setText(dataSnapshot.child("price").getValue().toString());
                                            SaveTime.setText(dataSnapshot.child("save Time").getValue().toString());
                                        }else{
                                            Toast.makeText(MaintenanceRequestAppleActivity.this,"nothing connection",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        // check location permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager = new LocationManager(this, this);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, requestLocationPermission);
        }

        // will be visible until get data
        progress_getddata.setVisibility(View.VISIBLE);
        getdatalocation.setVisibility(View.GONE);

        //Button Click Lisnter
        getdatalocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getdatalocation.setVisibility(View.VISIBLE);
                RetreiveOfficialCenterObject();
            }
        });

    }

    // on permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == requestLocationPermission && grantResults[0] == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, requestLocationPermission);
        } else if (requestCode == requestLocationPermission && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            locationManager = new LocationManager(this, this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (locationManager != null)
            locationManager.addListener();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationManager.removeListener(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        // here you can get lat , lng
        // location.getLatitude()
        //location.getLongitude()
        Longitude=String.valueOf(location.getLongitude());
        Latitude=String.valueOf(location.getAltitude());
        Toast.makeText(MaintenanceRequestAppleActivity.this,"Longitude : "+Longitude+" Latitude : "+Latitude,Toast.LENGTH_SHORT).show();
    }
    private ArrayList<OfficialCenterModel>RetreiveOfficialCenterObject(){
        officialcenterreference = FirebaseDatabase.getInstance().getReference("Official Center");
        officialcenterreference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        officialcenter.clear();
                        if(!data.hasChild("lat")&&!data.hasChild("lng")){
                            Log.e("hhhhhhhhhhhhhhhhhhh :", data.getValue().toString());
                            officialCenterModel.setName(data.child("name").getValue().toString());
                            officialCenterModel.setAddress(data.child("address").getValue().toString());
                            officialCenterModel.setServices(data.child("services").getValue().toString());
                            officialCenterModel.setLatitude("0");
                            officialCenterModel.setLongitude("0");
                            officialcenter.add(officialCenterModel);
                        }else {
                            Log.e("hhhhhhhhhhhhhhhhhhh :", data.getValue().toString());
                            officialCenterModel.setName(data.child("name").getValue().toString());
                            officialCenterModel.setAddress(data.child("address").getValue().toString());
                            officialCenterModel.setServices(data.child("services").getValue().toString());
                            officialCenterModel.setLatitude(data.child("lat").getValue().toString());
                            officialCenterModel.setLongitude(data.child("lng").getValue().toString());
                            officialcenter.add(officialCenterModel);
                        }

                    }
                }else {Toast.makeText(MaintenanceRequestAppleActivity.this,"there is no data here", Toast.LENGTH_LONG).show();}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return officialcenter;  }
}
