package enderbyteprograms.consolegames;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import enderbyteprograms.consolecolours;
import enderbyteprograms.enderlib;
import enderbyteprograms.LogR.LogGroup;
import enderbyteprograms.LogR.Logfile;
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
    public static int lines = Integer.parseInt(enderlib.getcmdasstr("tput lines"));
    public static int columns = Integer.parseInt(enderlib.getcmdasstr("tput cols"));
    public static Logfile lf = new Logfile(enderlib.getcwd() + "/consolegames.log");
    private static LogGroup logger = lf.register("Excpetions Handler");
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
        logger.fatal(sw.toString());
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
        logger.fatal(sw.toString());
        System.exit(-1);
    }
    public static void startMsg() {
        lines = Integer.parseInt(enderlib.getcmdasstr("tput lines"));
        columns = Integer.parseInt(enderlib.getcmdasstr("tput cols"));
        enderlib.clearscreen();
        String message = "Console Games";
        int ml = message.length();
        System.out.print("\033[H"); 
        int offset = Math.floorDiv(ml,2);
        enderlib.printrep("═",columns,"\n");
        enderlib.printrep("\n",Math.floorDiv(lines-2,2)-1,"");
        System.out.print("┌");
        enderlib.printrep("─",columns-2,"┐\n");
        System.out.print("│");
        enderlib.printrep(" ",columns/2-offset,"");
        System.out.print(message);
        enderlib.cgoto(columns-1,lines/2+1);
        System.out.print(" │\n");
        System.out.print("└");
        enderlib.printrep("─",columns-2,"┘");
        enderlib.printrep("\n",lines/2-2,"");
        
        enderlib.printrep("═", columns, "\033[A\r");
    }
}
