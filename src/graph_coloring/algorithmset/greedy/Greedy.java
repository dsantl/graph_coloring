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
	
	private void makeStep(){
		
		if (bridgeOrder)
			graph.makeBridgeOrder(orderMethod);
		else
			graph.makeNodeOrder(orderMethod);
		
		for(int index = 0 ; index < graph.getNodeSize() ; ++index){
			
			if ( !checkNode(index) )
				continue;
			int newColor = graph.chooseColor(index, colorSelector);
			graph.setNodeColor(index, newColor);
		}
	}
	
	@Override
	protected void algorithm() {
		
		for(int k = 0 ; k < step ; ++k){
			makeStep();
		
		}
	}
}
