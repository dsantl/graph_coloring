package graph_coloring.structure;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import graph_coloring.structure.Node;

import graph_coloring.common.MinMaxPairInteger;

public class Graph {
	
	protected Map<Integer, Node> nodeRepos = new HashMap<Integer, Node>();
	protected Map<MinMaxPairInteger, Bridge> bridgeRepos = new HashMap<MinMaxPairInteger, Bridge>();
	
	public Graph(Graph graph) {
		this.nodeRepos = new HashMap<Integer, Node>();
		
		
		
		this.bridgeRepos = graph.bridgeRepos;
	}

	public Graph(){
		
	}
	
	public void addNode(Node node){
		nodeRepos.put(node.getId(), node);
	}
	
	public int getStaturationNumber(int index){
		
		Node node = this.getNode(index);
		Set<Integer> counter = new HashSet<Integer>();
		
		int len  = node.getNeighborsSize();
		for(int i = 0 ; i < len ; ++i){
			int neighbour = node.getNeighbor(i);
			counter.add(this.getNode(neighbour).getColor());
		}
		
		return counter.size();
		
	}
	
	public List<Bridge> getNodeBridges(int index) {
		List<Bridge> ret = new ArrayList<Bridge>();
		
		Node node = this.getNode(index);
		
		int len = node.getNeighborsSize();
		
		for(int i = 0 ; i < len ; ++i){
			ret.add(this.getBridge(index, node.getNeighbor(i)));
		}
		
		return ret;
		
	}
	
	public void addBridge(Bridge bridge){
		bridgeRepos.put(bridge.getPair(), bridge);
		int node1 = bridge.getPair().getLeft();
		int node2 = bridge.getPair().getRight();
		
		nodeRepos.get(node1).addNeighbor(node2);
		nodeRepos.get(node2).addNeighbor(node1);
	}
	
	public Node getNode(int node){
		return nodeRepos.get(node);
	}
	
	public Bridge getBridge(int node1, int node2){
		return bridgeRepos.get(new MinMaxPairInteger(node1, node2));
	}
	
	public Bridge getBridge(MinMaxPairInteger bridge) {
		return bridgeRepos.get(bridge);
	}
	
	public int getBridgeSize(){
		return bridgeRepos.size();
	}
	
	public Set<MinMaxPairInteger> getBridgeIndices(){
		return bridgeRepos.keySet();
	}
	
	
	public Set<Integer> getNodeIndices(){
		return nodeRepos.keySet();
	}
	
	public int getNodeSize(){
		return nodeRepos.size();
	}


	
}
