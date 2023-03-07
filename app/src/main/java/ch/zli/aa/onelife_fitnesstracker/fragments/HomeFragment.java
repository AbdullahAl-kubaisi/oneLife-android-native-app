package ch.zli.aa.onelife_fitnesstracker.fragments;
/*
* Hilfsmittel: https://developer.android.com/guide/fragments
* ChatGPT gefragt bei einer Fehlermeldung
* Stackoverflow
* */
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import ch.zli.aa.onelife_fitnesstracker.DateSummary;
import ch.zli.aa.onelife_fitnesstracker.HomeWorkoutListAdapter;
import ch.zli.aa.onelife_fitnesstracker.R;
import ch.zli.aa.onelife_fitnesstracker.Workout;


public class HomeFragment extends Fragment {

    TextView tvUserName;
    TextView tvDate;
    TextView tvActivity;
    TextView tvCalories;
    RecyclerView rvWorkouts;
    private String TAG = "HomeFragment";

    List<String> workout;
    List<String> listTime;
    List<String> indivCalo;
    HomeWorkoutListAdapter adapter;
    private Double totalCal;
    private int totalTime;
    private static final DecimalFormat df2 = new DecimalFormat("#.##");



    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tvUserName = (TextView) view.findViewById(R.id.tvUserName);
        tvDate = (TextView) view.findViewById(R.id.tvDate);
        tvCalories = (TextView) view.findViewById(R.id.tvCalories);
        tvActivity = (TextView) view.findViewById(R.id.tvActivity);
        getCurrentUser();
        workout = new ArrayList<>();
        listTime = new ArrayList<>();
        indivCalo = new ArrayList<>();
        totalCal = 0.0;

        rvWorkouts = (RecyclerView) view.findViewById(R.id.rvWorkouts);
        adapter = new HomeWorkoutListAdapter(workout, listTime, indivCalo,this);
        rvWorkouts.setLayoutManager(new LinearLayoutManager(getContext()));
        rvWorkouts.setAdapter(adapter);

        populateWorkout();


        tvCalories.setText(String.valueOf(df2.format(totalCal)));
        Log.e(TAG, String.valueOf(workout.size()));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void getCurrentUser() {
        // After login, Parse will cache it on disk, don't need to login every time we open it
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
        } else {
        }
        this.tvUserName.setText("Hello " + (currentUser.getUsername()));
        this.tvDate.setText(DateSummary.getDate());
    }

    public void populateWorkout() {
        ParseQuery<Workout> query = ParseQuery.getQuery("Workout");
        query.include(Workout.KEY_USER);
        query.whereEqualTo(Workout.KEY_USER, ParseUser.getCurrentUser());
        query.setLimit(20);
        query.findInBackground(new FindCallback<Workout>() {
            @Override
            public void done(List<Workout> objects, ParseException e) {
                int min = 0;
                int sec = 0;
                if(e != null){
                    Log.e(TAG, "Error populating workouts", e);
                    return;
                }
                for(Workout w: objects){
                    workout.add((String) w.get("WorkoutType"));
                    listTime.add((String) w.get("duration"));
                    indivCalo.add(String.valueOf(w.get("calories")));
                    totalCal += ((Double) w.get("calories"));

                    String temp = (String) w.get("duration");
                    Log.i(TAG, "duration: " + temp);
                    String[] time = temp.split(":");
                    min = Integer.parseInt(time[0]);
                    sec = Integer.parseInt(time[1]);
                    totalTime += (min * 60) + sec;
                    Log.i(TAG, "Time: " + String.valueOf(totalTime));
                }

                Log.e(TAG, String.valueOf(totalCal));
                tvCalories.setText(String.valueOf(df2.format(totalCal)));
                String finalSec = "";
                if((totalTime % 60) < 9){
                    finalSec = "0" + String.valueOf(totalTime % 60);
                } else {
                    finalSec = String.valueOf(totalTime % 60);
                }

                String finalTime = String.valueOf(totalTime / 60) + ":" + String.valueOf(finalSec);
                tvActivity.setText(finalTime);

                adapter.notifyDataSetChanged();
            }
        });

    }

}