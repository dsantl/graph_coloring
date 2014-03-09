package graph_coloring.algorithmset.greedy;

import java.util.List;

import graph_coloring.algorithm.GraphColoringAlgorithm;
import graph_coloring.structure.EricssonGraph;
import graph_coloring.structure.EricssonNode;
import graph_coloring.structure.Graph;

public class MinimizeColorChange implements GraphColoringAlgorithm{

	private double targetError;
	private double targetChange;
	
	public void setParameters(double error, double colorChange){
		this.targetError = error;
		this.targetChange = colorChange;
	}
	
	@Override
	public void startAlgorithm(List<Integer> nodes, Graph graph) {
		//Random rnd = new Random();
		int cnt = 0;
		
		for( Integer index : nodes ){
			
			System.out.format("%d %d\n", cnt, nodes.size());
			cnt += 1;
			
			EricssonNode eNode = (EricssonNode) graph.getNode(index);
			
			if ( eNode.getColorable() == false )
			{
				continue;
			}
			
			int colorClass = eNode.getColorClass();
						
			int ret = getParameters((EricssonGraph)graph, index, colorClass);
			
			if ( ret == -1 )
				break;
			else if ( ret == 0 )
				continue;
		}
	
	}
	

	private int getParameters(EricssonGraph graph, int index, int colorClass) {
		
		EricssonNode eNode = (EricssonNode)graph.getNode(index);
		int startColor = eNode.getStartColor();
		
		if ( !graph.getColorClass(colorClass).containsColor(startColor) )
			return 0;
		
		double change = graph.getColorChange();
		double error = graph.getNodeError(index, startColor);
		
		if ( change > targetChange && error < 100)
		{
			((EricssonGraph)graph).setNodeColor(index, startColor);
		}
		
		return -1;
	}
}

