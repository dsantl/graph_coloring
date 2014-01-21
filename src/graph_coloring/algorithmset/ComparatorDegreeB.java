

package graph_coloring.algorithmset;

import java.util.Comparator;

public class ComparatorDegreeB implements Comparator<NodeGreedy> {

    @Override
    public int compare(NodeGreedy o1, NodeGreedy o2) {
        if (o2.color_inside_domain == o1.color_inside_domain){
            return o2.bridges.size() - o1.bridges.size();
        }
        else if (o2.color_inside_domain)
        {
            return -1;
        }
        else return 1;
    }
    
}
