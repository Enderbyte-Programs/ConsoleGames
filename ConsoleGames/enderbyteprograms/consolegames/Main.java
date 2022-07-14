package enderbyteprograms.consolegames;

import enderbyteprograms.enderlib; //Shared library for my software
import enderbyteprograms.consolegames.games.*;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import enderbyteprograms.consolecolours;
import enderbyteprograms.consolegames.config.Cfile;

public class Main {
    
    public static void init() {
        shared.myoptions.add("Exit");
        shared.myoptions.add("Options");
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
    public static void main(String[] args) {
        System.out.println("Consolegames: Loading...");
        try 
        {init(); //Adding games to list
        cfg(); //Initializing global configuration
        iloop();
        
        int status = 0;
        while (true) {
            
            String _header = consolecolours.CYAN + "ConsoleGames v0.2-alpha (c) 2022 Enderbyte Programs" + consolecolours.RESET;
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
                    System.out.println(consolecolours.RED_BRIGHT + "A critical error occured.");
                    e.printStackTrace();
                    System.out.println(consolecolours.RESET);
                    System.exit(-1);
                }
                continue;
            }
            Game g = shared.games.get(command-2);
            status = g.play();
        }
        System.out.println("Thank you for using consolegames!");
        //System.out.println(shared.games);
        //System.out.println(shared.myoptions);
        enderlib.closeInput();}
        catch (Exception e) {
            shared.EarlyLoadCrash(e);
        }
    }
}