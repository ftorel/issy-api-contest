package contest.api.com.apicontest.common.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by floriantorel on 19/01/16.
 */
public class BitmapUtils {

    public static Bitmap getBitmapFromFile ( String path ){
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(path,bmOptions);
        return bitmap;
    }
}
