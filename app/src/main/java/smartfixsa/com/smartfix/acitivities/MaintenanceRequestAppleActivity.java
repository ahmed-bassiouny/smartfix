package smartfixsa.com.smartfix.acitivities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import smartfixsa.com.smartfix.LocationManager;

import android.Manifest;

import smartfixsa.com.smartfix.R;

public class MaintenanceRequestAppleActivity extends AppCompatActivity implements LocationListener {
    Spinner ModelType;
    Spinner ChangeType;
    TextView Price;
    TextView SaveTime;
    Button getdatalocation;
    ProgressBar progress_getddata;


    String typeofmobileselected, typeofitemchangeselected;  // items selected from spinner
    double Longitude, Latitude; // items to get location user
    DatabaseReference databaseApple, databaseItem, DatabasePriceTime,
            officialcenterreference; //Data reference for all data of Official Center
    // Object of Officail center and array list of it
    // list of Officail Center will used in aonther Class

    final List<String> mobiletypeList = new ArrayList<String>();// list of mobiles type to fill modelType spinner
    final List<String> itemchangeList = new ArrayList<String>();// list of change type to fill  changetype Spinner

    private LocationManager locationManager;
    private int requestLocationPermission = 125;
    private String centerName = "";
    private double centerDistance = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_request_apple);
        ModelType = (Spinner) findViewById(R.id.spinner_modeltype);
        ChangeType = (Spinner) findViewById(R.id.spinner_changetype);
        Price = (TextView) findViewById(R.id.tv_price);
        SaveTime = (TextView) findViewById(R.id.tv_savetime);
        getdatalocation = (Button) findViewById(R.id.btn_getdatalocation);
        progress_getddata = (ProgressBar) findViewById(R.id.progress_getdatalocation);
        // get first spinner data and Display it


        getdatalocation.setVisibility(View.GONE);
        progress_getddata.setVisibility(View.VISIBLE);

        databaseApple = FirebaseDatabase.getInstance().getReference("Apple");
        databaseApple.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String result = data.getKey();
                    mobiletypeList.add(result);
                }
                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(MaintenanceRequestAppleActivity.this, android.R.layout.simple_spinner_item, mobiletypeList);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                ModelType.setAdapter(areasAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        ModelType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeofmobileselected = parent.getItemAtPosition(position).toString();
// get second spinner and display it
                databaseItem = databaseApple.child(typeofmobileselected);
                databaseItem.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        itemchangeList.clear();
                        for (DataSnapshot dataitem : dataSnapshot.getChildren()) {
                            String result = dataitem.getKey();
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
                                typeofitemchangeselected = parent.getItemAtPosition(position).toString();
                                DatabasePriceTime = databaseItem.child(typeofitemchangeselected);
                                DatabasePriceTime.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        System.out.println(dataSnapshot.getValue());
                                        if (dataSnapshot.hasChild("price")) {
                                            Price.setText(dataSnapshot.child("price").getValue().toString());
                                            SaveTime.setText(dataSnapshot.child("save Time").getValue().toString());

                                            getdatalocation.setVisibility(View.VISIBLE);
                                            progress_getddata.setVisibility(View.GONE);
                                        } else {
                                            Toast.makeText(MaintenanceRequestAppleActivity.this, "nothing connection", Toast.LENGTH_LONG).show();
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
        //Button Click Lisnter
        getdatalocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // check location permission
                if (ContextCompat.checkSelfPermission(MaintenanceRequestAppleActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager = new LocationManager(MaintenanceRequestAppleActivity.this, MaintenanceRequestAppleActivity.this);
                } else {
                    ActivityCompat.requestPermissions(MaintenanceRequestAppleActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, requestLocationPermission);
                }

                // will be visible until get data
                progress_getddata.setVisibility(View.VISIBLE);
                getdatalocation.setVisibility(View.GONE);
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

        Longitude = location.getLongitude();
        Latitude = location.getAltitude();
        locationManager.removeListener(this);
        RetreiveOfficialCenterObject();
    }

    private void RetreiveOfficialCenterObject() {
        officialcenterreference = FirebaseDatabase.getInstance().getReference("Official Center");
        officialcenterreference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        if (data != null && data.hasChild("lat") && data.hasChild("lng")) {
                            double lat = data.child("lat").getValue(Double.class);
                            double lng = data.child("lng").getValue(Double.class);
                            // get different between two location
                            double currentDistance = getDistanceBetweenTwoLocation(Latitude, Longitude, lat, lng);
                            if (centerDistance == 0 || currentDistance <= centerDistance) {
                                centerDistance = currentDistance;
                                centerName = data.child("name").getValue(String.class);
                            }
                        }
                    }
                    if (!centerName.isEmpty()) {
                        Intent i = new Intent(MaintenanceRequestAppleActivity.this, MaintenanceCenterDetailsActivity.class);
                        i.putExtra("maintennaceCenter", centerName);
                        startActivity(i);
                    } else {
                        Toast.makeText(MaintenanceRequestAppleActivity.this, "ﻻ يمكن ايجاد موقع المركز حاليا", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MaintenanceRequestAppleActivity.this, "ﻻ يمكن ايجاد موقع المركز حاليا", Toast.LENGTH_LONG).show();
                }
                progress_getddata.setVisibility(View.GONE);
                getdatalocation.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MaintenanceRequestAppleActivity.this, "ﻻ يمكن ايجاد موقع المركز حاليا", Toast.LENGTH_LONG).show();
                progress_getddata.setVisibility(View.GONE);
                getdatalocation.setVisibility(View.VISIBLE);
            }
        });
    }

    private double getDistanceBetweenTwoLocation(double lat1, double lng1, double lat2, double lng2) {
        double theta = lng1 - lng2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
