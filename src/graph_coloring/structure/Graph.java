package graph_coloring.structure;

import graph_coloring.common.OrderPair;
import graph_coloring.order.OrderMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph implements IGraph{

	//list of bridges
	protected List<Bridge> bridgeList = new ArrayList<Bridge>();
	
	//list of nodes
	protected List<Node> nodeList = new ArrayList<Node>();
	
	//map id-index in list
	private Map<Integer, Integer> nodeMap = new HashMap<Integer, Integer>();
	
	
	
	/**
	 * Get number of bridges
	 * @return Number of bridges
	 */
	@Override
	public int getBridgeSize(){
		return bridgeList.size();
	}
	
	/**
	 * Get OrderPair objects with nodes
	 * @param index of bridge (indices from 0)
	 * @return OrderPair object (nodes)
	 */
	@Override
	public OrderPair getBridge(int index){
		return bridgeList.get(index).getNodes();
	}
	
	/**
	 * Get node from map
	 * @param index
	 * @return Node object
	 */
	protected Node getNode(int index){
		return nodeList.get(index);
	}
	
	/**
	 * Get node color
	 * @param index
	 * @return Node color (integer)
	 */
	@Override
	public int getNodeColor(int index){
		return getNode(index).getColor();
	}
	
	/**
	 * Add node to structure
	 * @param node
	 */
	protected void addNode(Node node){
		nodeList.add(node);
		nodeMap.put(node.getId(), nodeList.size()-1);
	}
	
	/**
	 * Add node in graph
	 * @param id
	 */
	@Override
	public void addNode(int id){
		Node node = new Node(id);
		this.addNode(node);
	}
	
	/**
	 * Add node in graph with color
	 * @param id
	 * @param color
	 */
	@Override
	public void addNode(int id, int color){
		Node node = new Node(id, color);
		this.addNode(node);
	}
	
	/**
	 * Add bridge in graph
	 * @param id1 first id of node
	 * @param id2 second id of node
	 */
	@Override
	public void addBridge(int id1, int id2){
		Node node1 = nodeList.get(this.getNodeIndex(id1));
		Node node2 = nodeList.get(this.getNodeIndex(id2));
		Bridge b = new Bridge(node1, node2);
		this.addBridge(node1, node2, b);
	}

	/**
	 * Get number of nodes
	 * @return Number of nodes (integer)
	 */
	@Override
	public int getNodeSize() {
		return nodeList.size();
	}

	/**
	 * @param index 
	 * @return node id
	 */
	public int getNodeId(int index) {
		return this.getNode(index).getId();
	}

	/**
	 * Set new color to node
	 * @param index index of node
	 * @param color new color
	 */
	@Override
	public void setNodeColor(int index, int color) {
		this.getNode(index).setColor(color);
	}

	/**
	 * Get node index in current list
	 * @param id
	 * @return current node index
	 */
	@Override
	public int getNodeIndex(int id) {
		return this.nodeMap.get(id);
	}
	
	/**
	 * Add bridge to structure
	 * @param node1 
	 * @param node2
	 * @param b bridge
	 */
	protected void addBridge(Node node1, Node node2, Bridge b){
		bridgeList.add(b);
		node1.addBridge(b);
		node2.addBridge(b);
	}

	/**
	 * Refresh map of id-index
	 */
	private void refreshNodeMap(){
		nodeMap.clear();
		for(int i = 0 ; i < this.getNodeSize() ; ++i){
			nodeMap.put(this.getNodeId(i), i);
		}
	}
	
	@Override
	public void makeNodeOrder(OrderMethod ord) {
		List<? extends ElementProperty> nodeProp = nodeList;
		ord.makeOrder(nodeProp);
		refreshNodeMap();
	}

	@Override
	public void makeBridgeOrder(OrderMethod ord) {
		ord.makeOrder(bridgeList);
		
	}
	
}
