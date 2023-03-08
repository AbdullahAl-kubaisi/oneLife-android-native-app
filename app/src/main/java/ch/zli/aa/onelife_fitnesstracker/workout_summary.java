package ch.zli.aa.onelife_fitnesstracker;

import androidx.fragment.app.FragmentActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.parse.ParseUser;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

public class workout_summary extends FragmentActivity {

    public String TAG = "workout_summary";
    private TextView workoutType;
    private TextView timeDisplay;
    private TextView caloriesDisplay;
    private Button close;
    public List<String> workouts;
    public double calories;
    private int numOfActivities;
    private Date startTimeDate;
    private Date finishTimeDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_summary);

        workoutType = (TextView) findViewById(R.id.tvWorkout_summary);
        timeDisplay = (TextView) findViewById(R.id.tvWorkoutTimeSummary);
        caloriesDisplay = (TextView) findViewById(R.id.tvCaloriesDisplay);
        workouts = (List<String>) getIntent().getSerializableExtra("Workout2");
        numOfActivities = workouts.size();

        String workoutTypeString = createWorkoutString(workouts);
        workoutType.setText(workoutTypeString);

        int finalTime = (int) getIntent().getIntExtra("finalTime", 0);
        String time = createTimerString(finalTime);
        timeDisplay.setText(String.valueOf(time));

        calories = calculateCalories(finalTime, numOfActivities);
        caloriesDisplay.setText(String.valueOf(calories));

        startTimeDate = (Date) getIntent().getSerializableExtra("startTime");
        finishTimeDate = (Date) getIntent().getSerializableExtra("finishTime");
        Log.e(TAG, String.valueOf(startTimeDate));
        Log.e(TAG, String.valueOf(finishTimeDate));

        close = (Button) findViewById(R.id.btnFinish);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitWorkout(time, workoutTypeString);
                Intent i = new Intent(workout_summary.this, MainActivity.class);
                startActivity(i);
                Animatoo.INSTANCE.animateFade(workout_summary.this);
            }
        });

    }

    private void submitWorkout(String time, String workoutTypeString) {
        Workout workout = new Workout();
        workout.setStartDate(startTimeDate);
        workout.setKeyEnd(finishTimeDate);
        workout.setCalories(calories);
        workout.setDuration(time);
        workout.setWorkoutType(workoutTypeString);
        workout.setKeyUser(ParseUser.getCurrentUser());

        workout.saveInBackground((e) ->{
            if(e != null){
                Log.e(TAG, "ERROR BY SAVING");
            } else {
                Log.i(TAG, "Save Success");
            }
        });

    }

    public String createWorkoutString(List<String> workouts){
        String workoutTypeString = "";

        for(int i = 0; i < workouts.size(); i++){
            if(!(i < workouts.size())){
                workoutTypeString = workoutTypeString + workouts.get(i);
            } else if(i == 0) {
                workoutTypeString = workouts.get(i);
            } else {
                workoutTypeString = workoutTypeString + ", " + workouts.get(i);
            }
        }
        return workoutTypeString;
    }

    public String createTimerString(int finalTime){

        int min = 0;
        int sec = 0;

        String time = "";
        String sec_str = "";

        if(finalTime >= 60){
            min = finalTime / 60;
            sec = finalTime % 60;
            if(sec < 10){
                sec_str = "0" + String.valueOf(sec);
            } else {
                sec_str = String.valueOf(sec);
            }

        } else {
            if(finalTime < 10){
                sec_str = "0" + String.valueOf(finalTime);
            } else {
                sec_str = String.valueOf(finalTime);
            }
        }
        time = String.valueOf(min) + ":" + sec_str;
        return time;
    }

    private double calculateCalories(int finalTime, int numOfActivities){

        ParseUser user = ParseUser.getCurrentUser();
        Integer w = (Integer) user.get("weight");


        double cal = 0.0;
        int MET = 0;
        switch (numOfActivities){
            case 1:
                MET =  4;
                break;
            case 2:
                MET = 5;
                break;
            case 3:
                MET = 7;
                break;
            case 4:
                MET = 8;
                break;
            case 5:
                MET = 8;
                break;
            default:
                return 0;
        }

        double time = ((double) finalTime) / ((double) 60);
        double kgConv = ((double) w/2.2046);

        cal = (time)*((double) MET*3.5*kgConv)/200;

        BigDecimal bd = new BigDecimal(cal).setScale(2, RoundingMode.HALF_UP);
        cal = bd.doubleValue();

        return cal;
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Animatoo.INSTANCE.animateSlideRight(this);
    }
}
