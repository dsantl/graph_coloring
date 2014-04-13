package graph_coloring.stat;

import java.util.Iterator;

import graph_coloring.common.Pair;
import graph_coloring.structure.weight_graph.WeightNode;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonGraph;


public class ErrorFunctionEricsson {
	
	/**
	 * Compute error function of graph, O(M+N) where M is number of bridges and N number of nodes
	 * @param graph
	 * @return error value (double)
	 */
	public static double computeStat(EricssonGraph graph) {
		
		double ret = 0;
			
		for(int nodeIndex = 0 ; nodeIndex < graph.getNodeSize() ; ++nodeIndex){
			
			Iterator<Pair<Double, WeightNode>> neighbourIterator = graph.getNeighbours(nodeIndex);
			
			while(neighbourIterator.hasNext()){
				Pair<Double, WeightNode> doubleNode = neighbourIterator.next();
				double weight = doubleNode.getFirst();
				WeightNode neighbourNode = doubleNode.getSecond();
				
				int leftNode = nodeIndex;
				int rightNode = neighbourNode.getId();
					
				if ( (graph.getNodeGroup(leftNode) == graph.getNodeGroup(rightNode)) && 
					 (graph.getNodeColor(leftNode) == graph.getNodeColor(rightNode)) &&
					 (graph.getNodeColorable(leftNode) == true || graph.getNodeColorable(rightNode) == true)){	
						ret += weight;
					}
			}
		}
		
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
