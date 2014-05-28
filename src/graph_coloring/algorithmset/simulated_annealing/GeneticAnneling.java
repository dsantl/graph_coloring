package graph_coloring.algorithmset.simulated_annealing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import graph_coloring.algorithm.GraphColoringAlgorithm;
import graph_coloring.algorithm.unit.GeneralUnit;
import graph_coloring.color_selector.ColorSelector;
import graph_coloring.color_selector.ColorSelectorFactory;
import graph_coloring.stat.GetColorableNodes;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonGraph;

public class GeneticAnneling extends GraphColoringAlgorithm{

	private int steps;
	private int numberOfUnits;
	private double startEnergy;
	private double coolingFactor;
	private double prop;
	private String localColorSelector;
	private String globalColorSelector;
	private ColorSelector gColorSelector;
	private ColorSelector lColorSelector;
	private List<GeneralUnit> units;
	private List<Integer> colorableNodes;
	private Random rnd = new Random();
	private EricssonGraph ericssonGraph;
	
	/**
	 * Constructor
	 * @param steps Total number of iterations
	 * @param numberOfUnits Number of unuts
	 * @param startEnergy Start energy
	 * @param coolingFactor Cooling factor for energy
	 */
	public GeneticAnneling(int steps, int numberOfUnits, double startEnergy, double coolingFactor, double prop,
			String localColorSelector, String globalColorSelector){
		this.steps = steps;
		this.numberOfUnits = numberOfUnits;
		this.startEnergy = startEnergy;
		this.coolingFactor = coolingFactor;
		this.prop = prop;
		this.localColorSelector = localColorSelector;
		this.globalColorSelector = globalColorSelector;
	}
	
	/**
	 * Init population, one node is select and random color is set
	 */
	private void initPopulation(){
		this.units = new ArrayList<GeneralUnit>();
		
		for(int i = 0 ; i < this.numberOfUnits ; ++i){
			GeneralUnit unit = new GeneralUnit(this.ericssonGraph);
			unit.setColor(colorableNodes.get(rnd.nextInt(colorableNodes.size())), gColorSelector);
			this.units.add(unit);
		}
	}
	
	/**
	 * Init color selectors
	 */
	private void initColorSelector(){
		try {
			gColorSelector = ColorSelectorFactory.factory(this.globalColorSelector);
			lColorSelector = ColorSelectorFactory.factory(this.localColorSelector);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Mutation - choose one node and color selector
	 * @param unit
	 */
	private void mutation(GeneralUnit unit){
		
		double choose = rnd.nextDouble();
		
		int nodeId = colorableNodes.get(rnd.nextInt(colorableNodes.size()));
		ColorSelector colorSelector;
		
		
		if ( choose < this.prop ){
			colorSelector = lColorSelector;
		}
		else{
			colorSelector = gColorSelector;
		}
		
		unit.setColor(nodeId, colorSelector);
	}
	
	/**
	 * Find best unit in unit set
	 * @param bestUnit
	 * @return best unit
	 */
	private GeneralUnit findBestUnit(GeneralUnit bestUnit){
		
		double minError = bestUnit.getError();
		GeneralUnit best = bestUnit;
		
		for(int i = 0 ; i < this.numberOfUnits ; ++i){
			double currError = this.units.get(i).getError();
			if ( currError < minError ){
				minError = currError;
				best = this.units.get(i);
			}
		}
		return best;
	}
	
	@Override
	protected void algorithm(){
		this.ericssonGraph = (EricssonGraph) graph;
		this.colorableNodes = GetColorableNodes.getNodeIds(ericssonGraph);
		
		initColorSelector();
		initPopulation();
		
		GeneralUnit bestUnit = new GeneralUnit(ericssonGraph);
		double freeEnergy = this.startEnergy;
		
		for(int i = 0 ; i < this.steps ; ++i){
			double treshold = freeEnergy/this.numberOfUnits;
			freeEnergy = 0.0;
			for(int j = 0 ; j < this.numberOfUnits ; ++j){
				GeneralUnit currUnit = units.get(j);
				GeneralUnit newUnit = new GeneralUnit(this.ericssonGraph, currUnit);
				mutation(newUnit);
				
				if (newUnit.getError() < currUnit.getError() + treshold){
					double diff = currUnit.getError() + treshold - newUnit.getError();
					freeEnergy += Math.max(0, diff);
					units.set(j, newUnit);
				}
				else
					freeEnergy += currUnit.getError() + treshold;
			}
		
			bestUnit = findBestUnit(bestUnit);
			if ( i % 100 == 0){
				System.out.format("Error: %f\n", bestUnit.getError());
			}
			if ( i % 100 == 0)
				System.out.format("Energy: %f\n", freeEnergy);
			
			freeEnergy *= this.coolingFactor;
		}
		bestUnit.updateGraph();
	}
	
}
