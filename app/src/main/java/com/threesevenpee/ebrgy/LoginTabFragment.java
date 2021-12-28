package com.threesevenpee.ebrgy;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class LoginTabFragment extends Fragment implements View.OnClickListener{



    Button buttonLogin;

    private FirebaseAuth mAuth;
    private EditText editLoginEmail, editLoginPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false );

        mAuth = FirebaseAuth.getInstance();

        editLoginEmail = root.findViewById(R.id.editLoginEmail);
        editLoginPassword = root.findViewById(R.id.editLoginPassword);

        buttonLogin = root.findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(this);
        return root;
    }

    private void loginUser(){
        String email = editLoginEmail.getText().toString().trim();
        String password = editLoginPassword.getText().toString().trim();
//        progressDialog = new ProgressDialog(this);

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getActivity(), "Please enter email", Toast.LENGTH_SHORT).show(); //getActivity was used instead of 'this' because this is inside of a fragment
            //stop from registering further code hekhek
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getActivity(), "Please enter password", Toast.LENGTH_SHORT).show();
            //stop from registering further code hekhek
            return;
        }

//        progressDialog.setMessage("Logging in");
//        progressDialog.show();


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
//                            progressDialog.hide();
                            Log.d("LoginActivity", "Successful");
                            Toast.makeText(getActivity(), "Login success" , Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), SignupTabFragment.class); //tempo splash // home
                            startActivity(intent);

                        } else {
//                            progressDialog.hide();
                            String e = ((FirebaseAuthException) task.getException()).getErrorCode();
                            String errorString = "Please check your email and password";

                            if(e == "ERROR_WRONG_PASSWORD") {
                                errorString = "Invalid password";
                                editLoginPassword.setError(errorString);
                                editLoginPassword.requestFocus();
                            }
                            if(e == "ERROR_WRONG_EMAIL") {
                                errorString = "Invalid email";
                                editLoginEmail.setError(errorString);
                                editLoginEmail.requestFocus();
                            }


                            Toast.makeText(getActivity(), errorString, Toast.LENGTH_SHORT).show();
                            Log.e("LoginActivity", "Failed Login");
                        }


                    }
                });
    }


    @Override
    public void onClick(View view) {
        if(view == buttonLogin) {
            loginUser();
        }

    }

}
