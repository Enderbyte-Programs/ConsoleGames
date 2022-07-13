package enderbyteprograms.consolegames.config;

import java.util.Objects;
import enderbyteprograms.enderlib;

public class Cnode {
    public String name;
    public String data;
    public Boolean isnum;
    public String display;
    public Cnode(String nme,String dat,Boolean usenum) {
        name = nme;
        data = dat;
        isnum = usenum;
        display = name + "=:=" + data;
    }
    public void update() {
        while (true){
            enderlib.clearscreen();
            System.out.println("The current value is " + data + "\nTo not change data, Press enter.\n");
            String newdata = enderlib.input("New value:");
            if (newdata.contains("=:=")) {
                System.out.println("New value contains illegal string.");
                continue;
            }
            if (Objects.equals(newdata,"")) {
                break;
            }
            
            if (isnum) {
                try {
                    double nd = Double.parseDouble(newdata);
                } catch (NumberFormatException e) {
                    System.out.println("Please provide a valid number!");
                    enderlib.delay(500);
                    continue;
                }
            }
            data = newdata;
            display = name + "=:=" + newdata;
            break;
        }
        System.out.println("Changed data successfully!");
        enderlib.delay(500);
    }
}
