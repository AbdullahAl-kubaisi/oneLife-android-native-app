package ch.zli.aa.onelife_fitnesstracker.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.zli.aa.onelife_fitnesstracker.R;
import ch.zli.aa.onelife_fitnesstracker.WorkoutListAdapter;
import ch.zli.aa.onelife_fitnesstracker.workout_timer;


public class TrackFragment extends Fragment {

    public static final String TAG = "TrackFragment";
    private RecyclerView rvWorkout;
    private WorkoutListAdapter workoutListAdapter;
    List<String> workouts;
    List<String> selected;

    private Button startButton;

    public TrackFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_track, container, false);
        rvWorkout = (RecyclerView) view.findViewById(R.id.rvWorkout);
        CardView cardView = (CardView) view.findViewById(R.id.cardWorkoutHome);

        startButton = (Button) view.findViewById(R.id.btnStart);
        workoutListAdapter = new WorkoutListAdapter(workouts, this);

        startButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                selected = workoutListAdapter.getChecked();
                Log.i(TAG, Arrays.toString(selected.toArray()));
                if(selected.size() > 0){
                    Intent i = new Intent(getActivity(), workout_timer.class);
                    i.putExtra("Workout", (Serializable) selected);
                    startActivity(i);
                    Animatoo.INSTANCE.animateSlideLeft(getContext());
                }else {
                    Toast.makeText(getContext(), "No workout is selected!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Context context = view.getContext();
        //rvWorkout.setLayoutManager(new LinearLayoutManager(context));
        rvWorkout.setLayoutManager(new LinearLayoutManager(context));
        workoutListAdapter = new WorkoutListAdapter(workouts, this);
        rvWorkout.setAdapter(workoutListAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        workouts = new ArrayList<>();
        workouts.add("General");
        workouts.add("Strength Training");
        workouts.add("Run");
        workouts.add("Walk");
        workouts.add("Yoga");

        workoutListAdapter.setWorkouts(workouts);
    }

}