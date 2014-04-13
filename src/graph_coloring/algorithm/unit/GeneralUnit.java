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
	
	public double getError(){
		return error;
	}
	
	public int getSize(){
		return graph.getNodeSize();
	}
	
	public GeneralUnit(EricssonGraph graph){
		this.graph = graph;
		for(int i = 0 ; i < graph.getNodeSize() ; ++i){
			nodeIndexColor.add(graph.getNodeColor(i));
		}
		this.error = ErrorFunctionEricsson.computeStat(graph);
	}
	
	public GeneralUnit(EricssonGraph graph, GeneralUnit unit){
		this.graph = graph;
		for(int i = 0 ; i < graph.getNodeSize() ; ++i){
			nodeIndexColor.add(unit.nodeIndexColor.get(i));
		}
		this.error = unit.error;
	}
	
	public void setColor(int id, ColorSelector colorSelector){
		int index = graph.getNodeIndex(id);
		graph.setNodeColor(index, this.nodeIndexColor.get(index));
		
		this.updateGraph();
		
		double oldError = graph.getNodeError(index);
		int color = this.graph.chooseColor(index, colorSelector);
		this.nodeIndexColor.set(index, color);
		graph.setNodeColor(index, color);
		double newError = graph.getNodeError(index);
		this.error = this.error - 2*oldError + 2*newError;
	}
	
	public void updateGraph(){
		for(int i = 0 ; i < graph.getNodeSize() ; ++i){
			graph.setNodeColor(i, this.nodeIndexColor.get(i));
		}
	}
	
}
