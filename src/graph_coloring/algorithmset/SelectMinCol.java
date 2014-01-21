
package graph_coloring.algorithmset;

import java.util.HashMap;
import java.util.Map.Entry;

public class SelectMinCol implements IABWExtendedColorSelector{

    @Override
    public Integer select(NodeGreedy node, HashMap<Integer, Integer> availableColors) {
        int color = availableColors.entrySet().iterator().next().getKey();
        int min = node.getCollisions(color);
        for (Entry<Integer, Integer> entry : availableColors.entrySet()) {
            if (node.getCollisions(entry.getKey()) < min) {
                min = node.getCollisions(entry.getKey());
                color = entry.getKey();
            }
        }
        return color;
    }
    
}
