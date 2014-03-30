package graph_coloring.algorithmset.simulated_anneling;

import java.util.List;
import java.util.Random;

import graph_coloring.algorithm.GraphColoringAlgorithm;
import graph_coloring.color_selector.ColorSelector;
import graph_coloring.color_selector.ColorSelectorFactory;
import graph_coloring.stat.ErrorFunctionEricsson;
import graph_coloring.stat.GetColorableNodes;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonGraph;

public class SimpleAnneling extends GraphColoringAlgorithm{

	private double startTemperature;
	private int thermalEquilibrium;
	private int temperatureChangeSteps;
	private Random rnd = new Random();
	
	public SimpleAnneling(double startTemperature, int temperatureChangeSteps, int thermalEquilibrium){
		this.startTemperature = startTemperature;
		this.thermalEquilibrium = thermalEquilibrium;
		this.temperatureChangeSteps = temperatureChangeSteps;
	}
	
	
	@Override
	protected void algorithm() {
		
		EricssonGraph ericssonGraph = (EricssonGraph) graph;
		double T = this.startTemperature;
		double dError;
		double bestError = ErrorFunctionEricsson.computeStat(ericssonGraph);
		double currError = 0;
		
		ColorSelector colorSelector = null;
		ColorSelector rndColorSelector = null;
		try {
			rndColorSelector = ColorSelectorFactory.factory("RND");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			colorSelector = ColorSelectorFactory.factory("ABW");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<Integer> colorableNodes = GetColorableNodes.getNodeIds(ericssonGraph);
		
		for(int i = 0 ; i < temperatureChangeSteps ; ++i){
			for(int j = 0 ; j < thermalEquilibrium ; ++j){
				
				ColorSelector colorSel;
				if ( rnd.nextDouble() < 0.8 )
					colorSel = colorSelector;
				else
					colorSel = rndColorSelector;
				
				int nodeId = colorableNodes.get(rnd.nextInt(colorableNodes.size()));
				int nodeIndex = ericssonGraph.getNodeIndex(nodeId);
				int color = ericssonGraph.chooseColor(nodeIndex, colorSel);
				
				currError = bestError - 2*ericssonGraph.getNodeError(nodeIndex);
				currError += 2*ericssonGraph.getNodeColorError(nodeIndex, color);
				
				dError = currError - bestError;
				
				
				if ( dError < 0 || rnd.nextDouble() < Math.exp(-dError/T) ){
					graph.setNodeColor(nodeIndex, color);
					bestError = currError;
				}
			}
			T -= this.startTemperature/temperatureChangeSteps;
			if ( i%1000 == 0)
				System.out.format("%f %f\n", bestError, currError);
		}
	}
}
