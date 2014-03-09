
package graph_coloring.algorithmset.greedy;

import java.util.Comparator;

public class ComparatorFactory {
    
    public static Comparator<NodeGreedy> make(String value)
    {
        Comparator<NodeGreedy> comparator = null;
        
        if ( value.equals("LDO") )
        	comparator = new ComparatorDegree();
        if ( value.equals("FIT") ) 
    	    comparator = new ComparatorFitness();
        if ( value.equals("SDO") )
        	comparator = new ComparatorSaturation();
        if ( value.equals("COL") )
        	comparator = new ComparatorCollisions();
        if ( value.equals("COL2") )
        	comparator = new ComparatorCollisionsStartColor();
        if ( value.equals("LDO2") )
        	comparator = new ComparatorDegreeB();
        if ( value.equals("SDOLDO") )
        	comparator = new ComparatorSDOLDO(); 
        
        return comparator;
    }
}
