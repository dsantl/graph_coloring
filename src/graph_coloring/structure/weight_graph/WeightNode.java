package graph_coloring.structure.weight_graph;

import graph_coloring.structure.Node;

import java.util.ArrayList;
import java.util.List;

public class WeightNode extends Node{

	//weights to another nodes corresponding to neighbours
	private List<Double> weights = new ArrayList<Double>();
	
	public void addWeight(double w){
		weights.add(w);
	}
	
	public WeightNode(int id) {
		super(id);
	}
	
	public WeightNode(int id, int color){
		super(id, color);
	}
	
}
