package graph_coloring.stat;

import java.util.HashSet;
import java.util.Set;

import graph_coloring.structure.EricssonGraph;
import graph_coloring.structure.EricssonNode;
import graph_coloring.structure.Graph;

public class CountingColors {

	public static int computeStat(EricssonGraph graph){
		
		Set<Integer> counter = new HashSet<Integer>();
		
		for(Integer i : graph.getNodeIndices()){
			EricssonNode node = (EricssonNode) graph.getNode(i);
			if ( node.getColorable() && graph.isColorOfNodeValid(i) )
				counter.add(node.getColor());
		}
		
		return counter.size();
	}
	
}
