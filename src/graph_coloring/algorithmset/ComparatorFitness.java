
package graph_coloring.algorithmset;

import java.util.Comparator;

public class ComparatorFitness implements Comparator<NodeGreedy> {

    @Override
    public int compare(NodeGreedy o1, NodeGreedy o2) {
        if (o2.getFitness() > o1.getFitness()) return 1;
        else if (o1.getFitness() > o2.getFitness()) return -1;
        else return 0;
    }
    
}
