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
	
	/**
	 * Get node id where is agent
	 * @return node id
	 */
	public int getNodeId(){
		return currentNodeId;
	}
	
	/**
	 * Get node index where is agent
	 * @return
	 */
	public int getNodeIndex(){
		return graph.getNodeIndex(currentNodeId);
	}
	
	/**
	 * Get score of agent 
	 * @return
	 */
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
	
	/**
	 * Set color to node, by definition color selector is MF
	 */
	public void setColor(){
		int color = graph.chooseColor(this.getNodeIndex(), colorSelector);
		graph.setNodeColor(this.getNodeIndex(), color);
	}
	
	/**
	 * Move agent to neighbour node
	 * 10% chance for no move
	 * Last node is forbidden
	 * 
	 */
	public void move(){
		double error = 0;
		double maxError = 0;
		int nextNode = this.getNodeIndex();
		
		//10% chance for no move
		if ( rnd.nextDouble() < 0.1 )
			return;
		
		for(int i = 0 ; i < graph.getNodeDegree(this.getNodeIndex()) ; ++i){
			
			int neighbourId = graph.getNodeNeighburId(this.getNodeIndex(), i); 
			
			if ( neighbourId == this.previousNodeId )
				continue;
			
			//20% chance for move to this node
			if ( rnd.nextDouble() < 0.2 )
			{
				maxError = 0;
				nextNode = neighbourId;
				break;
			}
			
			error = graph.getNodeError(graph.getNodeIndex(neighbourId));
			
			//Get to node with maximum error (only nodes in 20% chance step are considered
			if ( error > maxError ){
				maxError = error;
				nextNode = neighbourId;
			}
		}
		
		//Score is max error, score is used in sorting
		this.score = maxError;
		this.previousNodeId = this.currentNodeId;
		this.currentNodeId = nextNode;
	}
}
