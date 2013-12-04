package graph_coloring.structure;

public class EricssonBridge extends Bridge{

	private double weight;
	
	EricssonBridge(int node1, int node2, double weight)
	{
		this.node1 = Math.max(node1, node2);
		this.node2 = Math.min(node1, node2);
		this.weight = weight;
	}
	
	double getWeight()
	{
		return weight;
	}
	
}
