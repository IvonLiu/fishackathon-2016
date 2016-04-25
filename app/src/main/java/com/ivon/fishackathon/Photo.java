package com.ivon.fishackathon;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Owner on 4/23/2016.
 */
public class Photo {

    public final String url;
    public final double length;
    public final double latitude;
    public final double longitude;
    public final String type;
    public final long timestamp;

    public Photo(String url, double length, double latitude, double longitude, String type, long timestamp) {
        this.url = url;
        this.length = length;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
        this.timestamp = timestamp;
    }

    public Map<String, Object> format() {
        Map<String, Object> map = new HashMap<>();
        map.put("url", url);
        map.put("length", length);
        map.put("latitude", latitude);
        map.put("longitude", longitude);
        map.put("type", type);
        map.put("timestamp", timestamp);
        return map;
    }

}
