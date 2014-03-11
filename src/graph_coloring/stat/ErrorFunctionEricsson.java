package graph_coloring.stat;

import graph_coloring.common.OrderPair;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonGraph;


public class ErrorFunctionEricsson {
	
	/**
	 * Compute error function of graph, O(M+N) where M is number of bridges and N number of nodes
	 * @param graph
	 * @return error value (double)
	 */
	public static double computeStat(EricssonGraph graph) {
		
		double ret = 0;
		
		for(int i = 0 ; i < graph.getBridgeSize() ; ++i){
			OrderPair eBridge = graph.getBridge(i);
			int leftNode = graph.getNodeIndex(eBridge.getFirst());
			int rightNode = graph.getNodeIndex(eBridge.getSecond());
				
			if ( (graph.getNodeGroup(leftNode) == graph.getNodeGroup(rightNode)) && 
				 (graph.getNodeColor(leftNode) == graph.getNodeColor(rightNode)) &&
				 (graph.getNodeColorable(leftNode) == true || graph.getNodeColorable(rightNode) == true)){	
					ret += graph.getBridgeWeight(i);
				}
		}
		
		ret *= 2;
		
		for(int i = 0 ; i < graph.getNodeSize() ; ++i){
			int colorClassId = graph.getNodeDomainName(i);
			int color = graph.getNodeColor(i);
			
			if ( graph.getNodeColorable(i) == true){
				if (!graph.colorClassContain(colorClassId, color)){
						ret += 10000000.0;
				}
			}
		}
		
		return ret;
	}

}
