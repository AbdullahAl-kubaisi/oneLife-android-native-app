package ch.zli.aa.onelife_fitnesstracker.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.parse.ParseUser;

import ch.zli.aa.onelife_fitnesstracker.LoginActivity;
import ch.zli.aa.onelife_fitnesstracker.R;


public class ProfileFragment extends Fragment {
    TextView tvName;
    TextView tvUsername;
    TextView tvUserHeight;
    TextView tvUserWeight;
    RecyclerView rvWorkouts;
    ImageButton btnLogout;
    ImageButton btnEdit;
    String TAG = "ProfileFragment";

    public ProfileFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvName = view.findViewById(R.id.tvUsernameProf);
        tvUsername = view.findViewById(R.id.tvName);
        tvUserHeight = view.findViewById(R.id.tvUserHeight);
        tvUserWeight = view.findViewById(R.id.tvUserWeight);
        rvWorkouts = view.findViewById(R.id.rvWorkouts);
        btnLogout = view.findViewById(R.id.btnLogout);
        getCurrentUser();
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                ParseUser.logOut();
                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);
            }
        });

    }
    public void getCurrentUser() {

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
        } else {
        }
        Log.i("ProfileFragment", "@"+ currentUser.getUsername());
        tvName.setText("@" + currentUser.getUsername());
        tvUsername.setText(currentUser.get("firstname") + " " + currentUser.get("lastname"));
        Log.i("ProfileFragment", currentUser.get("firstname") + " " + currentUser.get("lastname"));
        int radius = 30;
        int margin = 10;


    }
}
