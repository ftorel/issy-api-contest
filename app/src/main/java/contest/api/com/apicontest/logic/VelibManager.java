package contest.api.com.apicontest.logic;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Environment;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import contest.api.com.apicontest.Constant;
import contest.api.com.apicontest.models.events.Velib;
import contest.api.com.apicontest.services.GETService;

/**
 * Created by floriantorel on 18/03/16.
 */
public class VelibManager
{
    private static VelibManager ourInstance;

    private static Context context;

    private final String TAG = VelibManager.class.getName();

    private int delayBetweenUpdate = 1000 * 60 * 5;

    private Date lastUpdate;

    private List<Velib> velibList;

    private final String url = "your-url";
    private final String path = Environment.getExternalStorageDirectory().toString()+"/" + TAG + ".txt" ;

    public static VelibManager getInstance() {
        return ourInstance;
    }

    public static void init( Context context){
        if ( ourInstance == null ){
            ourInstance = new VelibManager(context);
        }
    }

    private VelibManager( Context c ) {
        context = c;
        velibList = new ArrayList<>();
    }

    public void updateVelibsData(){

//        if ( lastUpdate != null ){
//
//            Date nowDate = new Date();
//
//            long last = lastUpdate.getTime();
//            long now = nowDate.getTime();
//
//            //delay of 5 min between each update
//            if ( now - last < delayBetweenUpdate  ){
//                return;
//            }
//
//        }


        Intent i = new Intent( context , GETService.class );
        i.putExtra(Constant.INTENT.URL, url );
        i.putExtra(Constant.INTENT.ACTION, Constant.ACTION.JSON_VELIB_RECEIVER);
        i.putExtra(Constant.INTENT.PATH, path);
        context.startService(i);

    }

    public void parseVelibsData( String datas ){


        velibList = new ArrayList<>();

        try{
            JSONArray jsonArray = new JSONArray(datas);

            for ( int i = 0 ; i < jsonArray.length() ; i ++ ){

                JSONObject jsonObjectVelib = jsonArray.getJSONObject(i);

                Velib velib = new Velib( jsonObjectVelib );

                velibList.add(velib);

            }

            lastUpdate = new Date();

        } catch (JSONException e){
            Log.e(TAG,e.toString());
        }
    }

    public List<Velib> getTenClosestStation( double lat, double lng ){

        List<Velib> list = velibList;

        Location locationEvent = new Location("Event");

        locationEvent.setLatitude(lat);
        locationEvent.setLongitude(lng);

        for ( Velib velib : list ){
            Location locationVelib = new Location(velib.name);
            locationVelib.setLatitude(velib.latitude);
            locationVelib.setLongitude(velib.longitude);
            velib.distance = locationVelib.distanceTo(locationEvent);
        }

        Collections.sort(list, new Comparator<Velib>() {

            public int compare(Velib v1, Velib v2) {
                return (int) ( v1.distance - v2.distance );
            }
        });

        return list.subList(0, 5);
    }

}
