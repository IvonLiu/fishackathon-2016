package com.example.android.camera2basic;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by Owner on 4/23/2016.
 */
public class Utils {

    public static int getThemePrimaryColor(Context context) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    public static int getThemeAccentColor(Context context) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(R.attr.colorAccent, typedValue, true);
        return typedValue.data;
    }

}
