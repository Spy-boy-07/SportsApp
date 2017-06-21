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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


    public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{





        private Button mButtonRegister;
        private EditText mEditTextEmail;
        private EditText mEditTextPassword;
        private Button    mButtonSignin;

        private ProgressDialog mProgressDialog;

        private FirebaseAuth mFirebaseAuth;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sign_up);


            mFirebaseAuth = FirebaseAuth.getInstance();

        /*if(mFirebaseAuth.getCurrentUser() != null){
            //user is already logged in
            // start profile activity
            finish();
            startActivity(new Intent(getApplicationContext(),ContentActivity.class));
        }*/

            mProgressDialog = new ProgressDialog(this);

            mButtonRegister = (Button) findViewById(R.id.buttonRegister);

            mEditTextEmail = (EditText) findViewById(R.id.editTextEmail);
            mEditTextPassword = (EditText) findViewById(R.id.editTextPassword);

            mButtonSignin = (Button) findViewById(R.id.buttonSignin);

            mButtonRegister.setOnClickListener(this);
            mButtonSignin.setOnClickListener(this);

        }

        /// May be we should change here to validate whether we have user in our list or not
        private void registerUser() {
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

            mProgressDialog.setMessage("Registering User..");
            mProgressDialog.show();

            mFirebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task){
                            if(task.isSuccessful()){
                                // user is successfully registered and logged in
                                // we will start the profile activity here
                                // right now lets display toasts only
                                finish();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                Toast.makeText(SignUpActivity.this,"Registered Successfully", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(SignUpActivity.this,"Couldn't Register... Plese try again", Toast.LENGTH_SHORT).show();
                            }
                            mProgressDialog.dismiss();
                        }
                    });

        }

        @Override
        public void onClick(View view) {
            if(view == mButtonRegister) {
                //RegisterUser();
                registerUser();
            }

            if(view == mButtonSignin){
                // will OPen sign in or login activity
                startActivity(new Intent(this, LoginActivity.class));
            }
        }
    }
