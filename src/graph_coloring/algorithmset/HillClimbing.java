package graph_coloring.algorithmset;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import graph_coloring.algorithm.GraphColoringAlgorithm;
import graph_coloring.color_selector.ColorSelector;
import graph_coloring.color_selector.ColorSelectorFactory;
import graph_coloring.common.Pair;
import graph_coloring.structure.weight_graph.WeightNode;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonGraph;

public class HillClimbing extends GraphColoringAlgorithm{

	
	private void colorNode(int index, Set<Integer> visitedIndex){
		
		EricssonGraph eGraph = (EricssonGraph) graph;
		
		if (eGraph.getNodeColorable(index) == false)
			return;
		
		Iterator<Integer> colors = eGraph.getAvailableColorsOfNode(index);
		
		
		double minError = 0;
		int retColor = -1;
		
		while(colors.hasNext()){
			Iterator<Pair<Double, WeightNode>> neighbours = eGraph.getNeighbours(index);
			int color = colors.next();
			double colorError = 0;
			while(neighbours.hasNext()){
				Pair<Double, WeightNode> neighbour = neighbours.next();
				int neighbourIndex = graph.getNodeIndex(neighbour.getSecond().getId());
				if ( eGraph.getNodeColorable(neighbourIndex) && !visitedIndex.contains(neighbourIndex) )
					continue;
				if ( color == eGraph.getNodeColor(neighbourIndex) )
					colorError += neighbour.getFirst();
			}
			if ( colorError == 0.0 ){
				graph.setNodeColor(index, color);
				return;
			}
			if ( retColor == -1 || colorError < minError ){
				retColor = color;
				minError = colorError;
			}
		}
		graph.setNodeColor(index, retColor);
	}
	
	private void hillClimbing(int index, Set<Integer> visitedIndex){
		
		if ( visitedIndex.contains(index) )
			return;
		
		while(true){
			colorNode(index, visitedIndex);
			visitedIndex.add(index);
			int size = graph.getNodeDegre(index);
			
			int i;
			for(i = 0 ; i < size ; ++i){
				int neighbour = graph.getNodeIndex(graph.getNodeNeighburId(index, i));
				if ( visitedIndex.contains(neighbour) )
					continue;
				index = neighbour;
				break;
			}
			
			if (i == size)
				return;
		}
		
	}
	
	@Override
	protected void algorithm() {
		
		Set<Integer> visitedIndex = new HashSet<Integer>();
		
		for(int i = 0 ; i < graph.getNodeSize() ; ++i){
			hillClimbing(i, visitedIndex);
		}
		
		
	}

}
