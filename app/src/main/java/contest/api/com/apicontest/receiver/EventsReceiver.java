package contest.api.com.apicontest.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import contest.api.com.apicontest.Constant;
import contest.api.com.apicontest.common.utils.FilesUtils;
import contest.api.com.apicontest.logic.EventManager;

/**
 * Created by floriantorel on 04/03/16.
 */
public class EventsReceiver extends BroadcastReceiver {

    private static final String TAG = EventsReceiver.class.getName();

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();

        if ( intent.hasExtra( Constant.INTENT.EXCEPTION) ){

            Exception e = (Exception) bundle.getSerializable(Constant.INTENT.EXCEPTION);

            Log.e(TAG, e.toString() );

        } else if ( intent.hasExtra( Constant.INTENT.DATA ) ){

            String data = bundle.getString(Constant.INTENT.DATA);
            String path = bundle.getString(Constant.INTENT.PATH);

            if ( ! data.equals("") ){
                Log.e(TAG, data);
                Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
            }

            data = FilesUtils.readFileToString(path);

            if ( ! data.equals("") ){
                EventManager.getInstance().parseEvents(data);
                sendBroadcast(context);
            }

        }

    }

    private void sendBroadcast( Context context ){
        Intent intent = new Intent( Constant.FILTER.EVENTS );
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

}
