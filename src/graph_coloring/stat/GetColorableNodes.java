package graph_coloring.stat;

import graph_coloring.structure.weight_graph.ericsson_graph.EricssonGraph;

import java.util.ArrayList;
import java.util.List;


public class GetColorableNodes {

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
	
}
