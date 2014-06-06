package graph_coloring.color_selector;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import graph_coloring.common.Pair;
import graph_coloring.structure.Node;
import graph_coloring.structure.weight_graph.WeightNode;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonNode;

public class ColorSelectorABWMD implements ColorSelector{
	
	
	@Override
	public int selectColor(Node node, Iterator<Integer> colors){
		EricssonNode eNode = (EricssonNode)node;
		int retColor = node.getColor();
		Set<Integer> nodeColors = new HashSet<Integer>();
		
		if (eNode.getColorable() == false)
			return eNode.getColor();
		
		eNode.sortNeighbours();
		
		while(colors.hasNext()){
			nodeColors.add(colors.next());
		}
		
		Iterator<Pair<Double, WeightNode>> it = eNode.getNeighbours();
		
		while(it.hasNext()){
			if (nodeColors.size() == 1)
				break;
			nodeColors.remove(it.next().getSecond().getColor());
		}
		
		double minError = Double.MAX_VALUE;
		for(Integer i : nodeColors){
			double newError = eNode.getError(i);
			if (newError < minError){
				minError = newError;
				retColor = i;
			}
		}
		
		
		return retColor;
	}

	@Override
	public int setParam(Object param) {
		return 0;
	}
	
}
