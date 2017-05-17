package com.gabler.julianna.tigerhunt.task_list;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gabler.julianna.tigerhunt.R;
import com.gabler.julianna.tigerhunt.Task;

import java.util.ArrayList;

public class TabHostFragment extends Fragment {
    private FragmentTabHost mTabHost;
    ArrayList<Task> taskList;

    public static Fragment newInstance()
    {
        return new TabHostFragment();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mTabHost = new FragmentTabHost(getActivity());
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.layout.fragment_tab_host);

        mTabHost.addTab(mTabHost.newTabSpec("Tab1").setIndicator("Incomplete Tasks"), IncompleteTaskListFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("Tab2").setIndicator("Completed Tasks"), CompleteTaskListFragment.class, null);

        return mTabHost;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mTabHost = null;
    }
}
