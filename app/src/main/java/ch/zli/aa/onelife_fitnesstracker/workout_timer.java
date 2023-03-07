package ch.zli.aa.onelife_fitnesstracker;
/**
 * Hilfsmittel: Beispiele auf ZLI
 * ChatGPT
 * Youtube
 * TODO: Vibration
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import android.os.Vibrator;

public class workout_timer extends FragmentActivity implements SensorEventListener {
    private String TAG = "workout_timer";
    private Chronometer chronometer;
    private boolean running;
    private boolean started;
    private Button start_stop;
    private Button reset;
    private Button finishWorkout;
    private long pauseOffset;
    private int currentTime;
    public List<String> workouts;
    private TextView workoutTitle;
    private Date startTime;
    private Date finishTime;
    private ProgressBar progressBar;

    private Vibrator vibrator;

    private final static int SAMPLING_RATE = 100000 ;
    private final static int DISPLAY_PERIOD = 30;
    private final static int CHART_ENTRIES_LIMIT = SAMPLING_RATE / 10000 * DISPLAY_PERIOD;

    private SensorManager sensorManager;
    private Sensor sensor;

    private TextView xAxisValue;
    private TextView yAxisValue;
    private TextView zAxisValue;

    private LineChart chart;
    private int time;

    private LineDataSet xAxisLineData = new LineDataSet(new LinkedList<>(), "x");
    private LineDataSet yAxisLineData = new LineDataSet(new LinkedList<>(), "y");
    private LineDataSet zAxisLineData = new LineDataSet(new LinkedList<>(), "z");




    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_timer);
        SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        chronometer = (Chronometer) findViewById(R.id.timer);


        start_stop = (Button) findViewById(R.id.btnStart_stop);
        reset = (Button) findViewById(R.id.btnReset);
        finishWorkout = (Button) findViewById(R.id.btnFinish);
        workoutTitle = (TextView) findViewById(R.id.tvWorkoutList);
        progressBar = findViewById(R.id.circleProgress);
        running = false;
        started = false;
        long elapsedMillis = SystemClock.elapsedRealtime()
                - chronometer.getBase();

        workouts = (List<String>) getIntent().getSerializableExtra("Workout");
        Log.e(TAG, Arrays.toString(workouts.toArray()));
        String workouttitle = Arrays.toString(workouts.toArray());
        workoutTitle.setText(workouttitle);

        start_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStopTimer(v, dtf);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer(v);
            }
        });
        finishWorkout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                vibrator.vibrate(1000);
                finishWorkout(v);

            }
        });

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                Log.i(TAG, String.valueOf(SystemClock.elapsedRealtime() - chronometer.getBase()));
                long elapsedMillis = SystemClock.elapsedRealtime()
                        - chronometer.getBase();
                Log.e(TAG, String.valueOf((Math.round(elapsedMillis/1000))));
                int timerSeconds = Math.round(elapsedMillis/1000);
                int finalTimerSeconds = 0;
                if(timerSeconds > 60){
                    finalTimerSeconds = timerSeconds % 60;
                } else {
                    finalTimerSeconds = timerSeconds;
                }

                Log.e(TAG, String.valueOf(finalTimerSeconds * 1.666));
                progressBar.setProgress((int) Math.round(finalTimerSeconds * 1.666));
            }
        });
        configureLineDataSets();
        findViews();
        initializeSensor();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void startStopTimer(View view, SimpleDateFormat dtf){
        if(!running && !started){

            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            running = true;
            start_stop.setText("Stop");
            start_stop.setBackgroundTintList(getResources().getColorStateList(R.color.pastelred));
            started = true;
            startTime = new Date();
            Date st = startTime;
            Log.i(TAG, String.valueOf(st));
        }else if(!running && started) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
            start_stop.setText("Stop");
            start_stop.setBackgroundTintList(getResources().getColorStateList(R.color.pastelred));
        }else if(running){  //stops the timer
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
            long elapsedMili = SystemClock.elapsedRealtime()
                    - chronometer.getBase();
            currentTime = Math.round(elapsedMili/1000);
            start_stop.setText("start");
            start_stop.setBackgroundTintList(getResources().getColorStateList(R.color.pastelgreen));

        }
    }


    public void resetTimer(View view){
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }

    public void finishWorkout(View view){

        int seconds;
        if(!running){
            seconds = currentTime;
        }else{

            long elapsedMillis = SystemClock.elapsedRealtime()
                    - chronometer.getBase();
            seconds = Math.round(elapsedMillis/1000);

            Log.e("workout_timer", String.valueOf(seconds));

        }

        if(seconds == 0){
            Toast.makeText(this, "Workout Not Started!", Toast.LENGTH_SHORT).show();
            return;
        }

        finishTime = new Date();
        Log.i(TAG, String.valueOf(finishTime));

        Intent i = new Intent(this, workout_summary.class);
        i.putExtra("Workout2", (Serializable) workouts);
        i.putExtra("finalTime", seconds);
        i.putExtra("startTime",(Serializable) startTime);
        i.putExtra("finishTime",(Serializable) finishTime);
        startActivity(i);
        Animatoo.INSTANCE.animateSlideLeft(this);

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Animatoo.INSTANCE.animateSlideRight(this);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        setAxisValues(event.values);
        addValuesToChart(event.values);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // no need for this
    }

    private void configureLineDataSets() {
        xAxisLineData.setLabel(getString(R.string.x_achse));
        xAxisLineData.setCircleColor(Color.RED);
        xAxisLineData.setColor(Color.RED);
        yAxisLineData.setLabel(getString(R.string.y_achse));
        yAxisLineData.setCircleColor(Color.GREEN);
        yAxisLineData.setColor(Color.GREEN);
        zAxisLineData.setLabel(getString(R.string.z_achse));
        zAxisLineData.setCircleColor(Color.BLUE);
        zAxisLineData.setColor(Color.BLUE);
    }

    private void findViews() {
        this.xAxisValue = findViewById(R.id.xAxisValue);
        this.yAxisValue = findViewById(R.id.yAxisValue);
        this.zAxisValue = findViewById(R.id.zAxisValue);
        this.chart = findViewById(R.id.chart);
    }

    private void initializeSensor() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SAMPLING_RATE);
    }

    private void setAxisValues(float[] values) {
        xAxisValue.setText(String.valueOf(values[0]));
        yAxisValue.setText(String.valueOf(values[1]));
        zAxisValue.setText(String.valueOf(values[2]));
    }

    private void addValuesToChart(float[] values) {
        time++;

        xAxisLineData.addEntry(new Entry(time, values[0]));
        yAxisLineData.addEntry(new Entry(time, values[1]));
        zAxisLineData.addEntry(new Entry(time, values[2]));

        if(xAxisLineData.getEntryCount() >= CHART_ENTRIES_LIMIT) {
            xAxisLineData.removeFirst();
        }
        if(yAxisLineData.getEntryCount() >= CHART_ENTRIES_LIMIT) {
            yAxisLineData.removeFirst();
        }
        if(zAxisLineData.getEntryCount() >= CHART_ENTRIES_LIMIT) {
            zAxisLineData.removeFirst();
        }

        List<ILineDataSet> lineDataSets = new ArrayList<>();
        lineDataSets.add(xAxisLineData);
        lineDataSets.add(yAxisLineData);
        lineDataSets.add(zAxisLineData);
        LineData lineData = new LineData(lineDataSets);
        chart.setData(lineData);
        chart.invalidate();
    }
}

