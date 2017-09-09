package com.example.rohanbs.decibeltest;
import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity {

    TextView screenshow;
    MediaRecorder rec;

    private static double mEMA = 0.0;
    static final private double EMA_FILTER = 0.6;
    Thread decicalc;

    final Runnable updater = new Runnable(){
        public void run(){
            printDec();
        }
    };
    final Handler mHandler = new Handler();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("onCreate","reached");
        setContentView(R.layout.activity_main);
        screenshow = (TextView) findViewById(R.id.);
        if (decicalc == null)
        {
            decicalc = new Thread(){
                public void run()
                {
                    while (decicalc != null)
                    {
                        try
                        {
                            Log.i("Calling", "runnable");
                            Thread.sleep(200);
                        } catch (InterruptedException e) { };
                        mHandler.post(updater);
                    }
                }
            };

            decicalc.start();   //start the runnable's run method
        }
    }

    public void onResume()
    {
        super.onResume();
        Log.i("Starting","recorder");
        startRecord();
    }

    public void onPause()
    {
        super.onPause();
        stopRecord();
    }

    public void startRecord(){
        if (rec == null)
        {
            rec = new MediaRecorder();
            rec.setAudioSource(MediaRecorder.AudioSource.MIC);
            rec.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            rec.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            rec.setOutputFile("/dev/null");
            try
            {
                rec.prepare();
            }catch (java.io.IOException ioe) {
                android.util.Log.e("[Monkey]", "IOException: " +
                        android.util.Log.getStackTraceString(ioe));

            }catch (java.lang.SecurityException e) {
                android.util.Log.e("[Monkey]", "SecurityException: " +
                        android.util.Log.getStackTraceString(e));
            }
            try
            {
                rec.start();
            }catch (java.lang.SecurityException e) {
                android.util.Log.e("[Monkey]", "SecurityException: " +
                        android.util.Log.getStackTraceString(e));
            }

        }

    }
    public void stopRecord() {
        if (rec != null) {
            rec.stop();
            rec.release();
            rec = null;
        }
    }

    public void printDec(){
        screenshow.setText(Double.toString((decibelconverter())) + " dB");   //Print decibel value onto screen
    }

    public double decibelconverter() {
        double amplitude =  0;          //set amplitude to 0 initially
        if (rec!=null)
            amplitude = (rec.getMaxAmplitude());     //if sound is being recorded, getMaxAmplitude

        mEMA = EMA_FILTER * amplitude + (1.0 - EMA_FILTER) * mEMA;  //Formula for converting amplitude to decibels
        return mEMA/100;
    }

}
