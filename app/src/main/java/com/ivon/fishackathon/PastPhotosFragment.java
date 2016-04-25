package com.ivon.fishackathon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by Owner on 4/23/2016.
 */
public class PastPhotosFragment extends Fragment implements ValueEventListener {

    private ListView mListView;
    private PhotoAdapter mAdapter;
    private Firebase mFirebaseRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_past_photos, container, false);

        mListView = (ListView) rootView.findViewById(R.id.list);

        List<Photo> samples = new ArrayList<>();
        mAdapter = new PhotoAdapter(getActivity(), samples, null);
        mListView.setAdapter(mAdapter);

        View headerView = new View(getActivity());
        headerView.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.card_margin_bottom)));
        mListView.addHeaderView(headerView);

        View footerView = new View(getActivity());
        footerView.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.card_margin_top)));
        mListView.addFooterView(footerView);

        mFirebaseRef = new Firebase("https://fishackathon-2016.firebaseio.com");
        mFirebaseRef.addValueEventListener(this);

        return rootView;
    }

    @Override
    public void onDataChange(DataSnapshot snapshot) {
        Map<String, Map<String, Object>> map = (Map<String, Map<String, Object>>) snapshot.getValue();
        if (map != null) {
            mAdapter.clear();
            List<Photo> photos = new ArrayList<>();
            for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
                String key = entry.getKey();
                Map<String, Object> item = entry.getValue();
                String url = (String) item.get("url");
                double length = (Double) item.get("length");
                double latitude = (Double) item.get("latitude");
                double longitude = (Double) item.get("longitude");
                String type = (String) item.get("type");
                long timestamp = (Long) item.get("timestamp");
                Photo photo = new Photo(url, length, latitude, longitude, type, timestamp);
                photos.add(photo);
            }
            Collections.sort(photos, new Comparator<Photo>() {
                @Override
                public int compare(Photo a, Photo b) {
                    return Long.valueOf(b.timestamp).compareTo(a.timestamp);
                }
            });
            mAdapter.addAll(photos);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

    }
}
