package graph_coloring.stat;

import graph_coloring.structure.weight_graph.ericsson_graph.EricssonGraph;

public class ChangeColorGlobal {
	
	/**
	 * Ratio of total colorable nodes and nodes who doesn't have start color
	 * @param graph
	 * @return ratio (double)
	 */
	public static double computeStat(EricssonGraph graph) {
		
		int counter = 0;
		int total = 0;
		
		for(int i = 0 ; i < graph.getNodeSize() ; ++i){
			
			if ( graph.getNodeColorable(i) )
			{
				total += 1;
			
			if ( graph.getNodeColor(i) != graph.getNodeStartColor(i) )
				counter += 1;
			}
		}
		
		return ((double)counter)/total;
	}
}
