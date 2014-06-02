package graph_coloring.color_selector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import graph_coloring.common.Pair;
import graph_coloring.structure.Node;
import graph_coloring.structure.weight_graph.WeightNode;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonNode;

public class ColorSelectorABW implements ColorSelector{
	
	private Random rnd = new Random();
	
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
		
		//List<Integer> ret = new ArrayList();
		/*
		for(Integer i : nodeColors){
			ret.add(i);
		}ret.get(rnd.nextInt(ret.size()));
		*/
		retColor = nodeColors.iterator().next();
		
		return retColor;
	}

	@Override
	public int setParam(Object param) {
		return 0;
	}
	
}
