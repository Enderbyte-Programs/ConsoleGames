package enderbyteprograms.consolegames;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import enderbyteprograms.consolecolours;
import enderbyteprograms.consolegames.config.Cfile;
import enderbyteprograms.consolegames.games.Game;

public class shared {
    public static List<String> myoptions = new ArrayList<String>();
    public static List<Game> games = new ArrayList<Game>();
    public static String crashstatus = "";
    public static Cfile configdat;
    public static void defaultCrashHandler(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        shared.crashstatus = sw.toString();
        
    }
    public static void EarlyLoadCrash(Exception e) {
        System.out.println(consolecolours.RED_BRIGHT + "A critical early loading error occured.");
        e.printStackTrace();
        System.out.println(consolecolours.RESET);
        System.exit(-1);
}
}
