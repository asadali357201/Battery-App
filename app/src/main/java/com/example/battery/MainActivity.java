package com.example.battery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver br=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level=intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
            ProgressBar pb=findViewById(R.id.progressBar1);
            TextView tv=findViewById(R.id.textView1);

            pb.setProgress(level);
            tv.setText(String.valueOf(level));
            if(level==100)
            {
                try
                {
                    //Save small.mp4 in assets folder
                    //we can not start a media file from the drawable folder directly in broadcast method
                    //hence we used the assets folder
                    AssetFileDescriptor afd=getAssets().openFd("clip.mp3");
                    MediaPlayer mp=new MediaPlayer();
                    //set file and starting point and ending point in bytes
                    mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                    mp.prepare();
                    //start song
                    mp.start();
                }
                catch(IOException e){}
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       this.registerReceiver(this.br,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }
}