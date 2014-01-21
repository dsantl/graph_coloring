package graph_coloring.algorithmset.genetic.permutation_genetic;

import graph_coloring.structure.EricssonGraph;
import graph_coloring.structure.Graph;
import graph_coloring.algorithm.GraphColoringAlgorithm;

import java.util.List;
import java.util.Random;

public class PermutationGenetic implements GraphColoringAlgorithm{
	
	private EricssonGraph graph;
	private Random randomGen = new Random();
	private List<PerGenome> population;
	private PerGenome bestGenome;
	private int populationSize = 10;
	private double mutationRate = 0.01;
	private double mutationParam = 0.01;
	private int maxIteration = 1000;
	private List<Integer> colorableNodes;
	private double targetChange = 0.42;


	@Override
	public void startAlgorithm(List<Integer> nodes, Graph graph) {
		
		this.graph = (EricssonGraph) graph;
		int size = (int)(nodes.size() * this.targetChange);
		
		initPopulation(size);
		
		for(int i = 0 ; i < maxIteration ; ++i){
			
		}
		
	}


	private void initPopulation(int size) {
		for(int i = 0 ; i < populationSize ; ++i){
			PerGenome newGenome = new PerGenome(size, colorableNodes);
			newGenome.randomize();
			newGenome.setError();
		}
	}
}
