package graph_coloring.algorithmset.simulated_anneling;

import java.util.List;
import java.util.Random;

import graph_coloring.algorithm.GraphColoringAlgorithm;
import graph_coloring.color_selector.ColorSelector;
import graph_coloring.color_selector.ColorSelectorFactory;
import graph_coloring.stat.ChangeColorGlobal;
import graph_coloring.stat.ErrorFunctionEricsson;
import graph_coloring.stat.GetColorableNodes;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonGraph;

public class SimulatedAnneling extends GraphColoringAlgorithm{

	private double startTemperature;
	private int thermalEquilibrium;
	private int temperatureChangeSteps;
	private Random rnd = new Random();
	
	/**
	 * Constructor
	 * @param startTemperature Start temperature
	 * @param temperatureChangeSteps Number of global iterations
	 * @param thermalEquilibrium Number of thermal equilibrium steps (inner loop)
	 */
	public SimulatedAnneling(double startTemperature, int temperatureChangeSteps, int thermalEquilibrium){
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
		int NC = GetColorableNodes.getNumberOfColorableNodes(ericssonGraph);
		int bestChange = (int)(NC*ChangeColorGlobal.computeStat(ericssonGraph));
		int currChange;
		
		ColorSelector abwColorSelector = null;
		ColorSelector rndColorSelector = null;
		try {
			rndColorSelector = ColorSelectorFactory.factory("RND");
			abwColorSelector = ColorSelectorFactory.factory("ABW");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
		List<Integer> colorableNodes = GetColorableNodes.getNodeIdsFilter(ericssonGraph, this.getTouchableNodes());
		
		//double alpha = 0;
		
		for(int i = 0 ; i < temperatureChangeSteps ; ++i){
			for(int j = 0 ; j < thermalEquilibrium ; ++j){
				
				ColorSelector colorSelector;
				double choice = rnd.nextDouble();
				if ( choice < 0.8 )
					colorSelector = abwColorSelector;
				else
					colorSelector = rndColorSelector;
				
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
				//double pos = (double)(currChange)/NC; 
				
				
				dError = currError - bestError;
				//dError += alpha*pos*dError/bestError;
				
				if ( dError < 0 || rnd.nextDouble() < Math.exp(-dError/T) ){
					graph.setNodeColor(nodeIndex, color);
					bestError = currError;
					bestChange = currChange;
				}
			}
			T -= this.startTemperature/temperatureChangeSteps;
			//alpha += 0.001;
			
			if (i%1000 == 0){
				System.out.format("%f %f\n", bestError, currError);
				System.out.println((double)bestChange/NC);
			}
			//if ( bestError < 75.0 )
			//	break;
		}
	}
}
