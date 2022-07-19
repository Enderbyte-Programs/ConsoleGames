package enderbyteprograms.consolegames.games;

import enderbyteprograms.enderlib;
import enderbyteprograms.LogR.LogGroup;
import enderbyteprograms.consolegames.shared;

public class testgame implements Game{ //MAKE SURE TO INCLUDE IMPLEMENTS GAME otherwise it will not be able to be registered
    private LogGroup logger = shared.lf.register("Test Game");
    public static String name = "Test Game"; //SET THIS to the name that you want this to have

    public int play() { //All play starts from this method.
        System.out.println("If you can see this, the test game worked");
        logger.info("test");
        enderlib.input("Press enter to quit");
        
        return 0;
    }
    public void init() { //Game is initialized here
        shared.myoptions.add(name);
        logger.info("Testgame is initialized");
        //System.out.println("Test game is initialized");
    }
}
