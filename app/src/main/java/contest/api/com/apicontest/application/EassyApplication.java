package contest.api.com.apicontest.application;

import android.app.Application;

import com.koushikdutta.ion.Ion;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import contest.api.com.apicontest.logic.BeParkManager;
import contest.api.com.apicontest.logic.EventManager;
import contest.api.com.apicontest.logic.VelibManager;

/**
 * Created by floriantorel on 04/03/16.
 */
public class EassyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };

        Ion.getDefault(getApplicationContext()).getHttpClient().getSSLSocketMiddleware().setTrustManagers( trustAllCerts );

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            Ion.getDefault(getApplicationContext()).getHttpClient().getSSLSocketMiddleware().setSSLContext(sc);
        } catch (Exception e) {
        }

        EventManager.init(getApplicationContext());
        VelibManager.init(getApplicationContext());
        BeParkManager.init(getApplicationContext());

        BeParkManager.getInstance().getData();

        VelibManager.getInstance().updateVelibsData();


    }
}
