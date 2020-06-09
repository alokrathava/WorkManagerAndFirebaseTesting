package com.example.workmanagerandfirebasetesting.Alarms;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.workmanagerandfirebasetesting.Config;
import com.example.workmanagerandfirebasetesting.Networking.Api;
import com.example.workmanagerandfirebasetesting.Networking.AppConfig;
import com.example.workmanagerandfirebasetesting.Networking.Response;
import com.example.workmanagerandfirebasetesting.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        Toast.makeText(context, "Alarm....", Toast.LENGTH_LONG).show();

//        doInsert(context);

//        Log.d("Receiver ========", String.valueOf(Config.a));
//        doInsert(context);
        Config.a += 1;
        displayNotification("From Alarm Manager", "Round - " + Config.a, context);
    }

    private void doInsert(final Context context) {

        class DoInsert extends AsyncTask<String, String, String> {


            @Override
            protected String doInBackground(String... strings) {

                Retrofit retrofit = AppConfig.getRetrofit();
                Api service = retrofit.create(Api.class);

                String url = "http://192.168.0.112/BloodBank/api/Api.php?apicall=just_test";
                Call<Response> call = service.JustTest(url);
                call.enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                        Response response1 = response.body();
                        Log.d("RESPONSE ======== ", response1.getMessage());

                        displayNotification("From Alarm Manager", "Data Inserted Successfully", context);

                        return;
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {

                        Config.showToast(context, t.getMessage());
                        Log.d("RESPONSE ======== ", t.getMessage());
                    }
                });


                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                Config.showToast(context, "Success");
            }
        }
        new DoInsert().execute();


    }


    private void displayNotification(String title, String msg, Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channel_id", "channel_name", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        Intent notificationIntent = new Intent(context, Main2Activity.class);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 111, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, "channel_id")
                .setContentTitle(title)
                .setContentText(msg)
                .setContentIntent(contentIntent)
                .setSmallIcon(R.mipmap.ic_launcher);

        notificationManager.notify(1, notification.build());
    }


}
