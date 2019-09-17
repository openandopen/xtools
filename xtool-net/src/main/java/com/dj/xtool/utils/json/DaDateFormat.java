package com.dj.xtool.utils.json;

/*import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;*/

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期格式化基于 joda-time
 *
 * @author : <a href="mailto:dejianliu@bkjk.com">liudejian</a>
 * @version : Ver 1.0
 * @date : 2014-12-11 上午10:43:50
 */
public class DaDateFormat extends DateFormat {

    private static final long serialVersionUID = -3121650569779268081L;

    private final static Calendar calendar = Calendar.getInstance();

    private final static NumberFormat numFormat = NumberFormat.getInstance();

    private String pattern;

    public DaDateFormat(String pattern) {
        super.setCalendar(calendar);
        super.setNumberFormat(numFormat);
        this.pattern = pattern;
    }

    @Override
    public StringBuffer format(Date date, StringBuffer toAppendTo,
                               FieldPosition fieldPosition) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        toAppendTo.append(dateFormat.format(date.getTime()));
        return toAppendTo;
    }

    @Override
    public Date parse(String source, ParsePosition pos) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
       return dateFormat.parse(source,pos);

    }

    @Override
    public Date parse(String source) throws ParseException {
        return parse(source, null);
    }


}
