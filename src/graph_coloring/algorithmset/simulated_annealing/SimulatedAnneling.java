package graph_coloring.algorithmset.simulated_annealing;

import java.util.List;
import java.util.Random;

import graph_coloring.algorithm.GraphColoringAlgorithm;
import graph_coloring.color_selector.ColorSelector;
import graph_coloring.color_selector.ColorSelectorFactory;
import graph_coloring.order.OrderMethodFactory;
import graph_coloring.stat.ChangeColorGlobal;
import graph_coloring.stat.ErrorFunctionEricsson;
import graph_coloring.stat.GetColorableNodes;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonGraph;

public class SimulatedAnneling extends GraphColoringAlgorithm{

	private double startTemperature;
	private int thermalEquilibrium;
	private int temperatureChangeSteps;
	private String localColorSelector;
	private String globalColorSelector;
	private double alpha;
	private double prop;
	private Random rnd = new Random();
	
	
	public SimulatedAnneling(double startTemperature, int temperatureChangeSteps, int thermalEquilibrium,
			double prop, double alpha, String localColorSelector, String globalColorSelector){
		this.startTemperature = startTemperature;
		this.thermalEquilibrium = thermalEquilibrium;
		this.temperatureChangeSteps = temperatureChangeSteps;
		this.alpha = alpha;
		this.localColorSelector = localColorSelector;
		this.globalColorSelector = globalColorSelector;
		this.prop = prop;
	}
	
	
	@Override
	protected void algorithm() {
		
		EricssonGraph ericssonGraph = (EricssonGraph) graph;
		double T = this.startTemperature;
		double dError;
		double bestError = ErrorFunctionEricsson.computeStat(ericssonGraph);
		double currError = 0;
		int NC = GetColorableNodes.getNumberOfColorableNodes(ericssonGraph);
		int bestChange = (int)(NC*ChangeColorGlobal.computeStat(ericssonGraph));
		int currChange;
		ColorSelector globalColorSelector = null;
		ColorSelector localColorSelector = null;
		
		
		try {
			globalColorSelector = ColorSelectorFactory.factory(this.globalColorSelector);
			localColorSelector = ColorSelectorFactory.factory(this.localColorSelector);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
		List<Integer> colorableNodes = GetColorableNodes.getNodeIdsFilter(ericssonGraph, this.getTouchableNodes());
			
		for(int i = 0 ; i < temperatureChangeSteps ; ++i){
			for(int j = 0 ; j < thermalEquilibrium ; ++j){
				
				ColorSelector colorSelector;
				double choice = rnd.nextDouble();
				
				if ( choice < this.prop )
					colorSelector = localColorSelector;
				else
					colorSelector = globalColorSelector;
				
				
				int nodeId = colorableNodes.get(rnd.nextInt(colorableNodes.size()));
				int nodeIndex = ericssonGraph.getNodeIndex(nodeId);
				int color = ericssonGraph.chooseColor(nodeIndex, colorSelector);
				
				currError = bestError - 2*ericssonGraph.getNodeError(nodeIndex);
				currError += 2*ericssonGraph.getNodeColorError(nodeIndex, color);
				
				int changeTmp = 0;
				boolean colorIsStart = color == ericssonGraph.getNodeStartColor(nodeIndex);
				boolean oldColorIsStart = ericssonGraph.getNodeColor(nodeIndex) == ericssonGraph.getNodeStartColor(nodeIndex);
				
				if ( colorIsStart == oldColorIsStart )
					changeTmp = 0;
				else if (oldColorIsStart == true)
					changeTmp = 1;
				else
					changeTmp = -1;
				
				currChange = bestChange + changeTmp;
				
				dError = currError - bestError;
				
				if ( dError < 0 || rnd.nextDouble() < Math.exp(-dError/T) ){
					graph.setNodeColor(nodeIndex, color);
					bestError = currError;
					bestChange = currChange;
				}
			}
			T = T * this.alpha;
			
			if (i % 1 == 0){
				System.out.format("%f %f\n", bestError, (double)bestChange/NC);
				System.out.format("%f\n", T);
			}
			
		}
	}
}
