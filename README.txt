1. README:
   Name: PhuongLinh Tran	
   How to compile:
   How to run:
        Known Bugs and Limitations: I collected the time each method takes to count the number of duplicates by taking the start time as the time that deduplication method is called in the program and end time as the time that the method finish adding unique elements to the finalized ArrayList.

	Discussion:
	Design of equality comparision: I used the following fields to determine equality for two Suspect object: sex, race, age, height in feet and inch and eye color since these are factors are most likely to be unchanged within a one-year time period. A suspect's date of birth is another stable factor which should be taken into account, however, most of the suspects have "31121900" recorded for their date of birth which indicates that it was left empty so I did not include it when evaluating equality of two suspects. I chose to override both hashCode() and toString() for the Suspect class where the toString() would return a representation of the object by a concatenated string of all the fields used to determined equality and hashCode() would calculate the hash code of each object from the concatenated string. The two method compareTo() and equals() are also overriden to use the customized hash code to compare two Suspect objects.

    	Comparision of Methods: In terms of storage, all methods I wrote require an additional ArrayList to store the deduplicated list of Suspect objects. In terms of running-term, however, the deduplication method using hash table with double hashing is the most efficient since its operations used are all of O(1) time complexity on average (although there are very subtle differences in actual running time when I tested).
    Out of the two sorting methods used, the Collections.sort method takes less time to count duplicates. This might be due to the fact that Collections.sort uses merge sort which is a stable sort where equivalent elements remain their relative order after the sort. Therefore, in a data set with frequent duplicates like in this program, Collections.sort is more efficient than quick sort in that it has to perform less unnecessary swapping of equivalent elements.

     	Comparisions of Hash Tables: For the hash table that use linear probing method, the average number of probes is approximately 2.04 and the maximum number of probes can range from 21 to 32. There is improvement in these statistics for the hash table that uses double hashing. In specific, the average number of probes decreases to 1.99 and the maximum number of probes significantly decreases to 12. This is due to the fact that the hash table with a second hash function has less "clustering" for each step size being determined more randomly than in liner probing where each probe is at the adjacent slot of the current. The smaller number of probes hence indicates this and suggests that the hash table with a second hash function perform less probing then the one with linear probing and thus results in a running-time difference.


2. Source files:ProbeHashMap.java, DoubleHashMap.java, AbstractHashMap.java, ReadFile.java, Entry.java, Main.java, Suspect.java, Map.java, AbstractMap.java

3. Chart image: complexity.png
