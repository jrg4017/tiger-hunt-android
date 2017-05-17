package com.gabler.julianna.tigerhunt;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class TaskListItemAdapter extends BaseAdapter {

    private ArrayList<Task> taskList;
    private Drawable image;

    private static LayoutInflater inflater = null;

    public TaskListItemAdapter(
       Context context,
       ArrayList<Task> taskList,
       Drawable image
    ) {
        this.taskList = taskList;
        this.image = image;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public Object getItem(int position) {
        return taskList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null)
            view = inflater.inflate(R.layout.list_item_task, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.task_list_thumbnail);
        imageView.setImageDrawable(image);

        Task task = taskList.get(position);

        TextView title = (TextView) view.findViewById(R.id.task_list_title);
        title.setText(task.title);

        TextView subtitle = (TextView) view.findViewById(R.id.task_list_subtitle);
        String points = task.points + " points";
        subtitle.setText(points);

        return view;
    }
}