package graph_coloring.color_selector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import graph_coloring.structure.Node;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonNode;

public class ColorSelectorRND implements ColorSelector{

	Random rnd = new Random();
	
	@SuppressWarnings("unchecked")
	@Override
	public int selectColor(Node node, Object param) {
		EricssonNode eNode = (EricssonNode)node;
		
		if (eNode.getColorable() == false)
			return eNode.getColor();
		
		Iterator<Integer> colors = (Iterator<Integer>)param;
		List<Integer> listColor = new ArrayList<Integer>();
		
		while(colors.hasNext()){
			listColor.add(colors.next());
		}
		
		return listColor.get(rnd.nextInt(listColor.size()));
	}

}
