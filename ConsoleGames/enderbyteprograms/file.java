package enderbyteprograms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class file {
    private static String filename;
    private String mode;
    private BufferedReader br;
    public file(String name,String fmode) throws FileNotFoundException {
        //Name is name, mode is either r (read) or m (modify)
        filename = name;
        br = new BufferedReader(new FileReader(filename));
        mode = fmode;
    }
    public String read() throws IOException{
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();

        while (line != null) {
            sb.append(line);
            sb.append(System.lineSeparator());
            line = br.readLine(); //WHY IS IT NOT WORKING!?!?!?!?!?!
        }
        String everything = sb.toString();
        return everything;
    }
    public void close() {
        //Closes the readers. Prevents issues. Make sure to close.
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace(); //Assuming this will never happen
        }
    }
    public void write(String data)  throws IOException{
        if (!Objects.equals(mode,"m")) {
            throw new IOException("Invalid mode.");
        }
        //Overwrites a file with the data
        File oldf = new File(filename);
        oldf.delete();
        File newf = new File(filename);
        
        FileWriter f2 = new FileWriter(newf,false);
        f2.write(data);
        f2.close();
        
    }
}
