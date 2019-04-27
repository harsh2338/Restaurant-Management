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
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    TextView rpIns;
    EditText rpEmail;
    Button rpSubmit;
    String userEmail;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        firebaseAuth = FirebaseAuth.getInstance();
        ResetPassword_Variables();

        rpSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEmail = rpEmail.getText().toString().trim();
                Send_Email();
            }
        });
    }

    private void ResetPassword_Variables() {
        rpEmail = (EditText) findViewById(R.id.rpEmail);
        rpIns = (TextView) findViewById(R.id.rpIns);
        rpSubmit = (Button) findViewById(R.id.rpSubmit);
    }

    private void Send_Email() {
        if (userEmail.equals("\0")) {
            Toast.makeText(ResetPassword.this, "Please Enter your Registered Email Address", Toast.LENGTH_SHORT).show();
        } else {
            firebaseAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(ResetPassword.this, "Password Reset Instructions has been sent to your email", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(ResetPassword.this, Login.class));
                    } else {
                        Toast.makeText(ResetPassword.this, "Error in sending Password Reset Instructions", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
}


