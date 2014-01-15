package graph_coloring.stat;

import java.util.Set;

import graph_coloring.common.Pair;
import graph_coloring.structure.EricssonBridge;
import graph_coloring.structure.EricssonNode;
import graph_coloring.structure.Graph;
import graph_coloring.structure.Node;

public class ErrorFunctionEricsson implements ComputeStatistics{
	
	@Override
	public double computeStat(Graph graph) {
		
		double ret = 0;
		
		Set<Pair<Integer, Integer> > bridges = graph.getBridgeIndices();
		
		for(Pair<Integer, Integer> bridge : bridges){
			EricssonBridge eBridge = (EricssonBridge) graph.getBridge(bridge);
			int leftNode = eBridge.getLeftNode();
			int rightNode = eBridge.getRightNode();
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
