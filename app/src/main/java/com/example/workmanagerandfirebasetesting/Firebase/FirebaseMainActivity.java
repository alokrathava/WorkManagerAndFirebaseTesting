package com.example.workmanagerandfirebasetesting.Firebase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.workmanagerandfirebasetesting.Adapters.DisplayMessageAdapter;
import com.example.workmanagerandfirebasetesting.Config;
import com.example.workmanagerandfirebasetesting.Interface.RecyclerUserListClickListener;
import com.example.workmanagerandfirebasetesting.Model.FCMResponse;
import com.example.workmanagerandfirebasetesting.Model.MessageModel;
import com.example.workmanagerandfirebasetesting.Model.RequestNotificaton;
import com.example.workmanagerandfirebasetesting.Model.SendNotificationModel;
import com.example.workmanagerandfirebasetesting.Networking.ApiClient;
import com.example.workmanagerandfirebasetesting.Networking.ApiInterface;
import com.example.workmanagerandfirebasetesting.R;
import com.example.workmanagerandfirebasetesting.databinding.ActivityFirebaseMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;

public class FirebaseMainActivity extends AppCompatActivity implements RecyclerUserListClickListener {

    private ActivityFirebaseMainBinding binding;
    private Context context = this;

    private String receiver_id;

    private DatabaseReference myRef;
    private String token, name;

    private DisplayMessageAdapter adapter;
    private List<MessageModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_main);

        binding = ActivityFirebaseMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        myRef = FirebaseDatabase.getInstance().getReference("messages");
        adapter = new DisplayMessageAdapter(list, context, this);
        binding.mainRecyclerView.setAdapter(adapter);

        getDataFromIntent();
        clickListeners();


    }


    private void getDataFromIntent() {
        Intent intent = getIntent();
        receiver_id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        token = intent.getStringExtra("token");

    }

    private void clickListeners() {

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String msg = binding.edtMsg.getText().toString().trim();

                if (TextUtils.isEmpty(msg)) {
                    binding.edtMsg.setError("Required");
                    return;
                }


                sendMessage(msg, Config.user_id, receiver_id);
                binding.edtMsg.setText(null);

                sendNotificationToPatner(name, msg);

            }
        });
    }

    private void sendMessage(String msg, String sender_id, String receiver_id) {

        MessageModel message = new MessageModel(sender_id, receiver_id, msg);
        myRef.push().setValue(message);
    }


    private void sendNotificationToPatner(String name, String msg) {

        SendNotificationModel sendNotificationModel = new SendNotificationModel(msg, name);
        RequestNotificaton requestNotificaton = new RequestNotificaton(token, sendNotificationModel);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        retrofit2.Call<FCMResponse> responseBodyCall = apiService.sendChatNotification(requestNotificaton);

        responseBodyCall.enqueue(new Callback<FCMResponse>() {
            @Override
            public void onResponse(retrofit2.Call<FCMResponse> call, retrofit2.Response<FCMResponse> response) {
                Log.d("DREAM SUCCESS == ", "Well notification has been sent");

                FCMResponse fcmResponse = response.body();

                if (fcmResponse.getSuccess().equals("1")) {
//                    Config.showToast(context, "Success");

                } else if (fcmResponse.getFailure().equals("1")) {
                    Config.showToast(context, "Failed");
                }

            }

            @Override
            public void onFailure(retrofit2.Call<FCMResponse> call, Throwable t) {

                t.getMessage();
                t.getLocalizedMessage();
                Config.showToast(context, t.getMessage());
            }
        });
    }


    private void openActivity(Class aclass) {
        startActivity(new Intent(context, aclass));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    protected void onStart() {
        super.onStart();

//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("messages");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list.clear();
                for (DataSnapshot messageList : dataSnapshot.getChildren()) {


                    MessageModel messageModel = messageList.getValue(MessageModel.class);

                    if ((messageModel.getSender_id().equals(Config.user_id) && messageModel.getReceiver_id().equals(receiver_id)) || (messageModel.getSender_id().equals(receiver_id) && messageModel.getReceiver_id().equals(Config.user_id))) {
                        list.add(messageModel);
                    }

                }

                adapter.notifyDataSetChanged();
                binding.mainRecyclerView.scrollToPosition(adapter.getItemCount() - 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(int position) {
        MessageModel messageModel = list.get(position);
    }
}
