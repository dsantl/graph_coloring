package graph_coloring.algorithm.unit;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import graph_coloring.algorithm.GraphAlgorithmContext;
import graph_coloring.algorithmset.greedy.Greedy;
import graph_coloring.stat.CollisionCounter;
import graph_coloring.stat.ErrorFunctionEricsson;
import graph_coloring.stat.ErrorLogFunctionEricsson;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonGraph;

public class GeneralUnit {
	
	private EricssonGraph graph;
	private Map<Integer,Integer> nodeIdColor = new HashMap<Integer, Integer>();
	private double error;
	private boolean changed = true;
	
	private boolean isErrorChanged(){
		return this.changed;
	}
	
	public double getLogError(){
		this.setColorToGraph();
		return ErrorLogFunctionEricsson.computeStat(this.graph);
	}
	
	public double getError(){
		if (this.isErrorChanged()){
			this.setColorToGraph();
			this.error = ErrorFunctionEricsson.computeStat(this.graph); 
			this.changed = false;
			return this.error;
		}
		else
			return this.error;
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
	
	public void setColor(double changeRate, String orderMethod, String colorSelector, Object colorSelectorParam ,Set<Integer> touchableNodes){
		this.setColorToGraph();
		this.changed = true;
		
		Greedy greedy = new Greedy(orderMethod, colorSelector, 1, false, changeRate);
		greedy.setColorSelectorParam(colorSelectorParam);
		
		GraphAlgorithmContext alg = new GraphAlgorithmContext(greedy);
		
		alg.startAlgorithm(this.graph, touchableNodes);
		
		for(int i = 0 ; i < graph.getNodeSize() ; ++i){
			nodeIdColor.put(graph.getNodeId(i), graph.getNodeColor(i));
		}
	}
	
	public void setRNDColor(double changeRate, Set<Integer> touchableNodes){
		this.setColorToGraph();
		this.changed = true;
		
		GraphAlgorithmContext alg = new GraphAlgorithmContext(new Greedy("STDORD", "RND", 1, false, changeRate));
		
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

	public int getCollision() {
		return CollisionCounter.computeStat(graph); 
	}
}
