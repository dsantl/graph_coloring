package graph_coloring.structure.weight_graph;

import graph_coloring.structure.Bridge;
import graph_coloring.structure.Graph;
import graph_coloring.structure.Node;

public class WeightGraph extends Graph implements IWeightGraph{
	
	/**
	 * Get WeightBridge object
	 * @param id
	 * @return WeightBridge object
	 */
	private WeightBridge getWeightBridge(int id){
		return (WeightBridge)bridgeList.get(id);
	}
	
	/**
	 * Get bridge weight
	 * @param id bridge id
	 * @return weight (double)
	 */
	public double getBridgeWeight(int id) {
		return getWeightBridge(id).getWeight();
	}
	
	/**
	 * @param id1 
	 * @param id2
	 * @param weight
	 */
	@Override
	public void addWeightBridge(int id1, int id2, double weight) {
		Node node1 = nodeList.get(this.getNodeIndex(id1));
		Node node2 = nodeList.get(this.getNodeIndex(id2));
		Bridge b = new WeightBridge(node1, node2, weight);
		this.addBridge(node1, node2, b);
	}
	
}
