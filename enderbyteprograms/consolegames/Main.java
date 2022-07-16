package enderbyteprograms.consolegames;

import enderbyteprograms.enderlib; //Shared library for my software
import enderbyteprograms.consolegames.games.*;
import enderbyteprograms.consolegames.stats.sfile;
import enderbyteprograms.consolegames.stats.sgroup;
import enderbyteprograms.consolegames.stats.snode;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import enderbyteprograms.consolecolours;
import enderbyteprograms.consolegames.config.Cfile;

public class Main {
    private static sgroup s;
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
        System.out.println("Consolegames: Loading...");
        
        try 
        {
            init(); //Adding games to list
        cfg(); //Initializing global configuration
        sinit();
        iloop();
        snode __s = s.locate("Times Started");
        __s.set(__s.value + 1);
        shared.stats.save();
        
        //enderlib.delay(1000); //To show log
        int status = 0;
        while (true) {
            
            String _header = consolecolours.CYAN + "ConsoleGames Version 0.3.1" + consolecolours.RESET;
            if (status!=0) {
                _header = _header + consolecolours.RED_BRIGHT + "\nWe are sorry, but your previous game crashed.\n===STACKTRACE===:\n";
                _header = _header + shared.crashstatus + consolecolours.RESET;
            }
            int command = enderlib.menu(_header,"Main Menu",shared.myoptions);
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
            Game g = shared.games.get(command-4);
            status = g.play();
            snode _s = s.locate("Games Played");
            
            _s.set(_s.value + 1);
            shared.stats.save();
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