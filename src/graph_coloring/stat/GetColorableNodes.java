package graph_coloring.stat;

import graph_coloring.structure.weight_graph.ericsson_graph.EricssonGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class GetColorableNodes {

	/**
	 * Get node id of colorable nodes (if nodeIds is not null then only colorable nodes in this set)
	 * @param graph
	 * @return list of integers, where integers are id of nodes
	 */
	public static List<Integer> getNodeIdsFilter(EricssonGraph graph, Set<Integer> nodeIds){
		
		if ( nodeIds == null ){
			return GetColorableNodes.getNodeIds(graph);
		}
		
		List<Integer> ret = new ArrayList<Integer>();
		for(int i = 0 ; i < graph.getNodeSize() ; ++i){
			if ( graph.getNodeColorable(i) && nodeIds.contains(graph.getNodeId(i)));
				ret.add( graph.getNodeId(i) );
		}
		
		return ret;
	}
	
	/**
	 * Get node id of colorable nodes
	 * @param graph
	 * @return list of integers, where integers are id of nodes
	 */
	public static List<Integer> getNodeIds(EricssonGraph graph){
		
		List<Integer> ret = new ArrayList<Integer>();
		for(int i = 0 ; i < graph.getNodeSize() ; ++i){
			if ( graph.getNodeColorable(i) )
				ret.add( graph.getNodeId(i) );
		}
		
		return ret;
	}
	
	/**
	 * Get total number of colorable nodes
	 * @param graph EricssonGraph
	 * @return Total number of colorable nodes
	 */
	public static int getNumberOfColorableNodes(EricssonGraph graph){
		int ret = 0;
		for(int i = 0 ; i < graph.getNodeSize() ; ++i){
			if ( graph.getNodeColorable(i) )
				ret += 1;
		}
		return ret;
	}
	
}
