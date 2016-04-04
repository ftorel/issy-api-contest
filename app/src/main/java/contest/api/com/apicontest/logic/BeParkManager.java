package contest.api.com.apicontest.logic;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import contest.api.com.apicontest.Constant;
import contest.api.com.apicontest.models.events.BePark;
import contest.api.com.apicontest.services.POSTService;

/**
 * Created by floriantorel on 30/03/16.
 */
public class BeParkManager {

    private final String TAG = BeParkManager.class.getName();

    private final String URL = "https://platform.ecim-cities.eu/ecimServices/platform/mediator/callService";

    private static BeParkManager ourInstance;

    private final float maxDistanceParking = 2 * 1000;

    private Context context;

    private List<BePark> beParkList;

    public static BeParkManager getInstance() {
        return ourInstance;
    }

    public static void init( Context context){
        if ( ourInstance == null ){
          ourInstance = new BeParkManager(context);
        }
    }

    private BeParkManager( Context context) {
        this.context = context;
        this.beParkList = new ArrayList<>();
    }


    public void updateData(){
        if ( beParkList.size() == 0 ){
            getData();
        }
    }

    public void getData(){

        JSONObject jsonObject = new JSONObject();

        try{
            jsonObject.put("serviceID","2");
            jsonObject.put("developerKey","your-developer-Key");
            jsonObject.put("methodName","parking");
            jsonObject.put("serviceMediaType","json");

            //lat/lng

            JSONObject jsonObjectLongitude = new JSONObject();
            jsonObjectLongitude.put("name","longitude");
            jsonObjectLongitude.put("value",2.27);

            JSONObject jsonObjectLatitude = new JSONObject();
            jsonObjectLatitude.put("name","latitude");
            jsonObjectLatitude.put("value",48.82);

            JSONArray jsonArray = new JSONArray();
            jsonArray.put(jsonObjectLatitude);
            jsonArray.put(jsonObjectLongitude);

            jsonObject.put("serviceParameters", jsonArray );

            jsonObject.put("bodyParameter", "{}");


            String input = jsonObject.toString();


            Intent intent = new Intent(context, POSTService.class);

            intent.putExtra(Constant.INTENT.DATA, input );
            intent.putExtra(Constant.INTENT.URL, URL);
            intent.putExtra(Constant.INTENT.ACTION, Constant.ACTION.JSON_BEPARK_RECEIVER);

            context.startService(intent);

        } catch (JSONException e){
            Log.e(TAG, e.toString());
        }

    }

    public List<BePark> getTenClosestStation( double lat, double lng ){

        List<BePark> list = new ArrayList<>();

        Location locationEvent = new Location("Event");

        locationEvent.setLatitude(lat);
        locationEvent.setLongitude(lng);

        for ( BePark bePark : beParkList ){
            Location locationVelib = new Location(bePark.name);
            locationVelib.setLatitude(bePark.lat);
            locationVelib.setLongitude(bePark.lng);

            bePark.distance = locationVelib.distanceTo(locationEvent);

            if ( bePark.distance < maxDistanceParking ){
                list.add(bePark);
            }

        }

        if ( list.size() == 0 ){
            return list;
        }

        Collections.sort(list, new Comparator<BePark>() {

            public int compare(BePark v1, BePark v2) {
                return (int) (v1.distance - v2.distance);
            }
        });

        if ( list.size() < 5 ){
            return list;
        }

        return list.subList(0, 5);

    }


    public void parseBeParkData( String data ){

        try{
            JSONObject jsonObject = new JSONObject(data);

            String sResponse = jsonObject.getString("serviceResponse");
            String serviceResponse = sResponse.replaceAll("\\\\", "");

            JSONObject jsonObjectServiceResponse = new JSONObject(serviceResponse);

            try{
                JSONObject jsonObjectResult = jsonObjectServiceResponse.getJSONObject("result");

                JSONArray jsonArrayParkings = jsonObjectResult.getJSONArray("parkings");

                for ( int i = 0 ; i < jsonArrayParkings.length() ; i ++ ){


                    JSONObject jsonObjectParking = jsonArrayParkings.getJSONObject(i);

                    BePark bePark = new BePark(jsonObjectParking);

                    if ( bePark.dailyPrice != 0 ){
                        beParkList.add(bePark);
                    }

                }


            } catch (JSONException e){
                Log.e(TAG,e.toString());
            }


        } catch ( JSONException e){
            Log.e(TAG,e.toString());
        }

    }

}
