package ch.zli.aa.onelife_fitnesstracker;
/*
* Hilfsmittel: https://developer.android.com/reference/android/icu/text/SimpleDateFormat
* */
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateSummary {
    public static String mDate;
    public String Weather;
    double longitude, latitude;

    public static String getDate() {
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("MMM d");
        mDate = format.format(today);

        return mDate;
    }
}

