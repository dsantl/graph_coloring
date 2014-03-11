package graph_coloring.structure.weight_graph.ericsson_graph;


import graph_coloring.structure.weight_graph.WeightNode;

public class EricssonNode extends WeightNode{

	//Node group (A, B, C)
	private char nodeGroup;
	
	//Name of domain
	private int domainName;
	
	//Start color of node
	private int startColor;
	
	//Node colorable, if node is colorable variable is True
	private boolean colorable;
	
	/**
	 * Get node group
	 * @return Node group (A, B, C) (char)
	 */
	public char getNodeGroup(){
		return this.nodeGroup;
	}
	
	
	/**
	 * Get domain name
	 * @return integer of domain name
	 */
	public int getDomainName(){
		return this.domainName;
	}
	
	/**
	 * Get start color
	 * @return start color of node (integer)
	 */
	public int getStartColor(){
		return this.startColor;
	}
	
	/**
	 * Get colorable property 
	 * @return
	 */
	public boolean getColorable(){
		return this.colorable;
	}
	
	/**
	 * Constructor for EricssonNode
	 * @param id Id of node
	 * @param nodeGroup Node group (A, B, C)
	 * @param domainName Name of domain (integer)
	 * @param startColor Start color of node (integer)
	 * @param colorable True if node is colorable
	 */
	public EricssonNode(int id, char nodeGroup, int domainName, int startColor, boolean colorable) {
		super(id, startColor);
		this.startColor = startColor;
		this.nodeGroup = nodeGroup;
		this.colorable = colorable;
		
		if ( colorable == true){
			this.domainName = domainName;
		}
		else{
			this.domainName = -1;
		}
	}
}
