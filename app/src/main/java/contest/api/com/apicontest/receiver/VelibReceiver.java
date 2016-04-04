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
import contest.api.com.apicontest.logic.VelibManager;

/**
 * Created by floriantorel on 18/03/16.
 */
public class VelibReceiver extends BroadcastReceiver {

    private final String TAG = VelibReceiver.class.getName();

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();

        String path = bundle.getString(Constant.INTENT.PATH);

        if ( intent.hasExtra( Constant.INTENT.EXCEPTION) ){

            Exception e = (Exception) bundle.getSerializable(Constant.INTENT.EXCEPTION);
            Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
            FilesUtils.delete(path);
            Log.e(TAG, e.toString());

        } else if ( intent.hasExtra( Constant.INTENT.DATA ) ){

            String data = bundle.getString(Constant.INTENT.DATA);

            if ( data.equals("") ){
                data = FilesUtils.readFileToString(path);
                VelibManager.getInstance().parseVelibsData(data);
                sendBroadcast(context);
            } else {
                FilesUtils.delete(path);
                Log.e(TAG, data);
                Toast.makeText(context,data,Toast.LENGTH_SHORT).show();
            }

        }

    }

    private void sendBroadcast(Context context){
        Intent intent = new Intent(Constant.ACTION.UPDATE_DETAIL);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

}
