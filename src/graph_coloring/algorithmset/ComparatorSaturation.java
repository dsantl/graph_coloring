
package graph_coloring.algorithmset;

import java.util.Comparator;

public class ComparatorSaturation implements Comparator<NodeGreedy> {

    @Override
    public int compare(NodeGreedy o1, NodeGreedy o2) {
        return o2.getSaturation() - o1.getSaturation();
    }
    
}
