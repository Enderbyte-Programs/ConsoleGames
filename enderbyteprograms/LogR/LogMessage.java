package enderbyteprograms.LogR;

public class LogMessage{
    public String data;
    public LogGroup group;
    public LogSeverity severity;
    public LogMessage(LogGroup g,String d,LogSeverity s) {
        data = d;
        group = g;
        severity = s;
    }
    public String toString() {
        return data;
    }
}
