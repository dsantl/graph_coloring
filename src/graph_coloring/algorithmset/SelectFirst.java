
package graph_coloring.algorithmset;

import java.util.HashMap;

public class SelectFirst implements IABWExtendedColorSelector{

    @Override
    public Integer select(NodeGreedy node, HashMap<Integer, Integer> availableColors) {
        return availableColors.entrySet().iterator().next().getKey();
    }
    
}
