package graph_coloring.algorithmset;


import java.util.Iterator;
import java.util.Random;

import graph_coloring.algorithm.GraphColoringAlgorithm;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonGraph;


public class RandomAlgorithm extends GraphColoringAlgorithm{

	private int getRandomColor(EricssonGraph graph, int index){
		Random rnd = new Random();
		int domainName = graph.getNodeDomainName(index);
		int noColors = graph.getDomainNameSize();
		Iterator<Integer> it = graph.getDomainNameIterator(domainName);
		int rndInt = rnd.nextInt(noColors);
		int cnt = 0;
		
		while(it.hasNext()){
			int color = it.next();
			if ( rndInt == cnt )
				return color;
			cnt += 1;
		}
		
		return graph.getNodeColor(index);
	}
	
	private void startAlgorithm(EricssonGraph graph){
		
		int numOfNodes = graph.getNodeSize();
		
		for(int i = 0 ; i < numOfNodes ; ++i){
			
			if ( !this.checkNode(i) ){
				continue;
			}
			
			if ( !graph.getNodeColorable(i) )
				continue;
			
			int randomColor = getRandomColor(graph, i);
			graph.setNodeColor(i, randomColor);
		}
		
	}

	@Override
	protected void algorithm() {
		EricssonGraph eGraph = (EricssonGraph) graph;
		this.startAlgorithm(eGraph);
	}

}
