package com.gabler.julianna.tigerhunt;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.gabler.julianna.tigerhunt.login.LoginActivity;
import com.gabler.julianna.tigerhunt.task_list.TabHostFragment;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private ActionBar actionBar;
    final static String TASK_FRAGMENT = "TASK";
    final static String LEADERBOARD_FRAGMENT = "LEADERBOARD";
    final static String PROFILE_FRAGMENT = "PROFILE";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.content);
            switch (item.getItemId()) {
                case R.id.navigation_map:
                    if (!(currentFragment instanceof MapsFragment)) {
                        actionBar.hide();
                        Fragment map = MapsFragment.newInstance();
                        selectFragment(map, "map");
                    }
                    return true;
                case R.id.navigation_task_list:
                    if (!(currentFragment instanceof TabHostFragment)) {
                        Fragment ts = TabHostFragment.newInstance();
                        actionBar.hide();
                        selectFragment(ts, "");
                    }
                    return true;
                case R.id.navigation_leaderboard:
                    if (!(currentFragment instanceof LeaderboardFragment)) {
                        Fragment lb = LeaderboardFragment.newInstance();
                        actionBar.hide();
                        selectFragment(lb, "");
                    }
                    return true;
                case R.id.navigation_profile:
                    if (!(currentFragment instanceof ProfileFragment)) {
                        Fragment pf = ProfileFragment.newInstance();
                        actionBar.show();
                        selectFragment(pf, "");
                    }
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();
        // eliminate shadow dropdown -- for layout
        actionBar.setElevation(0);
        actionBar.setTitle("");
        actionBar.hide();

        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        Fragment map = MapsFragment.newInstance();
        selectFragment(map, "map");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_settings:
                Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.action_logout:
                this.signout();
                break;
            default:
                break;
        }

        return true;
    }


    private void signout() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signOut();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();

        Toast.makeText(getApplicationContext(), "Successfully Logged Out", Toast.LENGTH_SHORT).show();

    }


    private void selectFragment(Fragment fragment, String addToBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.content, fragment);

        if (!addToBackStack.equals(""))
            ft.addToBackStack(addToBackStack);

        ft.commit();
    }
}
