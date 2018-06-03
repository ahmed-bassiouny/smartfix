package smartfixsa.com.smartfix.acitivities;

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

import smartfixsa.com.smartfix.R;

public class MaintenanceRequestAppleActivity extends AppCompatActivity {
    Spinner ModelType;
    Spinner ChangeType;
    TextView Price;
    TextView SaveTime;
    String s,t;
    DatabaseReference databaseApple,databaseItem,getDatabaseItem;
    final List<String> model = new ArrayList<String>();
    final List<String> modeltwo = new ArrayList<String>();
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
                databaseItem=FirebaseDatabase.getInstance().getReference("Apple").child(s);
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
/*        ChangeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 t=parent.getItemAtPosition(position).toString();
                getDatabaseItem=FirebaseDatabase.getInstance().getReference("Apple").child(s).child(t);
                getDatabaseItem.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        modeltwo.clear();
                       System.out.println(dataSnapshot.getValue());
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
*/

        }
}
