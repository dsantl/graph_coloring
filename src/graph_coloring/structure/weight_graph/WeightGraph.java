package graph_coloring.structure.weight_graph;

import graph_coloring.structure.Bridge;
import graph_coloring.structure.Graph;

public class WeightGraph extends Graph implements IWeightGraph{

	/**
	 * Get WeightBridge object
	 * @param id
	 * @return WeightBridge object
	 */
	private WeightBridge getWeightBridge(int index){
		return (WeightBridge)bridgeList.get(index);
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
		WeightNode node1 = (WeightNode)nodeList.get(this.getNodeIndex(id1));
		WeightNode node2 = (WeightNode)nodeList.get(this.getNodeIndex(id2));
		Bridge b = new WeightBridge(node1, node2, weight);
		this.addBridge(node1, node2, b);
		node1.addWeight(weight);
		node2.addWeight(weight);
	}
	
	@Override
	public double getNodeNeighbourWeight(int nodeIndex, int neighbourIndex) {
		return ((WeightNode)this.getNode(nodeIndex)).getWeight(neighbourIndex);
	}

	@Override
	public double getNodeError(int index) {
		return ((WeightNode)this.getNode(index)).getError();
	}

	@Override
	public int getNodeCollision(int index) {
		return ((WeightNode)this.getNode(index)).getCollision();
	}
	
}
