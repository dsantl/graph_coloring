package graph_coloring.structure;


public class EricssonNode extends Node{

	private int startColor; //start color of node
	private int colorClass; //color class of node (id)
	private char nodeClass;  //class of node (A, B or C)
	private boolean colorable; //if node is colorable

	public int getStartColor(){
		return this.startColor;
	}
	
	public int getColorClass(){
		return this.colorClass;
	}
	
	
	public char getNodeClass(){
		return this.nodeClass;
	}
	
	public boolean getColorable(){
		return this.colorable;
	}
	
	public EricssonNode(int id, int color, int startColor, int colorClass, char nodeClass, boolean colorable) {
		super(id, color);
		this.startColor = startColor;
		this.colorClass = colorClass;
		this.nodeClass = nodeClass;
		this.colorable = colorable;
	}
}
