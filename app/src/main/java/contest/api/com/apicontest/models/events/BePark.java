package contest.api.com.apicontest.models.events;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by floriantorel on 30/03/16.
 */
public class BePark {

    private final String TAG = BePark.class.getName();

    public String name;

    public double lat;
    public double lng;

    public int dailyPrice;
    private String dailyTime;

    private String monthlyTime;
    private int monthlyPrice;

    private String street;
    private String number;
    private String zip;
    private String city;
    private String country;

    private String status;

    private String url;

    public float distance;


    public BePark( JSONObject jsonObject ) {

        try{
            this.name = jsonObject.getString("name");
        } catch (JSONException e){
            Log.e(TAG, e.toString());
        }

        try{
            this.url = jsonObject.getString("url");
        } catch (JSONException e){
            Log.e(TAG, e.toString());
        }

        try{
            this.status = jsonObject.getString("status");
        } catch (JSONException e){
            Log.e(TAG, e.toString());
        }

        try{
            JSONObject jsonObjectCoordonnate = jsonObject.getJSONObject("coordinate");

            try{
                this.lat = jsonObjectCoordonnate.getDouble("latitude");
            } catch (JSONException e){
                Log.e(TAG, e.toString());
            }

            try{
                this.lng = jsonObjectCoordonnate.getDouble("longitude");
            } catch (JSONException e){
                Log.e(TAG, e.toString());
            }

        } catch (JSONException e){
            Log.e(TAG, e.toString());
        }

        try{
            JSONObject jsonObjectPrice = jsonObject.getJSONObject("price");


            try{
                this.dailyPrice = jsonObjectPrice.getInt("price");
            } catch (JSONException e){
                Log.e(TAG, e.toString());
            }

            try{
                this.dailyTime = jsonObjectPrice.getString("length");
            } catch (JSONException e){
                Log.e(TAG, e.toString());
            }

            try{
                this.monthlyPrice = jsonObjectPrice.getInt("sub_price");
            } catch (JSONException e){
                Log.e(TAG, e.toString());
            }

            try{
                this.monthlyTime = jsonObjectPrice.getString("sub_length");
            } catch (JSONException e){
                Log.e(TAG, e.toString());
            }


        } catch (JSONException e){
            Log.e(TAG, e.toString());
        }

        try{
            JSONObject jsonObjectAddress = jsonObject.getJSONObject("address");


            try{
                this.street = jsonObjectAddress.getString("street");
            } catch (JSONException e){
                Log.e(TAG, e.toString());
            }

            try{
                this.number = jsonObjectAddress.getString("number");
            } catch (JSONException e){
                Log.e(TAG, e.toString());
            }

            try{
                this.zip = jsonObjectAddress.getString("zip");
            } catch (JSONException e){
                Log.e(TAG, e.toString());
            }

            try{
                this.city = jsonObjectAddress.getString("city");
            } catch (JSONException e){
                Log.e(TAG, e.toString());
            }

            try{
                this.country = jsonObjectAddress.getString("country");
            } catch (JSONException e){
                Log.e(TAG, e.toString());
            }



        } catch (JSONException e){
            Log.e(TAG, e.toString());
        }


    }
}
