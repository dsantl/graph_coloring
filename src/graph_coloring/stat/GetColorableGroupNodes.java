package graph_coloring.stat;

import graph_coloring.structure.weight_graph.ericsson_graph.EricssonGraph;

import java.util.ArrayList;
import java.util.List;


public class GetColorableGroupNodes {

	/**
	 * Get node id of all nodes with specific class (A, B or C)
	 * @param graph
	 * @param nodeClass (A, B or C)
	 * @return list of integers, where integers are id of nodes
	 */
	public static List<Integer> getNodeClass(EricssonGraph graph, char nodeClass){
		
		List<Integer> ret = new ArrayList<Integer>();
		for(int i = 0 ; i < graph.getNodeSize() ; ++i){
			if ( graph.getNodeColorable(i) && graph.getNodeGroup(i) == nodeClass)
				ret.add( graph.getNodeId(i) );
		}
		
		return ret;
	}
	
}
