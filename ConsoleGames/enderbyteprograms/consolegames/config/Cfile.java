package enderbyteprograms.consolegames.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import enderbyteprograms.enderlib;
import enderbyteprograms.file;

public class Cfile {
    public static String fdata;
    public static file f;
    public static String fname;
    public List<Cnode> c = new ArrayList<Cnode>();
    public List<String> lines = new ArrayList<String>();
    public List<String> coptions = new ArrayList<String>();
    public Cfile(String filename) throws FileNotFoundException, IOException{
        coptions.add("Save");
        coptions.add("Cancel");
        fname = filename;
        f = new file(filename,"r");
        fdata = f.read();
        f.close();
        lines = enderlib.split(fdata,"\n");
        int i;
        for (i=0;i<lines.size();i++) {
            if (Objects.equals(lines.get(i),"")) {
                continue;
            }
            Cnode l = new Cnode(enderlib.split(lines.get(i),"=:=").get(0),enderlib.split(lines.get(i),"=:=").get(1),false);
            c.add(l);
            coptions.add(l.name);
        }
    }
    public void newNode(String n,String d,Boolean u) {
        Cnode cc = new Cnode(n,d,u);
        c.add(cc);
        coptions.add(cc.name);
    }
    public void save() throws FileNotFoundException, IOException{
        int i;
        String sdat = "";
        for (i=0;i<c.size();i++) {
            sdat += c.get(i).display;
            sdat += "\n";
        }
        //System.out.println(sdat);
        //enderlib.delay(1000);
        f = new file(fname,"m");
        f.write(sdat);
        f.close();
    }
    public void makemenu() throws IOException, FileNotFoundException {
        int e;
        while (true)
        {
            e = enderlib.menu("Configuration Menu","Options",coptions);
            if (e==0) {
                save();
                break;
            }
            if (e==1) {
                break;
            } else {
                c.get(e-2).update();
            }
        }
    }
    public Cnode findbyname(String searcher) throws IOException {
        List<String> lnames = new ArrayList<String>();
        int i;
        for (i = 0;i < c.size(); i++) {
            lnames.add(c.get(i).name);
        }
        int result = enderlib.find(lnames,searcher);
        if (result==-1) {
            throw new IOException("Failed to find item");
        }
        return c.get(result);
    }
    public Boolean cexists(String n) {
        List<String> lnames = new ArrayList<String>();
        int i;
        for (i = 0;i < c.size(); i++) {
            lnames.add(c.get(i).name);
        }
        if (enderlib.find(lnames,n)==-1) {
            return false;
        }
        return true;
    }
}
