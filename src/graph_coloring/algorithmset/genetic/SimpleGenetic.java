package graph_coloring.algorithmset.genetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import graph_coloring.algorithm.GraphColoringAlgorithm;
import graph_coloring.structure.EricssonGraph;
import graph_coloring.structure.EricssonNode;
import graph_coloring.structure.Graph;

public class SimpleGenetic implements GraphColoringAlgorithm{
	
	private EricssonGraph graph;
	private Random randomGen = new Random();
	private List<EricssonGraph> population;
	private EricssonGraph bestGenome;
	private int populationSize = 20;
	private double mutationRate = 0.001;
	private int maxIteration = 100;
	private List<Integer> colorableNodes;
	
	
	private void initPopulation(){
		population = new ArrayList<EricssonGraph>();
		for(int i = 0 ; i < populationSize ; ++i){
			EricssonGraph genome = new EricssonGraph(graph);
			
			this.setRandomColors(genome, mutationRate*0.01);
			
			System.out.format("%d %f\n", i, genome.getError());
			
			if ( i == 0 || bestGenome.getError() > genome.getError() )
				bestGenome = genome;
			
			population.add(genome);
		}
	}
	
	private void setRandomColors(EricssonGraph genome, double rate) {
		
		for(Integer node : genome.getNodeIndices()){
			EricssonNode eNode = (EricssonNode) genome.getNode(node);
			if ( randomGen.nextDouble() < rate ){
				if ( eNode.getColorable() ){
					List<Integer> colors = genome.getAllColorsOfClass(eNode.getColorClass());
					int color = colors.get(randomGen.nextInt(colors.size()));
					genome.setNodeColor(node, color);
				}
			}
		}
	}

	@Override
	public void startAlgorithm(List<Integer> nodes, Graph graph) {
		
		this.colorableNodes = nodes;
		this.graph = (EricssonGraph) graph;
		
		initPopulation();
		
		for(int i = 0 ; i < maxIteration ; ++i){
			selection();
			EricssonGraph child = crossing();
			mutation(child);
			
			if ( child.getError() < bestGenome.getError() )
				bestGenome = child;
			
			System.out.format("%f %f\n", child.getError(), bestGenome.getError());
		}
		
	}

	private void mutation(EricssonGraph child) {
		
		this.setRandomColors(child, mutationRate);
		
	}

	private EricssonGraph crossing() {
		EricssonGraph first = population.get(0);
		EricssonGraph second = population.get(1);
		EricssonGraph child = population.get(2);
		
		
		int len = this.colorableNodes.size();
		int crossPoint = randomGen.nextInt(len);
		
		for(int i = 0 ; i < len ; ++i){
			int node = colorableNodes.get(i);
			if ( i < crossPoint  )
				child.setNodeColor(node, first.getNode(node).getColor() );
			else
				child.setNodeColor(node, second.getNode(node).getColor() );
		}
		
		return child;
	}

	private void selection() {
		
		Collections.shuffle(population);
		
		double error0 = population.get(0).getError();
		double error1 = population.get(1).getError();
		double error2 = population.get(2).getError();
		
		if ( error0 > error1 && error0 > error2 )
			Collections.swap(population, 0, 2);
		else if ( error1 > error0 && error1 > error2)
			Collections.swap(population, 1, 2);
	}
	
	
	
}
