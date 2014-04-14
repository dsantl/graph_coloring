package graph_coloring.color_selector;

import graph_coloring.structure.Node;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ColorSelectorSTART implements ColorSelector{
	
	Random rnd = new Random();
	
	@Override
	public int selectColor(Node node, Iterator<Integer> colors){
		
		EricssonNode ericssonNode = (EricssonNode) node;
		
		if (ericssonNode.getColorable() == false)
			return ericssonNode.getColor();
		
		
		int cnt = 0;
		List<Integer> allColors = new ArrayList<Integer>();
		
		while(colors.hasNext()){
			cnt += 1;
			int color = colors.next();
			allColors.add(color);
			if ( color == ericssonNode.getStartColor() )
				return color;
		}
		
		
		return allColors.get(rnd.nextInt(cnt));
	}
	
	@Override
	public int setParam(Object param){
		return 0;
	}
}
