package com.gabler.julianna.tigerhunt.firebase.images;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.gabler.julianna.tigerhunt.MainActivity;
import com.gabler.julianna.tigerhunt.Task;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;


public class FirebaseUtils {

    private final static String TAG = "FirebaseUtils";

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private MainActivity mActivity;
    private FirebaseUser mCurrentUser;
    private Task task;
    private MainActivity mActivty;
    private ImageView bmImage;

    private boolean hasSubmitted = false;

    public FirebaseUtils(ImageView bmImage, Task task, MainActivity activity) {
        this.bmImage = bmImage;
        this.task = task;
        this.mStorageRef = FirebaseStorage.getInstance().getReference();
        this.mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        this.mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        this.mActivity = activity;
    }

    public void setImageIfExistsInFirebase() {
        mStorageRef
                .child(mCurrentUser.getUid() + "/" + this.getFileName())
                .getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        new DownloadImageTask(bmImage).execute(uri.toString());
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // we don't care do nothing
            }
        });
    }

    public void addToScore() {
        if (!hasSubmitted) {
            final DatabaseReference pointsRef = this.mDatabaseRef
                    .child("users")
                    .child(this.mCurrentUser.getUid())
                    .child("totalScore");

            pointsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    Log.d(TAG, "onDataChange");
                    int totalPoints = (int) snapshot.getValue();
                    totalPoints += task.points;

                    pointsRef.setValue(totalPoints);
                    hasSubmitted = true;
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d(TAG, "OnCancelled");
                    // currently at zero so update
                    pointsRef.setValue(task.points);
                }
            });
        }
    }

    public void encodeBitmapAndSaveToFirebase(Bitmap imageBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);

        String path =
                MediaStore.Images.Media.insertImage(mActivity.getContentResolver(), imageBitmap, task.title, null);

        Uri file = Uri.parse(path);
        String fileName = getFileName();

        StorageMetadata metadata = new StorageMetadata.Builder()
                .setContentType("image/png")
                .build();

        StorageReference taskRef = mStorageRef
                .child(mCurrentUser.getUid() + "/" + fileName);

        taskRef.putFile(file, metadata)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        task.imageUrl = downloadUrl.toString();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(mActivity.getApplicationContext(), "Image upload failed to save", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private String getFileName() {
        return ((task.title).replaceAll(" ", "_").toLowerCase()) + ".png";
    }
}
