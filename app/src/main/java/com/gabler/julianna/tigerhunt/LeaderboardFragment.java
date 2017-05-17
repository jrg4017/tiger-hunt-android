package com.gabler.julianna.tigerhunt;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class LeaderboardFragment extends Fragment {

    public static Fragment newInstance()
    {
        LeaderboardFragment leaderboardFragment = new LeaderboardFragment();
        return leaderboardFragment;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_leaderboard, container, false);
    }
}