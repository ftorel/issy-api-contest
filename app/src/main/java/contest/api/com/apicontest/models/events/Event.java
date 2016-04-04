package contest.api.com.apicontest.models.events;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by floriantorel on 12/02/16.
 */
public class Event {

    public String recordId;

    public String enSavoirPlus;
    public String corps;
    public String centredInteret;

    public String url;

    public String pictureId;

    public String fin;
    public String debut;

    public String intro;
    public String lieu;
    public String title;

    public double longitude;
    public double latitude;

    public Event() {
    }

    public String getUrlImage() {
        return "https://data.issy.com/explore/dataset/agendav2/files/"+ pictureId +"/download/";
    }

    public String getDate(){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+SS:SS");

        SimpleDateFormat formatDay = new SimpleDateFormat("EEEE");
        SimpleDateFormat formatMonth = new SimpleDateFormat("MMMM");
        SimpleDateFormat formatNumber = new SimpleDateFormat("dd");

        String date = "";

        if ( debut != null ){
            try {
                Date startDate = simpleDateFormat.parse(debut);
                //date = startDate.toString();

                String day = formatDay.format(startDate);
                String Day = day.substring(0, 1).toUpperCase() + day.substring(1);

                String month = formatMonth.format(startDate);
                String Month = month.substring(0, 1).toUpperCase() + month.substring(1);

                String number = formatNumber.format(startDate);

                date = Day + " " + number + " " + Month;

            } catch (ParseException e){

            }
        }

        if ( fin != null ){

            String endDateString = "";
            try {
                Date endDate = simpleDateFormat.parse( fin );
                //endDateString = endDate.toString();

                String day = formatDay.format(endDate);
                String Day = day.substring(0, 1).toUpperCase() + day.substring(1);

                String month = formatMonth.format(endDate);
                String Month = month.substring(0, 1).toUpperCase() + month.substring(1);

                String number = formatNumber.format(endDate);

                endDateString =  Day + " " + number + " " + Month;

            } catch (ParseException e){

            }

            if ( ! endDateString.equals("") ){
                if ( ! date.equals("") ){
                    date = date + " - " + endDateString;
                } else if ( date.equals("") ){
                    date = endDateString;
                }
            }
        }

        return date;
    }

    @Override
    public boolean equals(Object object)
    {
        boolean isSame = false;

        if (object != null && object instanceof Event)
        {
            if ( this.recordId.equals(((Event) object).recordId) ){
                isSame = true;
            } else {
                isSame = false;
            }
        }

        return isSame;
    }

}
