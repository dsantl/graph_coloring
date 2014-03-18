package graph_coloring.algorithmset;


import graph_coloring.algorithm.GraphAlgorithmContext;
import graph_coloring.algorithm.GraphColoringAlgorithm;
import graph_coloring.algorithmset.greedy.Greedy;

public class RandomAlgorithm extends GraphColoringAlgorithm{

	@Override
	protected void algorithm() {
		GraphAlgorithmContext alg = new GraphAlgorithmContext(new Greedy("RND", "RND", 1));
		alg.startAlgorithm(graph);
	}
}
