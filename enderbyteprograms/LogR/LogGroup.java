package enderbyteprograms.LogR;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import enderbyteprograms.consolecolours;
import enderbyteprograms.enderlib;

public class LogGroup {
    public String name;
    public List<String> messages = new ArrayList<String>();
    public Logfile sy;
    
    public LogGroup(Logfile parent,String n) {
        name = n;
        sy = parent;
    }
    public void newMessage(String data,LogSeverity severity) {
        messages.add(data);
        LogMessage ms = new LogMessage(this,data,severity);
        sy.messages.add(ms);
        try {
            sy.save(ms);
        } catch (IOException e) {
            System.out.println(consolecolours.RED_BRIGHT + "IOExcpetion in logger" + consolecolours.RESET);
            try
            {sy.rcfile();}
            catch (Exception k) {
                enderlib.clearscreen();
        
                System.out.println(consolecolours.RED_BRIGHT + "A critical error occured.");
                k.printStackTrace();
                System.out.println(consolecolours.RESET);
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                k.printStackTrace(pw);
                System.exit(-1);
            }
        }
    }
    public void info(String data) {
        newMessage(data,LogSys.INFO);
    }
    public void warn(String data) {
        newMessage(data,LogSys.WARNING);
    }
    public void debug(String data) {
        newMessage(data,LogSys.DEBUG);
    }
    public void error(String data) {
        newMessage(data,LogSys.ERROR);
    }
    public void fatal(String data) {
        newMessage(data,LogSys.FATAL);
    }
}
