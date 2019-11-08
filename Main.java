import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
	ArrayList<Suspect> deduplicated = new ArrayList<Suspect>();
	ReadFile reader = new ReadFile();
	if (args.length == 1) {
	    deduplicated = reader.readFile(args[0]);
	}
	else {
	    throw new FileNotFoundException("Invalid Argument");
	}
    }
}
