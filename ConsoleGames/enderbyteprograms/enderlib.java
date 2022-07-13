package enderbyteprograms;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadLocalRandom;

public class enderlib {
    public static Scanner reader = new Scanner(System.in);
    public static String input(String prompt) {
        
        System.out.print(prompt+" ");
        String n = reader.nextLine();
        return n;
    }
    public static void closeInput() {
        reader.close();
    }
    public static void clearscreen() {
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }

    public static void delay(int ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e1) {
            
            e1.printStackTrace();// Assuming that this will NEVER happen
        }
    }

    public static int menu(String header,String title,List<String> options) {
        while (true) {
            clearscreen();
            System.out.println(header+"\n");
            System.out.println("====="+title+"=====");
            System.out.println("Please choose an option");
            System.out.println("--------------------------------");
            int size = options.size();
            int i;
            for (i = 0;i<size;i++) {
                String option = options.get(i);
                System.out.print(i);
                System.out.print(" - ");
                System.out.println(option);
            }
            System.out.println("--------------------------------\n");
            String p = input("Option:");
            int result;
            try {
                result = Integer.parseInt(p);
            } catch (NumberFormatException e) {
                System.out.println("Please provide a valid number!");
                result = -1;
                delay(500);
                continue;
                
            }
            if (result!=-1) {
                if (result > (size-1)) {
                    System.out.println("Not a valid option!");
                    delay(500);
                }
                else{
                    if (result < 0) {
                        System.out.println("Not a valid option!");
                        delay(500);
                    }else {
                        clearscreen();
                        return result;
                    }
                }
            }
        }
    }

    public static int randint(int min,int max) {
        int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
        return randomNum;
    }
    public static String join(List<String> l,String joiner) {
        String result = "";
        int i;
        if (l.size() == 0) {
            return "";
        }
        if (l.size() == 0) {
            return l.get(0);
        }
        for (i = 0;i < (l.size()-1);i++) {
            result = result + l.get(i);
            result = result + joiner;
        }
        result = result + l.get(l.size()-1);
        return result;

    }
    public static List<String> split(String val,String deli) {
        List<String> result = Arrays.asList(val.split(deli));
        return result;
    }
    public static String getcwd() {
        return System.getProperty("user.dir");
    }
    public static int find(List<String> opts,String finder) {
        int i;
        for (i=0;i<opts.size();i++) {
            if (Objects.equals(opts.get(i),finder)) {
                return i;
            }
        }
        return -1;
    }
}
