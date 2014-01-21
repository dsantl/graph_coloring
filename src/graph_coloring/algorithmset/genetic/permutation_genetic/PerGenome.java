package graph_coloring.algorithmset.genetic.permutation_genetic;

import java.util.ArrayList;
import java.util.List;

public class PerGenome {
	
	private List<Integer> genome = new ArrayList<Integer>();
	private List<Integer> colorableNodes;
	private int size;
	private double error;
	
	public PerGenome(int size, List<Integer> colorableNodes){
		this.size = size;
		this.colorableNodes = colorableNodes;
		for(int i = 0 ; i  < size ; ++i){
			genome.add(i);
		}
	}

	public void randomize() {
		
	}

	public double getError(){
		return this.error;
	}
	
	public void setError() {
		
	}
}
