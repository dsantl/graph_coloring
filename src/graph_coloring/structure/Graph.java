package graph_coloring.structure;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import graph_coloring.common.Pair;

public class Graph {
	
	protected Map<Integer, Node> nodeRepos = new HashMap<Integer, Node>();
	protected Map<Pair<Integer, Integer>, Bridge> bridgeRepos = new HashMap<Pair<Integer, Integer>, Bridge>();
	
	public void addNode(Node node){
		nodeRepos.put(node.getId(), node);
	}
	
	public void addBridge(Bridge bridge){
		bridgeRepos.put(bridge.getPair(), bridge);
		int node1 = bridge.getLeftNode();
		int node2 = bridge.getRightNode();
		nodeRepos.get(node1).addNeighbor(node2);
		nodeRepos.get(node2).addNeighbor(node2);
	}
	
	public Node getNode(int node){
		return nodeRepos.get(node);
	}
	
	public Bridge getBridge(Pair<Integer, Integer> pair){
		return bridgeRepos.get(pair);
	}
	
	public int getBridgeSize(){
		return bridgeRepos.size();
	}
	
	public Set<Pair<Integer, Integer>> getBridgeIndices(){
		return bridgeRepos.keySet();
	}
	
	
	public Set<Integer> getNodeIndices(){
		return nodeRepos.keySet();
	}
	
}
