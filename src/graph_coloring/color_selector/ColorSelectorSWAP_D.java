package graph_coloring.color_selector;

import graph_coloring.structure.Node;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ColorSelectorSWAP_D implements ColorSelector{

	private Random rnd = new Random();
	
	@Override
	public int selectColor(Node node, Iterator<Integer> colors) {
		
		int memColor = node.getColor();
		int nodeCol = node.getCollision();
		
		List<Integer> swapColors = new ArrayList<Integer>();
		
		while(colors.hasNext()){
			int currColor = colors.next();
			int err = node.getCollision(currColor);
			
			if ( err == nodeCol ){
				swapColors.add(currColor);
			}
		}
		
		if ( swapColors.size() == 0 )
			return memColor;
		
		return swapColors.get(rnd.nextInt(swapColors.size()));
	}

	@Override
	public int setParam(Object param) {
			return 0;
	}
}
