package ch.zli.aa.onelife_fitnesstracker.fragments;
/**
 * Hilfsmittel: https://www.youtube.com/watch?v=W4hTJybfU7s
 *
 * **/
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ch.zli.aa.onelife_fitnesstracker.R;
import ch.zli.aa.onelife_fitnesstracker.adapters.LearnListAdapter;


public class LearnFragment extends Fragment {

    ListView listView;
    RecyclerView rvLearningWorkout;
    CardView cardView;
    LearnListAdapter learnListAdapter;

    public LearnFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_learn, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Collection collection;
        List categories = new ArrayList<String>();
        categories.add("Before Workout Stretches");
        categories.add("Yoga");
        categories.add("Pilates");


        rvLearningWorkout = (RecyclerView) view.findViewById(R.id.rvLearnWorkout);
        cardView = (CardView) view.findViewById(R.id.card_learn_item);

        rvLearningWorkout.setLayoutManager(new LinearLayoutManager(getContext()));
        learnListAdapter = new LearnListAdapter(this.getContext(), categories);
        rvLearningWorkout.setAdapter(learnListAdapter);

    }
}