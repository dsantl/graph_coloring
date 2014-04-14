package graph_coloring.structure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Node extends ElementProperty {
	
	private int id;
	private int color = -1;
	private List<Node> neighbours = new ArrayList<Node>();
	
	/**
	 * Get number of bridges
	 * @return Number of bridges
	 */
	public int getBridgeSize(){
		return neighbours.size();
	}
	
	/**
	 * Get neighbour node
	 * @param index in neighbour list
	 * @return node (Node)
	 */
	private Node getNeighbour(int index){
		return neighbours.get(index);
	}
	
	public int getNeighbourId(int index){
		return getNeighbour(index).getId();
	}
	
	public int getSaturation(){
		Set<Integer> colors = new HashSet<Integer>();
		for(Node node : neighbours){
			colors.add(node.getColor());
		}
		return colors.size();

	}
	
	/**
	 * Add neighbour to node
	 * @param node id
	 */
	public void addNeighbour(Node node){
		neighbours.add(node);
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
	 * @return node color or negative int if node doesn't have a color
	 */
	public int getColor(){
		return this.color;
	}
	
	/**
	 * Get node id
	 * @return node id
	 */
	public int getId(){
		return this.id;
	}
	
	/**
	 * Set new color to node
	 * @param color
	 */
	public void setColor(int color){
		this.color = color;
	}
	
	/**
	 * Get collision of node
	 * @return Number of collisions of node
	 */
	public int getCollision(){
		int col = 0;
		
		for(int i = 0 ; i < this.getBridgeSize() ; ++i){
			if ( this.getNeighbour(i).getColor() == this.getColor() )
				col += 1;
		}
		
		return col;
	}
	
	/**
	 * Get collision of node when current color is "color"
	 * @param color Current color
	 * @return Number of collisions of node
	 */
	public int getCollision(int color){
		int oldColor = this.color;
		this.setColor(color);
		int col = this.getCollision();
		this.setColor(oldColor);
		return col;
	}
}
