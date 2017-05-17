package com.gabler.julianna.tigerhunt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DetailsFragment extends Fragment {

    private Task task;
    private int position;

    public static DetailsFragment newInstance(Task task, int position) {
        DetailsFragment fragment = new DetailsFragment();

        fragment.setTask(task);
        fragment.setPosition(position);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        TextView title = (TextView) view.findViewById(R.id.detail_task_name);
        title.setText(task.title);

        TextView hint = (TextView) view.findViewById(R.id.hint);
        hint.setText(task.hint);

        return view;
    }


    public void setTask(Task task) {
        this.task = task;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
