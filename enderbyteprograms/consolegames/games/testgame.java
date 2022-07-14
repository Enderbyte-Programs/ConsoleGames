package enderbyteprograms.consolegames.games;

import enderbyteprograms.enderlib;
import enderbyteprograms.consolegames.shared;

public class testgame implements Game{ //MAKE SURE TO INCLUDE IMPLEMENTS GAME otherwise it will not be able to be registered
    public static String name = "Test Game"; //SET THIS to the name that you want this to have

    public int play() { //All play starts from this method.
        enderlib.system("echo hi");
        System.out.println("If you can see this, the test game worked");
        enderlib.input("Press enter to quit");
        
        return 0;
    }
    public void init() {
        shared.myoptions.add(name);
        System.out.println("Test game is initialized");
    }
}
