package graph_coloring.algorithm;

import java.util.Set;

import graph_coloring.structure.Graph;

public abstract class GraphColoringAlgorithm {

	private Set<Integer> touchableNodes = null;
	protected Graph graph;
	
	/**
	 * Get nodes for change. User define nodes who can change. 
	 * @return Set<Integer> node ids
	 */
	protected Set<Integer> getTouchableNodes(){
		return this.touchableNodes;
	}
	
	/**
	 * Return true if node can change color (by user definition)
	 * @param index Node index
	 * @return true if node can change color or false if note is not in touchable node set
	 */
	protected boolean checkNode(int index){
		
		int id = graph.getNodeId(index);
		if (touchableNodes == null)
			return true;
		
		return touchableNodes.contains(id);
	}
	
	protected abstract void algorithm();
	
	/**
	 * Run algorithm, algorithm change graph object
	 * @param graph
	 * @param param list of parameters depends of algorithm
	 */
	public void startAlgorithm(Graph graph){
		touchableNodes = null;
		this.graph = graph;
		this.algorithm();
	}
	
	
	/**
	 * Run algorithm with touchable nodes
	 * @param graph
	 * @param touchNodeId List of touchable nodes (Ids)
	 * @param params list of parameters depends of algorithm
	 */
	public void startAlgorithm(Graph graph, Set<Integer> touchNodeId){
		touchableNodes = touchNodeId;
		this.graph = graph;
		this.algorithm();
	}
}
