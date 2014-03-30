package graph_coloring.algorithmset.simulated_anneling;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import graph_coloring.algorithm.GraphColoringAlgorithm;
import graph_coloring.algorithm.unit.GeneralUnit;
import graph_coloring.color_selector.ColorSelector;
import graph_coloring.color_selector.ColorSelectorFactory;
import graph_coloring.stat.CheckValidColoring;
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
		try {
			colorSelector = ColorSelectorFactory.factory("RND");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<Integer> colorableNodes = GetColorableNodes.getNodeIds(ericssonGraph);
		
		for(int i = 0 ; i < temperatureChangeSteps ; ++i){
			for(int j = 0 ; j < thermalEquilibrium ; ++j){
				
				int nodeId = colorableNodes.get(rnd.nextInt(colorableNodes.size()));
				int nodeIndex = ericssonGraph.getNodeIndex(nodeId);
				int color = ericssonGraph.chooseColor(nodeIndex, colorSelector);
				
				currError = bestError - ericssonGraph.getNodeError(nodeIndex);
				currError += ericssonGraph.getNodeColorError(nodeIndex, color);
				
				dError = currError - bestError;
				
				System.out.format("%f %f %f %f\n", bestError, currError, dError,  Math.exp(-dError/T));
				
				if ( dError < 0 || rnd.nextDouble() < Math.exp(-dError/T) ){
					graph.setNodeColor(nodeIndex, color);
					bestError = currError;
				}
			}
			T -= this.startTemperature/temperatureChangeSteps;
		}
	}
}
