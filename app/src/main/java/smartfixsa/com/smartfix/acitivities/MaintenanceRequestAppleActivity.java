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
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import smartfixsa.com.smartfix.LocationManager;
import android.Manifest;
import smartfixsa.com.smartfix.R;

public class MaintenanceRequestAppleActivity extends AppCompatActivity implements LocationListener {
    Spinner ModelType;
    Spinner ChangeType;
    TextView Price;
    TextView SaveTime;
    String s,t; // afhm eh mn s , t ya fathyyyyyyyyyyyyyyyy
    // ar7mny ya fathy
    DatabaseReference databaseApple,databaseItem,DatabasePriceTime;
    // model , model two
    // lw 3andy 5 array list yeb2a model five kman s7 ?????
    final List<String> model = new ArrayList<String>();
    final List<String> modeltwo = new ArrayList<String>();

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
// get first spinner data and Display it


        databaseApple= FirebaseDatabase.getInstance().getReference("Apple");
        databaseApple.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    String result=data.getKey();
                    model.add(result);
                }
                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(MaintenanceRequestAppleActivity.this, android.R.layout.simple_spinner_item, model);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                ModelType.setAdapter(areasAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
        ModelType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s=parent.getItemAtPosition(position).toString();
                databaseItem=databaseApple.child(s);
                databaseItem.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        modeltwo.clear();
                        for(DataSnapshot dataitem:dataSnapshot.getChildren()){
                            String result=dataitem.getKey();
                            modeltwo.add(result);
                        }
                        ArrayAdapter<String> areaitem = new ArrayAdapter<String>(MaintenanceRequestAppleActivity.this, android.R.layout.simple_spinner_item, modeltwo);
                        areaitem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        ChangeType.setAdapter(areaitem);
                        ChangeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                t=parent.getItemAtPosition(position).toString();
                                DatabasePriceTime=databaseItem.child(t);
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
    }
}
