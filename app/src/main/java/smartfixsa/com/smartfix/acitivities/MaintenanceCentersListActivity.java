package smartfixsa.com.smartfix.acitivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import smartfixsa.com.smartfix.R;
import smartfixsa.com.smartfix.adapter.OfficialCentersAdapter;
import smartfixsa.com.smartfix.interfaces.ClickListener;

public class MaintenanceCentersListActivity extends AppCompatActivity implements ClickListener {

    private RecyclerView recycler;
    private ProgressBar progress;
    private List<String> centers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_centers_list);
        recycler = findViewById(R.id.recycler);
        progress = findViewById(R.id.progress);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        centers = new ArrayList<>();
        progress.setVisibility(View.VISIBLE);
        FirebaseDatabase.getInstance().getReference().child("Official Center")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot item:dataSnapshot.getChildren()){
                            String center = item.getKey();
                            centers.add(center);
                        }
                        progress.setVisibility(View.GONE);
                        recycler.setAdapter(new OfficialCentersAdapter(MaintenanceCentersListActivity.this,centers));
                        recycler.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        progress.setVisibility(View.GONE);
                        Toast.makeText(MaintenanceCentersListActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onClick(int position) {

    }
}
