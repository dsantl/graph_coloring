package graph_coloring.structure.weight_graph;

import graph_coloring.structure.Graph;

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
		// TODO Auto-generated method stub
		
	}
	
}
