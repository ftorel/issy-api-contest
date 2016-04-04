package contest.api.com.apicontest.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import contest.api.com.apicontest.Constant;
import contest.api.com.apicontest.common.utils.NetworkUtils;

/**
 * Created by floriantorel on 30/03/16.
 */
public class POSTService extends IntentService {

    public POSTService() {
        super("POSTService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Bundle bundle = intent.getExtras();

        String url = bundle.getString(Constant.INTENT.URL);
        final String action = bundle.getString(Constant.INTENT.ACTION);
        final String data =  bundle.getString(Constant.INTENT.DATA);


        NetworkUtils.IDownloadProgressListener iDownloadProgressListener = new NetworkUtils.IDownloadProgressListener() {
            @Override
            public void onComplete(String response) {
                sendBroadCastResponse(action,response);
            }

            @Override
            public void onFailure(Exception exception) {
                sendBroadCastException(action,exception);
            }
        };

        NetworkUtils.postRequest(url, data, iDownloadProgressListener);

    }

    private void sendBroadCastResponse( String action, String data ){
        sendBroadCast(action, data,null);
    }

    private void sendBroadCastException( String action, Exception e ){
        sendBroadCast(action,"", e);
    }

    private void sendBroadCast( String action, String data,  Exception e ){
        Intent intent = new Intent();
        intent.setAction(action);

        intent.putExtra(Constant.INTENT.DATA, data);

        if ( e != null ){
            intent.putExtra(Constant.INTENT.EXCEPTION, e);
        }

        sendBroadcast(intent);
    }


}
