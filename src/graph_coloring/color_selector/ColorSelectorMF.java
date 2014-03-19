package graph_coloring.color_selector;

import java.util.Iterator;

import graph_coloring.structure.Node;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonNode;

public class ColorSelectorMF implements ColorSelector{

	@Override
	public int selectColor(Node node, Iterator<Integer> colors){
		EricssonNode eNode = (EricssonNode)node;
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

	@Override
	public int setParam(Object param) {
		return 0;
	}
	
}
