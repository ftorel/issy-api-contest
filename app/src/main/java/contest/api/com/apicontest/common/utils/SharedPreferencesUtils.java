package contest.api.com.apicontest.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

/**
 * Created by floriantorel on 22/01/16.
 */
public class SharedPreferencesUtils {

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    protected SharedPreferencesUtils(Context context, String name) {
        sharedPreferences = context.getSharedPreferences( name, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    //
    //All

    protected void delete( String TAG ){
        editor.remove(TAG).commit();
    }

    //
    //Long

    protected void saveLong( String TAG, long value ){
        editor.putLong(TAG,value);
        editor.commit();
    }

    protected Long getLong( String TAG ){
        return sharedPreferences.getLong(TAG,-1);
    }


    //
    //String

    protected void saveString( String TAG, String value ){
        editor.putString( TAG, value);
        editor.commit();
    }

    protected String getString( String TAG ){
        return sharedPreferences.getString( TAG , "");
    }

    //
    //Byte []

    protected void saveByteArray( String TAG, byte[] array ){
        String data = Base64.encodeToString(array, Base64.DEFAULT);
        saveString(TAG, data);
    }

    protected byte[] getByteArray( String TAG ){
        String data = getString(TAG);
        if ( data.isEmpty() ){
            return null;
        } else {
            return Base64.decode(data, Base64.DEFAULT);
        }
    }


}
