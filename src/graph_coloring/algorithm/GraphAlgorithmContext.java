package graph_coloring.algorithm;


import java.util.Set;

import graph_coloring.structure.Graph;

public class GraphAlgorithmContext {
	
	private GraphColoringAlgorithm algorithm;
	
	/**
	 * Set algorithm to context
	 * @param algorithm
	 */
	public GraphAlgorithmContext(GraphColoringAlgorithm algorithm){
		this.algorithm = algorithm;
	}
	
	/**
	 * Start algorithm on graph
	 * @param graph
	 */
	public void startAlgorithm(Graph graph){
		algorithm.startAlgorithm(graph);
	}
	
	/**
	 * Start algorithm with parameters
	 * @param graph
	 * @param touchableNodes
	 */
	public void startAlgorithm(Graph graph, Set<Integer> touchableNodes){
		algorithm.startAlgorithm(graph, touchableNodes);
	}
	
}
