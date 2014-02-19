package graph_coloring.algorithmset;

import graph_coloring.algorithm.GraphColoringAlgorithm;
import graph_coloring.structure.Bridge;
import graph_coloring.structure.EricssonBridge;
import graph_coloring.structure.EricssonGraph;
import graph_coloring.structure.EricssonNode;
import graph_coloring.structure.Graph;
import graph_coloring.structure.Node;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

//STDORDER
public class MWFast implements GraphColoringAlgorithm{
	
	@Override
	public void startAlgorithm(List<Integer> nodes, Graph graph) {
		
		//Random rnd = new Random();
		int cnt = 0;
		
		for( Integer index : nodes ){
			
			//System.out.format("%d %d\n", cnt, nodes.size());
			cnt += 1;
			
			EricssonNode eNode = (EricssonNode) graph.getNode(index);
			
			if ( eNode.getColorable() == false )
			{
				continue;
			}
			
			int colorClass = eNode.getColorClass();
						
			int newColor = getMinimalErrorColor((EricssonGraph)graph, index, colorClass);
			eNode.setColor(newColor);
			//((EricssonGraph)graph).setNodeColor(index, newColor);
		}
		((EricssonGraph)graph).setError();
	}

	private int getMinimalErrorColor(EricssonGraph graph, int index, int colorClass) {
		
		List<Integer> colors = graph.getAllColorsOfClass(colorClass);
		
		double mini = Double.MAX_VALUE;
		
		int colorNode = -1;
		
		for(Integer color : colors){
			double tmpRes = graph.getNodeError(index, color);
			if ( tmpRes < mini ){
				mini = tmpRes;
				colorNode = color;
			}
		}
		
		return colorNode;
	}
}
