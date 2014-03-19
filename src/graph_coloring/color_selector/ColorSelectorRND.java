package graph_coloring.color_selector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import graph_coloring.structure.Node;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonNode;

public class ColorSelectorRND implements ColorSelector{

	Random rnd = new Random();
	
	@Override
	public int selectColor(Node node, Iterator<Integer> colors) {
		EricssonNode eNode = (EricssonNode)node;
		
		if (eNode.getColorable() == false)
			return eNode.getColor();
		
		List<Integer> listColor = new ArrayList<Integer>();
		
		while(colors.hasNext()){
			listColor.add(colors.next());
		}
		
		int rndColor = rnd.nextInt(listColor.size());
		
		return listColor.get(rndColor);
	}


	@Override
	public int setParam(Object param) {
		return 0;
	}

}
