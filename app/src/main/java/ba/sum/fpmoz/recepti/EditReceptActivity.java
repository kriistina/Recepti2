package ba.sum.fpmoz.recepti;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

import ba.sum.fpmoz.recepti.model.Recept;

public class EditReceptActivity extends AppCompatActivity {

    DatabaseReference mDatabase;
    private StorageReference storageReference;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recept);

        getSupportActionBar().hide();

        final String key = getIntent().getStringExtra("ReceptKey");

        mDatabase = FirebaseDatabase.getInstance("https://recepti-ef89e-default-rtdb.europe-west1.firebasedatabase.app/").getReference("timetables/recepti").child(key);
        storageReference = FirebaseStorage.getInstance("gs://recepti-ef89e.appspot.com").getReference();

        EditText editNaziv = findViewById(R.id.editNazivRecepta);
        EditText editSastojci = findViewById(R.id.editSastojciRecepta);
        EditText editPriprema = findViewById(R.id.editPripremaRecepta);
        ImageView novaSlika = findViewById(R.id.odaberiNovuSliku);
        Button editBtn = findViewById(R.id.editBtn);

        ActivityResultLauncher<String> mGetConcent = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        //ako je slika izabrana uzmi uri slike
                        if(result != null){
                            novaSlika.setImageURI(result);
                            imageUri = result;
                        }
                    }
                }
        );

        novaSlika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetConcent.launch("image/*");

            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageUri != null){
                    StorageReference ref = storageReference
                            .child("slike/" + UUID.randomUUID().toString());

                    ref.putFile(imageUri);
                }

                Recept r = new Recept();
                r.naziv = editNaziv.getText().toString();
                r.sastojci = editSastojci.getText().toString();
                r.priprema = editPriprema.getText().toString();
                r.slika = imageUri.toString();
                mDatabase.setValue(r);

                Intent i = new Intent(getApplicationContext(), StartActivity.class);
                startActivity(i);
            }
        });

        this.mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Recept recept = snapshot.getValue(Recept.class);
                editNaziv.setText(recept.naziv);
                editSastojci.setText(recept.sastojci);
                editPriprema.setText(recept.priprema);
                novaSlika.setImageURI(Uri.parse(recept.slika));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });



    }
}