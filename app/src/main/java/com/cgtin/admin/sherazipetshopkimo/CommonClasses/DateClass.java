package com.cgtin.admin.sherazipetshopkimo.CommonClasses;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Admin on 24-02-2018.
 */

public class DateClass {

    String ReturnDate;


    public String getDate(String date) {

        Date disp = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy",Locale.US);
        try {
            disp=formatter.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        ReturnDate=dateFormatter.format(disp);

        return ReturnDate;
    }

}
