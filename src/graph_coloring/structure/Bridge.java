package graph_coloring.structure;

import graph_coloring.common.OrderPair;

public class Bridge extends ElementProperty{
	
	//pair of nodes in bridge
	private OrderPair Nodes; 
	
	/***
	 * Function add two nodes in bridge 
	 * @param node1 reference to first node
	 * @param node2 reference to second node
	 */
	public Bridge(Node node1, Node node2){
		Nodes = new OrderPair(node1.getId(), node2.getId());
	}
	
	/***
	 * Get nodes in OrderPair object (first and second)
	 * @return OrderPair object of nodes
	 */
	public OrderPair getNodes(){
		return Nodes;
	}
}
