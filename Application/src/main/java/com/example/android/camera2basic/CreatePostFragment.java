package com.example.android.camera2basic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.io.File;

/**
 * Created by Owner on 4/23/2016.
 */
public class CreatePostFragment extends Fragment
        implements OnFabClickListener, FilestackTask.OnFilestackTaskCompleteListener {

    public static final String EXTRA_IMAGE = "image";
    public static final String EXTRA_LENGTH = "length";
    public static final String EXTRA_LATITUDE = "latitude";
    public static final String EXTRA_LONGITUDE = "longitude";

    public static CreatePostFragment newInstance(File image, double length,
                                                 double latitude, double longitude) {
        CreatePostFragment f = new CreatePostFragment();
        f.mImage = image;
        f.mLength = length;
        f.mLatitude = latitude;
        f.mLongitude = longitude;
        return f;
    }

    private File mImage;
    private double mLength;
    private double mLatitude;
    private double mLongitude;

    private ImageView mImageView;
    private TextView mLengthText;
    private TextView mLocationText;
    private EditText mTypeField;

    private Firebase mFirebaseRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_create_photos, container, false);

        mImageView = (ImageView) rootView.findViewById(R.id.image);
        mLengthText = (TextView) rootView.findViewById(R.id.length);
        mLocationText = (TextView) rootView.findViewById(R.id.location);
        mTypeField = (EditText) rootView.findViewById(R.id.type);

        Bitmap bitmap = BitmapFactory.decodeFile(mImage.getAbsolutePath());
        mImageView.setImageBitmap(bitmap);
        mLengthText.setText(String.format("%.2f cm", mLength));
        mLocationText.setText(String.format("%.3f, %.3f", mLatitude, mLongitude));

        ((GenericActivity) getActivity()).setFabClickListener(this);

        mFirebaseRef = new Firebase("https://fishackathon-2016.firebaseio.com");

        return rootView;
    }

    private void save() {
        Toast.makeText(getActivity(), "Submitting...", Toast.LENGTH_SHORT).show();
        new FilestackTask(this).execute(mImage);
    }

    @Override
    public void onFilestackTaskComplete(String url) {
        String type = mTypeField.getText().toString();
        Photo photo = new Photo(url, mLength, mLatitude, mLongitude, type, System.currentTimeMillis());
        mFirebaseRef.push().setValue(photo.format());
        getActivity().finish();
    }

    @Override
    public void onFabClick(FloatingActionButton fab) {
        save();
    }

}
