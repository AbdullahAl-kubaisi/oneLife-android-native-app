package ch.zli.aa.onelife_fitnesstracker;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import ch.zli.aa.onelife_fitnesstracker.fragments.TrackFragment;


public class WorkoutListAdapter extends RecyclerView.Adapter<WorkoutListAdapter.ViewHolder> {

    public interface OnClickListener{
        void onItemClicked(int position);
    }

    private List<String> workouts;
    private List<String> checked;
    private TrackFragment activity;

    OnClickListener clickListener;

    public WorkoutListAdapter(List<String> list, TrackFragment trackFragment){
        this.workouts = list;
        this.activity = trackFragment;
        checked = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.workout_task_layout, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String item = workouts.get(position);
        holder.task.setText(item);
        holder.task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.task.isChecked()){
                    Log.i("WorkoutListAdapter", holder.task.getText().toString());
                    checked.add(holder.task.getText().toString());
                } else if(!holder.task.isChecked()){
                    if(checked.contains(holder.task.getText().toString())){
                        Log.i("WorkoutListAdapter", holder.task.getText().toString() + " Is unchecked");
                        checked.remove(holder.task.getText().toString());
                    }
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }

    public void setWorkouts(List<String> workoutList){
        this.workouts = workoutList;
        this.notifyDataSetChanged();
    }


    public List<String> getChecked(){
        Log.i("WorkoutListAdapter", Arrays.toString(checked.toArray()));
        return checked;
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        public CheckBox task;
        private Context context;

        public ViewHolder(View view){
            super(view);
            task = (CheckBox) view.findViewById(R.id.checkbox);
        }

        public void bind(String item) {
            task.setText(item);
            task.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClicked(getAdapterPosition());

                }
            });
        }
    }
}
