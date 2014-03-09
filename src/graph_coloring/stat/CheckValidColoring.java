package graph_coloring.stat;

import graph_coloring.structure.weight_graph.ericsson_graph.EricssonGraph;


public class CheckValidColoring {

	/**
	 * Check if graph have all nodes in right color
	 * @param graph
	 * @return true if graph is valid else false
	 */
	public static boolean computeStat(EricssonGraph graph) {
		
		for(int i = 0 ; i < graph.getNodeSize() ; ++i){
			if ( !graph.checkValidColorOfNode(i) )
				return false;
		}
		return true;
	}
	
}
