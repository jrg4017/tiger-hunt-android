package com.gabler.julianna.tigerhunt;


import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class Task {
    public int id;
    public String title;

    public String locationName;
    public double latitude;
    public double longitude;

    public String hint;
    public boolean isActivity;

    public String imageUrl;
    public int points;


    /** json functions **/
    public static ArrayList<Task> getTasksFromFile(String filename, Context context){
        final ArrayList<Task> taskList = new ArrayList<>();

        try {
            // Load data
            String jsonString = loadJsonFromAsset(filename, context);
            JSONObject json = new JSONObject(jsonString);
            JSONArray tasks = json.getJSONArray("tasks");

            // Get Task objects from data
            for(int i = 0; i < tasks.length(); i++){
                Task task = new Task();

                task.id = tasks.getJSONObject(i).getInt("taskID");
                task.title = tasks.getJSONObject(i).getString("taskName");
                task.locationName = tasks.getJSONObject(i).getString("locationName");

                task.isActivity = tasks.getJSONObject(i).getBoolean("isActivity");

                if (!task.isActivity) {
                    task.longitude = tasks.getJSONObject(i).getDouble("longitude");
                    task.latitude = tasks.getJSONObject(i).getDouble("latitude");
                }

                task.hint = tasks.getJSONObject(i).getString("hint");
                task.imageUrl = tasks.getJSONObject(i).getString("imageURL");

                task.points = tasks.getJSONObject(i).getInt("points");

                taskList.add(task);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return taskList;
    }

    private static String loadJsonFromAsset(String filename, Context context) {
        String json = null;

        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (java.io.IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }
}
