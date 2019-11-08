import java.util.*;

public class DoubleHashMap<K,V> extends AbstractHashMap<K,V> {
    public static final int PRIME = 392299;// used for second hash function
    private MapEntry<K,V>[] table;// a fixed array of entries
    private MapEntry<K,V> DEFUNCT = new MapEntry<>(null, null);// sentinel

    private int totalProbes = 0;// total number of probes
    private int maxProbes = 0;//maximum number of probes
    
    // Creates a hash table with capacity 17 and prime factor 109345121. 
    public DoubleHashMap() { super(); }

    // Creates a hash table with given capacity and prime factor 109345121. 
    public DoubleHashMap(int cap) { super(cap); }

    // Creates a hash table with the given capacity and prime factor. 
    public DoubleHashMap(int cap, int p) { super(cap, p); }

    // Creates an empty table having length equal to current capacity. 
    @Override
    @SuppressWarnings({"unchecked"})
    protected void createTable() {
        table = (MapEntry<K,V>[]) new MapEntry[capacity];// safe cast        
    }

    // Returns true if location is either empty or the "defunct" sentinel. 
    private boolean isAvailable(int j) {
        return (table[j] == null || table[j] == DEFUNCT);
    }

    /* Secondary hash function
     * @param key Key to be calculate secondary hash function of
     * @return Step size for each probe
     */
    private int stepSize(K key) {
	return PRIME - (key.hashCode()% PRIME);
    }

    private int findSlot(int h, K k) {
	int avail = -1;// no slot available
	int j = h;// index while scanning table
	int count = 0;// count number of probes each time method is called
	
	do {
	    totalProbes++;// increments total number of probes
	    count++;// increments probe count
	    if (isAvailable(j)) {// either empty or defunct
		if (avail == -1) avail = j;// first slot available
		if (table[j] == null) break;
	    }

	    else if (table[j].getKey().equals(k)) return j;
	    
	    int stepSize = stepSize(k);
	    j = (j+stepSize) % capacity;// looks at next position with step size determined by second hash function	    
	} while (j != h);
	
	if (count >= maxProbes) {// if probe count is greater than current maximum number of probes
            maxProbes = count;
        }
        return -(avail + 1);// search has failed
    }
    
    /* Returns value associated with key k in bucket with hash value h.
     * If no such entry exists, returns null.                                  
     * @param h  the hash value of the relevant bucket                         
     * @param k  the key of interest                                           
     * @return   associate value (or null, if no such entry)                   
     */
    @Override
    protected V bucketGet(int h, K k) {
        int j = findSlot(h, k);
        if (j < 0) return null;// no match found                                
        return table[j].getValue();
    }

    /* Associates key k with value v in bucket with hash value h, returning   
     * the previously associated value, if any.                                
     * @param h  the hash value of the relevant bucket                         
     * @param k  the key of interest                                           
     * @param v  the value to be associated                                    
     * @return   previous value associated with k (or null, if no such entry)  
     */
    @Override
    protected V bucketPut(int h, K k, V v) {
        int j = findSlot(h, k);
        if (j >= 0)// this key has an existing entry	    
            return table[j].setValue(v);
        table[-(j+1)] = new MapEntry<>(k, v);// convert to proper index
	
        n++;
        return null;
    }

    /* Removes entry having key k from bucket with hash value h, returning    
     * the previously associated value, if found.                              
     * @param h  the hash value of the relevant bucket                         
     * @param k  the key of interest                                           
     * @return   previous value associated with k (or null, if no such entry)  
     */
    @Override
    protected V bucketRemove(int h, K k) {
        int j = findSlot(h, k);
        if (j < 0) return null;// nothing to remove
        V answer = table[j].getValue();
        table[j] = DEFUNCT;// mark this slot as deactivated
        n--;
        return answer;
    }

    
    /* Returns an iterable collection of all key-value entries of the map.
     * @return iterable collection of the map's entries            
     */
    @Override
    public Iterable<Entry<K,V>> entrySet() {
        ArrayList<Entry<K,V>> buffer = new ArrayList<>();
    for (int h=0; h < capacity; h++)
        if (!isAvailable(h)) buffer.add(table[h]);
    return buffer;
    }

    /* return average number of probes
     * @return average number of probes
     */
    public double averageProbes() {
        return (double) totalProbes/n;
    }

    /* return max number of probes 
     * @return max number of probes
     */
    public int maxProbes() {
	return  this.maxProbes;
    }

    /* return load factor                                                   
     * @return load factor                                                    
     */
    public double loadFactor() {
       return (double) n/capacity;
    }

    
}
