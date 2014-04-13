package graph_coloring.structure.weight_graph;

import java.util.Iterator;

import graph_coloring.structure.Graph;
import graph_coloring.common.Pair;

public class WeightGraph extends Graph implements IWeightGraph{

	/**
	 * @param id1 
	 * @param id2
	 * @param weight
	 */
	@Override
	public void addWeightBridge(int id1, int id2, double weight) {
		WeightNode node1 = (WeightNode)nodeList.get(this.getNodeIndex(id1));
		WeightNode node2 = (WeightNode)nodeList.get(this.getNodeIndex(id2));
		node1.addWeightToNeighbour(weight, node2);
		node2.addWeightToNeighbour(weight, node1);
	}
	
	
	@Override
	public double getNodeError(int index) {
		return ((WeightNode)this.getNode(index)).getError();
	}

	@Override
	public double getNodeColorError(int index, int color) {
		return ((WeightNode)this.getNode(index)).getError(color);
	}
	
	@Override
	public int getNodeCollision(int index) {
		return ((WeightNode)this.getNode(index)).getCollision();
	}
	
	@Override
	public Iterator<Pair<Double, WeightNode>> getNeighbours(int index){
		return ((WeightNode)this.getNode(index)).getNeighbours();
	}

	@Override
	public void sortNeighbours(int index) {
		((WeightNode)this.getNode(index)).sortNeighbours();
	}

	
	
}
