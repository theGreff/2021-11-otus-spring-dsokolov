package ru.otus.dsokolov.base;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static void printEmptyResult() {
        System.out.println("No records found");
    }

    public static Date dateStrFormat(String date) {
        Date dateFormatted;
        SimpleDateFormat sdf = new SimpleDateFormat();
        try {
            sdf.applyPattern("dd.MM.yyyy");
            dateFormatted = sdf.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(MessageFormat.format("Wrong date format {0}", date));
        }

        return dateFormatted;
    }
}
