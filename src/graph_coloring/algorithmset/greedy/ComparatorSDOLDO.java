package graph_coloring.algorithmset.greedy;

import java.util.Comparator;

public class ComparatorSDOLDO implements Comparator<NodeGreedy>{

	@Override
	public int compare(NodeGreedy o1, NodeGreedy o2) {
		int dif = o2.getSaturation() - o1.getSaturation();
		if ( dif != 0 )
			return dif;
		return o2.bridges.size() - o1.bridges.size();
	}
	
	

}
