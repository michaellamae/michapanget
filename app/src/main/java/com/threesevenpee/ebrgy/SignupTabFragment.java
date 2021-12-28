package com.threesevenpee.ebrgy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupTabFragment extends Fragment implements View.OnClickListener {

    Button buttonUserSignup;

    private EditText editUserSignupFirstName, editUserSignupLastName, editUserSignupMiddleName, editUserSignupHouseNumber,
            editUserSignupStreet, editUserSignupBarangay, editUserSignupContactNumber, editUserSignupBirthday, editUserSignupMaritalStatus,
            editUserSignupEmail, editUserSignupPassword, editUserSignupConfirmPassword;
    String userID;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false );


        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //variables
        editUserSignupFirstName = (EditText) root.findViewById(R.id.editUserSignupFirstName);
        editUserSignupLastName = (EditText) root.findViewById(R.id.editUserSignupLastName);
//        editUserSignupMiddleName;
//        editUserSignupHouseNumber;
//        editUserSignupStreet;
//        editUserSignupBarangay;
        editUserSignupEmail = (EditText) root.findViewById(R.id.editUserSignupEmail);
        editUserSignupPassword = (EditText) root.findViewById(R.id.editUserSignupPassword);
        editUserSignupConfirmPassword = (EditText) root.findViewById(R.id.editUserSignupConfirmPassword);

        //add this additional details on verification module, plus the proof of identification and type
//        editUserVerificationContactNumber;
//        editUserVerificationBirthday;
//        editUserverificationMaritalStatus;

        buttonUserSignup = (Button) root.findViewById(R.id.buttonUserSignup);

        buttonUserSignup.setOnClickListener(this);

        return root;
    }


    private void registerUser(){
        String firstName = editUserSignupFirstName.getText().toString().trim();
        String middleName = editUserSignupMiddleName.getText().toString().trim();
        String lastName = editUserSignupLastName.getText().toString().trim();
//        String houseNumber = editUserSignupHouseNumber.getText().toString().trim();
//        String street = editUserSignupStreet.getText().toString().trim();
//        String barangay = editUserSignupBarangay.getText().toString().trim();
        String email = editUserSignupEmail.getText().toString().trim();
        String documentName = editUserSignupLastName.getText().toString().trim() + editUserSignupFirstName.getText().toString().trim() + editUserSignupMiddleName.getText().toString().trim()  ;
        String password = editUserSignupPassword.getText().toString().trim();
        String confirmPassword = editUserSignupConfirmPassword.getText().toString().trim();


        //put check statements here if following are correct

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            userID = mAuth.getCurrentUser().getUid();
                            Map<String, Object> user = new HashMap<>();

                            Map <String, Object> userFullName = new HashMap<>();
                            userFullName.put("firstName", firstName);
                            userFullName.put("middleName", middleName);
                            userFullName.put("lastName", lastName);

//                                Map <String, Object> userAddress = new HashMap<>();
//                                userAddress.put("houseNumber", houseNumber);
//                                userAddress.put("street", street);
//                                userAddress.put("barangay", barangay);

                            user.put("userID", userID);
                            user.put("name", userFullName);
//                            user.put("address", userAddress);


                            DocumentReference documentReference = fStore.collection("users")
                                    .document("citizen")
                                    .collection("citizenAccounts")
                                    .document(documentName);


                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG", "onSuccess: User Profile is created for " + documentName);
                                    Toast.makeText(getActivity(), "Registered " + documentName + " successfully", Toast.LENGTH_SHORT).show();

                                }

                            });

                            Intent intent = new Intent(getActivity(), LoginActivity.class); //temporary
                            startActivity(intent);
                        }

                        else{
                            String e = ((FirebaseAuthException) task.getException()).getErrorCode();
                            Toast.makeText(getActivity(), "Registration Failed" + e,Toast.LENGTH_SHORT).show();
                            Log.e("RegistrationActivity", "Failed Registration");
                        }

                    }
                });
    }


    @Override
    public void onClick(View view) {
        if(view == buttonUserSignup) {
            registerUser();
        }

    }

}
