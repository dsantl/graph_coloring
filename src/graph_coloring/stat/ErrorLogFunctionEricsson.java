package graph_coloring.stat;

import graph_coloring.structure.weight_graph.ericsson_graph.EricssonGraph;

public class ErrorLogFunctionEricsson {

	public static double computeStat(EricssonGraph graph) {
		return Math.log(ErrorFunctionEricsson.computeStat(graph));
	}
	
}
