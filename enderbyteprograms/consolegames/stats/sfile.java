package enderbyteprograms.consolegames.stats;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import enderbyteprograms.file;
import enderbyteprograms.enderlib;

public class sfile {
    public String filename;
    public List<sgroup> groups = new ArrayList<sgroup>();
    public String fdata;
    public List<String> flines = new ArrayList<String>();
    List<String> gnames = new ArrayList<String>();
    public List<String> rgnames = new ArrayList<String>();
    public List<sgroup> rgroups = new ArrayList<sgroup>();
    public sfile(String fname) throws IOException {
        filename = fname;
        gnames.add("Back");
        file f = new file(filename,"r");
        fdata = f.read();
        flines = enderlib.split(fdata,"\n");
        int i;
        
        for (i=0;i<flines.size();i++) {
            
            String dat = flines.get(i);
            //System.out.println(dat);
            //System.out.println(i);
            if (Objects.equals(dat,"")) {
                continue;
            }
            String group = enderlib.split(dat,";").get(0);
            String name = enderlib.split(enderlib.split(dat,";").get(1),"=").get(0);
            double data = Double.parseDouble(enderlib.split(enderlib.split(dat,";").get(1),"=").get(1));
            if (!gnames.contains(group))
            {
                sgroup ng = new sgroup(group);
                ng.newnode(name,data);
                groups.add(ng);
            gnames.add(group);}
            else {

            }

        }
    }
    public void createMenu() {
        while (true) {
            int e = enderlib.menu("Statistics Menu","Options",gnames);
            if (e==0) {
                break;
            } else {
                groups.get(e-1).show();
            }
        }
    }
    public sgroup locatek(String finder) {
        return groups.get(enderlib.find(gnames,finder)-1);
    }
    public boolean exists(String finder) {
        if (enderlib.find(gnames,finder)==-1) {
            return false;
        } else {
            return true;
        }
    }
    public void register(sgroup s) {
        
        rgroups.add(s);
        rgnames.add(s.name);
        groups.clear();
        gnames.clear();
        gnames.add("Back");
        int i;
        for (i=0;i<rgroups.size();i++) {
            groups.add(rgroups.get(i));
            gnames.add(rgnames.get(i));
        }
        for (i=0;i<flines.size();i++) {
            String dat = flines.get(i);
            if (Objects.equals(dat,"")) {
                continue;
            }
            //System.out.println(dat);
            //System.out.println(i);
            String group = enderlib.split(dat,";").get(0);
            String name = enderlib.split(enderlib.split(dat,";").get(1),"=").get(0);
            double data = Double.parseDouble(enderlib.split(enderlib.split(dat,";").get(1),"=").get(1));
            if (!gnames.contains(group))
            {
                sgroup ng = new sgroup(group);
                ng.newnode(name,data);
                groups.add(ng);
            gnames.add(group);}
            else {
            
                sgroup _g = locatek(group);
                if (!_g.names.contains(name)) {
                    _g.newnode(name,data);
                } else {
                    snode _n = _g.locate(name);
                    _n.set(_n.value + data); //TODO stop double adding, maybe with reglist??
                }
                

            } 
        }
    }
    public void save() throws IOException {
        file f = new file(filename,"m");
        String assembled = "";
        int i;
        int j;
        for (i=0;i<groups.size();i++) {
            for (j=0;j<groups.get(i).nodes.size();j++) {
                assembled += groups.get(i).name;
                assembled += ";";
                assembled += groups.get(i).nodes.get(j).name;
                assembled += "=";
                assembled += Double.toString(groups.get(i).nodes.get(j).value);
                assembled += "\n";
            }
        }
        f.write(assembled);
    }
}
