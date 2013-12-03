package graph_coloring.algorithm;

import graph_coloring.structure.Graph;
import graph_coloring.structure.Nodes;

public class GraphAlgorithmContext {
	
	private GraphColoringAlgorithm algorithm;

	public GraphAlgorithmContext(GraphColoringAlgorithm algorithm)
	{
		this.algorithm = algorithm;
	}
	
	public Nodes startAlgorithm(Nodes nodes, Graph graph)
	{
		return this.algorithm.startAlgorithm(nodes, graph);
	}
}
