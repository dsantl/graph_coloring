package graph_coloring.color_selector;

import java.util.Iterator;

import graph_coloring.structure.Node;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonNode;

public class ColorSelectorMC implements ColorSelector{

	@SuppressWarnings("unchecked")
	@Override
	public int selectColor(Node node, Object param){
		EricssonNode eNode = (EricssonNode)node;
		Iterator<Integer> colors = (Iterator<Integer>)param;
		
		if (eNode.getColorable() == false)
			return eNode.getColor();
		
		int minCollision = Integer.MAX_VALUE;
		int minColor = -1;
		
		while(colors.hasNext()){
			int currColor = colors.next();
			int col = eNode.getCollision(currColor);
			
			if ( col < minCollision ){
				minCollision = col;
				minColor = currColor;
			}
		}
		
		return minColor;
	}
}
