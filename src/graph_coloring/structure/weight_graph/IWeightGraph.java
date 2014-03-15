package graph_coloring.structure.weight_graph;

import graph_coloring.common.Pair;
import java.util.Iterator;

public interface IWeightGraph {

	public double getBridgeWeight(int index);
	public void addWeightBridge(int id1, int id2, double weight);
	public double getNodeError(int index);
	public int getNodeCollision(int index);
	public Iterator<Pair<Double, WeightNode>> getNeighbours(int index);
	public void sortNeighbours(int index);
}
