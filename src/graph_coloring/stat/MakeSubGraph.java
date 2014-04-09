package graph_coloring.stat;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import graph_coloring.common.OrderPair;
import graph_coloring.structure.Graph;
import graph_coloring.structure.weight_graph.WeightGraph;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonGraph;

public class MakeSubGraph {

	private static Set<Integer> getNodes(Graph graph, int nodeId, int nodeSize){
		Queue<Integer> nodeIdQ = new LinkedList<Integer>();
		Set<Integer> nodeIds = new HashSet<Integer>();
		
		nodeIdQ.add(nodeId);
		
		while(nodeIds.size() < nodeSize && !nodeIdQ.isEmpty()){
			
			int currNode = nodeIdQ.poll();
			
			if (nodeIds.contains(currNode))
				continue;
			
			nodeIds.add(currNode);
			
			int nodeIndex = graph.getNodeIndex(currNode);
			
			for(int i = 0 ; i < graph.getNodeDegree(nodeIndex) ; ++i ){
				int neighbour = graph.getNodeNeighburId(nodeIndex, i);
				nodeIdQ.add(neighbour);
			}
			
		}
		
		return nodeIds;
	}
	
	public static Graph createSubGraphBFS(Graph graph, int nodeId, int nodeSize){
		Graph ret = new Graph();
		
		Set<Integer> nodeIds = getNodes(graph, nodeId, nodeSize);
		
		for(Integer id : nodeIds){
			int nodeIndex = graph.getNodeIndex(id);
			int color = graph.getNodeColor(nodeIndex);
			ret.addNode(id, color);
		}
		
		for(int i = 0 ; i < graph.getBridgeSize() ; ++i){
			OrderPair bridge = graph.getBridge(i);
			int node1 = bridge.getFirst();
			int node2 = bridge.getSecond();
			
			if ( nodeIds.contains(node1) && nodeIds.contains(node2) ){
				ret.addBridge(node1, node2);
			}
		}
		
		return ret;
	}
	
	public static WeightGraph createWeightSubGraphBFS(WeightGraph graph, int nodeId, int nodeSize){
		WeightGraph ret = new WeightGraph();
		
		Set<Integer> nodeIds = getNodes(graph, nodeId, nodeSize);
		
		for(Integer id : nodeIds){
			int nodeIndex = graph.getNodeIndex(id);
			int color = graph.getNodeColor(nodeIndex);
			ret.addNode(id, color);
		}
		
		for(int i = 0 ; i < graph.getBridgeSize() ; ++i){
			OrderPair bridge = graph.getBridge(i);
			int node1 = bridge.getFirst();
			int node2 = bridge.getSecond();
			double weight = graph.getBridgeWeight(i);
			
			if ( nodeIds.contains(node1) && nodeIds.contains(node2) ){
				ret.addWeightBridge(node1, node2, weight);
			}
		}
		
		
		return ret;
	}
	
	
	
	public static EricssonGraph createEricssonSubGraphBFS(EricssonGraph graph, int nodeId, int nodeSize){
		EricssonGraph ret = new EricssonGraph();
		
		for(Integer name : graph.getDomainNames()){
			ret.addColorClass(name);
			Iterator<Integer> colors = graph.getDomainNameIterator(name);
			while(colors.hasNext()){
				ret.addColorToColorClass(name, colors.next());
			}
		}
		
		Set<Integer> nodeIds = getNodes(graph, nodeId, nodeSize);
		
		for(Integer id : nodeIds){
			int nodeIndex = graph.getNodeIndex(id);
			int color = graph.getNodeColor(nodeIndex);
			int startColor = graph.getNodeStartColor(nodeIndex);
			char nodeGroup = graph.getNodeGroup(nodeIndex);
			int domainName = graph.getNodeDomainName(nodeIndex);
			boolean colorable = graph.getNodeColorable(nodeIndex);
			ret.addEricssonNode(id, nodeGroup, domainName, startColor, colorable);
			ret.setNodeColor(ret.getNodeIndex(id), color);
		}
		
		for(int i = 0 ; i < graph.getBridgeSize() ; ++i){
			OrderPair bridge = graph.getBridge(i);
			int node1 = bridge.getFirst();
			int node2 = bridge.getSecond();
			double weight = graph.getBridgeWeight(i);
			
			if ( nodeIds.contains(node1) && nodeIds.contains(node2) ){
				ret.addWeightBridge(node1, node2, weight);
			}
		}
		
		return ret;
	}
	
}
