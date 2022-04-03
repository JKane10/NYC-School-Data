package com.jkane.a20220402_joshkane_nycschools.app.utils;

import android.content.Context;

import com.jkane.a20220402_joshkane_nycschools.R;

import java.util.Locale;

import javax.inject.Singleton;

@Singleton
public class StringUtils {

    Context context;

    public StringUtils(Context context) {
        this.context = context;
    }

    /**
     * Utility to null checks a string value.
     *
     * @param value to be checked
     * @return value as a string or the not available string from resources.
     */
    public String valueOrUnavailable(String value) {
        return (value != null) ? value : context.getString(R.string.data_not_available);
    }

    public String addressOrUnavailable(String value) {
        String result;
        try {
            result = value.split(" \\(")[0];
        } catch (Exception e) {
            result = null;
        }
        return (result != null) ? result : context.getString(R.string.data_not_available);
    }

    /**
     * Utility to null check a string value that represents a double.
     * It will attempt to convert it to a percentage by multiplying by 100 and adding a % sign.
     * <p>
     * If the conversion fails it will return null. If it fails or was originally null. The method
     * will return the not available string resource.
     *
     * @param value to be checked that represents a decimal percentage "0.XX"
     * @return value as a string or the not available string resources.
     */
    public String percentOrUnavailable(String value) {
        String result;
        try {
            Double doubleValue = Double.parseDouble(value) * 100;
            result = String.format(Locale.getDefault(), "%1$,.0f", doubleValue) + "%";
        } catch (Exception e) {
            result = null;
        }
        return (result != null) ? result : context.getString(R.string.data_not_available);
    }
}
