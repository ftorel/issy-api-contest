package contest.api.com.apicontest.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import contest.api.com.apicontest.Constant;
import contest.api.com.apicontest.common.utils.NetworkUtils;


/**
 * Created by floriantorel on 12/02/16.
 */
public class GETService extends IntentService {

    public GETService() {
        super("GETService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Bundle bundle = intent.getExtras();

        String url = bundle.getString(Constant.INTENT.URL);
        final String action = bundle.getString(Constant.INTENT.ACTION);
        final String path = bundle.getString(Constant.INTENT.PATH);


        NetworkUtils.IDownloadProgressListener iDownloadProgressListener = new NetworkUtils.IDownloadProgressListener() {
            @Override
            public void onComplete(String response) {
                sendBroadCastResponse(action, response, path);
            }

            @Override
            public void onFailure(Exception exception) {
                sendBroadCastException(action, exception);
            }
        };

        NetworkUtils.getRequest(url, path, iDownloadProgressListener);

    }


    private void sendBroadCastResponse( String action, String data, String path ){
        sendBroadCast(action, data, path,null);
    }

    private void sendBroadCastException( String action, Exception e ){
        sendBroadCast(action,"", "", e);
    }

    private void sendBroadCast( String action, String data, String path, Exception e ){
        Intent intent = new Intent();
        intent.setAction(action);

        if ( ! path.equals("") ){
            intent.putExtra(Constant.INTENT.PATH,path);
        }

        intent.putExtra(Constant.INTENT.DATA, data);

        if ( e != null ){
           intent.putExtra(Constant.INTENT.EXCEPTION, e);
        }

        sendBroadcast(intent);
    }

}
