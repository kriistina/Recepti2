package ba.sum.fpmoz.recepti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ba.sum.fpmoz.recepti.adapters.ReceptiAdapter;
import ba.sum.fpmoz.recepti.model.Recept;

public class StartActivity extends AppCompatActivity{

    FirebaseAuth auth;
    DatabaseReference mDatabase;
    ReceptiAdapter adapter;

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        getSupportActionBar().hide();


        this.auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance("https://recepti-ef89e-default-rtdb.europe-west1.firebasedatabase.app/").getReference("timetables/recepti");

        FloatingActionButton addNewRecepieBtn = findViewById(R.id.addNewRecepieBtn);
        Button logoutBtn = findViewById(R.id.logoutBtn);
        ImageButton logoutBtn2 = findViewById(R.id.logoutBtn2);



        FirebaseRecyclerOptions<Recept> options = new FirebaseRecyclerOptions.Builder<Recept>()
                .setQuery(mDatabase, Recept.class)
                .build();


        this.adapter = new ReceptiAdapter(options);

        RecyclerView receptiListItems = findViewById(R.id.receptiListView);
        receptiListItems.setLayoutManager(new LinearLayoutManager(this));
        receptiListItems.setAdapter(this.adapter);


        addNewRecepieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), DodajReceptActivity.class);
                startActivity(i);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), TabbedActivity.class);
                startActivity(i);
                auth.signOut();
            }
        });

        logoutBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), TabbedActivity.class);
                startActivity(i);
                auth.signOut();
            }
        });

    }

}