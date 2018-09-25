package app.busobj;
import java.util.Comparator;

public class RegistrantObjectSort implements Comparator<RegistrantObject> 
{ 
    // Used for sorting in ascending order of 
    // registrant_id  
    public int compare(RegistrantObject a, RegistrantObject b) 
    { 
        return a.getRegistrantId() - b.getRegistrantId(); 
    } 
} 
