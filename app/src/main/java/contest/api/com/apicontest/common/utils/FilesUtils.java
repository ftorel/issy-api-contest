package contest.api.com.apicontest.common.utils;


import android.content.Context;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.CRC32;

/**
 * Created by floriantorel on 17/12/15.
 */
public class FilesUtils {

    private static String TAG = FilesUtils.class.getName();

    public static boolean isExist( String path ){
        File file = new File( path );
        return file.exists();
    }

    public static long getCRC32(File file) {
        try {
            CRC32 crc32 = new CRC32();

            BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(file));

            byte[] buffer = new byte[1024 * 64];
            int read;

            while ((read = inStream.read(buffer)) > 0)
                crc32.update(buffer, 0, read);

            inStream.close();

            return crc32.getValue();
        }
        catch (Exception ex) {
        }

        return -1;
    }

    public static void delete(String path){
        File file = new File(path);
        if (file.exists()){
            file.delete();
        }
    }

    public static String readFileToString(String path){

        File file = new File(path);

        int length = (int) file.length();

        byte[] bytes = new byte[length];

        String contents = "";

        try{
            FileInputStream in = new FileInputStream(file);

            try {
                in.read(bytes);
            } finally {
                in.close();
            }

            contents = new String(bytes);

        } catch (Exception e){

        }

        return contents;

    }


    protected static void saveObject( Object object , Context c, String name ) {
        FileOutputStream fos;
        try {
            fos = c.openFileOutput(name,
                    Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.flush();
            oos.close();
        } catch (Exception e){
            Log.e( TAG , e.toString() );
        }
    }

    protected static Object getObject( Context c, String name ){
        FileInputStream fis;
        Object o = null;
        try {
            fis = c.openFileInput(name);
            ObjectInputStream ois = new ObjectInputStream(fis);
            o = (Object) ois.readObject();
            fis.close();
        } catch (Exception e) {
            Log.e( TAG , e.toString() );
            return null;
        }
        return o;
    }
}
