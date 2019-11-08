/* Name: PhuongLinh Tran
 * File: Suspect.java
 * Desc: This class holds data of one row in the data file necessary for 
 * determining equality of two suspects
 */

public class Suspect implements Comparable<Suspect> {
    private String sex;
    private String race;
    private int age;
    private int heightFeet;
    private int heightInch;
    private String eyeColor;
    
    // Constructs a new Suspect object
    public Suspect(String sex, String race, int age, int heightFeet,
		   int heightInch, String eyeColor) {
	this.sex = sex;
	this.race = race;
	this.age = age;
	this.heightFeet = heightFeet;
	this.heightInch = heightInch;
	this.eyeColor = eyeColor;
    }
    
    /* Returns sex of suspect
     * @return Sex of suspect
     */
    public String getSex() {
	return this.sex;
    }
    
    /* Returns race of suspect
     * @return Race of suspect
     */
    public String getRace() {
	return this.race;
    }
    
    /* Returns age of suspect
     * @return age of suspect
     */
    public int getAge() {
	return this.age;
    }
    
    /* Returns height in feet of suspect
     * @return Height in feet of suspect
     */
    public int getHeightFeet() {
	return this.heightFeet;
    }
    
    /* Returns height in inch of suspect
     * @return height in inch of suspect
     */
    public int getHeightInch() {
	return this.heightInch;
    }
    
    /* Returns eye color of suspect
     * @return eye color of suspect
     */
    public String getEyeColor() {
	return this.eyeColor;
    }
    
    /* Returns string representation of suspect
     * @return String representation of suspect
     */    
    public String toString() {
	return sex + race + age + heightFeet + heightInch + eyeColor;
    }
    
    /* Compares this Suspect object with another Suspect object and gives them
     * an order based on their hash codes
     * @param sus object to be compared
     * @return -1 if this object has smaller hash code, 1 if this object has
     * greater hash code or 0 if the two objects have same hash code
     */
    @Override
    public int compareTo(Suspect sus) {
	if (this.hashCode() < sus.hashCode()) {
	    return -1;
	}
	else if (this.hashCode() == sus.hashCode()) {// same objects
	    return 0;
	}
	else {
	    return 1;
	}
    }
    
    /* Overrides equals() method to determine equality of two Suspect objects 
     * by comparing their hash codes
     * @param o Object to be compared
     * @return True if the two objects have equal hash codes anf false 
     * otherwise
     */
    @Override
    public boolean equals(Object o) {
	Suspect sus = (Suspect) o;// casts o to type Suspect
	if (this.hashCode() == sus.hashCode()) {
	    return true;
	}
	return false;
    }
    
    /* Overrides hashCode() method to compute hash code of each object by its
     * string representation
     * @return Hash code of the object
     */
    public int hashCode() {
	return this.toString().hashCode();
    }
}
    
    
    
