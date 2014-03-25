package graph_coloring.stat;


import graph_coloring.structure.Graph;

public class CollisionCounter {
	
	/**
	 * Compute error function of graph, O(M+N) where M is number of bridges and N number of nodes
	 * @param graph
	 * @return error value (double)
	 */
	public static int computeStat(Graph graph) {
		
		int ret = 0;
		
		for(int i = 0  ; i < graph.getNodeSize() ; ++i){
			ret += graph.getNodeCollision(i);
		}
		
		return ret;
	}

}
