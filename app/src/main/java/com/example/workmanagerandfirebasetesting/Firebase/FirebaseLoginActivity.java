package com.example.workmanagerandfirebasetesting.Firebase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.workmanagerandfirebasetesting.Config;
import com.example.workmanagerandfirebasetesting.R;
import com.example.workmanagerandfirebasetesting.databinding.ActivityFirebaseLoginBinding;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class FirebaseLoginActivity extends AppCompatActivity {

    ActivityFirebaseLoginBinding binding;

    private List<AuthUI.IdpConfig> providers;

    private int RC_SIGN_IN = 101;

    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFirebaseLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            openActivity(FirebaseUserListActivity.class);
            finish();
        }

    }

    private void loginProviders() {

        providers = Arrays.asList(
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.EmailBuilder().build()

        );
        showSignInOptions();
    }

    private void showSignInOptions() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.MyTheme)
                        .setLogo(R.mipmap.work_icon)
                        .build(), RC_SIGN_IN
        );
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Config.showToast(context, "Welcome " + user.getDisplayName());
                openActivity(FirebaseUserListActivity.class);
                finish();

            } else {

                Config.showToast(context, response.getError().getMessage());
            }
        }
    }

    private void openActivity(Class aclass) {
        startActivity(new Intent(context, aclass));
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void doLogin(View view) {

        loginProviders();

    }
}
