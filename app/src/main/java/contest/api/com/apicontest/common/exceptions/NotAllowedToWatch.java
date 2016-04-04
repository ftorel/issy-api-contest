package contest.api.com.apicontest.common.exceptions;

/**
 * Created by floriantorel on 29/01/16.
 */
public class NotAllowedToWatch extends Exception {
    public NotAllowedToWatch() {
    }

    @Override
    public String toString() {
        return "The user is able to watch the media only during the flight";
    }
}
