package com.gabler.julianna.tigerhunt;


import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link MapsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private View rootView;
    GoogleMap mMap;
    MapView mMapView;
    private ArrayList<Task> taskList;


    public static Fragment newInstance()
    {
        MapsFragment mapsFragment = new MapsFragment();
        return mapsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {
            rootView = inflater.inflate(R.layout.fragment_maps, container, false);

            taskList =
                    Task.getTasksFromFile("tasks.json", getActivity().getApplicationContext());

            MapsInitializer.initialize(this.getActivity());

            mMapView = (MapView) rootView.findViewById(R.id.map);
            mMapView.onCreate(savedInstanceState);
            mMapView.getMapAsync(this);
        }
        catch (InflateException e){
            Log.e("TAG", "Inflate exception");
        }

        return rootView;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


//        mMap.setMyLocationEnabled(true);


        // Add a marker in Sydney and move the camera
        LatLng rit = new LatLng(43.084748, -77.674764);
        mMap
            .addMarker(new MarkerOptions().position(rit).title("Rochester Institute of Technology"))
            .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_icon));

        // add other icons from task list
        this.addTaskMarkers();

        mMap.moveCamera(CameraUpdateFactory.newLatLng(rit));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);
    }

    private void addTaskMarkers() {

        for (Task task: this.taskList) {

            if (!task.isActivity) { //activities don't have markers

                Log.d("MapsFragment", task.latitude + ", " + task.longitude);

                LatLng pin = new LatLng(task.latitude, task.longitude);
                mMap
                    .addMarker(new MarkerOptions().position(pin).title(task.title))
                    .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_icon));
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState); mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
