package graph_coloring.color_selector;

import graph_coloring.structure.Node;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ColorSelectorRND_D implements ColorSelector{

	Random rnd = new Random();
	
	@Override
	public int selectColor(Node node, Iterator<Integer> colors) {
		
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
