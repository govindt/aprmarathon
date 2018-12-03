package app.busobj;
import core.util.Util;
import java.util.Comparator;

public class RegistrantObjectSort implements Comparator<RegistrantObject> 
{ 
    // Used for sorting in ascending order of 
    // registrant_id  
    public int compare(RegistrantObject a, RegistrantObject b) 
    { 
        //return a.getRegistrantId() - b.getRegistrantId();
		
		return (Util.trim(a.getRegistrantName())).compareTo(Util.trim(b.getRegistrantName()));
    } 
} 
