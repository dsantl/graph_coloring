package graph_coloring.algorithm.unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import graph_coloring.color_selector.ColorSelector;
import graph_coloring.common.Pair;
import graph_coloring.stat.ErrorFunctionEricsson;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonGraph;

public class GeneralUnit {
	
	private EricssonGraph graph;
	private Map<Integer, Integer> nodeIdColor = new HashMap<Integer, Integer>();
	private double error;
	
	/**
	 * Get error of unit
	 * @return Total error of graph who have colors defined by unit
	 */
	public double getError(){
		return error;
	}
	
	/**
	 * Get node size of unit - same as number of nodes in graph
	 * @return Number of nodes
	 */
	public int getSize(){
		return graph.getNodeSize();
	}
	
	/**
	 * Constructor
	 * @param graph Unit is connect to this graph
	 */
	public GeneralUnit(EricssonGraph graph){
		this.graph = graph;
		for(int i = 0 ; i < graph.getNodeSize() ; ++i){
			nodeIdColor.put(graph.getNodeId(i), graph.getNodeColor(i));
		}
		this.error = ErrorFunctionEricsson.computeStat(graph);
	}
	
	
	/**
	 * Constructor for copy GeneralUnit into new one
	 * @param graph Unit is connect to this graph
	 * @param unit Clone this unit into new one
	 */
	public GeneralUnit(EricssonGraph graph, GeneralUnit unit){
		this.graph = graph;
		for(int i = 0 ; i < graph.getNodeSize() ; ++i){
			int nodeId = graph.getNodeId(i);
			nodeIdColor.put(nodeId, unit.nodeIdColor.get(nodeId));
		}
		this.error = unit.error;
	}
	
	/**
	 * Change color and compute new error
	 * @param id Id of node
	 * @param colorSelector Selector who can change color of node
	 * @return pair (old color, error)
	 */
	public void setColor(int id, ColorSelector colorSelector){
		int index = graph.getNodeIndex(id);
		graph.setNodeColor(index, this.nodeIdColor.get(id));
		for(int i = 0 ; i < graph.getNodeDegre(index) ; ++i){
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
	
	
	
	/**
	 * Set unit colors to graph
	 */
	public void updateGraph(){
		for(int i = 0 ; i < graph.getNodeSize() ; ++i){
			graph.setNodeColor(i, this.nodeIdColor.get(graph.getNodeId(i)));
		}
	}
	
}
