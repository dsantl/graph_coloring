

package graph_coloring.algorithmset.greedy;

import java.util.Comparator;

public class ComparatorCollisionsStartColor implements Comparator<NodeGreedy>{

    @Override
    public int compare(NodeGreedy o1, NodeGreedy o2) {
        return o2.getCollisions(o2.color_start) - o1.getCollisions(o1.color_start);
    }
}
