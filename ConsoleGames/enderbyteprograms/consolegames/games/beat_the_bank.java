package enderbyteprograms.consolegames.games;

import enderbyteprograms.consolegames.shared;
import java.util.ArrayList;
import java.util.List;
import enderbyteprograms.enderlib;

public class beat_the_bank implements Game{
    public static String name = "Beat The Bank";
    public int play() {
        try {
            //game here
            List<String> ol = new ArrayList<String>();
            ol.add("Quit");
            ol.add("Play");
            ol.add("How to Play");
            while (true)
            {
                int m = enderlib.menu("Welcome to Beat The Bank, " + shared.configdat.findbyname("username").data + "!","Menu",ol);
                if (m==0) {
                    break;
                }
            }
        } catch (Exception e) { //Ambiguous error checking, will set MAIN stack trace
            shared.defaultCrashHandler(e);
            return 1;
        }
        return 0;
    }
    public void init() {
        shared.myoptions.add(name);
        System.out.println("Beat The Bank is initialized");
    }
}
