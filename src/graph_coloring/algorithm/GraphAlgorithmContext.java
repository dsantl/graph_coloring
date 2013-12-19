package graph_coloring.algorithm;

import java.util.List;

import graph_coloring.structure.Graph;
import graph_coloring.structure.Node;

public class GraphAlgorithmContext {
	
	private GraphColoringAlgorithm algorithm;

	public GraphAlgorithmContext(GraphColoringAlgorithm algorithm)
	{
		this.algorithm = algorithm;
	}
	
	public void startAlgorithm(List<Node> nodes, Graph graph)
	{
		algorithm.startAlgorithm(nodes, graph);
	}
}
