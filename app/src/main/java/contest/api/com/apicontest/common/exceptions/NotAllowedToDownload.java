package contest.api.com.apicontest.common.exceptions;

/**
 * Created by floriantorel on 29/01/16.
 */
public class NotAllowedToDownload extends Exception {
    public NotAllowedToDownload() {
    }

    @Override
    public String toString() {
        return "The user is able to download only before the arrival time";
    }
}
