package enderbyteprograms.consolegames.games;

import enderbyteprograms.consolegames.shared;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import enderbyteprograms.consolecolours;
import enderbyteprograms.enderlib;
import enderbyteprograms.consolegames.stats.sgroup;
import enderbyteprograms.consolegames.stats.snode;

public class beat_the_bank implements Game{
    public static String name = "Beat The Bank";
    private sgroup s = new sgroup(name);
    public int play() {
        try {
            //game here
            List<String> ol = new ArrayList<String>();
            List<Integer> pl = new ArrayList<Integer>();
            pl.add(0);
            pl.add(10);
            pl.add(50);
            pl.add(100);
            pl.add(150);
            pl.add(200);
            pl.add(0);
            pl.add(0);
            pl.add(250);
            pl.add(300);
            ol.add("Quit");
            ol.add("Play");
            ol.add("How to Play");
            while (true)
            {
                double tmoney = s.locate("Money").value;
                int m = enderlib.menu("Welcome to Beat The Bank, " + shared.configdat.findbyname("username").data + "!","Menu",ol);
                if (m==0) {
                    break;
                }

                if (m==1) {
                    boolean gameon = true;
                    int money = 0;
                    int vault = 0;
                    while (gameon) {
                        enderlib.clearscreen();
                        vault += 1;
                        System.out.println("You have $"+money);
                        String ov = enderlib.input("Would you like to open vault " + vault + "?");
                        if (Objects.equals(ov.substring(0,1),"y")) {
                            int ir = choose(pl);
                            if (ir==0) {
                                money = 0;
                                gameon = false;
                                snode l = s.locate("Alarms");
                                l.set(l.value + 1);
                                //System.out.println(s.locate("Alarms").value);
                                System.out.println(consolecolours.YELLOW + "You got an alarm!" + consolecolours.RESET);
                                System.out.println("You finished with no money.");
                                enderlib.delay(2000);
                                break;
                            } else {
                                money += ir;
                                System.out.println("Vault " + vault + " contained $" + ir + " for a total of $" + money);
                                enderlib.delay(1000);
                            }
                        } else {
                            gameon = false;
                            System.out.println("You finished with $"+money+"!");
                            tmoney += money;
                            snode _s = s.locate("Money");
                            _s.set(tmoney);
                            System.out.println("You now have a total of $" + tmoney + "!");
                            enderlib.delay(2000);
                        }
                    }
                    snode _s = s.locate("Plays");
                    _s.set(_s.value + 1);

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
        s.newnode("Alarms",0);
        s.newnode("Plays",0);
        s.newnode("Money",0);
        shared.stats.register(s);
        
        System.out.println("Beat The Bank is initialized");
    }

    public int choose(List<Integer> pl) {
        return pl.get(enderlib.randint(0,pl.size()-1));
    }
}
