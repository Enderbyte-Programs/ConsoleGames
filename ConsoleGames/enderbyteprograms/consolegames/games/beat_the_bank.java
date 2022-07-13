package enderbyteprograms.consolegames.games;

import enderbyteprograms.consolegames.shared;

public class beat_the_bank implements Game{
    public static String name = "Beat The Bank";
    public int play() {
        try {
            //game here
        } catch (Exception e) { //Ambiguous error checking, will set MAIN stack trace
            shared.defaultCrashHandler(e);
            return 1;
        }
        return 0;
    }
}
