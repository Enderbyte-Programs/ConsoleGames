package enderbyteprograms.consolegames.stats;

import java.util.ArrayList;
import java.util.List;
import enderbyteprograms.enderlib;

public class sgroup {
    public List<snode> nodes = new ArrayList<snode>();
    public List<String> names = new ArrayList<String>();
    public String name;
    public sgroup(String n) {
        name = n;
    } 
    public void newnode(String n,double v) {
        nodes.add(new snode(this,n,v));
        names.add(n);
    }
    public void show() {
        enderlib.clearscreen();
        System.out.println("=====Statistics for " + name + "=====");
        int i;
        for (i=0;i<nodes.size();i++) {
            snode _s = nodes.get(i);
            System.out.println(_s.dvalue);

        }
        System.out.println("------------------------------------");
        enderlib.input("Press enter to return to menu");
    }
    public snode locate(String f) throws ArrayIndexOutOfBoundsException {
        List<String> l = new ArrayList<String>();
        int i;
        for (i=0;i<nodes.size();i++) {
            l.add(nodes.get(i).name);
        }
        return nodes.get(enderlib.find(l,f));
    }
    public boolean nexists(String f) {
        List<String> l = new ArrayList<String>();
        int i;
        for (i=0;i<nodes.size();i++) {
            l.add(nodes.get(i).name);
        }
        if (l.contains(f)) {
            return true;

        } else {
            return false;
        }
    }
}
