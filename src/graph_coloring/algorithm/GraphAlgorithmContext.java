package graph_coloring.algorithm;


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
	public void startAlgorithm(Graph graph)
	{
		algorithm.startAlgorithm(graph);
	}
	

}
