package graph_coloring.algorithm;

import java.util.List;

import graph_coloring.structure.Graph;

public class GraphAlgorithmContext {
	
	private GraphColoringAlgorithm algorithm;

	public GraphAlgorithmContext(GraphColoringAlgorithm algorithm)
	{
		this.algorithm = algorithm;
	}
	
	public void startAlgorithm(List<Integer> nodes, Graph graph)
	{
		algorithm.startAlgorithm(nodes, graph);
	}
}
