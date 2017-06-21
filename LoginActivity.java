package com.example.varun.finalproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mButtonSignin;
    private EditText mEditTextEmail;
    private EditText mEditTextPassword;
    private TextView mTextViewForgotPassword;
    private Button    mButtonSignUp;
    private ProgressDialog mProgressDialog;

    private FirebaseAuth mFirebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();


        mEditTextEmail = (EditText) findViewById(R.id.editTextEmail);
        mEditTextPassword = (EditText) findViewById(R.id.editTextPassword);

        mButtonSignin = (Button) findViewById(R.id.buttonSignin);
        mButtonSignUp = (Button) findViewById(R.id.buttonSignUp);

        mButtonSignin.setOnClickListener(this);

        mTextViewForgotPassword = (TextView) findViewById(R.id.textViewForgotPassword);
        mTextViewForgotPassword.setOnClickListener(this);
        mButtonSignUp.setOnClickListener(this);

        mProgressDialog = new ProgressDialog(this);

    }

    private void userLogin() {
        String email = mEditTextEmail.getText().toString().trim();
        String password = mEditTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this,"Please enter email", Toast.LENGTH_SHORT).show();
            // Stops further executing
            return;
        }

        if(TextUtils.isEmpty(password)){
            //Password id empty
            Toast.makeText(this,"Please enter Password", Toast.LENGTH_SHORT).show();
            // Stops further executing
            return;
        }

        // if Validations are OK

        mProgressDialog.setMessage("Logging In..");
        mProgressDialog.show();

        mFirebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mProgressDialog.dismiss();
                        if(task.isSuccessful()) {
                            // start the profile activity
                            finish();
                            /// start different activity
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }

                    }
                })

                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {

                        //and displaying error message
                        Toast.makeText(getApplicationContext(),"Incorrect username or password.", Toast.LENGTH_SHORT).show();
                    }
                });




    }
    @Override
    public void onClick(View view) {
        if(view == mButtonSignin) {
            userLogin();
        }

        if(view == mButtonSignUp){
            finish();
            startActivity(new Intent(this, SignUpActivity.class));
        }

        if(view == mTextViewForgotPassword){
            finish();
            startActivity(new Intent(this, ForgotPasswordActivity.class));
        }
    }
}