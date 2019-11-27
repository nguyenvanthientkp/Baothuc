package com.example.baothuc;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button btnAdd, btnClose;
    TextView tvhienthi;
    TimePicker timepicker;
    Calendar calendar;
    AlarmReceiver alarmReceiver;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    @SuppressLint("ServiceCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd=(Button)findViewById(R.id.btn_add);
        btnClose=(Button)findViewById(R.id.btn_close);
        tvhienthi=(TextView)findViewById(R.id.tv_hienthi);
        timepicker=(TimePicker)findViewById(R.id.timePicker);
        calendar=Calendar.getInstance();
        alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);

        final Intent intent= new Intent(MainActivity.this,AlarmReceiver.class);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.set(Calendar.HOUR_OF_DAY,timepicker.getCurrentHour());
                calendar.set(Calendar.MINUTE,timepicker.getCurrentMinute());

                int gio=timepicker.getCurrentHour();
                int phut=timepicker.getCurrentMinute();

                String string_gio= String.valueOf(gio);
                String string_phut= String.valueOf(phut);

                if(gio<10) string_gio="0" + String.valueOf(gio);
                if(phut<10) string_phut="0" + String.valueOf(phut);

                intent.putExtra("extra","on");
                pendingIntent=PendingIntent.getBroadcast(
                        MainActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT
                );

                alarmManager.set(
                        AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent
                );

                tvhienthi.setText("Gio da dat la "+string_gio+":"+string_phut);
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvhienthi.setText("Dung lai");
                alarmManager.cancel(pendingIntent);
                intent.putExtra("extra","off");
                sendBroadcast(intent);
            }
        });
    }
}
