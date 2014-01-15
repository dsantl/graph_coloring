package graph_coloring.structure;


import java.util.ArrayList;
import java.util.List;

public abstract class Node {

	protected List<Integer> neighbors = new ArrayList<Integer>();
	protected int color;
	protected int id;
	
	
	public Node(int id, int color){
		this.color = color;
		this.id = id;
	}
	
	public void setColor(int color){
		this.color = color;
	}
	
	public void addNeighbor(int node){
		neighbors.add(node);
	}
	
	public int getNeighborsSize(){
		return neighbors.size();
	}
	
	public int getNeighbor(int index){
		return neighbors.get(index);
	}
	
	public int getId(){
		return this.id;
	}
	
	public int getColor(){
		return this.color;
	}
	
}
