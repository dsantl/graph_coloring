package graph_coloring.algorithmset.simulated_anneling;

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
	private ColorSelector colorSelectorRND;
	private ColorSelector colorSelectorABW;
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
	public GeneticAnneling(int steps, int numberOfUnits, double startEnergy, double coolingFactor){
		this.steps = steps;
		this.numberOfUnits = numberOfUnits;
		this.startEnergy = startEnergy;
		this.coolingFactor = coolingFactor;
	}
	
	/**
	 * Init population, one node is select and random color is set
	 */
	private void initPopulation(){
		this.units = new ArrayList<GeneralUnit>();
		
		for(int i = 0 ; i < this.numberOfUnits ; ++i){
			GeneralUnit unit = new GeneralUnit(this.ericssonGraph);
			unit.setColor(colorableNodes.get(rnd.nextInt(colorableNodes.size())), colorSelectorRND);
			this.units.add(unit);
		}
	}
	
	/**
	 * Init color selectors
	 */
	private void initColorSelector(){
		try {
			colorSelectorRND = ColorSelectorFactory.factory("SWAP");
			colorSelectorABW = ColorSelectorFactory.factory("ABW");
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
		
		
		if ( choose < 0.8 ){
			colorSelector = colorSelectorABW;
		}
		else{
			colorSelector = colorSelectorRND;
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
			freeEnergy = 0;
			for(int j = 0 ; j < this.numberOfUnits ; ++j){
				GeneralUnit currUnit = units.get(j);
				GeneralUnit newUnit = new GeneralUnit(this.ericssonGraph, currUnit);
				mutation(newUnit);
				
				if (newUnit.getError() < currUnit.getError() + treshold){
					double diff = currUnit.getError() + treshold - newUnit.getError();
					freeEnergy += diff;
					units.set(j, newUnit);
				}
			}
		
		bestUnit = findBestUnit(bestUnit);
		if ( i % 1000 == 0){
			System.out.format("Error: %f\n", bestUnit.getError());
		}
		//System.out.format("Energy: %f\n", freeEnergy);
		freeEnergy *= this.coolingFactor;
		}
		bestUnit.updateGraph();
	}
	
}
