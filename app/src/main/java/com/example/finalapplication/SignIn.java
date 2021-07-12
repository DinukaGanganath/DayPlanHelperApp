package com.example.finalapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(1);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_sign_in);

        TextView SignUp = (TextView) findViewById(R.id.SignUpLink);
        EditText EmailSignIn = (EditText) findViewById(R.id.Email);
        EditText PassWordSignIn = (EditText) findViewById(R.id.Password);
        Button SignInBtn = (Button) findViewById(R.id.SignInButton);

        auth = FirebaseAuth.getInstance();

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toSignUpIntent = new Intent(SignIn.this, SignUp.class);
                startActivity(toSignUpIntent);
            }
        });

        SignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(EmailSignIn.getText().toString().equals("") || PassWordSignIn.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Enter both username and password",Toast.LENGTH_SHORT).show();
                }else{
                    logInUser(EmailSignIn.getText().toString(), PassWordSignIn.getText().toString());
                }
            }
        });
    }

    private void logInUser(String email, String password){

        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(SignIn.this, "Sign in successfully", Toast.LENGTH_SHORT).show();
                Intent toSelectActivity = new Intent(SignIn.this, SelectActivity.class);
                startActivity(toSelectActivity);
            }
        });
    }
}