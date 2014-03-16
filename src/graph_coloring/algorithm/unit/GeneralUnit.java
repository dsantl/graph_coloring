package graph_coloring.algorithm.unit;

import java.util.HashMap;
import java.util.Map;

import graph_coloring.stat.ErrorFunctionEricsson;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonGraph;

public class GeneralUnit {
	
	private EricssonGraph graph;
	private double error;
	private Map<Integer,Integer> nodeIdColor = new HashMap<Integer, Integer>();
	
	public double getError(){
		return this.error;
	}
	
	public GeneralUnit(EricssonGraph graph){
		this.graph = graph;
		this.error = ErrorFunctionEricsson.computeStat(graph);
		
		for(int i = 0 ; i < graph.getNodeSize() ; ++i){
			nodeIdColor.put(graph.getNodeId(i), graph.getNodeColor(i));
		}
	}
	
	public void changeColor(int nodeId, int color){
		int nodeIndex = graph.getNodeIndex(nodeId);
		int oldColor = this.nodeIdColor.get(nodeId);
		double nodeError = graph.getNodeError(nodeIndex);
		
		this.error -= nodeError;
		graph.setNodeColor(nodeIndex, color);
		this.error += graph.getNodeError(nodeIndex);
		graph.setNodeColor(nodeIndex, oldColor);
	}
	
	public void setColorToGraph(){
		for(int i = 0 ; i < graph.getNodeSize() ; ++i){
			int nodeId = graph.getNodeId(i);
			graph.setNodeColor(i, this.nodeIdColor.get(nodeId));
		}
	}
}
