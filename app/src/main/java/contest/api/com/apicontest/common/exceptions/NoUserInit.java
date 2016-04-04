package contest.api.com.apicontest.common.exceptions;

/**
 * Created by floriantorel on 29/01/16.
 */
public class NoUserInit extends Exception {

    public NoUserInit() {
    }

    @Override
    public String toString() {
        return "You have to init a user to start to download";
    }
}
