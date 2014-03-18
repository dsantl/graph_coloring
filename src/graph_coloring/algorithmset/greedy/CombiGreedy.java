package graph_coloring.algorithmset.greedy;

import graph_coloring.algorithm.GraphAlgorithmContext;
import graph_coloring.algorithm.GraphColoringAlgorithm;
import graph_coloring.stat.ChangeColorGlobal;
import graph_coloring.stat.ErrorFunctionEricsson;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonGraph;

public class CombiGreedy extends GraphColoringAlgorithm {
	
	private int step;
	
	public CombiGreedy(int numberOfIterations){
		this.step = numberOfIterations;
	}
	
	@Override
	protected void algorithm() {
		
		GraphAlgorithmContext algSDO = new GraphAlgorithmContext(new Greedy("SDO", "ABW", 1));
		GraphAlgorithmContext algFIT = new GraphAlgorithmContext(new Greedy("FIT", "MF", 1));
		
		
		for(int i = 0 ; i < step ; ++i){
			
			double error = ErrorFunctionEricsson.computeStat((EricssonGraph) graph);
			System.out.format("Old error: %f\n", error);		
			System.out.format("Color change: %f\n\n", ChangeColorGlobal.computeStat((EricssonGraph) graph));
			
			algSDO.startAlgorithm(graph, this.getTouchableNodes());
			
			double newError = ErrorFunctionEricsson.computeStat((EricssonGraph) graph);
			
			if ( error - newError <= 2.0){
				algFIT.startAlgorithm(graph, this.getTouchableNodes());
			}
		}
	}

}
