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
	private boolean bridgeOrder = false;
	
	public Greedy(String orderMethod, String colorSelector, int step, boolean bridgeOrder){
		try{
			this.orderMethod = OrderMethodFactory.factory(orderMethod);
			this.colorSelector = ColorSelectorFactory.factory(colorSelector);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		this.step = step;
		this.bridgeOrder = bridgeOrder;
	}
	
	public Greedy(String orderMethod, String colorSelector, int step){
		try{
			this.orderMethod = OrderMethodFactory.factory(orderMethod);
			this.colorSelector = ColorSelectorFactory.factory(colorSelector);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		this.step = step;
		this.bridgeOrder = false;
	}
	
	private void sortElements(){
		if (bridgeOrder)
			graph.makeBridgeOrder(orderMethod);
		else
			graph.makeNodeOrder(orderMethod);
	}
	
	private void makeNodeStep(){
		
		this.sortElements();
		
		for(int index = 0 ; index < graph.getNodeSize() ; ++index){
			this.setColorToNode(index);
		}
	}
	
	private void setColorToNode(int index){
		if ( !checkNode(index) )
			return;

		int newColor = graph.chooseColor(index, colorSelector);
		graph.setNodeColor(index, newColor);
	}
	
	private void makeBridgeStep(){
		
		this.sortElements();
		
		for(int index = 0 ; index < graph.getBridgeSize() ; ++index){
			int node1Index = graph.getNodeIndex(graph.getBridge(index).getFirst());
			int node2Index = graph.getNodeIndex(graph.getBridge(index).getSecond());
			setColorToNode(node1Index);
			setColorToNode(node2Index);
		}
		
	}
	
	@Override
	protected void algorithm() {
		
		for(int k = 0 ; k < step ; ++k){
			if (this.bridgeOrder)
				makeBridgeStep();
			else
				makeNodeStep();
		}
	}
}
