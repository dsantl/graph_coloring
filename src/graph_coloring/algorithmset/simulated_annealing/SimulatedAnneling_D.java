package graph_coloring.algorithmset.simulated_annealing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import graph_coloring.algorithm.GraphColoringAlgorithm;
import graph_coloring.color_selector.ColorSelector;
import graph_coloring.color_selector.ColorSelectorFactory;
import graph_coloring.stat.CollisionCounter;

public class SimulatedAnneling_D extends GraphColoringAlgorithm{

	private double startTemperature;
	private int thermalEquilibrium;
	private int temperatureChangeSteps;
	private String localColorSelector;
	private String globalColorSelector;
	private double alpha;
	private double prop;
	private Random rnd = new Random();
	
	
	public SimulatedAnneling_D(double startTemperature, int temperatureChangeSteps, int thermalEquilibrium,
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
		
		double T = this.startTemperature;
		double dError;
		double bestError = CollisionCounter.computeStat(graph);
		double currError = 0;
		
		ColorSelector globalColorSelector = null;
		ColorSelector localColorSelector = null;
		
		
		try {
			globalColorSelector = ColorSelectorFactory.factory(this.globalColorSelector);
			localColorSelector = ColorSelectorFactory.factory(this.localColorSelector);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
		List<Integer> colorableNodes = new ArrayList<Integer>();
		
		for(int i = 0 ; i < graph.getNodeSize() ; ++i){
			colorableNodes.add(graph.getNodeId(i));
		}
		
		
		for(int i = 0 ; i < temperatureChangeSteps ; ++i){
			for(int j = 0 ; j < thermalEquilibrium ; ++j){
				
				
				ColorSelector colorSelector;
				double choice = rnd.nextDouble();
				
				if ( choice < this.prop )
					colorSelector = localColorSelector;
				else
					colorSelector = globalColorSelector;
				
				int nodeId = colorableNodes.get(rnd.nextInt(colorableNodes.size()));
				int nodeIndex = graph.getNodeIndex(nodeId);
				int color = graph.chooseColor(nodeIndex, colorSelector);
				
				currError = bestError - 2*graph.getNodeCollision(nodeIndex);
				currError += 2*graph.getNodeColorCollsision(nodeIndex, color);
				
				dError = currError - bestError;
				
				if ( dError < 0 || rnd.nextDouble() < Math.exp(-dError/T) ){
					graph.setNodeColor(nodeIndex, color);
					bestError = currError;
				}
				
				System.out.println(bestError);
			}
			
			if (i % 100 == 0){
				System.err.println(bestError);
				System.err.println(i);
			}
			T = T * this.alpha;
		}
	}
}
