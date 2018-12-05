package utils;

import org.apache.commons.lang3.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: chenzx
 * Date: 13-11-13
 * Time: 下午10:55
 * To change this template use File | Settings | File Templates.
 */
public class DateTools {
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static String getNextDate(String currDate){
        Date date = null;
        try {
            date = DateUtils.parseDate(currDate, "yyyy-MM-dd");
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Date next = DateUtils.addDays(date,1);
        return sdf.format(next);
    }

    public static void main(String[] args) throws ParseException {
//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        calendar.add(Calendar.DAY_OF_YEAR, 1);
//        Date date = calendar.getTime();
        System.out.println(getNextDate("2013-12-31"));
    }
}
