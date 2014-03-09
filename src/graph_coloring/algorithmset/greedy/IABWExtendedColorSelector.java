
package graph_coloring.algorithmset.greedy;

import java.util.HashMap;

public interface IABWExtendedColorSelector {
    public Integer select(NodeGreedy node, HashMap<Integer, Integer> availableColors);   
}
