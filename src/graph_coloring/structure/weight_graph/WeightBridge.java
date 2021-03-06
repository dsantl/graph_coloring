package graph_coloring.structure.weight_graph;

import graph_coloring.structure.Bridge;

public class WeightBridge extends Bridge{

	//Bridge weight
	private double weight = 0;
	
	/**
	 * Add node1 and node2 to bridge with weight
	 * @param node1
	 * @param node2
	 * @param weight
	 */
	public WeightBridge(WeightNode node1, WeightNode node2, double weight){
		super(node1, node2);
		this.weight = weight;
	}
	
	/**
	 * Get weight of bridge
	 * @return Weight (double)
	 */
	public double getWeight(){
		return this.weight;
	}
	
}
