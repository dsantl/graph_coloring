package graph_coloring.algorithmset;

import graph_coloring.algorithm.GraphColoringAlgorithm;
import graph_coloring.structure.Bridge;
import graph_coloring.structure.EricssonBridge;
import graph_coloring.structure.EricssonGraph;
import graph_coloring.structure.EricssonNode;
import graph_coloring.structure.Graph;
import graph_coloring.structure.Node;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class MinimalNeighbourWeight implements GraphColoringAlgorithm{
	
	@Override
	public void startAlgorithm(List<Node> nodes, Graph graph) {
		
		Random rnd = new Random();
		
		int cnt = 0;
		
		for( Integer index : graph.getNodeIndices() ){
			
			System.out.format("%d %d\n", cnt, graph.getNodeSize());
			cnt += 1;
			
			EricssonNode eNode = (EricssonNode) graph.getNode(index);
			
			if ( eNode.getColorable() == false )
			{
				continue;
			}
			
			int colorClass = eNode.getColorClass();
			
			if (((EricssonGraph)graph).colorClasses.get(colorClass).containsColor(eNode.getColor()))
				if (rnd.nextDouble() > 0.3)
					continue;
			
						
			int newColor = getMinimalErrorColor((EricssonGraph)graph, index, colorClass);
			eNode.setColor(newColor);
		}
		
	}

	private int getMinimalErrorColor(EricssonGraph graph, int index, int colorClass) {
		List<Bridge> neighbours = graph.getNodeNeighbours(index);
		
		Set<Integer> colors = graph.colorClasses.get(colorClass).getAllColors();
		
		double mini = Double.MAX_VALUE;
		
		int colorNode = -1;
		
		for(Integer color : colors){
			graph.getNode(index).setColor(color);
			double tmpRes = getNewWeight(neighbours, graph);
			if ( tmpRes < mini ){
				mini = tmpRes;
				colorNode = color;
			}
		}
		
		return colorNode;
	}

	private double getNewWeight(List<Bridge> neighbours, Graph graph) {
		double ret = 0;
		for( Bridge bridge : neighbours ){
			if ( checkColors(bridge, graph) )
				ret += ((EricssonBridge)bridge).getWeight();
		}
		return ret;
	}

	private boolean checkColors(Bridge bridge, Graph graph) {
		int left = bridge.getLeftNode();
		int right = bridge.getRightNode();
		int lColor = graph.getNode(left).getColor();
		int rColor = graph.getNode(right).getColor();
		
		return lColor == rColor;
	}
	
}
