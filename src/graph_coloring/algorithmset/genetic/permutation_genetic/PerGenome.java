package graph_coloring.algorithmset.genetic.permutation_genetic;

import graph_coloring.algorithm.GraphColoringAlgorithm;
import graph_coloring.structure.EricssonGraph;
import graph_coloring.structure.Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PerGenome {
	
	private List<Integer> genome = new ArrayList<Integer>(); //subset of colorable nodes
	private int size;
	private double error;
	private GraphColoringAlgorithm algorithm;
	
	public PerGenome(int size, List<Integer> colorableNodes, GraphColoringAlgorithm alg){
		this.size = size;
		this.algorithm = alg;
		for(int i = 0 ; i  < size ; ++i){
			genome.add(colorableNodes.get(i));
		}
	}

	public void randomize(List<Integer> colorableNodes) {
		
		Collections.shuffle(colorableNodes);
		
		for(int i = 0 ; i < size ; ++i){
			genome.set(i, colorableNodes.get(i));
		}
	}

	public double getError(){
		return this.error;
	}
	
	
	public void setError(EricssonGraph graph) {
		double oldError = graph.getError(); 
		algorithm.startAlgorithm(this.genome, graph);
		error = graph.getError();
		graph.reset(oldError);
	}

	public void setGraph(EricssonGraph graph) {
		algorithm.startAlgorithm(this.genome, graph);
	}

	public int get(int index){
		return genome.get(index);
	}
	
	public void crossing(List<Integer> colorable, PerGenome first, PerGenome second, int a, int b) {
		Set<Integer> visited = new HashSet<Integer>();
		
		for(int i = a ; i <= b ; ++i){
			this.genome.set(i, first.get(i));
			visited.add(first.get(i));
		}
		
		for(int i = 0 ; i < a ; ++i){
			if ( !visited.contains(second.get(i)) ){
				this.genome.set(i, second.get(i));
				visited.add(second.get(i));
			}
			else{
				for(Integer node : colorable){
					if ( !visited.contains(node) ){
						this.genome.set(i, node);
						visited.add(node);
					}
				}
			}
		}
		
		for(int i = b+1 ; i < size ; ++i){
			if ( !visited.contains(second.get(i)) ){
				this.genome.set(i, second.get(i));
				visited.add(second.get(i));
			}
			else
			{
				for(Integer node : colorable){
					if ( !visited.contains(node) ){
						this.genome.set(i, node);
						visited.add(node);
					}
				}
			}
		}
		
	}

	public void swap(int a, int b) {
		Collections.swap(genome, a, b);
	}
}
