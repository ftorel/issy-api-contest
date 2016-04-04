package contest.api.com.apicontest.common.utils;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * Created by floriantorel on 31/01/16.
 */
public class MemoryUtils {


    public static long getAvailableInternalStorage(){
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getAvailableBlocksLong();
        long availableStorage = availableBlocks * blockSize;
        return availableStorage;
    }
}
