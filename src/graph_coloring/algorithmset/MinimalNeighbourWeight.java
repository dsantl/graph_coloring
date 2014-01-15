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
		int changeNumber = graph.getNodeSize();
		
		for(int i = 0 ; i < changeNumber ; ++i){
			System.out.format("%d / %d\n", i, changeNumber);
			int index = rnd.nextInt(graph.getNodeSize());
			EricssonNode eNode = (EricssonNode) graph.getNode(index);
			if ( eNode.getColorable() == false )
			{
				i -= 1;
				continue;
			}
			
			int colorClass = eNode.getColorClass();
			int newColor = getMinimalErrorColor((EricssonGraph)graph, index, colorClass);
			eNode.setColor(newColor);
		}
		
	}

	private int getMinimalErrorColor(EricssonGraph graph, int index, int colorClass) {
		List<Bridge> neighbours = graph.getNodeNeighbours(index);
		Set<Integer> colors = graph.colorClasses.get(colorClass).getAllColors();
		
		double mini = Double.MAX_VALUE;
		int currColor = graph.getNode(index).getColor();
		int colorNode = currColor;
		
		for(Integer color : colors){
			graph.getNode(index).setColor(color);
			double tmpRes = getNewWeight(neighbours, graph);
			if ( tmpRes < mini ){
				mini = tmpRes;
				colorNode = color;
			}
		}
		
		graph.getNode(index).setColor(currColor);
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
		return graph.getNode(left).getColor() == graph.getNode(right).getColor();
	}
	
}
