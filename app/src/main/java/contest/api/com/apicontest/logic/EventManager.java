package contest.api.com.apicontest.logic;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import contest.api.com.apicontest.Constant;
import contest.api.com.apicontest.models.events.Event;
import contest.api.com.apicontest.services.GETService;

/**
 * Created by floriantorel on 04/03/16.
 */
public class EventManager {

    private final String TAG = EventManager.class.getName();

    private final String TAG_RECORD = "records";
    private final String TAG_FIELDS = "fields";

    private final String TAG_EN_SAVOIR_PLUS = "en_savoir_plus";
    private final String TAG_CORPS = "corps";
    private final String TAG_CENTRE_INTEREST = "centre_d_interet";
    private final String TAG_DEBUT = "debut";
    private final String TAG_FIN = "fin";
    private final String TAG_LONGITUDE = "longitude";
    private final String TAG_LATITUDE = "latitude";
    private final String TAG_INTRO = "intro";
    private final String TAG_URL = "url";
    private final String TAG_TITRE = "titre";
    private final String TAG_LIEU = "lieu";
    private final String TAG_IMAGE = "image";
    private final String TAG_ID_IMAGE = "id";
    private final String TAG_RECORD_ID = "recordid";

    private static EventManager ourInstance;
    private static Context context;
    private static List<Event> events;

    private final String path = Environment.getExternalStorageDirectory().toString()+"/" + TAG + ".txt" ;


    private int start   = -1;

    public static EventManager getInstance() {
        return ourInstance;
    }

    public static void init( Context con ){
        ourInstance = new EventManager();
        context = con;
        events = new ArrayList<>();
    }

    private EventManager() {

    }

    public void parseEvents( String json ){

        try{
            JSONObject jsonResponse = new JSONObject( json );

            JSONArray jsonArrayRecord = jsonResponse.getJSONArray( TAG_RECORD );

            parseRecord( jsonArrayRecord );

        } catch (JSONException e){

            Log.e(TAG, e.toString());

        }

    }

    private void parseRecord( JSONArray jsonArray ){

        for ( int i = 0 ; i < jsonArray.length() ; i ++ ){

            Event event = new Event();

            try {
                JSONObject jsonObjectRecord =  jsonArray.getJSONObject(i);

                try {
                    event.recordId = jsonObjectRecord.getString( TAG_RECORD_ID );
                } catch (JSONException e){
                    Log.e( TAG, e.toString());
                }

                JSONObject jsonObjectFields = jsonObjectRecord.getJSONObject( TAG_FIELDS );

                try {
                    event.enSavoirPlus = jsonObjectFields.getString( TAG_EN_SAVOIR_PLUS );
                } catch (JSONException e){
                    Log.e( TAG, e.toString());
                }

                try {
                    event.corps = jsonObjectFields.getString( TAG_CORPS );
                } catch (JSONException e){
                    Log.e( TAG, e.toString());
                }

                try {
                    event.centredInteret = jsonObjectFields.getString( TAG_CENTRE_INTEREST );
                } catch (JSONException e){
                    Log.e( TAG, e.toString());
                }

                try {
                    event.debut = jsonObjectFields.getString( TAG_DEBUT );
                } catch (JSONException e){
                    Log.e( TAG, e.toString());
                }

                try {
                    event.fin = jsonObjectFields.getString( TAG_FIN );
                } catch (JSONException e){
                    Log.e( TAG, e.toString());
                }

                try {
                    event.debut = jsonObjectFields.getString(TAG_DEBUT);
                } catch (JSONException e){
                    Log.e( TAG, e.toString());
                }

                try {
                    event.longitude = jsonObjectFields.getDouble(TAG_LONGITUDE);
                } catch (JSONException e){
                    Log.e( TAG, e.toString());
                }

                try {
                    event.latitude = jsonObjectFields.getDouble(TAG_LATITUDE);
                } catch (JSONException e){
                    Log.e( TAG, e.toString());
                }

                try {
                    event.intro = jsonObjectFields.getString(TAG_INTRO);
                } catch (JSONException e){
                    Log.e( TAG, e.toString());
                }

                try {
                    event.url = jsonObjectFields.getString(TAG_URL);
                } catch (JSONException e){
                    Log.e( TAG, e.toString());
                }

                try {
                    event.title = jsonObjectFields.getString(TAG_TITRE);
                } catch (JSONException e){
                    Log.e( TAG, e.toString());
                }

                try {
                    event.url = jsonObjectFields.getString( TAG_URL );
                } catch (JSONException e){
                    Log.e( TAG, e.toString());
                }

                try {
                    event.lieu = jsonObjectFields.getString( TAG_LIEU );
                } catch (JSONException e){
                    Log.e( TAG, e.toString());
                }

                try{

                    JSONObject jsonObjectImage = jsonObjectFields.getJSONObject(TAG_IMAGE);

                    try {
                        event.pictureId = jsonObjectImage.getString( TAG_ID_IMAGE );
                    } catch (JSONException e){
                        Log.e( TAG, e.toString());
                    }


                } catch ( JSONException e){
                    Log.e( TAG, e.toString());
                }


            } catch (JSONException e){
                Log.e( TAG, e.toString());
            }


            if ( ! events.contains(event) ){
                events.add(event);
            }

        }

        String a = new String();

    }

    private String getEventURL( int start ){
        return  "https://data.issy.com/api/records/1.0/search/?dataset=agendav2&sort=-debut&start=" +
                String.valueOf(start) +
                "&row=10";
    }

    private void updateStartUrl(){
        if ( start == - 1 ){
            start = 0;
        } else {
            start = start + 20;
        }
    }

    public List<Event> getAllEvent(){
        return events;
    }

    public Event getEventByPosition( int position ){
        return events.get(position);
    }

    public void updateEventsData(){
        updateStartUrl();
        String url = getEventURL(start);
        Intent i = new Intent( context , GETService.class );
        i.putExtra(Constant.INTENT.URL, url );
        i.putExtra(Constant.INTENT.PATH, path);
        i.putExtra(Constant.INTENT.ACTION, Constant.ACTION.JSON_RECEIVER);
        context.startService(i);
    }

}
