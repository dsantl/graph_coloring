package graph_coloring.algorithmset.greedy;

import graph_coloring.algorithm.GraphColoringAlgorithm;
import graph_coloring.color_selector.ColorSelector;
import graph_coloring.color_selector.ColorSelectorFactory;
import graph_coloring.order.OrderMethod;
import graph_coloring.order.OrderMethodFactory;

public class Greedy extends GraphColoringAlgorithm {

	private OrderMethod orderMethod;
	private ColorSelector colorSelector;
	private int step;
	private double percentOfNodes;
	private int firstBest;
	
	/**
	 * Set param to color selector
	 * @param param Parameter for color selector
	 */
	public void setColorSelectorParam(Object param){
		this.colorSelector.setParam(param);
	}
	
	/**
	 * Constructor
	 * @param orderMethod Order method for greedy
	 * @param colorSelector Color selector for greedy
	 * @param step Total number of steps
	 * @param percentOfNodes Percent of how many nodes is used for change (first p percent in order method)
	 */
	public Greedy(String orderMethod, String colorSelector, int step, double percentOfNodes){
		try{
			this.orderMethod = OrderMethodFactory.factory(orderMethod);
			this.colorSelector = ColorSelectorFactory.factory(colorSelector);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		this.step = step;
		this.percentOfNodes = percentOfNodes;
	}
	
	/**
	 * Constructor
	 * @param orderMethod Order method for greedy
	 * @param colorSelector Color selector for greedy 
	 * @param step Total number of steps
	 */
	public Greedy(String orderMethod, String colorSelector, int step){
		try{
			this.orderMethod = OrderMethodFactory.factory(orderMethod);
			this.colorSelector = ColorSelectorFactory.factory(colorSelector);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		this.step = step;
		this.percentOfNodes = 1.0;
	}
	
	
	/**
	 * Sort elements by order method
	 */
	private void sortElements(){
		graph.makeNodeOrder(orderMethod);
	}
	
	/**
	 * Set color to nodes
	 */
	private void makeNodeStep(){
		
		this.sortElements();
		
		for(int index = 0 ; index < this.firstBest ; ++index){
			this.setColorToNode(index);
		}
	}
	
	/**
	 * Set color to node
	 * @param index
	 */
	private void setColorToNode(int index){
		if ( !checkNode(index) )
			return;

		int newColor = graph.chooseColor(index, colorSelector);
		graph.setNodeColor(index, newColor);
	}
	
	
	@Override
	protected void algorithm() {
		
		this.firstBest = (int)(this.graph.getNodeSize() * this.percentOfNodes);
		
		for(int k = 0 ; k < step ; ++k){
				makeNodeStep();
		}
	}
}
