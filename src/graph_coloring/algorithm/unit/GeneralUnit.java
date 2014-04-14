package graph_coloring.algorithm.unit;

import java.util.ArrayList;
import java.util.List;

import graph_coloring.color_selector.ColorSelector;
import graph_coloring.stat.ErrorFunctionEricsson;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonGraph;

public class GeneralUnit {
	
	private EricssonGraph graph;
	private List<Integer> nodeIndexColor = new ArrayList<Integer>();
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
			nodeIndexColor.add(graph.getNodeColor(i));
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
			nodeIndexColor.add(unit.nodeIndexColor.get(i));
		}
		this.error = unit.error;
	}
	
	/**
	 * Change color and compute new error
	 * @param id Id of node
	 * @param colorSelector Selector who can change color of node
	 */
	public void setColor(int id, ColorSelector colorSelector){
		int index = graph.getNodeIndex(id);
		graph.setNodeColor(index, this.nodeIndexColor.get(index));
		for(int i = 0 ; i < graph.getNodeDegree(index) ; ++i){
			int neighbourId = graph.getNodeNeighburId(index, i);
			int neighbourIndex = graph.getNodeIndex(neighbourId);
			graph.setNodeColor(neighbourIndex, this.nodeIndexColor.get(neighbourIndex));
		}
		
		double oldError = graph.getNodeError(index);
		int color = this.graph.chooseColor(index, colorSelector);
		this.nodeIndexColor.set(index, color);
		graph.setNodeColor(index, color);
		double newError = graph.getNodeError(index);
		this.error = this.error - 2*oldError + 2*newError;
	}
	
	/**
	 * Set unit colors to graph
	 */
	public void updateGraph(){
		for(int i = 0 ; i < graph.getNodeSize() ; ++i){
			graph.setNodeColor(i, this.nodeIndexColor.get(i));
		}
	}
	
}
