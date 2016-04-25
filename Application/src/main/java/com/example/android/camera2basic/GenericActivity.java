package com.example.android.camera2basic;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.io.File;

/**
 * Created by Owner on 4/23/2016.
 */
public class GenericActivity extends AppCompatActivity {

    public static final String EXTRA_TYPE = "type";
    public static final int TYPE_CREATE = 0;
    public static final int TYPE_PAST = 1;

    private OnFabClickListener mFabClickListener;
    public void setFabClickListener(OnFabClickListener listener) {
        mFabClickListener = listener;
    }

    private FloatingActionButton mFab;

    public FloatingActionButton getFab() {
        return mFab;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (!isTaskRoot()) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        }

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setBackgroundTintList(new ColorStateList(new int[][]{{0}}, new int[]{Utils.getThemePrimaryColor(this)}));
        mFab.setRippleColor(Utils.getThemeAccentColor(this));
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFabClickListener != null) {
                    mFabClickListener.onFabClick((FloatingActionButton) view);
                }
            }
        });

        int type = getIntent().getIntExtra(EXTRA_TYPE, TYPE_CREATE);
        switch(type) {
            case TYPE_CREATE:
            default:
                File image = (File) getIntent().getExtras().get(CreatePostFragment.EXTRA_IMAGE);
                double length = getIntent().getDoubleExtra(CreatePostFragment.EXTRA_LENGTH, 0);
                double latitude = getIntent().getDoubleExtra(CreatePostFragment.EXTRA_LATITUDE, 0);
                double longitude = getIntent().getDoubleExtra(CreatePostFragment.EXTRA_LONGITUDE, 0);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment, CreatePostFragment.newInstance(image, length, latitude, longitude))
                        .commit();
                getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
                getFab().setVisibility(View.VISIBLE);
                break;
            case TYPE_PAST:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment, new PastPhotosFragment())
                        .commit();
                getSupportActionBar().setTitle("Past Catches");
                getFab().setVisibility(View.GONE);
                break;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id ==  android.R.id.home) {
            if (!isTaskRoot()) {
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }

}
