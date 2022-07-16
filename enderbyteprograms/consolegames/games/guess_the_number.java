package enderbyteprograms.consolegames.games;

import enderbyteprograms.enderlib;
import enderbyteprograms.consolegames.shared;
import enderbyteprograms.consolecolours;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class guess_the_number implements Game{
    public static String name = "Guess the Number";
    private void winhandler(Boolean won,String answer) {
        List<String> o = new ArrayList<String>();
        o.add("Return to menu");
        o.add("Play again");
        if (Objects.equals(answer,"__NONE__")) {
            //Assume not answer
            enderlib.clearscreen();
            if (won) {
                int r = enderlib.menu("",consolecolours.GREEN + "You win!" + consolecolours.RESET,o);
                if (r==1) {
                    play();
                }
            }
            if (!won) {
                int r = enderlib.menu("",consolecolours.RED_BRIGHT + "You lose :(" + consolecolours.RESET,o);
                if (r==1) {
                    play();
                }
            }
        }
        else {
            enderlib.clearscreen();
            if (won) {
                int r = enderlib.menu("The answer was "+answer,consolecolours.GREEN + "You win!" + consolecolours.RESET,o);
                if (r==1) {
                    play();
                }
            }
            if (!won) {
                int r = enderlib.menu("The answer was "+answer,consolecolours.RED_BRIGHT + "You lose :(" + consolecolours.RESET,o);
                if (r==1) {
                    play();
                }
            }
        }
    }
    public int play() {
        try {
            Boolean gameon = true;
            Boolean won = false;
            List<String> guessed = new ArrayList<String>();
            int guesses = 10;
            int answ = enderlib.randint(0,100);
            
            while (gameon) {
                enderlib.clearscreen();
                if (guesses<0) {
                    gameon = false;
                    break;
                }
                System.out.println("Guess The Number\n");
                System.out.print("Guesses: ");
                System.out.println(guesses);
                System.out.print("Guessed: ");
                if (guessed != null) {
                    System.out.println(enderlib.join(guessed,", "));
                }
                System.out.println("");
                String _guess = enderlib.input("What number do you guess: ");
                int guess;
                try {
                    guess = Integer.parseInt(_guess);
                } catch (NumberFormatException e) {
                    System.out.println("Please use a valid number!");
                    enderlib.delay(500);
                    //int f = 100/0;//For testing exceptions handler
                    continue;
                }
                if (guessed.contains(_guess)) {
                    System.out.println("You have already guessed this!");
                    enderlib.delay(500);
                    continue;
                }
                if (guess < 0 || guess > 100) {
                    System.out.println("Unnaceptable number.");
                    enderlib.delay(500);
                    continue;
                }
                else {
                    guesses = guesses - 1;
                    guessed.add(_guess);
                    if (guess==answ) {
                        won = true;
                        gameon = false;
                    } else {
                        if (guess < answ) {
                            System.out.println("You guessed too low.");
                            enderlib.delay(500);
                        }
                        if (guess > answ) {
                            System.out.println("You guessed too high.");
                            enderlib.delay(500);
                        }
                    }
                }
            }
            winhandler(won,Integer.toString(answ));
        } catch (Exception e) { //Ambiguous error checking, will set MAIN stack trace
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            shared.crashstatus = sw.toString();
            return 1;
        }
        return 0;
    }
    public void init() {
        shared.myoptions.add(name);
        shared.credits.add("Guess The Number by Enderbyte Programs LLC");
        System.out.println("Guess The Number is initialized");
    }
}
