package graph_coloring.structure;

public class EricssonBridge extends Bridge{

	private double weight; 
	
	public EricssonBridge(int node1, int node2, double weight){
		super(node1, node2);
		
		if (node1>node2){
			int tmp = node1;
			node1 = node2;
			node2= tmp;
		}
		
		this.leftNode = node1;
		this.rightNode = node2;
		this.weight = weight;
	}
	
	public double getWeight(){
		return this.weight;
	}
	
}
