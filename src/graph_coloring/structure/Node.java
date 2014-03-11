package graph_coloring.structure;

import graph_coloring.common.OrderPair;

import java.util.ArrayList;
import java.util.List;

public class Node extends ElementProperty {
	
	private int id;
	private int color = -1;
	private List<Bridge> bridges = new ArrayList<Bridge>();
	
	
	/**
	 * Get number of bridges
	 * @return Number of bridges
	 */
	public int getBridgeSize(){
		return bridges.size();
	}
	
	/**
	 * Get OrderPair objects with nodes
	 * @param id of bridge (indices from 0)
	 * @return OrderPair object (nodes)
	 */
	public OrderPair getBridge(int id){
		return bridges.get(id).getNodes();
	}
	
	/**
	 * Add bridge to node
	 * @param bridge Object Bridge
	 */
	public void addBridge(Bridge bridge){
		bridges.add(bridge);
	}
	
	/**
	 * Constructor for Node class
	 * @param id node id in graph
	 */
	public Node(int id){
		this.id = id;
	}
	
	/**
	 * Constructor for Node class
	 * @param id node id in graph
	 * @param color node color
	 */
	public Node(int id, int color){
		this.id = id;
		this.color = color;
	}
	
	/**
	 * Get node color
	 * @return node color or null if node doesn't have a color
	 */
	public Integer getColor(){
		if ( color == -1 )
			return null;
		return this.color;
	}
	
	/**
	 * Get node id
	 * @return node id
	 */
	public Integer getId(){
		return this.id;
	}
	
	/**
	 * Set new color to node
	 * @param color
	 */
	public void setColor(int color){
		this.color = color;
	}
}
