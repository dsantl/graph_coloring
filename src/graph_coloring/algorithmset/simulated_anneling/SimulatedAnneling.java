package graph_coloring.algorithmset.simulated_anneling;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import graph_coloring.algorithm.GraphColoringAlgorithm;
import graph_coloring.algorithm.unit.GeneralUnit;
import graph_coloring.color_selector.ColorSelector;
import graph_coloring.color_selector.ColorSelectorFactory;
import graph_coloring.stat.CheckValidColoring;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonGraph;

public class SimulatedAnneling extends GraphColoringAlgorithm{

	private double startTemperature;
	private int thermalEquilibrium;
	private int temperatureChangeSteps;
	private double changeRate;
	private ColorSelector colorSelector;
	private Random rnd = new Random();
	private Set<Integer> nodeIdSet = new HashSet<Integer>();
	
	public SimulatedAnneling(double startTemperature, int temperatureChangeSteps, int thermalEquilibrium, double changeRate, String colorSelector){
		this.startTemperature = startTemperature;
		this.thermalEquilibrium = thermalEquilibrium;
		this.temperatureChangeSteps = temperatureChangeSteps;
		this.changeRate = changeRate;
		
		try {
			this.colorSelector = ColorSelectorFactory.factory(colorSelector);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private GeneralUnit similarSolution(GeneralUnit bestUnit){
		
		GeneralUnit newUnit = new GeneralUnit((EricssonGraph) this.graph);
		newUnit.copy(bestUnit);
		
		int nodeChangeSize = (int)(changeRate * bestUnit.getSize());
		nodeIdSet.clear();
		
		while(nodeIdSet.size() < nodeChangeSize){
			int nodeId = graph.getNodeId(rnd.nextInt(bestUnit.getSize()));
			nodeIdSet.add(nodeId);
		}
		
		newUnit.changeColor(nodeIdSet, colorSelector);
	
		return newUnit;
	}
	
	@Override
	protected void algorithm() {
		
		GeneralUnit bestUnit = new GeneralUnit((EricssonGraph) this.graph);
		
		double T = this.startTemperature;
		double dError;
		
		for(int i = 0 ; i < temperatureChangeSteps ; ++i){
			for(int j = 0 ; j < thermalEquilibrium ; ++j){
				
				GeneralUnit currUnit = this.similarSolution(bestUnit);
				double bestError = bestUnit.getError();
				double currError = currUnit.getError();
				
				System.out.format("%f %f\n", bestError, currError);
				System.out.format("Valid coloring: %b\n", CheckValidColoring.computeStat((EricssonGraph)graph));
				
				dError = currError - bestError;
				
				if ( dError < 0 || rnd.nextDouble() < Math.exp(-dError/T) ){
					bestUnit = currUnit;
				}
			}
			T -= this.startTemperature/temperatureChangeSteps;
		}
		
		bestUnit.setColorToGraph();
	}
}
