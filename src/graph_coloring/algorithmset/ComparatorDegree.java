
package graph_coloring.algorithmset;

import java.util.Comparator;

public class ComparatorDegree implements Comparator<NodeGreedy> {
    @Override
    public int compare(NodeGreedy o1, NodeGreedy o2) {
        return o2.bridges.size() - o1.bridges.size();
    }
}

