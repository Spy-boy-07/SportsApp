package com.example.varun.finalproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;


public class ProfileActivity extends BaseAppCompatActivity {

    private static final int REQUEST_CODE_PICK_IMAGE = 1;
    private static final int PERMISSION_READ_EXTERNAL_STORAGE = 2;

    private static final String TAG = "Tag1";
    static final int REQUEST_TAKE_PHOTO = 1;
    final int ACTIVITY_SELECT_IMAGE = 3;
    private Uri mfileURI;
    private ProgressDialog mProgressDialog;
    private StorageReference mStorageReference;
    private DatabaseReference mDatabase;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mData;
    public String foo_addr="";


    @BindView(R.id.iv_profile)
    ImageView mImageView;

    ImageView mProfile;

    String mCurrentPhotoPath;
    Button mSetPictureButton;

    @BindView(R.id.et_profile)
    EditText mEditText;

    private Uri mUri;
    private FirebaseUser mFirebaseUser;

    Camera mCamera = new Camera();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //    setContentView(R.layout.activity_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser != null) {
            Glide.with(this)
                    .load(mFirebaseUser.getPhotoUrl())
                    .into(mImageView);
            mEditText.setText(mFirebaseUser.getDisplayName());

            mProfile = (ImageView) findViewById(R.id.iv_profile);
      //      mSetPictureButton = (Button) findViewById(R.id.set_profile);

        }
    }

    @OnClick(R.id.picture_camera)
    void loadCamera() {
        Log.d(TAG, "Camera loaded");
        Camera test = new Camera();
        Intent i = new Intent(ProfileActivity.this, Camera.class);
        startActivityForResult(i, REQUEST_TAKE_PHOTO);

        //     startCamera();
    }



    //Upload images from gallery and obtain the URL to the pictures and store them in the Realtime Database
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {


            //Original code
            try {
                Bitmap help1 = MediaStore.Images.Media.getBitmap(getContentResolver(), mfileURI);

                mProgressDialog.setMessage("Upload");
                mProgressDialog.show();
                MediaStore.Images.Media.insertImage(getContentResolver(), help1, "", "");

                StorageReference filepath = mStorageReference.child("photos").child(mfileURI.getLastPathSegment());

                filepath.putFile(mfileURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Uri myUri = taskSnapshot.getDownloadUrl();
                        String downloadurl = myUri.toString();

                        mDatabase.child("photos").push().setValue(downloadurl);

                        Toast.makeText(ProfileActivity.this, "Upload completed", Toast.LENGTH_LONG).show();
                        mProgressDialog.dismiss();


                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Upload photos to database
        if (requestCode == ACTIVITY_SELECT_IMAGE && resultCode == RESULT_OK) {
            mProgressDialog.setMessage("Upload");
            mProgressDialog.show();
            Uri uri = data.getData();
            StorageReference filepath = mStorageReference.child("photos").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri myUri = taskSnapshot.getDownloadUrl();
                    String downloadurl = myUri.toString();
                    mDatabase.child("photos").push().setValue(downloadurl);
                    Toast.makeText(ProfileActivity.this, "Upload completed", Toast.LENGTH_LONG).show();
                    mProgressDialog.dismiss();

                }
            });


        }

    }


    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_profile;
    }

    @OnClick(R.id.btn_profile)
    void onUpdateProfileClick() {


        UserProfileChangeRequest.Builder builder = new UserProfileChangeRequest.Builder();
        builder.setDisplayName(mEditText.getText().toString());
        if (mUri != null) {
            builder.setPhotoUri(mUri);
        }
        UserProfileChangeRequest profileUpdates = builder.build();

        mFirebaseUser.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            showToast("Profile Updated Successfully.");
                        }
                    }
                });
    }

    @OnClick(R.id.btn_profile_change_email)
    void onChangeEmailClick() {
        startActivity(new Intent(this, ChangeEmailActivity.class));
    }


  /* @OnClick(R.id.btn_profile_change_password)
    void onChangePasswordClick() {
        try {
            startActivity(new Intent(this, ChangePasswordActivity.class));
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    } */

}

