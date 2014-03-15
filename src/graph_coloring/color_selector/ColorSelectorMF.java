package graph_coloring.color_selector;

import java.util.Iterator;

import graph_coloring.structure.Node;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonNode;

public class ColorSelectorMF implements ColorSelector{

	@SuppressWarnings("unchecked")
	@Override
	public int selectColor(Node node, Object param){
		EricssonNode eNode = (EricssonNode)node;
		Iterator<Integer> colors = ((Iterator<Integer>)param);
		int memColor = node.getColor();
		
		if (eNode.getColorable() == false)
			return eNode.getColor();
		
		double minError = Double.MAX_VALUE;
		int minColor = memColor;
		
		while(colors.hasNext()){
			int currColor = colors.next();
			double err = eNode.getError(currColor);
			
			if ( err < minError ){
				minError = err;
				minColor = currColor;
			}
		}
		
		return minColor;
	}
	
}
