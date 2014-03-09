package graph_coloring.structure.weight_graph;

public interface IWeightGraph {

	public double getBridgeWeight(int id);
	public void addWeightBridge(int id1, int id2, double weight);
}
