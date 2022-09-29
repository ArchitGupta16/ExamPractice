package com.example.exampractice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private EditText name,email, pass, confirmPass, enroll;
    private Button signUpB;
    private ImageView backB;
    private FirebaseAuth mAuth;
    private String emailStr, passStr, confirmPassStr, nameStr, enRollStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.username);
        email = findViewById(R.id.emailID);
        pass = findViewById(R.id.password);
        confirmPass = findViewById(R.id.confirm_pass);
        enroll = findViewById(R.id.enroll_num);
        signUpB = findViewById(R.id.signupB);
        backB = findViewById(R.id.backB);

        mAuth = FirebaseAuth.getInstance();

        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        signUpB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validate()) {
                    signupNewUser();
                }
            }
        });

    }

    private boolean validate()
    {
        nameStr = name.getText().toString().trim();
        passStr = pass.getText().toString().trim();
        emailStr = email.getText().toString().trim();
        confirmPassStr = confirmPass.getText().toString().trim();
        enRollStr = enroll.getText().toString().trim();

        if(nameStr.isEmpty())
        {
            name.setError("Enter your name");
            return false;
        }
        if(emailStr.isEmpty())
        {
            email.setError("Enter Email ED");
        }
        if(passStr.isEmpty())
        {
            pass.setError("Enter password");
            return false;
        }
        if(confirmPassStr.isEmpty())
        {
            confirmPass.setError("Enter password");
            return false;
        }
        if(passStr.compareTo(confirmPassStr) != 0)
        {
            Toast.makeText(SignUpActivity.this,"Password and confirm Password should be same",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(enRollStr.isEmpty())
        {
            enroll.setError("Enter Enrollment number");
            return false;
        }
        return true;
    }

    private void signupNewUser()
    {


        mAuth.createUserWithEmailAndPassword(emailStr, passStr)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(SignUpActivity.this,"Sign up successful",Toast.LENGTH_SHORT).show();


                                            Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                                            startActivity(intent);
                                            SignUpActivity.this.finish();

                        } else {

                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}