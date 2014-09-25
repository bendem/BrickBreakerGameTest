package be.bendem.gametest.core.logging;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author bendem
 */
public class Logger {

    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss SSS");

    public static void debug(String string) {
        debug(string, null);
    }

    public static void debug(String string, Throwable throwable) {
        log(string, throwable, LogLevel.DEBUG);
    }

    public static void info(String string) {
        info(string, null);
    }

    public static void info(String string, Throwable throwable) {
        log(string, throwable, LogLevel.INFO);
    }

    public static void warning(String string) {
        warning(string, null);
    }

    public static void warning(String string, Throwable throwable) {
        log(string, throwable, LogLevel.WARNING);
    }

    public static void error(String string) {
        error(string, null);
    }

    public static void error(String string, Throwable throwable) {
        log(string, throwable, LogLevel.ERROR);
    }

    private static void log(String string, Throwable throwable, LogLevel level) {
        PrintStream out = level.isError() ? System.err : System.out;
        out.println("[" + level.getName() + "] [" + getTime() + "] " + string);
        if(throwable != null) {
            throwable.printStackTrace(out);
        }
    }

    private static String getTime() {
        return TIME_FORMAT.format(new Date());
    }

    private enum LogLevel {
        DEBUG(false, "DEBUG"), INFO(false, "INFO "), WARNING(true, "WARNI"), ERROR(true, "ERROR");
        private final boolean error;
        private final String name;

        LogLevel(boolean error, String name) {
            this.error = error;
            this.name = name;
        }

        public boolean isError() {
            return error;
        }

        public String getName() {
            return name;
        }
    }

}
