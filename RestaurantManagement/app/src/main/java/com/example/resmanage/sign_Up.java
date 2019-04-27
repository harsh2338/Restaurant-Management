package com.example.resmanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class sign_Up extends AppCompatActivity {
    //widgets
    private EditText newUserName, newpassword, newEmail;
    private Button register;
    private TextView oldUser;
    //Firebase authentication and other variables
    private FirebaseAuth firebaseAuth;
    private String name = "\0", password = "\0", userEmail = "\0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);
        firebaseAuth = FirebaseAuth.getInstance();//getting an instance from firebase
        UIvariables();//calling function to reference the variables

        //if user has already registered
        oldUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(sign_Up.this, Login.class));
                finish();
            }
        });

        //on clicking register button
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterData();
                if (Validate_Login_Credentials()) {
                    firebaseAuth.createUserWithEmailAndPassword(userEmail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                                                                                                               @Override
                                                                                                               public void onComplete(@NonNull Task<AuthResult> task) {
                                                                                                                   if (task.isSuccessful()) {
                                                                                                                       Toast.makeText(sign_Up.this, "Registeration Successful", Toast.LENGTH_SHORT).show();
                                                                                                                       startActivity(new Intent(sign_Up.this, Login.class));
                                                                                                                   } else {
                                                                                                                       Toast.makeText(sign_Up.this, "Registeration Failed", Toast.LENGTH_SHORT).show();
                                                                                                                   }
                                                                                                               }
                                                                                                           }
                    );
                }
            }
        });


    }

    private void UIvariables()//            to reference xml id's to java variables
    {
        newUserName = findViewById(R.id.newUserName);
        newpassword = findViewById(R.id.newpassword);
        newEmail = findViewById(R.id.newEmail);
        register = findViewById(R.id.register);
        oldUser = findViewById(R.id.oldUser);
    }

    private void enterData() {
        name = newUserName.getText().toString().trim();
        password = newpassword.getText().toString().trim();
        userEmail = newEmail.getText().toString().trim();
    }

    private boolean Validate_Login_Credentials()//to see to that the user fills in all the fields
    {
        boolean field_check = false;
        if (userEmail.isEmpty() || password.isEmpty() || name.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
        } else {
            field_check = true;
        }
        return field_check;

    }
}