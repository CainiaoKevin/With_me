package com.example.kefeng.withyou;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private EditText inputEmail,inputUserName,inputPassword;
    private Button btnSignIn, btnRegister, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnRegister = (Button) findViewById(R.id.register_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputUserName = (EditText) findViewById(R.id.userName);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);
        final Intent i = new Intent(this,MapsActivity.class);
       //final Intent i = new Intent();


        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(RegisterActivity.this, ResetPasswordActivity.class));
            }
        });


        /*btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, SignInActivity.class));
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();
                String userName = inputUserName.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();





                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(getApplicationContext(), "Enter Last name!", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String[] userMassages = {email,userName};
                i.putExtra("transitionMessage", userMassages);
                System.out.println("before goes to map");
                //startActivity(i);





                progressBar.setVisibility(View.VISIBLE);
                //create user
                //addUsers();
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(RegisterActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.VISIBLE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(i);

                                    //System.out.println("goes to map");
                                    //startActivity(new  Intent(RegisterActivity.this, MapsActivity.class));
                                    finish();
                                }
                            }
                        });


            }
        });



    }

    /*public void onClick(View view){
        Intent i = new Intent(this,MapsActivity.class);
        inputEmail = (EditText) findViewById(R.id.email);
        inputUserName = (EditText) findViewById(R.id.userName);
        String email = inputEmail.getText().toString().trim();
        String userName = inputUserName.getText().toString().trim();
        String[] userMassages = {email,userName};
        i.putExtra("transitionMessage", userMassages);
        startActivity(i);

    }*/
    /*protected void addUsers(){

        inputEmail = (EditText) findViewById(R.id.email);
        inputUserName = (EditText) findViewById(R.id.userName);
        String email = (inputEmail.getText().toString().trim()).replace(".","");
        String userName = inputUserName.getText().toString().trim();
        MapUser mapUser = new MapUser();
        mapUser.setCurrentTime();
        String currentTime = mapUser.getCurrentTime();
        System.out.println(currentTime + " current time");
        databaseMapUsers.child(email).setValue(email);
        databaseMapUsers.child(email).setValue(userName);
    }*/


    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
