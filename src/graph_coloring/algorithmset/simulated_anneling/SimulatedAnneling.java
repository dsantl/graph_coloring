package graph_coloring.algorithmset.simulated_anneling;

import java.util.Random;

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
	private String colorSelector;
	private Random rnd = new Random();
	
	public SimulatedAnneling(double startTemperature, int temperatureChangeSteps, int thermalEquilibrium, double changeRate, String colorSelector){
		this.startTemperature = startTemperature;
		this.thermalEquilibrium = thermalEquilibrium;
		this.temperatureChangeSteps = temperatureChangeSteps;
		this.changeRate = changeRate;
		this.colorSelector = colorSelector;
	}
	
	private GeneralUnit similarSolution(GeneralUnit bestUnit){
		
		GeneralUnit newUnit = new GeneralUnit((EricssonGraph) this.graph);
		newUnit.copy(bestUnit);
		newUnit.changeColor(changeRate, colorSelector, this.getTouchableNodes());
		return newUnit;
	}
	
	@Override
	protected void algorithm() {
		
		GeneralUnit bestUnit = new GeneralUnit((EricssonGraph) this.graph);
		
		double T = this.startTemperature;
		double dError;
		int convergenceCount = 0;
		
		for(int i = 0 ; i < temperatureChangeSteps ; ++i){
			for(int j = 0 ; j < thermalEquilibrium ; ++j){
				
				
				
				GeneralUnit currUnit = this.similarSolution(bestUnit);
				
				if (convergenceCount == 3){
					currUnit.changeColor(0.3, "RND", this.getTouchableNodes());
					currUnit.changeColor(1, "ABW", this.getTouchableNodes());
					convergenceCount = 0;
				}
				
				double bestError = bestUnit.getError();
				double currError = currUnit.getError();
				dError = currError - bestError;
				
				System.out.format("%f %f\n", bestError, currError);
				System.out.format("Valid coloring: %b\n", CheckValidColoring.computeStat((EricssonGraph)graph));
				System.out.format("%f %f\n\n", T, Math.exp(-dError/T));
				
				if ( dError == 0.0 ){
					convergenceCount += 1;
				}
				
				if ( dError < 0 || rnd.nextDouble() < Math.exp(-dError/T) ){
					bestUnit = currUnit;
				}
			}
			T -= this.startTemperature/temperatureChangeSteps;
		}
		
		bestUnit.setColorToGraph();
	}
}
