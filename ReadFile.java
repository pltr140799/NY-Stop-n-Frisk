/* Name: PhuongLinh Tran
 * File: ReadFile.java
 * Desc: Reads in the data file, parses to create a list of suspects, removes
 * deduplicated suspects by method specified by user (the program uses a hash
 * table and double hashing by default since it is the fastest method among the
 * four) and return a list of unique suspects.
 */

import java.util.*;
import java.io.*;

public class ReadFile {
    public static final int SEX = 81;
    public static final int RACE = 82;
    public static final int AGE = 84;
    public static final int HEIGHT_FEET = 85;
    public static final int HEIGHT_INCH = 86;
    public static final int EYE_COLOR = 89;
    public static final String ATTRIBUTES = "sex, race, age, ht_feet, ht_inch, eyecolor"; 
    private ArrayList<Suspect> suspects = new ArrayList<Suspect>();// original ArrayList stores Suspect objects (may have duplicates) 

    /* Reads in data file, parses each line to create a Suspect object, adds
     * that object to a list and call a deduplication method to filter list.
     * @param fileName Name of data file
     * @return A deduplicated ArrayList of Suspect objects
     */ 
    public ArrayList<Suspect> readFile(String fileName)
	throws FileNotFoundException {
	// deduplicated list of Suspect objects to be returned
	ArrayList<Suspect> deduplicated = new ArrayList<Suspect>();
    
	int countEntries = 0;// counts number of rows containing data in file
	
	try{
	    FileReader file = new FileReader(fileName);
	    Scanner  input = new Scanner(file);// read input file
	    input.nextLine();// skips first line

	    // parses each line and create a Suspect object from data of
	    while (input.hasNextLine()) {
		countEntries++;
		String line = input.nextLine();
		String[] tokens = line.split(",", -1);

		if (tokens.length != 112) {// handles lines of unusual format
		    tokens = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
		}

		String sex = tokens[SEX];
		String race = tokens[RACE];
		int age = Integer.parseInt(tokens[AGE]);
		int heightFeet = Integer.parseInt(tokens[HEIGHT_FEET]);
		int heightInch = Integer.parseInt(tokens[HEIGHT_INCH]);
		String eyeColor = tokens[EYE_COLOR];
		// creates new Suspect object
		Suspect indiv = new Suspect(sex, race, age,
					    heightFeet, heightInch, eyeColor);
		suspects.add(indiv);// adds new object to original ArrayList
	    }
	} catch (FileNotFoundException e) {
	    System.out.println("\nInvalid file name. \n");
	}

	// deduplicates using a hash table and double hashing
	deduplicated = hashDoubleDeduplication();

	// calculates number of duplicates 
	int countDupes = suspects.size() - deduplicated.size();

	System.out.println("\nRecords given: " + countEntries);
	System.out.println("Attributes checked: " + ATTRIBUTES);
	System.out.println("Duplicates found: " + countDupes +"\n" );

	return suspects;
    }

    /* Deduplicates by comparing each object to each other object in the list
     * @return Deduplicated list of all Suspect objects
     */
    public ArrayList<Suspect> allPairsDeduplication() {
	//deduplicated ArrayList to be returned
	ArrayList<Suspect> finals = new ArrayList<>();
	
	for (int i = 0; i < suspects.size(); i++) {// runs for each item in list
	    if (!finals.contains(suspects.get(i))) {//if item has not existed
		finals.add(suspects.get(i));// adds item to the final list
	    }
	}
	return finals;
    }

    /* Deduplicates by using a has table and linear probing method
     * @return Deduplicated list of all Suspect objects
     */
    public ArrayList<Suspect> hashLinearDeduplication() {
	// deduplicated ArrayList to be returned
	ArrayList<Suspect> finals = new ArrayList<>();
	
	// creates hash table using linear probing 
        ProbeHashMap<Suspect, Integer> table
	    = new ProbeHashMap<Suspect, Integer>(1000003);

	// adds each item to the hash table to deduplicate
	for (int i = 0; i < suspects.size(); i++) {
	    table.put(suspects.get(i), null);	    
	}

	Iterator<Entry<Suspect,Integer>> it = table.entrySet().iterator();

	while (it.hasNext()) {// adds all unique hash table to final list
	    finals.add(it.next().getKey());
	}

	// prints out required statistics
	System.out.println("\nAverage number of probes: "+table.averageProbes());
        System.out.println("Max number of probes: "+table.maxProbes());        
        System.out.println("Load factor: "+table.loadFactor() + "\n");    
	
	return finals;
	}

