package ba.sum.fpmoz.recepti;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReceptActivity extends AppCompatActivity {

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recept);

        getSupportActionBar().hide();

        final String key = getIntent().getStringExtra("ReceptKey");

        mDatabase = FirebaseDatabase.getInstance("https://recepti-ef89e-default-rtdb.europe-west1.firebasedatabase.app/").getReference("timetables/recepti").child(key);

        TextView naziv = findViewById(R.id.nazivTxt);
        TextView sastojci = findViewById(R.id.sastojci);
        TextView priprema = findViewById(R.id.priprema);
        ImageView receptImage = findViewById(R.id.receptImageView);
        FloatingActionButton shareBtn = findViewById(R.id.shareBtn);


        this.mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    String nazivRecepta = snapshot.child("naziv").getValue().toString();
                    String sastojciRecepta = snapshot.child("sastojci").getValue().toString();
                    String pripremaRecepta = snapshot.child("priprema").getValue().toString();
                    String slikaRecepta = snapshot.child("slika").getValue().toString();

                    Glide.with(receptImage.getContext()).load(slikaRecepta).into(receptImage);
                    naziv.setText(nazivRecepta);
                    sastojci.setText(sastojciRecepta);
                    priprema.setText(pripremaRecepta);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                String text = "Podijeli recept";
                i.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(i, "Podijeli"));

            }
        });

    }


}