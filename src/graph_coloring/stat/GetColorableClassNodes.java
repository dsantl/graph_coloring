package graph_coloring.stat;

import java.util.ArrayList;
import java.util.List;

import graph_coloring.structure.EricssonGraph;
import graph_coloring.structure.EricssonNode;
import graph_coloring.structure.Node;

public class GetColorableClassNodes {

	public static List<Integer> getNodeClass(EricssonGraph graph, char nodeClass){
		
		List<Integer> ret = new ArrayList<Integer>();
		for(Integer node : graph.getNodeIndices()){
			EricssonNode eNode = (EricssonNode) graph.getNode(node);
			if ( eNode.getColorable() && eNode.getNodeClass() == nodeClass)
				ret.add(node);
		}
		
		return ret;
	}
	
}
