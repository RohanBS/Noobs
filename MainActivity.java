package com.example.rohanbs.soundtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.os.Handler;
import android.util.Log;
import android.media.MediaRecorder;

public class MainActivity extends AppCompatActivity {

    TextView mStatusView;
    Thread runner;
    private static double mEMA = 0.0;
    static final private double EMA_FILTER = 0.6;

    static String decider="0";
    private dbRunnable newdb = new dbRunnable();
    MediaRecorder recorder;

    class dbRunnable implements Runnable{

        @Override
        public void run(){
            Log.i("Runnable","called");
            Log.i("decider",decider);
            while(true){
                Log.i("Runnable","called");//updateTv();
                if(decider=="1"){
                    break;
                }
            }
        }

    }
final Handler mHandler = new Handler();


    public void startRecord(View view) {
        Log.i("Inside", "start record");
        if (recorder == null) {
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.setOutputFile("/dev/null");
            try {
                recorder.prepare();
            } catch (java.io.IOException ioe) {
                android.util.Log.e("[Monkey]", "IOException: " +
                        android.util.Log.getStackTraceString(ioe));

            } catch (java.lang.SecurityException e) {
                android.util.Log.e("[Monkey]", "SecurityException: " +
                        android.util.Log.getStackTraceString(e));
            }
            try {
                recorder.start();
                Thread dec1 = new Thread(newdb);
                dec1.start();
                //mHandler.post(updater);
            } catch (java.lang.SecurityException e) {
                android.util.Log.e("[Monkey]", "SecurityException: " +
                        android.util.Log.getStackTraceString(e));
            }
        }
    }
    public void stopRecord(View view){
        if (recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null;
        }

        decider="1";

        Log.i("Hello","record complete");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
