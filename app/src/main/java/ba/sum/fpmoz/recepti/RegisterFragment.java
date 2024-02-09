package ba.sum.fpmoz.recepti;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Intent;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterFragment extends Fragment {

    public RegisterFragment() {
        super(R.layout.fragment_register);
    }

    FirebaseAuth auth;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();

        EditText firstNameTxt = view.findViewById(R.id.firstNameTxt);
        EditText lastNameTxt = view.findViewById(R.id.lastNameTxt);
        EditText registerEmailTxt = view.findViewById(R.id.registerEmailTxt);
        EditText registerPasswordPwd = view.findViewById(R.id.registerPasswordPwd);

        Button registerbuttonBtn = view.findViewById(R.id.registerbuttonBtn);

        registerbuttonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstname = firstNameTxt.getText().toString();
                String lastname = lastNameTxt.getText().toString();
                String email = registerEmailTxt.getText().toString();
                String password = registerPasswordPwd.getText().toString();

                if(firstname.equals("") || lastname.equals("") || email.equals("") || password.equals("")){
                    Toast.makeText( getActivity(),"Niste unijeli sve podatke!", Toast.LENGTH_LONG).show();
                }else{
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                firstNameTxt.setText("");
                                lastNameTxt.setText("");
                                registerEmailTxt.setText("");
                                registerPasswordPwd.setText("");
                                Toast.makeText( getActivity(),"Uspješno ste se registrirali!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }


}

