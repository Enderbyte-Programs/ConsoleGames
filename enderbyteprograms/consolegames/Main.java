package enderbyteprograms.consolegames;

import enderbyteprograms.enderlib; //Shared library for my software
import enderbyteprograms.LogR.LogGroup;
import enderbyteprograms.consolegames.games.*;
import enderbyteprograms.consolegames.stats.sfile;
import enderbyteprograms.consolegames.stats.sgroup;
import enderbyteprograms.consolegames.stats.snode;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import enderbyteprograms.consolecolours;
import enderbyteprograms.consolegames.config.Cfile;
import enderbyteprograms.consolegames.sound.Sound;

public class Main {
    private static sgroup s;
    private static LogGroup logger = shared.lf.register("Main");
    public static void init() {
        shared.myoptions.add("Exit");
        shared.myoptions.add("Options");
        shared.myoptions.add("Statistics");
        shared.myoptions.add("Credits");
        shared.credits.add("Consolegames (c) 2022 Enderbyte Programs LLC, some rights reserved.");
        shared.games.add(new testgame());
        shared.games.add(new guess_the_number());
        shared.games.add(new beat_the_bank());
        
        //For mods, register here by adding to the games list
    }

    private static void iloop() {
        int i;
        for (i=0;i<shared.games.size();i++) {
            shared.games.get(i).init();
        }
    }
    
    public static void cfg() {
        //Registering configurations
        Path p = Paths.get(enderlib.getcwd() + "/cfg.txt");
        try {
            Files.createFile(p);
        }catch (FileAlreadyExistsException l) {
            //DO NOTHING
        
        } catch (IOException e) {
            shared.EarlyLoadCrash(e);
        }
        try {
            shared.configdat = new Cfile(enderlib.getcwd() + "/cfg.txt");
        } catch (IOException e) {
            shared.EarlyLoadCrash(e);
        }
        if (!shared.configdat.cexists("username")) {
            shared.configdat.newNode("username","User",false);
            try {
                shared.configdat.save();
            } catch (IOException e) {
                shared.EarlyLoadCrash(e);
            }
        }

    }

    public static void sinit() {
        //Initializes statistics
        Path p = Paths.get(enderlib.getcwd() + "/stats.txt");
        try {
            Files.createFile(p);
        } catch (FileAlreadyExistsException l) {
            //DO NOTHING
        
        } catch (IOException e) {
            shared.EarlyLoadCrash(e);
        } try {
            shared.stats = new sfile(enderlib.getcwd() + "/stats.txt");
        }catch (IOException e) {
            shared.EarlyLoadCrash(e);
        }
        s = new sgroup("System");
        s.newnode("Games Played",0);
        s.newnode("Times Started",0);
        shared.stats.register(s);
        
    }
    public static void main(String[] args) {
        //LogSys.init();
        logger.info("Starting Consolegames");
        shared.startMsg();
        System.out.print("Starting Consolegames\r");
        try 
        {
            logger.info("Preparing games");
            init(); //Adding games to list
            logger.info("Preparing Configurations");
        cfg(); //Initializing global configuration
        logger.info("Preparing Statistics");
        sinit();
        logger.info("Initializing games...");

        iloop();
        snode __s = s.locate("Times Started");
        __s.set(__s.value + 1);
        shared.stats.save();
        Sound menumusic = new Sound("/menu.wav");
        enderlib.delay(5000); //To show log
        int status = 0;
        int sa = 0;
        int k;

        List<String> nfa = new ArrayList<String>();
        for (k=0;k<shared.assetslist.size();k++) {
            String nfp = shared.assetslist.get(k);
            if (enderlib.filexists(nfp)) {
                continue;
            }
            else {
                sa = 1;
                nfa.add(nfp);
                
            }
        }
        int m;
        String _header_ =consolecolours.YELLOW+ "Some assets could not be found. Some games may crash";
        for (m=0;m<nfa.size();m++) {
            _header_ += nfa.get(m);
            _header_ += "\n";
        }
        _header_ += consolecolours.RESET;

        menumusic.play();
        logger.info("Consolegames is started");
        int command = 0;
        while (true) {
            
            String _header = consolecolours.CYAN + "ConsoleGames Version 0.3.3" + consolecolours.RESET;
            if (status!=0) {
                logger.error("Error in game " + shared.myoptions.get(command) + ".\n" + shared.crashstatus);
                _header = _header + consolecolours.RED_BRIGHT + "\nWe are sorry, but your previous game crashed.\n===STACKTRACE===:\n";
                _header = _header + shared.crashstatus + consolecolours.RESET;
            }
            if (sa!=0) {
                _header += "\n";
                _header += _header_;
            }
            command = enderlib.menu(_header,"Main Menu",shared.myoptions);
            if (command==0) {
                //exiting
                break;
            }
            if (command==1) {
                try {
                    shared.configdat.makemenu();
                } catch (IOException e) {
                    shared.FatalCrash(e);
                }
                continue;
            }
            if (command==2) {
                shared.stats.createMenu();
                continue;
            }
            if (command==3) {
                System.out.println("Credits");
                System.out.println("--------------------------------");
                int i;
                for (i=0;i<shared.credits.size();i++) {
                    System.out.println(shared.credits.get(i));
                }
                System.out.println("--------------------------------");
                enderlib.input("Press enter to return to menu...");
                continue;
                
            }
            menumusic.stop();
            Game g = shared.games.get(command-4);
            status = g.play();
            snode _s = s.locate("Games Played");
            
            _s.set(_s.value + 1);
            shared.stats.save();
            menumusic.play();
        }
        System.out.println("Thank you for using consolegames!");
        //System.out.println(shared.games);
        //System.out.println(shared.myoptions);
        enderlib.closeInput();}
        catch (Exception e) {
            shared.FatalCrash(e);
        }
    }
}