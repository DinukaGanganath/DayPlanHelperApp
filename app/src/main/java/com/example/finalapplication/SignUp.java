package com.example.finalapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();

        Button SignUpBtn = (Button) findViewById(R.id.SignupButton);
        EditText Name = (EditText) findViewById(R.id.nameEdit);
        EditText Email = (EditText) findViewById(R.id.EmailEdit);
        EditText Password = (EditText) findViewById(R.id.PasswordEdit);
        EditText ConfirmPassword = (EditText) findViewById(R.id.ConfirmPasswordEdit);

        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Name.getText().toString().equals("") || Email.getText().toString().equals("") || Password.getText().toString().equals("") || ConfirmPassword.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter your all details", Toast.LENGTH_SHORT).show();
                } else if (!(Password.getText().toString().equals(ConfirmPassword.getText().toString()))) {
                    Toast.makeText(getApplicationContext(), "Password you entered are mismatched", Toast.LENGTH_SHORT).show();
                } else if(Password.getText().toString().length() < 6){
                    Toast.makeText(getApplicationContext(), "Password is too short", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(Email.getText().toString(), Password.getText().toString());
                }

                Log.d("key", "-----------------------------------------------------------------------------------------");
                Log.d("key", Password.getText().toString());
                Log.d("key", ConfirmPassword.getText().toString());
                Log.d("key", Email.getText().toString());
                Log.d("key", Name.getText().toString());
            }
        });
    }
    private void registerUser(String email, String password){

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(SignUp.this, "Registering user successful!", Toast.LENGTH_SHORT).show();
                    Intent toSignIn = new Intent(SignUp.this, SignUp.class);
                    startActivity(toSignIn);
                }else{
                    Toast.makeText(SignUp.this, "Registration failed!", Toast.LENGTH_SHORT).show();
                    Intent toSignUp = new Intent(SignUp.this, SignUp.class);
                    startActivity(toSignUp);
                }
            }
        });
    }
}
