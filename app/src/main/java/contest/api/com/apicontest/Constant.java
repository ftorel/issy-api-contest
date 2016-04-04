package contest.api.com.apicontest;

/**
 * Created by floriantorel on 12/02/16.
 */
public class Constant {

    public class INTENT{
        final public static String URL = "url";
        final public static String ACTION = "action";
        final public static String PATH = "path";
        final public static String DATA = "data";
        final public static String EXCEPTION = "exception";

        final public static String POSITION = "position";
    }

    public class FILTER{
        final public static String EVENTS = "events";
    }

    public class ACTION{
        final public static String JSON_RECEIVER =  "contest.api.com.apicontest.EVENTS_RECEIVER";
        final public static String JSON_VELIB_RECEIVER =  "contest.api.com.apicontest.VELIB_RECEIVER";
        final public static String JSON_BEPARK_RECEIVER =  "contest.api.com.apicontest.BEPARK_RECEIVER";

        final public static String UPDATE_DETAIL = "update_detail";
    }


}
