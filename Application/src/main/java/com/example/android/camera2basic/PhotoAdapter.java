package com.example.android.camera2basic;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Owner on 4/23/2016.
 */
public class PhotoAdapter extends ArrayAdapter<Photo> {

    private List<Photo> mList;
    private OnCardClickListener mCardClickListener = null;

    public PhotoAdapter(Context context, List<Photo> list, OnCardClickListener listener) {
        super(context, android.R.layout.simple_list_item_1, list);
        mList = list;
        mCardClickListener = listener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final Photo photo = mList.get(position);

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.photo_adapter_item, null);
        }

        CardView cardView = (CardView) view.findViewById(R.id.photo_card);
        ImageView photoImage = (ImageView) view.findViewById(R.id.image);
        TextView lengthText = (TextView) view.findViewById(R.id.length);
        TextView dateText = (TextView) view.findViewById(R.id.timestamp);
        ImageView mapButton = (ImageView) view.findViewById(R.id.map);
        ImageView shareButton = (ImageView) view.findViewById(R.id.share);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCardClickListener != null) {
                    mCardClickListener.onCardClick(view, position);
                }
            }
        });
        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return mCardClickListener != null && mCardClickListener.onCardLongClick(view, position);
            }
        });

        Picasso.with(getContext())
                .load(photo.url)
                .fit()
                .centerCrop()
                .into(photoImage);

        lengthText.setText(String.format("%.2f cm", photo.length));
        dateText.setText(format(photo.timestamp));

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q="+photo.latitude+","+photo.longitude);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                getContext().startActivity(mapIntent);
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = String.format("Look at the %.2f cm fish I just caught! %s", photo.length, photo.url);
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, text);
                sendIntent.setType("text/plain");
                getContext().startActivity(sendIntent);
            }
        });

        return view;
    }

    private String format(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        return sdf.format(new Date(timestamp));
    }
}

