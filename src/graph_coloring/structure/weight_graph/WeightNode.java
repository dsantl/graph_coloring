package graph_coloring.structure.weight_graph;

import graph_coloring.structure.Node;

import java.util.ArrayList;
import java.util.List;

public class WeightNode extends Node{

	//weights to another nodes corresponding to neighbours
	private List<Double> weights = new ArrayList<Double>();
	
	public double getWeight(int index){
		return weights.get(index);
	}
	
	public void addWeight(double w){
		weights.add(w);
	}
	
	public double getError(){
		double error = 0;
		
		for(int i = 0 ; i < this.getBridgeSize() ; ++i){
			if ( this.getNeighbour(i).getColor() == this.getColor() )
				error += this.getWeight(i);
		}
		
		return error;
	}
	
	public int getCollision(){
		int col = 0;
		
		for(int i = 0 ; i < this.getBridgeSize() ; ++i){
			if ( this.getNeighbour(i).getColor() == this.getColor() )
				col += this.getWeight(i);
		}
		
		return col;
	}
	
	public WeightNode(int id) {
		super(id);
	}
	
	public WeightNode(int id, int color){
		super(id, color);
	}
	
}
