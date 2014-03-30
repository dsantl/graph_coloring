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
	private int mutationIteration;
	private Random rnd = new Random();
	private List<Integer> nodesForColoring;
	private ColorSelector colorABW;
	private ColorSelector colorMF;
	private ColorSelector colorRND;
	
	public GeneticAlgorithm(int miSize, int lambdaSize, int iterations, int mutationIteration){
		this.miSize = miSize;
		this.lambdaSize = lambdaSize;
		this.iterations = iterations;
		this.mutationIteration = mutationIteration;
		try {
			colorABW = ColorSelectorFactory.factory("ABW");
			colorMF = ColorSelectorFactory.factory("MF");
			colorRND = ColorSelectorFactory.factory("RND");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initMiSet(List<GeneralUnit> miSet){
		nodesForColoring = GetColorableNodes.getNodeIdsFilter((EricssonGraph) graph, this.getTouchableNodes());
		for(int i = 0 ; i < miSize ; ++i){
			GeneralUnit unit = new GeneralUnit((EricssonGraph) graph);
			miSet.add(unit);
		}
	}
	
	private GeneralUnit select(List<GeneralUnit> miSet){
		GeneralUnit oldUnit = miSet.get(rnd.nextInt(miSet.size()));
		GeneralUnit newUnit = new GeneralUnit((EricssonGraph)graph, oldUnit);
		return newUnit;
	}
	
	private void pertubation(GeneralUnit unit){
		
		double choice = rnd.nextDouble();
		
		for(int i = 0; i < this.mutationIteration ; ++i)
		{
			int nodeId = this.nodesForColoring.get(rnd.nextInt(nodesForColoring.size()));
			if ( choice < 0.5 ){
				unit.setColor(nodeId, colorABW);
			}
			else if ( choice < 0.8 ){
				unit.setColor(nodeId, colorMF);
			}
			else{
				unit.setColor(nodeId, colorRND);
			}
		}
	}
	
	class GeneticUnitComp implements Comparator<GeneralUnit>{

		@Override
		public int compare(GeneralUnit unit1, GeneralUnit unit2) {
			return Double.compare(unit1.getError(), unit2.getError());
		}
		
	}
	
	private void collectBestUnits(List<GeneralUnit> miSet, List<GeneralUnit> lambdaSet){
		Collections.sort(lambdaSet, new GeneticUnitComp());
		miSet.clear();
		for(int i = 0 ; i < miSize - 1; ++i)
			miSet.add(lambdaSet.get(i));
	}
	
	@Override
	protected void algorithm() {
		
		List<GeneralUnit> miSet = new ArrayList<GeneralUnit>();
		List<GeneralUnit> lambdaSet = new ArrayList<GeneralUnit>();
		GeneralUnit elit;
		
		initMiSet(miSet);
		
		for(int i = 0 ; i < iterations ; ++i){
			lambdaSet.clear();
			for(int k = 0 ; k < lambdaSize ; ++k){
				GeneralUnit individual = this.select(miSet);
				this.pertubation(individual);
				lambdaSet.add(individual);
				//System.out.format("Error: %f\n", individual.getError());
			}
			Collections.sort(miSet, new GeneticUnitComp());
			elit = miSet.get(0);
			System.out.format("Error: %f\n", elit.getError());
			collectBestUnits(miSet, lambdaSet);
			miSet.add(elit);
		}
		
	}

}
