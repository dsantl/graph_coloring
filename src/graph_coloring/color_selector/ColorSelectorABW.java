package graph_coloring.color_selector;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import graph_coloring.common.Pair;
import graph_coloring.structure.Node;
import graph_coloring.structure.weight_graph.WeightNode;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonNode;

public class ColorSelectorABW implements ColorSelector{
	
	//TODO
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
		
		Iterator<Pair<Double, WeightNode>> it = eNode.getNeighbours();
		
		while(it.hasNext()){
			if (nodeColors.size() == 1)
				break;
			nodeColors.remove(it.next().getSecond().getColor());
		}
		
		if (nodeColors.contains(eNode.getColor()))
			return eNode.getColor();
		
		retColor = nodeColors.iterator().next();
		
		return retColor;
	}
	
}
