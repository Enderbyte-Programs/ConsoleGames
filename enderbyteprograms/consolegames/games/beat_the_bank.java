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
                if (m==2) {
                    enderlib.clearscreen();
                    System.out.println("How to Play\n\nBeat the bank is a small game created in March 2021. It is based off of a Vancouver radio game show of the same name.\nThe point is to open as many vaults as you can without getting the alarm.");
                    enderlib.input("Press enter to return to menu...");
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
