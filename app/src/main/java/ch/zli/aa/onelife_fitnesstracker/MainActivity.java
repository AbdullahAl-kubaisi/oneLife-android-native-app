package ch.zli.aa.onelife_fitnesstracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseObject;

import ch.zli.aa.onelife_fitnesstracker.fragments.HomeFragment;
import ch.zli.aa.onelife_fitnesstracker.fragments.LearnFragment;
import ch.zli.aa.onelife_fitnesstracker.fragments.ProfileFragment;
import ch.zli.aa.onelife_fitnesstracker.fragments.TrackFragment;

public class MainActivity extends AppCompatActivity {

    final FragmentManager mFragmentManager = getSupportFragmentManager();
    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    // test my connection
    //    ParseObject firstObject = new  ParseObject("FirstClass");
    //    firstObject.put("message","Hey ! First message from android. Parse is now connected");
    //    firstObject.saveInBackground(e -> {
    //        if (e != null){
    //            Log.e("MainActivity", e.getLocalizedMessage());
    //        }else{
    //            Log.d("MainActivity","Object saved.");

        mBottomNavigationView = findViewById(R.id.bottomNavigation);

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.action_learn:
                        fragment = new LearnFragment();
                        break;
                    case R.id.action_track:
                        fragment = new TrackFragment();
                        break;
                    case R.id.action_profile:
                    default:
                        fragment = new ProfileFragment();
                        break;
                }
                mFragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        mBottomNavigationView.setSelectedItemId(R.id.action_home);
    }
}
