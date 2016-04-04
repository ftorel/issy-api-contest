package contest.api.com.apicontest.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import contest.api.com.apicontest.Constant;
import contest.api.com.apicontest.logic.BeParkManager;

/**
 * Created by floriantorel on 30/03/16.
 */
public class BeParkReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();

        if ( intent.hasExtra(Constant.INTENT.EXCEPTION) ){

        } else {
            String data = bundle.getString(Constant.INTENT.DATA);
            BeParkManager.getInstance().parseBeParkData(data);
            sendBroadCast(context);
        }

    }


    private void sendBroadCast(Context context){
        Intent intent = new Intent(Constant.ACTION.UPDATE_DETAIL);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

}
