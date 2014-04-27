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
			int ret = Double.compare(arg1.getFirst(), arg0.getFirst()); 
			if ( ret != 0 )
				return ret;
			return arg1.getSecond().getId() - arg0.getSecond().getId();
		}
		
	}
	
	//weights to another nodes corresponding to neighbours
	private List<Pair<Double, WeightNode>> weights = new ArrayList<Pair<Double, WeightNode>>();
	
	private boolean sortedNeighbours = false;
	
	/**
	 * Sort neighbours of node by weight
	 */
	public void sortNeighbours(){
		if ( this.sortedNeighbours == true )
			return;
		Collections.sort(weights, new bWCmp());
		sortedNeighbours = true;
	}
	
	/**
	 * Get neighbours of node
	 * @return Iterator to neighbours
	 */
	public Iterator<Pair<Double, WeightNode>> getNeighbours(){
		return weights.iterator();
	}
	
	@Override
	public boolean equals(Object o){
		WeightNode node = (WeightNode)o;
		return node.getId() == this.getId();
	}
	
	/**
	 * Add Weight neighbour to node
	 * @param weight Bridge weight
	 * @param node Neighbour node
	 */
	public void addWeightToNeighbour(double weight, WeightNode node){
		Pair<Double, WeightNode> newPair = new Pair<Double, WeightNode>(weight, node);
		weights.add(newPair);
	}
	
	/**
	 * Get total weight error of node
	 * @return Error
	 */
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
	
	/** 
	 * Get total weight error when current color is set to "color"
	 * @param color Current color
	 * @return Weight error of node
	 */
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
