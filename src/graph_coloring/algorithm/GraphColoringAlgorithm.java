package graph_coloring.algorithm;

import java.util.Map;

import graph_coloring.structure.Graph;

public interface GraphColoringAlgorithm {

	/**
	 * Run algorithm, algorithm change graph object
	 * @param graph
	 * @param param Map - key is parameter name and value is some Object
	 */
	public void startAlgorithm(Graph graph, Map<String, Object> param);
	
}
