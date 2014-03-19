package graph_coloring.algorithm.unit;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import graph_coloring.algorithm.GraphAlgorithmContext;
import graph_coloring.algorithmset.greedy.Greedy;
import graph_coloring.stat.ErrorFunctionEricsson;
import graph_coloring.stat.ErrorLogFunctionEricsson;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonGraph;

public class GeneralUnit {
	
	private EricssonGraph graph;
	private Map<Integer,Integer> nodeIdColor = new HashMap<Integer, Integer>();
	
	public double getLogError(){
		this.setColorToGraph();
		return ErrorLogFunctionEricsson.computeStat(this.graph);
	}
	
	public double getError(){
		this.setColorToGraph();
		return ErrorFunctionEricsson.computeStat(this.graph);
	}
	
	public int getSize(){
		return graph.getNodeSize();
	}
	
	public GeneralUnit(EricssonGraph graph){
		this.graph = graph;
		for(int i = 0 ; i < graph.getNodeSize() ; ++i){
			nodeIdColor.put(graph.getNodeId(i), graph.getNodeColor(i));
		}
	}
	
	public void copy(GeneralUnit unit){
		this.graph = unit.graph;
		for(Map.Entry<Integer, Integer> nodeColor : unit.nodeIdColor.entrySet()){
			this.nodeIdColor.put(nodeColor.getKey(), nodeColor.getValue());
		}
	}
	
	public void changeColor(double changeRate, String colorSelector, Set<Integer> touchableNodes){
		this.setColorToGraph();
		
		GraphAlgorithmContext alg = new GraphAlgorithmContext(new Greedy("RND", colorSelector, 1, false, changeRate));
		alg.startAlgorithm(this.graph, touchableNodes);
		
		for(int i = 0 ; i < graph.getNodeSize() ; ++i){
			nodeIdColor.put(graph.getNodeId(i), graph.getNodeColor(i));
		}
	}
	
	public void setColorToGraph(){
		for(Integer nodeId : this.nodeIdColor.keySet()){
			int nodeIndex = graph.getNodeIndex(nodeId);
			graph.setNodeColor(nodeIndex, this.nodeIdColor.get(nodeId));
		}
	}
}
