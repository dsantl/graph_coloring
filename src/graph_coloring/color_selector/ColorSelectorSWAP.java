package graph_coloring.color_selector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import graph_coloring.structure.Node;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonNode;

public class ColorSelectorSWAP implements ColorSelector{

	private Random rnd = new Random();
	
	@Override
	public int selectColor(Node node, Iterator<Integer> colors) {
		
		EricssonNode eNode = (EricssonNode)node;
		int memColor = node.getColor();
		double nodeError = eNode.getError();
		
		if (eNode.getColorable() == false)
			return eNode.getColor();
		
		List<Integer> swapColors = new ArrayList<Integer>();
		
		while(colors.hasNext()){
			int currColor = colors.next();
			double err = eNode.getError(currColor);
			
			if ( Math.abs(err-nodeError) <= 0.01*nodeError ){
				swapColors.add(currColor);
			}
		}
		
		if ( swapColors.size() == 0 )
			return memColor;
		
		//if ( swapColors.contains(eNode.getStartColor()) )
		//	return eNode.getStartColor();
		
		return swapColors.get(rnd.nextInt(swapColors.size()));
	}

	@Override
	public int setParam(Object param) {
			return 0;
	}

}
