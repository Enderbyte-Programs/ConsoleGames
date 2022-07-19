package enderbyteprograms.LogR;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import enderbyteprograms.file;

public class Logfile {
    public String filename;
    public List<LogGroup> lg = new ArrayList<LogGroup>();
    public List<LogMessage> messages = new ArrayList<LogMessage>();
    public file lf;
    public Logfile(String file) {
        filename = file;
        try {
            Files.createFile(Paths.get(filename));
        } catch (IOException f) {
            //DO NOTHING
        }
    }
    public void reload() throws IOException {
        String fulldata = "";
        int i;
        for (i=0;i<messages.size();i++) {
            LogMessage m = messages.get(i);
            fulldata += java.time.LocalDateTime.now().toString();
            fulldata += " ";
            fulldata += Long.toString(LogSys.getms());
            fulldata += " ";
            fulldata += m.severity.name;
            fulldata += " (";
            fulldata += Integer.toString(m.severity.slevel);
            fulldata += ") ";
            fulldata += m.group.name;
            fulldata += ": ";
            fulldata += m.data;
            fulldata += "\n";
        }
        lf = new file(filename,"m");
        lf.write(fulldata);
        lf.close();
    }
    public void save(LogMessage m) throws IOException {
        String fulldat  = "";
        fulldat += java.time.LocalDateTime.now().toString();
            fulldat += " ";
            fulldat += Long.toString(LogSys.getms());
            fulldat += " ";
            fulldat += m.severity.name;
            fulldat += " (";
            fulldat += Integer.toString(m.severity.slevel);
            fulldat += ") ";
            fulldat += m.group.name;
            fulldat += ": ";
            fulldat += m.data;
            fulldat += "\n";
        lf = new file(filename,"m");
        lf.append(fulldat);
        lf.close();
    }
    public void rcfile() throws IOException {
        try {
            Files.createFile(Paths.get(filename));
        } catch (FileAlreadyExistsException f) {
            //DO NOTHING
        }
    }
    
    public LogGroup register(String newname) {
        LogGroup l = new LogGroup(this,newname);
        lg.add(l);
        return l;
    }
}
