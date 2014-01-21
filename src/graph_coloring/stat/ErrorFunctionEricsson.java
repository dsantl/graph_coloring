package graph_coloring.stat;

import java.util.Set;

import graph_coloring.common.MinMaxPairInteger;
import graph_coloring.common.Pair;
import graph_coloring.structure.EricssonBridge;
import graph_coloring.structure.EricssonNode;
import graph_coloring.structure.Graph;
import graph_coloring.structure.Node;
import graph_coloring.structure.EricssonGraph;

public class ErrorFunctionEricsson {
	

	public static double computeStat(Graph graph) {
		
		double ret = 0;
		
		Set<MinMaxPairInteger> bridges = graph.getBridgeIndices();
		
		for(MinMaxPairInteger bridge : bridges){
			EricssonBridge eBridge = (EricssonBridge) graph.getBridge(bridge);
			int leftNode = eBridge.getLeft();
			int rightNode = eBridge.getRight();
			EricssonNode lNode = (EricssonNode) graph.getNode(leftNode);
			EricssonNode rNode = (EricssonNode) graph.getNode(rightNode);
			
			if ( (lNode.getNodeClass() == rNode.getNodeClass()) && 
				 (lNode.getColor() == rNode.getColor()) &&
				 (lNode.getColorable() == true || rNode.getColorable() == true) ){	
					
					//System.out.format("%d %d\n", lNode.getId(), rNode.getId());
					ret += eBridge.getWeight();
			}
		}
		
		return ret;
	}

}
