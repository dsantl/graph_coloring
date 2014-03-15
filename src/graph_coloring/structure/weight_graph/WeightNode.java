package graph_coloring.structure.weight_graph;

import graph_coloring.common.Pair;
import graph_coloring.structure.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class WeightNode extends Node{

	private class bWCmp implements Comparator<Pair<Double, WeightNode>>{
		
		@Override
		public int compare(Pair<Double, WeightNode> arg0, Pair<Double, WeightNode> arg1) {
			return Double.compare(arg1.getFirst(), arg0.getFirst());
		}
		
	}
	
	//weights to another nodes corresponding to neighbours
	private List<Pair<Double, WeightNode>> weights = new ArrayList<Pair<Double, WeightNode>>();
	
	private boolean sortedNeighbours = false;
	
	public void sortNeighbours(){
		if ( this.sortedNeighbours == true )
			return;
		Collections.sort(weights, new bWCmp());
		sortedNeighbours = true;
	}
	
	public Iterator<Pair<Double, WeightNode>> getNeighbours(){
		return weights.iterator();
	}
	
	@Override
	public boolean equals(Object o){
		WeightNode node = (WeightNode)o;
		return node.getId() == this.getId();
	}
	
	public void addWeightToNeighbour(double weight, WeightNode node){
		Pair<Double, WeightNode> newPair = new Pair<Double, WeightNode>(weight, node);
		weights.add(newPair);
	}
	
	public double getError(){
		double error = 0;
		
		Iterator<Pair<Double, WeightNode>> it = this.getNeighbours();
		
		while(it.hasNext()){
			Pair<Double, WeightNode> bridge = it.next();
			
			if ( bridge.getSecond().getColor() == this.getColor() )
				error += bridge.getFirst();
		}
		
		return error;
	}
	
	public double getError(int color){
		int oldColor = this.getColor();
		this.setColor(color);
		double error = this.getError();
		this.setColor(oldColor);
		return error;
	}
	
	public WeightNode(int id) {
		super(id);
	}
	
	public WeightNode(int id, int color){
		super(id, color);
	}
	
}
