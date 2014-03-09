

package graph_coloring.algorithmset.greedy;

import java.util.Comparator;

public class ComparatorCollisions implements Comparator<NodeGreedy>{

    @Override
    public int compare(NodeGreedy o1, NodeGreedy o2) {
        return o2.getCollisions() - o1.getCollisions();
    }
}
