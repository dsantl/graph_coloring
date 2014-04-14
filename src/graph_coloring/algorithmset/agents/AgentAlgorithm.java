package graph_coloring.algorithmset.agents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import graph_coloring.algorithm.GraphAlgorithmContext;
import graph_coloring.algorithm.GraphColoringAlgorithm;
import graph_coloring.algorithmset.greedy.Greedy;
import graph_coloring.stat.ChangeColorGlobal;
import graph_coloring.stat.ErrorFunctionEricsson;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonGraph;

public class AgentAlgorithm extends GraphColoringAlgorithm{

	private int numberOfUnits;
	private int iterations;
	private List<AgentUnit> units = new ArrayList<AgentUnit>();
	private String order;
	private String colorSelector; 
	
	private class AgentCmp implements Comparator<AgentUnit>{

		@Override
		public int compare(AgentUnit arg0, AgentUnit arg1) {
			return -Double.compare(arg0.getScore(), arg1.getScore());
		}
		
	}
	
	/**
	 * Constructor for agent algorithm
	 * @param numberOfUnits number of agents, one agent is connected to one node
	 * @param iterations total number of iterations (one iteration for all agents)
	 * @param order When algorithm have zero error change
	 * @param colorSelector When algorithm have zero error change
	 */
	public AgentAlgorithm(int numberOfUnits, int iterations, String order, String colorSelector){
		this.numberOfUnits = numberOfUnits;
		this.iterations = iterations;
		this.order = order;
		this.colorSelector = colorSelector;
	}
	
	@Override
	protected void algorithm() {
		Random rnd = new Random();
		
		int nodeSize = graph.getNodeSize();
		
		for(int i = 0 ; i < numberOfUnits ; ++i){
			int randomId = graph.getNodeId(rnd.nextInt(nodeSize));
			units.add(new AgentUnit(randomId, (EricssonGraph) this.graph));
		}
		
		startAlgorithm();
	}
	
	private void startAlgorithm(){
		
		GraphAlgorithmContext alg = new GraphAlgorithmContext(new Greedy(order, colorSelector, 1));
		double oldError = 0;
		
		for(int i = 0 ; i < this.iterations ; ++i){
			for(int j = 0 ; j < this.numberOfUnits ; ++j){
				if ( this.checkNode(units.get(j).getNodeIndex()) )
					units.get(j).setColor();
				units.get(j).move();
			}
			Collections.sort(units, new AgentCmp());
			
			double error = ErrorFunctionEricsson.computeStat((EricssonGraph) graph);
			if ( i != 0 && oldError == error)
				alg.startAlgorithm(graph, this.getTouchableNodes());
			
			oldError = error;
			System.out.format("Error: %f\n", error);	
			System.out.format("Color change: %f\n\n", ChangeColorGlobal.computeStat((EricssonGraph) graph));
		}
		
	}
	
}
