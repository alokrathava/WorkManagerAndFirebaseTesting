package com.example.workmanagerandfirebasetesting.Alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.workmanagerandfirebasetesting.R;

public class Main2Activity extends AppCompatActivity {
    Button start;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        start = findViewById(R.id.button);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAlert();
            }
        });

        startAlert();
    }

    public void startAlert() {


//        EditText text = findViewById(R.id.time);
//        int i = Integer.parseInt(text.getText().toString());
        Intent intent = new Intent(this, MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


//        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                SystemClock.elapsedRealtime(),
//                (1000 * 60 * 2), pendingIntent);

//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, 11);
//        calendar.set(Calendar.MINUTE, 3);
//        calendar.set(Calendar.SECOND, 50);
//
//        if(calendar.before(Calendar.getInstance())) {
//            calendar.add(Calendar.DATE, 1);
//        }

//        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (i * 1000), pendingIntent);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//        Toast.makeText(this, "Alarm set in " + i + " seconds", Toast.LENGTH_LONG).show();


        /* Set the alarm to start at 10:30 AM */

//
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), (1000 * 60 * 2), pendingIntent);  // Not Repeating -- Works Once only
//
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                2000, pendingIntent);
//        Toast.makeText(this, "Repeating Alarm set in " + i + " seconds", Toast.LENGTH_LONG).show();
    }


}
