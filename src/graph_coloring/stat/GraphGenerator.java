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
	
	private static void generateColors(Set<Integer> colors, int sameColors, int colorDomainSize, int numberColor){
		
		for(int i = 0 ; i < sameColors ; ++i){
			colors.add(i);
		}
		
		for(int i = 0 ; i < numberColor - sameColors ; ++i){
			int color = rnd.nextInt(numberColor-sameColors);
			colors.add(color+sameColors);
		}
	}
	
	private static void generateColorClasses(List<Set<Integer>> colorsDomains, int numberColorDomains, int colorDomainSize, int numberColor, double overlapping){
		int sameColors = (int)(colorDomainSize * overlapping);
		
		for(int i = 0 ; i < numberColorDomains ; ++i){
			colorsDomains.add(new HashSet<Integer>());
			generateColors(colorsDomains.get(i), sameColors, colorDomainSize, numberColor);
		}
		
		
		
	}
	
	private static double generateWeight(){
		int categoryCount = 4;
		double sizer = 1;
		double category = rnd.nextInt(categoryCount);
		
		
		if (category == 0)
			sizer = 1;
		else if (category == 1)
			sizer = 1000;
		else if (category == 2)
			sizer = 10000;
		else if (category == 3)
			sizer = 100000;
		
		return Math.abs(rnd.nextGaussian()*sizer);
	}
	
	private static EricssonGraph generateGraph(List<Set<Integer>> nodes, List<Set<Integer>> colorsDomains, int numberColor, double notColorable){
		EricssonGraph graph = new EricssonGraph();
		
		for(int i = 0 ; i < colorsDomains.size() ; ++i){
			graph.addColorClass(i);
			for(Integer color : colorsDomains.get(i)){
				graph.addColorToColorClass(i, color);
			}
		}
		
		for(int i = 0 ; i < nodes.size() ; ++i){
			boolean colorable = true;
			if ( rnd.nextDouble() < notColorable )
				colorable = false;
			graph.addEricssonNode(i, 'A', rnd.nextInt(colorsDomains.size()), rnd.nextInt(numberColor),  colorable); 
		}
		
		for(int i = 0 ; i < nodes.size() ; ++i){
			for(Integer neighbour : nodes.get(i)){
				graph.addWeightBridge(i, neighbour, generateWeight());
			}
		}
		
		return graph;
	}
	
	public static EricssonGraph generate(int nodeSize, double density, int numberColorDomains, int colorDomainSize, int numberColor, double overlapping, double notColorable){
		List<Set<Integer>> nodes = new ArrayList<Set<Integer>>();
		List<Set<Integer>> colorsDomains = new ArrayList<Set<Integer>>();
		init(nodes, nodeSize);
		generateBridges(nodes, density);
		generateColorClasses(colorsDomains, numberColorDomains, colorDomainSize, numberColor, overlapping);
		return generateGraph(nodes, colorsDomains, numberColor, notColorable);
	}
	
}
