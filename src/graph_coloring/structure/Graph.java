package graph_coloring.structure;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import graph_coloring.common.Pair;

public class Graph {
	
	protected Map<Integer, Node> nodeRepos = new HashMap<Integer, Node>();
	protected Map<Pair<Integer, Integer>, Bridge> bridgeRepos = new HashMap<Pair<Integer, Integer>, Bridge>();
	
	public List<Bridge> getNodeNeighbours(int nodeIndex){
		List<Bridge> ret = new ArrayList<Bridge>();
		
		int nodeSize = nodeRepos.get(nodeIndex).getNeighborsSize();
		
		for(int neighbour = 0 ; neighbour < nodeSize ; ++neighbour){
			Pair<Integer, Integer> first = new Pair<Integer, Integer>(nodeIndex, neighbour);
			Pair<Integer, Integer> second = new Pair<Integer, Integer>(neighbour, nodeIndex);
			
			if ( bridgeRepos.containsKey(first) )
				ret.add( bridgeRepos.get(first) );
			else if (  bridgeRepos.containsKey(second) ){
				ret.add ( bridgeRepos.get(second) );
			}
		}
		return ret;
	}
	
	public void addNode(Node node){
		nodeRepos.put(node.getId(), node);
	}
	
	public void addBridge(Bridge bridge){
		bridgeRepos.put(bridge.getPair(), bridge);
		int node1 = bridge.getLeftNode();
		int node2 = bridge.getRightNode();
		nodeRepos.get(node1).addNeighbor(node2);
		nodeRepos.get(node2).addNeighbor(node1);
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
	
	public int getNodeSize(){
		return nodeRepos.size();
	}
	
}
