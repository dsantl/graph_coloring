package graph_coloring.algorithmset.genetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import graph_coloring.algorithm.GraphColoringAlgorithm;
import graph_coloring.algorithm.unit.GeneralUnit;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonGraph;

public class GeneticAlgorithm extends GraphColoringAlgorithm{

	private int miSize;
	private int lambdaSize;
	private int iterations;
	private Random rnd = new Random();
	
	public GeneticAlgorithm(int miSize, int lambdaSize, int iterations){
		this.miSize = miSize;
		this.lambdaSize = lambdaSize;
		this.iterations = iterations;
	}
	
	private void initMiSet(List<GeneralUnit> miSet){
		for(int i = 0 ; i < miSize ; ++i){
			GeneralUnit unit = new GeneralUnit((EricssonGraph) graph);
			//unit.setRNDColor(0.01, this.getTouchableNodes());
			miSet.add(unit);
		}
	}
	
	private GeneralUnit select(List<GeneralUnit> miSet){
		GeneralUnit newUnit = new GeneralUnit((EricssonGraph)graph);
		newUnit.copy(miSet.get(rnd.nextInt(miSet.size())));
		return newUnit;
	}
	
	private void pertubation(GeneralUnit unit){
		if ( rnd.nextDouble() < 0.5 ){
			unit.setColor(1, "SDO", "ABW", null, this.getTouchableNodes());
		}
		else if ( rnd.nextDouble() < 0.8 ){
			unit.setColor(1, "RND", "MF", null, this.getTouchableNodes());
		}
		else{
			unit.setColor(0.001, "RND", "RND", null, this.getTouchableNodes());
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
			}
			Collections.sort(miSet, new GeneticUnitComp());
			elit = miSet.get(0);
			System.out.format("Error: %f\n", elit.getError());
			collectBestUnits(miSet, lambdaSet);
			miSet.add(elit);
		}
		
	}

}
