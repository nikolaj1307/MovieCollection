package GUI.Util;

// Utility class to represent custom exceptions related to movie-related operations
public class MovieExceptions extends Exception {

    // Default constructor
    public MovieExceptions() {
    }

    // Constructor with a message
    public MovieExceptions(String msg) {
        super(msg);
    }

    // Constructor with a message and a cause (nested exception)
    public MovieExceptions(String msg, Exception cause) {
        super(msg, cause);
    }

    // Constructor with a cause (nested exception)
    public MovieExceptions(Throwable cause) {
        super(cause);
    }

    // Constructor with a message, cause, suppression enable/disable, and writable stack trace enable/disable
    public MovieExceptions(String msg, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(msg, cause, enableSuppression, writableStackTrace);
    }
}
