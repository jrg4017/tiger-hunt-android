package com.gabler.julianna.tigerhunt;


import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ProfileFragment extends Fragment {
    FirebaseUser currentUser;


    public static Fragment newInstance()
    {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) view.findViewById(R.id.profile_collapsing);
        collapsingToolbar.setTitle(currentUser.getDisplayName());

        return view;
    }
}
