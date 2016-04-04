package contest.api.com.apicontest.models.events;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by floriantorel on 18/03/16.
 */
public class Velib {

    private String TAG = Velib.class.getName();

    public String number;
    public String name;
    public String available_bike_stands;
    public String available_bikes;

    public double latitude;
    public double longitude;

    private final String NAME = "name";
    private final String NUMBER = "number";
    private final String AVAILABLE_BIKE_STANDS = "available_bike_stands";
    private final String AVAILABLE_BIKES = "available_bikes";
    private final String POS = "position";
    private final String LAT = "lat";
    private final String LONG = "lng";

    public float distance;

    public Velib() {
    }

    public Velib( JSONObject jsonObject ){

        try{
            name = jsonObject.getString(NAME);
        } catch (JSONException e){
            Log.e(TAG, e.toString());
        }

        try{
            number  = jsonObject.getString(NUMBER);
        } catch (JSONException e){
            Log.e(TAG, e.toString());
        }

        try{
            available_bike_stands = jsonObject.getString(AVAILABLE_BIKE_STANDS);
        } catch (JSONException e){
            Log.e(TAG, e.toString());
        }

        try{
            available_bikes = jsonObject.getString(AVAILABLE_BIKES);
        } catch (JSONException e){
            Log.e(TAG, e.toString());
        }

        try{
            JSONObject jsonObjectPosition = jsonObject.getJSONObject(POS);

            try{
                latitude = jsonObjectPosition.getDouble(LAT);
            } catch (JSONException e){
                Log.e(TAG, e.toString());
            }

            try{
                longitude = jsonObjectPosition.getDouble(LONG);
            } catch (JSONException e){
                Log.e(TAG, e.toString());
            }

        } catch (JSONException e){
            Log.e(TAG, e.toString());
        }



    }
}
