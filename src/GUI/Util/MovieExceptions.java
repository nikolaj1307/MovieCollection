package GUI.Util;

import javafx.scene.control.ButtonType;

public class MovieExceptions extends Exception {

    public MovieExceptions() {
    }

    public MovieExceptions(String msg) {
        super(msg);
    }

    public MovieExceptions(String msg, Exception cause) {
        super(msg, cause);
    }

    public MovieExceptions(Throwable cause) {
        super(cause);
    }

    public MovieExceptions(String msg, Throwable cause, boolean enableSuppresion, boolean writableStackTrace) {
        super(msg, cause, enableSuppresion, writableStackTrace);
    }
}

