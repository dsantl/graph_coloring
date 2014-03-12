package graph_coloring.color_selector;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import graph_coloring.structure.Node;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonNode;

public class ColorSelectorABW implements ColorSelector{
	
	@SuppressWarnings("unchecked")
	@Override
	public int selectColor(Node node, Object param){
		EricssonNode eNode = (EricssonNode)node;
		Iterator<Integer> colors = (Iterator<Integer>)param;
		int retColor = node.getColor();
		Set<Integer> nodeColors = new HashSet<Integer>();
		
		if (eNode.getColorable() == false)
			return eNode.getColor();
		
		while(colors.hasNext()){
			nodeColors.add(colors.next());
		}
		
		for(int i = 0 ; i  < eNode.getBridgeSize() ; ++i){
			if (nodeColors.size() == 1)
				retColor = nodeColors.iterator().next();
			nodeColors.remove(eNode.getNeighbour(i).getColor());
		}
		
		return retColor;
	}
	
}
