package enderbyteprograms;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    public static int system(String command) {
        String s;
        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            BufferedReader br = new BufferedReader(
                new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null)
                System.out.println(s);
            p.waitFor();
            
            p.destroy();
        return p.exitValue();
        } catch (Exception e) {
            return -1;
        }
        
    
    }
    public static String getcmdasstr(String command) {
        String s;
        Process p;
        StringBuilder sb = new StringBuilder();
        try {
            p = Runtime.getRuntime().exec(command);
            BufferedReader br = new BufferedReader(
                new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null)
                sb.append(s);
            p.waitFor();
            
            p.destroy();
            return sb.toString();
        
        } catch (Exception e) {
            return "error";
        }
    }

    public static void clearscreen() {
        Process p;
        String s;
        try {
            p = Runtime.getRuntime().exec("clear");
            BufferedReader br = new BufferedReader(
                new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null)
                System.out.println(s);
            p.waitFor();
            
            p.destroy();
        } catch (Exception e) {} 
        System.out.print("\033[H"); 
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
            //clearscreen();
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
    public static boolean filexists(String fpn) {
        File f = new File(fpn);
        if(f.exists() && !f.isDirectory()) { 
            return true;
        }
        return false;

        
    }
    public static void printrep(String msg, int times,String end) {
        int i;
        for (i=0;i<times;i++) {
            System.out.print(msg);
        }
        System.out.print(end);
    }
    public static void cgoto(int x, int y) {
        char escCode = 0x1B;
        System.out.print(String.format("%c[%d;%df",escCode,y,x));

    }
    public static long download(String url, String fileName) throws IOException {
        try (InputStream in = URI.create(url).toURL().openStream()) {
            return Files.copy(in, Paths.get(fileName));
        }
    }
}
