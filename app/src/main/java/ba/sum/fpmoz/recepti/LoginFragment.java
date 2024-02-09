package ba.sum.fpmoz.recepti;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {
    public LoginFragment() {
        super(R.layout.fragment_login);
    }

    FirebaseAuth auth;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();

        FirebaseUser user = this.auth.getCurrentUser();

        if (user != null){
            Intent i = new Intent(getActivity(), StartActivity.class);
            startActivity(i);
        }

        EditText emailTxt = view.findViewById(R.id.emailTxt);
        EditText passwordPwd = view.findViewById(R.id.passwordPwd);
        Button loginBtn = view.findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailTxt.getText().toString();
                String password = passwordPwd.getText().toString();
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //Korisnik se uspjesno prijavio
                            Toast.makeText( getActivity(),"Uspjesno ste se prijavili", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(getActivity(), StartActivity.class);
                            startActivity(i);

                            emailTxt.setText("");
                            passwordPwd.setText("");

                        }else{
                            //Korisnik se nije uspjesno prijavio
                            Toast.makeText(getActivity(), "Niste se  uspjesno prijavili", Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }
        });

    }

}
