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
	private double noMove;
	private double rndMove;
	
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
	
	public AgentUnit(int id, EricssonGraph graph, String colorSelector, double noMove, double rndMove){
		
		try {
			this.colorSelector = ColorSelectorFactory.factory(colorSelector); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.noMove = noMove;
		this.rndMove = rndMove;
		this.currentNodeId = id;
		this.graph = graph;
	}
	
	/**
	 * Set color to node
	 */
	public void setColor(){
		
		int color = graph.chooseColor(this.getNodeIndex(), colorSelector);
		graph.setNodeColor(this.getNodeIndex(), color);
	}
	
	/**
	 * Move agent to neighbour node
	 * 
	 */
	public void move(){
		double error = 0;
		double maxError = 0;
		int nextNode = this.getNodeId();
		
		//chance for no move
		if ( rnd.nextDouble() < noMove )
			return;
		
		int nodeDegree = graph.getNodeDegre(this.getNodeIndex());
		
		if (rnd.nextDouble() < rndMove && nodeDegree > 0){
			int rndNeighbour = rnd.nextInt(nodeDegree);
			int neighbourId = graph.getNodeNeighburId(this.getNodeIndex(), rndNeighbour);
			nextNode = neighbourId;
		}
		else{
			for(int i = 0 ; i < nodeDegree ; ++i){
				
				int neighbourId = graph.getNodeNeighburId(this.getNodeIndex(), i); 
				
				if ( neighbourId == this.previousNodeId )
					continue;
				
				error = graph.getNodeError(graph.getNodeIndex(neighbourId));
				
				//Get to node with maximum error (only nodes in 20% chance step are considered)
				if ( error > maxError ){
					maxError = error;
					nextNode = neighbourId;
				}
			}
			
			//Score is max error, score is used in sorting
			this.score = maxError;
		}
		this.previousNodeId = this.currentNodeId;
		this.currentNodeId = nextNode;
	}
}
