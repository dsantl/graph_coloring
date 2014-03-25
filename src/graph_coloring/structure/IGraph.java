package graph_coloring.structure;

import graph_coloring.color_selector.ColorSelector;
import graph_coloring.common.OrderPair;
import graph_coloring.order.OrderMethod;

public interface IGraph {
	
	public void setNodeColor(int index, int color);
	
	public int getNodeNeighburId(int nodeIndex, int neighbourIndex);
	
	public int getNodeDegree(int index);
	
	public int getNodeSaturation(int index);
	
	public int getNodeCollision(int index);
	
	public int getNodeColor(int index);
	
	public int getNodeIndex(int id);
	
	public int getNodeId(int index);
	
	public void addNode(int index);
	
	public void addNode(int id, int color);
	
	public void addBridge(int id1, int id2);

	public int getBridgeSize();

	public OrderPair getBridge(int index);
	
	public int getNodeSize();
	
	public void makeNodeOrder(OrderMethod ord);

	public void makeBridgeOrder(OrderMethod ord);	

	public int chooseColor(int nodeIndex, ColorSelector colorSelector);
}
