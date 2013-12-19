package graph_coloring.stat;

import java.util.Set;

import graph_coloring.common.Pair;
import graph_coloring.structure.EricssonBridge;
import graph_coloring.structure.Graph;

public class ErrorFunctionEricsson implements ComputeStatistics{

	@Override
	public double computeStat(Graph graph) {
		
		double ret = 0;
		
		Set<Pair<Integer, Integer> > bridges = graph.getBridgeIndices();
		
		for(Pair<Integer, Integer> bridge : bridges){
			EricssonBridge eBridge = (EricssonBridge) graph.getBridge(bridge);
			int color1 = graph.getNode(eBridge.getLeftNode()).getColor();
			int color2 = graph.getNode(eBridge.getRightNode()).getColor();
			if ( color1 != color2 ) 
				ret += eBridge.getWeight();
		}
		
		return ret;
	}

}
