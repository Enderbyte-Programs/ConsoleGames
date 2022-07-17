package enderbyteprograms.consolegames;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import enderbyteprograms.consolecolours;
import enderbyteprograms.enderlib;
import enderbyteprograms.consolegames.config.Cfile;
import enderbyteprograms.consolegames.games.Game;
import enderbyteprograms.consolegames.stats.sfile;

public class shared {
    public static List<String> myoptions = new ArrayList<String>();
    public static List<Game> games = new ArrayList<Game>();
    public static String crashstatus = "";
    public static Cfile configdat;
    public static sfile stats;
    public static List<String> credits = new  ArrayList<String>();
    public static List<String> assetslist = new ArrayList<String>();

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
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        System.exit(-1);
    }
    public static void FatalCrash(Exception e) {
        enderlib.clearscreen();
        System.out.println(consolecolours.RED_BRIGHT + "A critical error occured.");
        e.printStackTrace();
        System.out.println(consolecolours.RESET);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        System.exit(-1);
    }
}
