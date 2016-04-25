package com.ivon.fishackathon;

/**
 * Created by Owner on 4/23/2016.
 */
public class CameraUtils {

    public static double getObjectDistance(double f, double di) {
        return 1 / ((1/f) - (1/di));
    }

    public static double getObjectDistance(double di) {
        // This is calibrated for Nexus 6P camera only
        return 69.53310965 * Math.pow(di, -0.88427916);
    }
    //=125.019/H4^1.04544005185382
    public static double getObjectLength(double di) {
        double distance = 125.019 * Math.pow(di, -1.04544005185382);
        return distance * 1.2;
    }
}