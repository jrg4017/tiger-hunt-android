package com.gabler.julianna.tigerhunt.task_list;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.gabler.julianna.tigerhunt.R;
import com.gabler.julianna.tigerhunt.Task;

import java.util.ArrayList;


public class CompleteTaskListFragment extends Fragment {

    private ListView mListView;

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

        final ArrayList<Task> taskList = Task.getTasksFromFile("tasks.json", context);

        Drawable image = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_completed_task, null);

        TaskListItemAdapter adapter = new TaskListItemAdapter(context, taskList, image);
        mListView.setAdapter(adapter);

        return view;
    }
}
