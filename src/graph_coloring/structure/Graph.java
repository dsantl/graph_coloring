package graph_coloring.structure;

import graph_coloring.color_selector.ColorSelector;
import graph_coloring.order.OrderMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Graph implements IGraph{

	
	//list of nodes
	protected List<Node> nodeList = new ArrayList<Node>();
	
	//map id-index in list
	private Map<Integer, Integer> nodeMap = new HashMap<Integer, Integer>();
	
	private int bridgeSize = 0;
	
	
	/**
	 * Get number of bridges
	 * @return Number of bridges
	 */
	@Override
	public int getBridgeSize(){
		return bridgeSize;
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
		Node node1 = this.getNode(this.getNodeIndex(id1));
		Node node2 = this.getNode(this.getNodeIndex(id2));
		bridgeSize += 1;
		this.addBridge(node1, node2);
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
	protected void addBridge(Node node1, Node node2){
		node1.addNeighbour(node2);
		node2.addNeighbour(node1);
		System.out.format("%d %d \n", node1.getId(), node2.getId());
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
	public int getNodeDegree(int index) {
		return this.getNode(index).getBridgeSize();
	}
	
	@Override
	public int getNodeSaturation(int index) {
		return this.getNode(index).getSaturation();
	}

	@Override
	public int chooseColor(int nodeIndex, ColorSelector colorSelector) {
		return colorSelector.selectColor(this.getNode(nodeIndex), null);
	}

	@Override
	public int getNodeNeighburId(int nodeIndex, int neighbourIndex) {
		return this.getNode(nodeIndex).getNeighbourId(neighbourIndex);
	}

	@Override
	public int getNodeCollision(int index) {
		return this.getNode(index).getCollision();
	}
}
