package graph_coloring.structure.weight_graph;

public interface IWeightGraph {

	public double getBridgeWeight(int index);
	public void addWeightBridge(int id1, int id2, double weight);
	public double getNodeNeighbourWeight(int nodeIndex, int neighbourIndex);
	public double getNodeError(int index);
	public int getNodeCollision(int index);
}
