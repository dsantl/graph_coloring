package graph_coloring.algorithmset.genetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import graph_coloring.algorithm.GraphColoringAlgorithm;
import graph_coloring.algorithm.unit.GeneralUnit;
import graph_coloring.color_selector.ColorSelector;
import graph_coloring.color_selector.ColorSelectorFactory;
import graph_coloring.stat.GetColorableNodes;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonGraph;

public class GeneticAlgorithm extends GraphColoringAlgorithm{

	private int miSize;
	private int lambdaSize;
	private int iterations;
	private int mutationIterations;
	private double localProp;
	private Random rnd = new Random();
	private List<Integer> nodesForColoring;
	private ColorSelector colorLocal;
	private ColorSelector colorGlobal;
	
	/**
	 * Constructor
	 * @param miSize Size of mi set (population size)
	 * @param lambdaSize Size of lambda set (new population size) < miSize
	 * @param iterations Total iterations
	 * @param mutationIteration How many mutation is done on one unit
	 */
	public GeneticAlgorithm(int miSize, int lambdaSize, int iterations, 
			int mutationIterations, double localProp, 
			String localColorSelector, String globalColorSelector){
		this.miSize = miSize;
		this.lambdaSize = lambdaSize;
		this.iterations = iterations;
		this.mutationIterations = mutationIterations;
		this.localProp = localProp;
		try {
			colorLocal = ColorSelectorFactory.factory(localColorSelector);
			colorGlobal = ColorSelectorFactory.factory(globalColorSelector);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Init mi set
	 * @param miSet
	 */
	private void initMiSet(List<GeneralUnit> miSet){
		nodesForColoring = GetColorableNodes.getNodeIdsFilter((EricssonGraph) graph, this.getTouchableNodes());
		for(int i = 0 ; i < miSize ; ++i){
			GeneralUnit unit = new GeneralUnit((EricssonGraph) graph);
			miSet.add(unit);
		}
	}
	
	/**
	 * Select unit
	 * @param miSet
	 * @return unit from mi set
	 */
	private GeneralUnit select(List<GeneralUnit> miSet){
		GeneralUnit oldUnit = miSet.get(rnd.nextInt(miSet.size()));
		GeneralUnit newUnit = new GeneralUnit((EricssonGraph)graph, oldUnit);
		return newUnit;
	}
	
	
	/**
	 * Make mutation on unit
	 * @param unit
	 */
	private void pertubation(GeneralUnit unit){
		
		double choice = rnd.nextDouble();
		
		int max_iterations = (int)(rnd.nextDouble() * this.mutationIterations)+1;
		
		for(int i = 0; i < max_iterations ; ++i)
		{
			int nodeId = this.nodesForColoring.get(rnd.nextInt(nodesForColoring.size()));
			if ( choice < this.localProp )
				unit.setColor(nodeId, colorLocal);
			else
				unit.setColor(nodeId, colorGlobal);
		}
		
	}
	
	class GeneticUnitComp implements Comparator<GeneralUnit>{

		@Override
		public int compare(GeneralUnit unit1, GeneralUnit unit2) {
			return Double.compare(unit1.getError(), unit2.getError());
		}
		
	}
	
	/**
	 * Collect best units from lambda set and copy in mi set
	 * @param miSet
	 * @param lambdaSet
	 */
	private void collectBestUnits(List<GeneralUnit> miSet, List<GeneralUnit> lambdaSet){
		miSet.clear();
		for(int i = 0 ; i < miSize - 1; ++i)
			miSet.add(lambdaSet.get(i));
	}
	
	@Override
	protected void algorithm() {
		
		List<GeneralUnit> miSet = new ArrayList<GeneralUnit>();
		List<GeneralUnit> lambdaSet = new ArrayList<GeneralUnit>();
		GeneralUnit elit = null;
		
		
		initMiSet(miSet);
		
		for(int i = 0 ; i < iterations ; ++i){
			lambdaSet.clear();
			for(int k = 0 ; k < lambdaSize ; ++k){
				GeneralUnit individual = this.select(miSet);
				this.pertubation(individual);
				lambdaSet.add(individual);
				//System.out.format("Error: %f\n", individual.getError());
			}
			
			Collections.sort(lambdaSet, new GeneticUnitComp());
			
			/*
			if ( elit != null && elit.getError() == lambdaSet.get(0).getError() )
				for(Integer id : this.nodesForColoring){
					lambdaSet.get(0).setColor(id, colorSWAP);
				}
			*/
			
			if ( elit == null || elit.getError() > lambdaSet.get(0).getError() )
				elit = lambdaSet.get(0);
			
			if ( i % 100 == 0)
				System.out.format("Error global: %f\n", elit.getError());
			
			collectBestUnits(miSet, lambdaSet);
			miSet.add(elit);
		}
		
		elit.updateGraph();
	}

}
