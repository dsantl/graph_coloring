package graph_coloring.structure;

import java.util.ArrayList;
import java.util.List;

import graph_coloring.structure.Node;

public class Nodes{

	private List<Node> nodeList;
	
	Nodes()
	{
		nodeList = new ArrayList<Node>();
	}
	
	public void addNode(Node node)
	{
		nodeList.add(node);
	}
	
	public List<Node> getList()
	{
		return nodeList;
	}
}
