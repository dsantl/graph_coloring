package graph_coloring.algorithm;

import java.util.Map;

import graph_coloring.structure.Graph;

public class GraphAlgorithmContext {
	
	private GraphColoringAlgorithm algorithm;
	
	/**
	 * Set algorithm to context
	 * @param algorithm
	 */
	public GraphAlgorithmContext(GraphColoringAlgorithm algorithm)
	{
		this.algorithm = algorithm;
	}
	
	/**
	 * Start algorithm with parameters
	 * @param graph
	 * @param param
	 */
	public void startAlgorithm(Graph graph, Map<String, Object> param)
	{
		algorithm.startAlgorithm(graph, param);
	}
	
	/**
	 * Start algorithm without parameters
	 * @param graph
	 */
	public void startAlgorithm(Graph graph)
	{
		algorithm.startAlgorithm(graph, null);
	}
}
