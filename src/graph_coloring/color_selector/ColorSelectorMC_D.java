package graph_coloring.color_selector;

import graph_coloring.structure.Node;

import java.util.Iterator;

public class ColorSelectorMC_D implements ColorSelector{

	@Override
	public int selectColor(Node node, Iterator<Integer> colors) {
		
		int minCollision = Integer.MAX_VALUE;
		int minColor = -1;
		
		while(colors.hasNext()){
			int currColor = colors.next();
			int col = node.getCollision(currColor);
			
			if ( col < minCollision || minColor == -1){
				minCollision = col;
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
