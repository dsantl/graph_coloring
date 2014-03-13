package graph_coloring.structure.weight_graph;

import graph_coloring.common.Pair;
import graph_coloring.structure.Node;

import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class WeightNode extends Node{

	private class bWCmp implements Comparator<Pair<Double, WeightNode>>{

		@Override
		public int compare(Pair<Double, WeightNode> arg0, Pair<Double, WeightNode> arg1) {
			return Double.compare(arg0.getFirst(), arg1.getFirst());
		}
		
	}
	
	//weights to another nodes corresponding to neighbours
	private SortedSet<Pair<Double, WeightNode>> weights = new TreeSet<Pair<Double, WeightNode>>(new bWCmp());
	
	public Iterator<Pair<Double, WeightNode>> getNeighbours(){
		return weights.iterator();
	}
	
	public void addWeight(double weight, WeightNode node){
		weights.add(new Pair<Double, WeightNode>(weight, node));
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
	
	public int getCollision(){
		int col = 0;
		
		for(int i = 0 ; i < this.getBridgeSize() ; ++i){
			if ( this.getNeighbour(i).getColor() == this.getColor() )
				col += 1;
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
