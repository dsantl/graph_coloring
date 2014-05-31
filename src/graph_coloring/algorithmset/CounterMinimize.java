package graph_coloring.algorithmset;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import graph_coloring.algorithm.GraphColoringAlgorithm;
import graph_coloring.color_selector.ColorSelector;
import graph_coloring.color_selector.ColorSelectorFactory;
import graph_coloring.stat.ChangeColorGlobal;
import graph_coloring.stat.ErrorFunctionEricsson;
import graph_coloring.stat.GetColorableNodes;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonGraph;

public class CounterMinimize extends GraphColoringAlgorithm{

	private Random rnd = new Random();
	private int iterations;
	private List<Integer> counters = new ArrayList<Integer>();
	private List<ColorSelector> selectors = new ArrayList<ColorSelector>();
	private ColorSelector swap;
	
	public CounterMinimize(int iterations){
		this.iterations = iterations;
		
		try {
			this.swap = ColorSelectorFactory.factory("SWAP");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		String[] stringSelector = {"ABW", "MF", "MC"};
		
		for(int i = 0 ; i < stringSelector.length ; ++i){
			counters.add(1);
			try {
				selectors.add(ColorSelectorFactory.factory(stringSelector[i]));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private int chooseSelector(double wheele){
		double sum = 0;
		double prop = 0;
		
		for(Integer i : this.counters){
			sum += i;
		}
		
		for(int i = 0 ; i < this.counters.size() ; ++i){
			prop += this.counters.get(i)/sum;
			if ( wheele <= prop )
				return i;
		}
		return 0;
	}
	
	void addBFS(int id, List<Integer> nodes, int number){
		Queue<Integer> nodeId = new LinkedList<Integer>();
		nodeId.add(id);
		
		while(nodes.size() < number && !nodeId.isEmpty()){
			int headId = nodeId.poll();
			int index = graph.getNodeIndex(headId);
			nodes.add(headId);
			
			for(int i = 0 ; i < graph.getNodeDegre(index) ; ++i){
				int neighbourId = graph.getNodeNeighburId(index, i);
				if ( !nodeId.contains(neighbourId) )
					nodeId.add(neighbourId);
			}
		}
		
	}
	
	@Override
	protected void algorithm() {
		
		EricssonGraph ericssonGraph = (EricssonGraph) graph;
		double dError;
		double bestError = ErrorFunctionEricsson.computeStat(ericssonGraph);
		double currError = 0;
		int NC = GetColorableNodes.getNumberOfColorableNodes(ericssonGraph);
		int bestChange = (int)(NC*ChangeColorGlobal.computeStat(ericssonGraph));
		int currChange;
		ColorSelector colorSelector;
		
		List<Integer> colorableNodes = GetColorableNodes.getNodeIdsFilter(ericssonGraph, this.getTouchableNodes());
			
		double maxError = 0.0;
		for(Integer j : colorableNodes){
			maxError = Math.max(maxError, ericssonGraph.getNodeError(graph.getNodeIndex(j)));
		}
		System.out.println(maxError);
		
		colorableNodes.clear();
		
		for(int j = 0 ; j < graph.getNodeSize() ; ++j){
			if ( ericssonGraph.getNodeColorable(j) && ericssonGraph.getNodeError(j) == maxError ){
				addBFS(graph.getNodeId(j), colorableNodes, 5000);
			}
		}
		
		for(int i = 0 ; i < this.iterations ; ++i){
			
			double wheel = rnd.nextDouble();
			int choice;
			
			choice = chooseSelector(wheel);
			
			colorSelector = this.selectors.get(choice);
			
			
			int nodeId = colorableNodes.get(rnd.nextInt(colorableNodes.size()));
			int nodeIndex = ericssonGraph.getNodeIndex(nodeId);
			
			if (rnd.nextDouble() > 0.6){
				colorSelector = swap;
			}
			
			int color = ericssonGraph.chooseColor(nodeIndex, colorSelector);
			
			currError = bestError - 2*ericssonGraph.getNodeError(nodeIndex);
			currError += 2*ericssonGraph.getNodeColorError(nodeIndex, color);
			
			int changeTmp = 0;
			boolean colorIsStart = color == ericssonGraph.getNodeStartColor(nodeIndex);
			boolean oldColorIsStart = ericssonGraph.getNodeColor(nodeIndex) == ericssonGraph.getNodeStartColor(nodeIndex);
			
			if ( colorIsStart == oldColorIsStart )
				changeTmp = 0;
			else if (oldColorIsStart == true)
				changeTmp = 1;
			else
				changeTmp = -1;
			
			currChange = bestChange + changeTmp;
			
			dError = currError - bestError;
			
			if ( dError < 0 || colorSelector == swap ){
				graph.setNodeColor(nodeIndex, color);
				bestError = currError;
				bestChange = currChange;
				if ( dError < 0){
					int newCounter = counters.get(choice) + 1;
					this.counters.set(choice, newCounter);
				}
			}
			
			if (i % 10000 == 0){
				for(int k = 0 ; k < counters.size() ; ++k)
					counters.set(k, 1);
			}
			
			if (i % 1 == 0){
				System.out.format("%f %f\n", bestError, (double)bestChange/NC);
			}
		}
	}
}
