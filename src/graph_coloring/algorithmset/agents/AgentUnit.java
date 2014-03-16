package graph_coloring.algorithmset.agents;

import java.util.Random;

import graph_coloring.color_selector.ColorSelector;
import graph_coloring.color_selector.ColorSelectorFactory;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonGraph;

public class AgentUnit {
	
	private Random rnd = new Random();
	private ColorSelector colorSelector;
	private double score = 0;
	private int currentNodeId;
	private int previousNodeId = -1;
	private EricssonGraph graph;
	
	private int getNodeIndex(){
		return graph.getNodeIndex(currentNodeId);
	}
	
	public double getScore(){
		return score;
	}
	
	public AgentUnit(int id, EricssonGraph graph){
		try {
			colorSelector = ColorSelectorFactory.factory("MF");
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.currentNodeId = id;
		this.graph = graph;
	}
	
	public void setColor(){
		int color = graph.chooseColor(this.getNodeIndex(), colorSelector);
		graph.setNodeColor(this.getNodeIndex(), color);
	}
	
	public void move(){
		double error = 0;
		double maxError = 0;
		int nextNode = this.getNodeIndex();
		
		for(int i = 0 ; i < graph.getNodeDegree(this.getNodeIndex()) ; ++i){
			
			int neighbourId = graph.getNodeNeighburId(this.getNodeIndex(), i); 
			
			if ( neighbourId == this.previousNodeId )
				continue;
			
			
			if ( rnd.nextDouble() < 0.2 )
			{
				maxError = 0;
				nextNode = neighbourId;
				break;
			}
			
			error = graph.getNodeError(graph.getNodeIndex(neighbourId));
			
			if ( error > maxError ){
				maxError = error;
				nextNode = neighbourId;
			}
		}
		
		
		this.score = maxError;
		this.previousNodeId = this.currentNodeId;
		this.currentNodeId = nextNode;
	}
}
