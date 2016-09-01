package quotestoday.tek.com.quotestoday.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by Tek on 2/9/2016.
 */
public class DateUtils {
    public static synchronized String getDateTime(String datetime) throws ParseException {
        SimpleDateFormat format=new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
        Date date=format.parse(datetime);

        Calendar calendar=new GregorianCalendar();
        calendar.setTimeInMillis(date.getTime());
        calendar.setTimeZone(TimeZone.getDefault());

        SimpleDateFormat outFormat=new SimpleDateFormat("E, dd MMM, HH:mm");
        String resDate = outFormat.format(calendar.getTime());

        return resDate;
    }

    public static synchronized long getOffsetToMidnight() {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        int hour=cal.get(Calendar.HOUR_OF_DAY);
        int min=cal.get(Calendar.MINUTE);
        int sec=cal.get(Calendar.SECOND);

        int offset= ((24-hour)*60*60+(60-min)*60+(60-sec))*1000;

        return offset;
    }
}
