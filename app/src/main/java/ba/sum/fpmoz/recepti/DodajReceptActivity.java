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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import ba.sum.fpmoz.recepti.model.Recept;
import java.util.UUID;

public class DodajReceptActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private StorageReference storageReference;
    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_recept);

        getSupportActionBar().hide();


        mDatabase = FirebaseDatabase.getInstance("https://recepti-ef89e-default-rtdb.europe-west1.firebasedatabase.app/").getReference("timetables/recepti");
        storageReference = FirebaseStorage.getInstance("gs://recepti-ef89e.appspot.com").getReference();

        EditText nazivReceptaTxt = findViewById(R.id.nazivReceptaTxt);
        EditText sastojciTxt = findViewById(R.id.sastojciTxt);
        EditText pripremaTxt = findViewById(R.id.pripremaTxt);
        Button dodajRecept = findViewById(R.id.dodajReceptBtn);
        ImageView odaberiSliku = findViewById(R.id.odaberiSliku);


        ActivityResultLauncher<String> mGetConcent = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        //ako je slika izabrana uzmi uri slike
                        if(result != null){
                            odaberiSliku.setImageURI(result);
                            imageUri = result;
                        }
                    }
                }
        );


        odaberiSliku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetConcent.launch("image/*");

            }
        });


        dodajRecept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageUri != null){
                    StorageReference ref = storageReference
                            .child("slike/" + UUID.randomUUID().toString());

                    ref.putFile(imageUri);
                } else {
                    Toast.makeText(getApplicationContext(), "Molimo odaberite sliku", Toast.LENGTH_LONG).show();
                }
           

                String naziv = nazivReceptaTxt.getText().toString();
                String sastojci = sastojciTxt.getText().toString();
                String priprema = pripremaTxt.getText().toString();
                String slika = imageUri.toString();

                Recept noviRecept = new Recept(naziv, sastojci, priprema, slika);

                mDatabase.push().setValue(noviRecept).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            nazivReceptaTxt.setText("");
                            sastojciTxt.setText("");
                            pripremaTxt.setText("");

                            Intent i = new Intent(getApplicationContext(), StartActivity.class);
                            startActivity(i);
                        }


                    }
                });


            }
        });


    }
}