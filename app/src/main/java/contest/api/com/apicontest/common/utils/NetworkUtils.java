package contest.api.com.apicontest.common.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import contest.api.com.apicontest.common.exceptions.ErrorOccured;

/**
 * Created by floriantorel on 20/11/15.
 */
public class NetworkUtils {

    private static String TAG = NetworkUtils.class.getName();

    public static final int CONNECT_TIMEOUT = 5000;
    public static final int READ_TIMEOUT = 15000;

    public interface IDownloadProgressListener {
        void onComplete( String response );
        void onFailure(Exception exception);
    }


    public static void postRequest( String URL, String data, IDownloadProgressListener iDownloadProgressListener ){

        String response = "";

        try{

            URL url = new URL(URL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String a = data;
            String input = a.replaceAll("\\\\", "");

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            int responseCode=conn.getResponseCode();
            String responseMessage = conn.getResponseMessage();

            if ( responseCode == 200 ){

                String line;

                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ( (line=br.readLine()) != null ) {
                    response+=line;
                }

                iDownloadProgressListener.onComplete(response);

            } else {

                iDownloadProgressListener.onFailure(new ErrorOccured());

            }

            conn.disconnect();

        } catch ( Exception e){
            e.printStackTrace();
            iDownloadProgressListener.onFailure(new ErrorOccured());
        }

    }

    public static void getRequest(String URL, String filePath, IDownloadProgressListener iDownloadProgressListener ) {

        URL url;
        File file;
        String response = "";

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

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {

        }

        try {
            url = new URL( URL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setReadTimeout(READ_TIMEOUT);


            int responseCode=conn.getResponseCode();
            String responseMessage = conn.getResponseMessage();

            // Create file object and check if URI is valid
            try {
                file = new File(filePath);
            }
            catch (Exception ex) {
                // An error occured while opening file, log it
                Log.e(TAG, "Error while opening file", ex);
                iDownloadProgressListener.onFailure(ex);
                return;
            }

            if ( ! file.getParentFile().exists() ){
                file.getParentFile().mkdir();
            }


            if (responseCode == 200) {

                String line;

                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ( (line=br.readLine()) != null ) {
                    response+=line;
                }

                FileOutputStream stream = new FileOutputStream(file);
                try {
                    stream.write(response.getBytes());
                } finally {
                    stream.close();
                }

                iDownloadProgressListener.onComplete("");

            }  else {

                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }

                Log.e(TAG, response);
                iDownloadProgressListener.onComplete(response);
            }


        } catch (Exception e) {
            Log.e(TAG, e.toString());
            iDownloadProgressListener.onFailure(e);
        }
    }

}
