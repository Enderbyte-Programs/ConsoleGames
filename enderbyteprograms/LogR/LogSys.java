package enderbyteprograms.LogR;

public class LogSys {
    public static long syst = System.nanoTime();
    public static final LogSeverity DEBUG = new LogSeverity("DEBUG",0);
    public static final LogSeverity INFO = new LogSeverity("INFO",1);
    public static final LogSeverity WARNING = new LogSeverity("WARNING",10);
    public static final LogSeverity ERROR = new LogSeverity("ERROR",50);
    public static final LogSeverity FATAL = new LogSeverity("FATAL",99);
    public static void init() {
        syst = System.nanoTime();
    }
    public static long getms() {
        long ct = System.nanoTime();
        return (ct - syst) / 100000;
    }
}
