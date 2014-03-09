package graph_coloring.structure;

import graph_coloring.common.OrderPair;

public interface IGraph {
	
	public void setNodeColor(int index, int color);
	
	
	public int getNodeColor(int index);
	
	public int getNodeIndex(int id);
	
	public int getNodeId(int index);
	
	public void addNode(int index);
	
	public void addNode(int id, int color);
	
	public void addBridge(int id1, int id2);

	public int getBridgeSize();

	public OrderPair getBridge(int index);
	
	public int getNodeSize();
}
