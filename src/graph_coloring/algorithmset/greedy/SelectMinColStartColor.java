
package graph_coloring.algorithmset.greedy;

import java.util.HashMap;
import java.util.Map.Entry;

public class SelectMinColStartColor implements IABWExtendedColorSelector {

    @Override
    public Integer select(NodeGreedy node, HashMap<Integer, Integer> availableColors) {
        for (BridgeGreedy bridge : node.bridges) {
            if (availableColors.containsKey(bridge.node.color_start))
                availableColors.put(bridge.node.color_start, availableColors.get(bridge.node.color_start) + 1);
        }
        int color = availableColors.entrySet().iterator().next().getKey();
        int min = availableColors.entrySet().iterator().next().getValue();
        for (Entry<Integer, Integer> entry : availableColors.entrySet()) {
            if (entry.getValue() < min) {
                min = entry.getValue();
                color = entry.getKey();
            }
        }
        return color;
    }
    
}
