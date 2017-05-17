package com.gabler.julianna.tigerhunt.task_list;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gabler.julianna.tigerhunt.MainActivity;
import com.gabler.julianna.tigerhunt.R;
import com.gabler.julianna.tigerhunt.Task;
import com.gabler.julianna.tigerhunt.firebase.images.FirebaseUtils;


public class DetailsFragment extends Fragment {

    private static final int CAMERA_REQUEST = 1888;
    private MainActivity mActivity;

    private Task task;
    private int position;
    private ImageView mImageView;

    private FirebaseUtils firebaseUtils;

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

        this.mImageView = (ImageView) view.findViewById(R.id.app_bar_image);
        //set neccessary items
        this.mActivity = (MainActivity) getActivity();
        this.firebaseUtils = new FirebaseUtils(this.mImageView, this.task, this.mActivity);

        this.firebaseUtils.setImageIfExistsInFirebase();

        FloatingActionButton upload = (FloatingActionButton) view.findViewById(R.id.fab_upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkPermission(android.Manifest.permission.CAMERA);
                checkPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        this.setTaskDeatilsViews(view);

        return view;
    }

    private void checkPermission(String permission) {
        if ( ContextCompat.checkSelfPermission(mActivity.getApplicationContext(), permission)
                != mActivity.getPackageManager().PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{permission}, 5);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
            this.firebaseUtils.encodeBitmapAndSaveToFirebase(imageBitmap);
        }
    }

    private void setTaskDeatilsViews(View view) {
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) view.findViewById(R.id.task_collasping);
        collapsingToolbar.setTitle(task.title);

        TextView type = (TextView) view.findViewById(R.id.type);

        String typeActivity = getResources().getString(R.string.type_start);
        if (task.isActivity) {
            typeActivity += " " + getResources().getString(R.string.type_activity);
        } else {
            typeActivity += " " + getResources().getString(R.string.type_location);
        }

        type.setText(typeActivity);

        TextView points = (TextView) view.findViewById(R.id.points);
        points.setText("Worth " + task.points + " points");

        TextView hint = (TextView) view.findViewById(R.id.hint_details);
        hint.setText(task.hint);
    }


    public void setTask(Task task) {
        this.task = task;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
