package smartfixsa.com.smartfix.acitivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import smartfixsa.com.smartfix.R;
import smartfixsa.com.smartfix.adapter.HomeAdapter;
import smartfixsa.com.smartfix.interfaces.ClickListener;

public class MainActivity extends AppCompatActivity implements ClickListener {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new HomeAdapter(this));

    }

    @Override
    public void onClick(int position) {
        Intent intent = null;
        switch (position) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                intent = new Intent(this, RegisterStuffActivity.class);
                break;
            case 3:
                intent = new Intent(this, MobileCompanyActivity.class);
                break;
        }
        if (intent != null)
            startActivity(intent);
    }
}
