package ch.zli.aa.onelife_fitnesstracker;

import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.List;

import ch.zli.aa.onelife_fitnesstracker.fragments.HomeFragment;


public class HomeWorkoutListAdapter extends RecyclerView.Adapter<HomeWorkoutListAdapter.ViewHolder> {

    private List<String> workout;
    private List<String> time;
    private List<String> indivCalor;
    private HomeFragment activity;

    public HomeWorkoutListAdapter(){

    }

    public HomeWorkoutListAdapter(List<String> w, List<String> t, List<String> i, HomeFragment fragment){
        this.workout = w;
        this.time = t;
        this.indivCalor = i;
        this.activity = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_workout, parent, false);
        RecyclerView rc;
        rc = parent.findViewById(R.id.rvWorkouts);
        rc.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(parent.getContext(), R.anim.layout_fade_animation));

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(workout.get(position), time.get(position), indivCalor.get(position));
    }

    @Override
    public int getItemCount() {
        return workout.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvWorkout;
        public TextView time;
        public TextView calo;
        public CardView cardView;
        public RecyclerView rc;

        public ViewHolder(View itemView) {
            super(itemView);
            tvWorkout = itemView.findViewById(R.id.tvWorkoutHome);
            time = itemView.findViewById(R.id.tvTimeHome);
            calo = itemView.findViewById(R.id.tvIndivCalories);
            cardView = itemView.findViewById(R.id.cardWorkoutHome);
            rc = itemView.findViewById(R.id.rvWorkouts);
        }

        public void bind(String wrk, String t, String c){
            tvWorkout.setText(wrk);
            time.setText(t);
            calo.setText(c);
            cardView.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(itemView.getContext(), R.anim.layout_fade_animation));

        }

    }
}
