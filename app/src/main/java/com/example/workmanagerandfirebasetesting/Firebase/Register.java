package com.example.workmanagerandfirebasetesting.Firebase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.workmanagerandfirebasetesting.Model.Users;
import com.example.workmanagerandfirebasetesting.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class Register extends AppCompatActivity {
    private EditText edt_email, edt_pwd, edt_name;
    private TextView tv_login;
    private Button btn_reg;

    private FirebaseAuth mauth;

    private DatabaseReference myRef;

    private String user_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mauth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("users");

        edt_name = findViewById(R.id.edt_name);
        edt_email = findViewById(R.id.edt_email);
        edt_pwd = findViewById(R.id.edt_pwd);
        tv_login = findViewById(R.id.tv_login);
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(FirebaseLoginActivity.class);
                finish();

            }
        });

        btn_reg = findViewById(R.id.btn_register);
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = edt_name.getText().toString().trim();
                String email = edt_email.getText().toString().trim();
                String password = edt_pwd.getText().toString().trim();


                doRegister(name, email, password);

            }
        });
    }

    private void doRegister(final String name, final String email, final String password) {


        if (password.length() < 6) {

            edt_pwd.setError("Minimum 6 characters");
            return;
        }

        mauth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            generateUserToken(name, email, password);


                        } else {

                            Log.w("FAIL====", "createUserWithEmail:failure", task.getException());

                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                showToast("User already registered");
                                return;
                            }
                            showToast("Oops...Something went wrong!!");

                        }

                    }
                });
    }

    private void generateUserToken(final String name, final String email, final String password) {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        insertData(token, name, email, password);

                        Log.d("FCM TOKEN ==== ", token);
                    }
                });

    }

    private void insertData(String token, String name, String email, String password) {

        String user_id = mauth.getCurrentUser().getUid();
        Users users = new Users(name, email, password, user_id, token);
        myRef.child(user_id).setValue(users);
        showToast("Registered!!");

    }

    private void openActivity(Class aclass) {
        startActivity(new Intent(this, aclass));
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}
