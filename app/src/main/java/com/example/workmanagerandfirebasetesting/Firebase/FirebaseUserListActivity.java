package com.example.workmanagerandfirebasetesting.Firebase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.workmanagerandfirebasetesting.Adapters.UserListAdapter;
import com.example.workmanagerandfirebasetesting.Config;
import com.example.workmanagerandfirebasetesting.Interface.RecyclerUserListClickListener;
import com.example.workmanagerandfirebasetesting.Model.Users;
import com.example.workmanagerandfirebasetesting.R;
import com.example.workmanagerandfirebasetesting.databinding.ActivityFirebaseUserListBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FirebaseUserListActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener, RecyclerUserListClickListener {

    private ActivityFirebaseUserListBinding binding;
    private UserListAdapter userListAdapter;
    private Context context = this;
    private List<Users> usersList = new ArrayList<>();
    private FirebaseAuth firebaseAuth;
    private DatabaseReference myRef;

    private String user_token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFirebaseUserListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        myRef = FirebaseDatabase.getInstance().getReference("users");

        initRecyclerView();

    }

    private void initRecyclerView() {

        userListAdapter = new UserListAdapter(usersList, context, this);
        binding.userListRecyclerView.setAdapter(userListAdapter);
    }

    private void openActivity(Class aclass) {
        startActivity(new Intent(context, aclass));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.logout:

                firebaseAuth.signOut();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.addAuthStateListener(this);

        getUserToken();
        Config.user_id = firebaseAuth.getCurrentUser().getUid();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                usersList.clear();
                for (DataSnapshot userList : dataSnapshot.getChildren()) {


                    Users users = userList.getValue(Users.class);

                    if (!users.getUserId().equals(Config.user_id)) {
                        usersList.add(users);
                    }

                }

                userListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getUserToken() {

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        Log.d("FCM TOKEN ==== ", token);

                        updateUser(token);
                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();

        firebaseAuth.removeAuthStateListener(this);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

        if (firebaseAuth.getCurrentUser() == null) {
            Config.user_id = null;
            openActivity(Login.class);
            finish();
            return;
        }

        firebaseAuth.getCurrentUser().getIdToken(true).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
            @Override
            public void onSuccess(GetTokenResult getTokenResult) {

                Log.d("TOKEN ======= ", getTokenResult.getToken());
            }
        });
    }


    @Override
    public void onClick(int position) {
        Users users = usersList.get(position);
//        Config.showToast(context, users.getToken());

        Intent intent = new Intent(context, FirebaseMainActivity.class);
        intent.putExtra("id", users.getUserId());
        intent.putExtra("name", users.getUserName());
        intent.putExtra("token", users.getToken());
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    private void updateUser(String token) {

//        Config.showToast(context, token);
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", token);
        myRef.child(Config.user_id).updateChildren(map);
    }
}
