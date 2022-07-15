package enderbyteprograms.consolegames.stats;

public class snode {
    public String name; //Name of node
    public double value; //Value of node
    public String display; //How it is stored in the sfile()
    public String dvalue; //How it is shown in the menu
    public sgroup group; //Its parent sgroup()
    public snode(sgroup g,String n,double v) {
        name = n;
        value = v;
        group = g;
        display = name + ";" + name +"=" + Double.toString(v);
        dvalue = name + " : " + Double.toString(v);
    }
    public void set(double newvalue) {
        value = newvalue;
        display = name + ";" + name +"=" + Double.toString(value);
        dvalue = name + " : " + Double.toString(value);
    }
}
