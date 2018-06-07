package smartfixsa.com.smartfix.acitivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import smartfixsa.com.smartfix.R;
import smartfixsa.com.smartfix.adapter.StoreListAdapter;
import smartfixsa.com.smartfix.models.StoreListModel;

import smartfixsa.com.smartfix.R;

public class StoreListActivity extends AppCompatActivity {
    private ProgressBar progress;

    private RecyclerView recyclerView;
    private StoreListAdapter storeListAdapter;
    DatabaseReference dbref;
    FirebaseDatabase database;
    private ArrayList<StoreListModel> products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_store_list);
                // todo ui
        progress = findViewById(R.id.progress);
        // todo ui
        products=new ArrayList<>();
        recyclerView=findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        storeListAdapter=new StoreListAdapter(StoreListActivity.this,products);
        recyclerView.setAdapter(storeListAdapter);
        database=FirebaseDatabase.getInstance();
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        dbref=database.getReference();
        progress.setVisibility(View.VISIBLE);
        dbref.child("Products").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    StoreListModel p=data.getValue(StoreListModel.class);
                    progress.setVisibility(View.GONE);
                    products.add(p);
                    storeListAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progress.setVisibility(View.GONE);
                Toast.makeText(StoreListActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
                }
}
