package graph_coloring.algorithm.unit;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import graph_coloring.algorithm.GraphAlgorithmContext;
import graph_coloring.algorithmset.greedy.Greedy;
import graph_coloring.color_selector.ColorSelector;
import graph_coloring.stat.CollisionCounter;
import graph_coloring.stat.ErrorFunctionEricsson;
import graph_coloring.stat.ErrorLogFunctionEricsson;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonGraph;

public class GeneralUnit {
	
	private EricssonGraph graph;
	private Map<Integer,Integer> nodeIdColor = new HashMap<Integer, Integer>();
	private double error;
	
	public double getError(){
		return error;
	}
	
	public int getSize(){
		return graph.getNodeSize();
	}
	
	public GeneralUnit(EricssonGraph graph){
		this.graph = graph;
		for(int i = 0 ; i < graph.getNodeSize() ; ++i){
			nodeIdColor.put(graph.getNodeId(i), graph.getNodeColor(i));
		}
		this.error = ErrorFunctionEricsson.computeStat(graph);
	}
	
	public GeneralUnit(EricssonGraph graph, GeneralUnit unit){
		this.graph = graph;
		for(int i = 0 ; i < graph.getNodeSize() ; ++i){
			int nodeId = graph.getNodeId(i);
			nodeIdColor.put(nodeId, unit.nodeIdColor.get(nodeId));
		}
		this.error = unit.error;
	}
	
	public void setColor(int id, ColorSelector colorSelector){
		int index = graph.getNodeIndex(id);
		graph.setNodeColor(index, this.nodeIdColor.get(id));
		for(int i = 0 ; i < graph.getNodeDegree(index) ; ++i){
			int neighbourId = graph.getNodeNeighburId(index, i);
			int neighbourIndex = graph.getNodeIndex(neighbourId);
			graph.setNodeColor(neighbourIndex, this.nodeIdColor.get(neighbourId));
		}
		
		double oldError = graph.getNodeError(index);
		int color = this.graph.chooseColor(index, colorSelector);
		this.nodeIdColor.put(id, color);
		graph.setNodeColor(index, color);
		double newError = graph.getNodeError(index);
		this.error = this.error - 2*oldError + 2*newError;
	}
	
	public void updateGraph(){
		for(Map.Entry<Integer, Integer> nodeColor : this.nodeIdColor.entrySet()){
			graph.setNodeColor(graph.getNodeIndex(nodeColor.getKey()), nodeColor.getValue());
		}
	}
	
}
