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
	
	private static void generateBridge(EricssonGraph graph, int index, int nodes, double density){
		Set<Integer> neighbours = new HashSet<Integer>();
		for(int i = 0 ; i < graph.getNodeDegre(index) ; ++i){
				neighbours.add(graph.getNodeNeighburId(index, i));
		}
		
		double noise  = 0.1*(rnd.nextDouble()-0.5); //density*[-0.5,0.5]
		double per = Math.min(1, density + noise);
		int neighboursSize = (int)(nodes*per);
		neighboursSize = Math.max(neighboursSize, 1);
		neighboursSize = Math.min(neighboursSize, nodes-1);
		neighboursSize = Math.max(0, neighboursSize - neighbours.size());
		
		List<Integer> avbNodes = new LinkedList<Integer>();
		
		for(int i = 0 ; i < nodes ; ++i){
			if ( neighbours.contains(i) || i == index )
				continue;
			avbNodes.add(i);
		}
		
		for(int j = 0 ; j < neighboursSize ; ++j){
			int nIndex = rnd.nextInt(avbNodes.size());
			int neighbour = avbNodes.get(nIndex);
			avbNodes.remove(nIndex);
			graph.addWeightBridge(index, neighbour, generateWeight());
		}	
	}
	
	private static void generateBridges(EricssonGraph graph, int nodes, double density){
		for(int i = 0 ; i < nodes ; ++i){
			generateBridge(graph, i, nodes, density);
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
		double sizer = 1;
		double category = rnd.nextDouble();
		
		if (category < 0.4)
			sizer = 1;
		else if (category < 0.8)
			sizer = 1000;
		else if (category < 0.9)
			sizer = 10000;
		else
			sizer = 100000;
		
		return Math.abs(rnd.nextGaussian()*sizer);
	}
	
	private static void generateNodes(EricssonGraph graph, int nodes, List<Set<Integer>> colorsDomains, int numberColor, double notColorable){
		
		for(int i = 0 ; i < colorsDomains.size() ; ++i){
			graph.addColorClass(i);
			for(Integer color : colorsDomains.get(i)){
				graph.addColorToColorClass(i, color);
			}
		}
		
		
		for(int i = 0 ; i < nodes ; ++i){
			boolean colorable = true;
			if ( rnd.nextDouble() < notColorable )
				colorable = false;
			graph.addEricssonNode(i, 'A', rnd.nextInt(colorsDomains.size()), rnd.nextInt(numberColor),  colorable); 
		}
	}
	
	public static EricssonGraph generate(int nodeSize, double density, int numberColorDomains, int colorDomainSize, int numberColor, double overlapping, double notColorable){
		List<Set<Integer>> colorsDomains = new ArrayList<Set<Integer>>();
		EricssonGraph graph = new EricssonGraph();
		System.out.println("START");
		generateColorClasses(colorsDomains, numberColorDomains, colorDomainSize, numberColor, overlapping);
		System.out.println("COLOR CLASSES DONE");
		generateNodes(graph, nodeSize, colorsDomains, numberColor, notColorable);
		System.out.println("NODES DONE");
		generateBridges(graph, nodeSize, density);
		System.out.println("BRIDGES DONE");
		return graph; 
	}
	
}