    /* Deduplicates by using a has table and double hashing method
     * @return Deduplicated list of all Suspect objects
     */
    public ArrayList<Suspect> hashDoubleDeduplication() {	
	//deduplicated ArrayList to be returned
	ArrayList<Suspect> finals = new ArrayList<>();
	
	// creates hash table using double hashing 
	DoubleHashMap<Suspect, Integer> table
	    = new DoubleHashMap<Suspect, Integer>(1000003);

	// adds each item to the hash table to deduplicate
	for (int i = 0; i < suspects.size(); i++) {
	    table.put(suspects.get(i), null);
	}

	Iterator<Entry<Suspect,Integer>> it = table.entrySet().iterator();

	while (it.hasNext()) {// adds all unique hash table to final list
	    finals.add(it.next().getKey());
	}

	System.out.println("\nAverage number of probes: "+table.averageProbes());
        System.out.println("Max number of probes: "+table.maxProbes());        
        System.out.println("Load factor: "+table.loadFactor()+"\n");    
	
	return finals;
	}

    /* Swaps positions of two items in an ArrayList
     * @param list ArrayList containing items to be swapped
     * @param i, j Two items to be swapped
     */
    private void swap(ArrayList<Suspect> list, int i, int j) {
	Suspect temp = list.get(i);
	list.set(i, list.get(j));
	list.set(j, temp);
    }

    /* Takes last element as pivot, places all smaller items compared to pivot
     * to the left of pivot and all bigger items to the right of it
     * @param list ArrayList to be sorted
     * @param low, high Index of first and last items in the ArrayList
     * @return Index of the pivot in the list
     */
    private int partition(ArrayList<Suspect> list, int low, int high) {
	Suspect pivot = list.get(high);// takes the last item as pivot
	int i = low - 1;// index of "smaller" item

	// keeps index of "greater" item and compares with pivot
	for (int j = low; j < high; j++) {
	    
	    // if current item is "smaller" or "equal" to pivot
	    if (list.get(j).compareTo(pivot) <= 0) {
		i++;// increments index of "smaller" item
		swap(list, i, j);//
	    }
	}

	swap(list, i+1, high);// swaps item at index (i+1) and pivot

	return i+1;
    }

    /* Performs quick sort on an ArrayList
     * @param list ArrayList to be sorted
     * @param low, high Index of first and last items of list
     */
    private void quickSort(ArrayList<Suspect> list, int low, int high) {
	if (low < high) {
	    //partition index
	    int pi = partition(list, low, high);

	    quickSort(list, low, pi - 1);// performs quick sort on left half
	    quickSort(list, pi + 1, high);// performs quick sort on right half
	}
    }

    /* Deduplicates by using quick sort method 
     * @return Deduplicated list of all Suspect objects
     */
    public ArrayList<Suspect> quickSortDeduplication() {
	// performs quick sort on the original list of Suspect objects
	quickSort(suspects, 0, suspects.size() - 1);

	if (suspects.size() < 2) {// if list has 0 or 1 item
	    return suspects;
	}
	
	// deduplicated ArrayList to be returned
	ArrayList<Suspect> finals = new ArrayList<Suspect>();

	// loops through original list of Suspect objects
	for (int i = 0; i < suspects.size() - 1; i++) {
	    // compares items at index i and (i+1)
	    if (suspects.get(i).compareTo(suspects.get(i+1)) != 0) {
		finals.add(suspects.get(i));// add item at index i to final list
	    }		
	}
	finals.add(suspects.get(suspects.size() - 1));// adds last element to final list
	
	return finals;
    }

    /* Deduplicates by using built-in Collections.sort() method of java 
     * @return Deduplicated list of all Suspect objects
     */
    public ArrayList<Suspect> builtinSortDeduplication() {
	// performs Collections sort on the original list
	Collections.sort(suspects);
	
	if (suspects.size() < 2) {// if list has 0 or 1 item
	    return suspects;
	}
	
	//deduplicated ArrayList to be returned
	ArrayList<Suspect> finals = new ArrayList<Suspect>();

	// loops through original list of Suspect objects
	for (int i = 0; i < suspects.size() - 1; i++) {

	    // compares items at index i and (i+1)
	    if (suspects.get(i).compareTo(suspects.get(i+1)) != 0) {
		finals.add(suspects.get(i));// add item at index i to final list
	    }
	}
	finals.add(suspects.get(suspects.size() - 1));// adds last element to final list
	
	return finals;
    }	
}
