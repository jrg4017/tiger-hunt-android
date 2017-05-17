package com.gabler.julianna.tigerhunt;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class IncompleteTaskListFragment extends Fragment {
    private ListView mListView;
    private ArrayList<Task> taskList;

    public static Fragment newInstance()
    {
        return new CompleteTaskListFragment();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {

        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        mListView = (ListView) view.findViewById(R.id.task_list_view);

        Context context = getActivity().getApplicationContext();

        taskList = Task.getTasksFromFile("tasks.json", context);
        Drawable image = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_incomplete_task, null);

        TaskListItemAdapter adapter = new TaskListItemAdapter(context, taskList, image);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Task task = taskList.get(arg2);
                DetailsFragment detailsFragment = DetailsFragment.newInstance(task, arg2);


                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.content, detailsFragment);
                ft.addToBackStack(null);
                ft.commit();

            }

        });

        return view;
    }
}
