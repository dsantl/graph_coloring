package graph_coloring.algorithmset.genetic.permutation_genetic;

import graph_coloring.structure.EricssonGraph;
import graph_coloring.structure.Graph;
import graph_coloring.algorithm.GraphColoringAlgorithm;
import graph_coloring.algorithmset.MWFast;
import graph_coloring.algorithmset.MinimalNeighbourWeight;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class PermutationGenetic implements GraphColoringAlgorithm{
	
	private EricssonGraph graph;
	private Random randomGen = new Random();
	private List<PerGenome> population = new ArrayList<PerGenome>();
	private PerGenome bestGenome;
	private int populationSize = 10;
	private double mutationRate = 0.01;
	private double mutationParam = 0.01;
	private int maxIteration = 1000;
	private List<Integer> colorableNodes;
	private double targetChange = 0.46;
	private GraphColoringAlgorithm algorithm;
	private int size;
	

	@Override
	public void startAlgorithm(List<Integer> nodes, Graph graph) {
		
		this.algorithm = new MWFast();
		this.graph = (EricssonGraph) graph;
		this.size = (int)(nodes.size() * this.targetChange);
		this.colorableNodes = nodes;
		
		initPopulation();
		evolution();
		
		bestGenome.setGraph(this.graph);
	}


	private void evolution(){

		for(int i = 0 ; i < maxIteration ; ++i){
			selection();
			crossing();
			mutation();
			PerGenome child = population.get(2);
			child.setError(graph);
			if ( child.getError() < bestGenome.getError() )
				bestGenome = child;
			
			System.out.format("%f %f\n", child.getError(), bestGenome.getError());
		}
	}
	
	private void selection(){
		Collections.shuffle(population);
		
		if ( population.get(0).getError() > population.get(1).getError() && population.get(0).getError() > population.get(2).getError()){
			Collections.swap(population, 0, 2);
		}
		else if ( population.get(1).getError() > population.get(0).getError() && population.get(1).getError() > population.get(2).getError()){
			Collections.swap(population, 1, 2);
		}
	}
	
	private void crossing(){
		PerGenome first = population.get(0);
		PerGenome second = population.get(1);
		PerGenome child = population.get(2);
		
		int a = randomGen.nextInt(this.size);
		int b = randomGen.nextInt(this.size);
		
		if (a > b){
			int tmp = a;
			a = b;
			b = tmp;
		}
		
		child.crossing(colorableNodes, first, second, a, b);
	}
	
	private void mutation(){
		PerGenome child = population.get(3);
		
		if ( randomGen.nextDouble() > mutationRate)
			return;
		
		int noOfMutations = (int)(size * mutationParam);
		
		for(int i = 0 ; i < noOfMutations ; ++i){
			child.swap(randomGen.nextInt(this.size), randomGen.nextInt(size));
		}
	}
	
	private void initPopulation() {
		for(int i = 0 ; i < populationSize ; ++i){
			PerGenome newGenome = new PerGenome(size, colorableNodes, algorithm);
			newGenome.randomize(colorableNodes);
			newGenome.setError(graph);
			population.add(newGenome);
			if ( i == 0 || newGenome.getError() < bestGenome.getError() )
				bestGenome = newGenome;
			System.out.format("%d %f\n", i, newGenome.getError());
		}
	}
}
