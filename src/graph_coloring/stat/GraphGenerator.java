package graph_coloring.stat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import graph_coloring.structure.weight_graph.ericsson_graph.EricssonGraph;

public class GraphGenerator {
	
	private static Random rnd = new Random();
	
	private static void init(List<Set<Integer>> nodes, int N){
		for(int i = 0 ; i < N ; ++i){
			nodes.add(new HashSet<Integer>());
		}
	}
	
	private static void generateBridge(int index, List<Set<Integer>> nodes, double density){
		List<Integer> neighbours = new LinkedList<Integer>();
		for(int i = 0 ; i < nodes.size() ; ++i){
			if ( i != index )
				neighbours.add(i);
		}
		
		double noise  = density*(rnd.nextDouble()-0.5); //density*[-0.5,0.5]
		double per = Math.max(1, density + noise);
		int neighboursSize = (int)(nodes.size()*per);
		neighboursSize = Math.max(neighboursSize, 1);
		neighboursSize = Math.min(neighboursSize, nodes.size()-1);
		
		for(int j = 0 ; j < neighboursSize ; ++j){
			int nIndex = rnd.nextInt(neighbours.size());
			int neighbour = neighbours.get(nIndex);
			neighbours.remove(nIndex);
			nodes.get(index).add(neighbour);
		}	
	}
	
	private static void generateBridges(List<Set<Integer>> nodes, double density){
		for(int i = 0 ; i < nodes.size() ; ++i){
			generateBridge(i, nodes, density);
		}
	}
	
	private static void generateColorClasses(List<Set<Integer>> colorsDomains, int numberColorDomains, int colorDomainSize, int colors, double overlapping){
		
	}
	
	public static EricssonGraph generate(int nodeSize, double density, int numberColorDomains, int colorDomainSize, int colors, double overlapping, double notColorable){
		List<Set<Integer>> nodes = new ArrayList<Set<Integer>>();
		List<Set<Integer>> colorsDomains = new ArrayList<Set<Integer>>();
		init(nodes, nodeSize);
		generateBridges(nodes, density);
		generateColorClasses(colorsDomains, numberColorDomains, colorDomainSize, colors, overlapping);
		return null;
	}
	
}
