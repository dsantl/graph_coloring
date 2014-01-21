package graph_coloring.algorithmset;

import java.util.List;
import java.util.Random;
import java.util.Set;

import graph_coloring.algorithm.GraphColoringAlgorithm;
import graph_coloring.structure.EricssonGraph;
import graph_coloring.structure.EricssonNode;
import graph_coloring.structure.Graph;
import graph_coloring.structure.Node;

public class RandomAlgorithm implements GraphColoringAlgorithm{
	
	@Override
	public void startAlgorithm(List<Integer> nodes, Graph graph) {
		
		EricssonGraph eGraph = (EricssonGraph) graph;
		Set<Integer> allNodes = graph.getNodeIndices();
		
		for(Integer index : allNodes){
			EricssonNode eNode = (EricssonNode) graph.getNode(index);
			if ( eNode.getColorable() == false )
				continue;
			int colorClass = eNode.getColorClass();
			int newColor = randomNewColor((EricssonGraph)graph, colorClass);
			eGraph.setNodeColor(index, newColor);
		}
		
	}

	private int randomNewColor(EricssonGraph graph, int colorClass) {
		List<Integer> colors = graph.getAllColorsOfClass(colorClass);
		Random rnd = new Random();
		int index  = rnd.nextInt(colors.size());
		int cnt = 0;
		
		for(Integer color : colors){
			if ( cnt == index)
				return color; 
		}
		
		return -1;
	}

}
