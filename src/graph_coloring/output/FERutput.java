package graph_coloring.output;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Set;

import graph_coloring.common.Pair;
import graph_coloring.structure.ColorClass;
import graph_coloring.structure.EricssonBridge;
import graph_coloring.structure.EricssonGraph;
import graph_coloring.structure.EricssonNode;
import graph_coloring.structure.Graph;

public class FERutput implements Converter{

	@Override
	public void convertGraphToFile(Graph g, String fileName) throws FileNotFoundException, UnsupportedEncodingException {
		
		EricssonGraph graph = (EricssonGraph) g;
		
		PrintWriter outFile = new PrintWriter(fileName, "UTF-8");
		outFile.println(graph.colorClasses.size());
		outFile.println(graph.colorClasses.size());
		
		for(Integer colorClass : graph.colorClasses.keySet()){
			outFile.println(colorClass);
			outFile.println(graph.colorClasses.get(colorClass).getColorCount());
			Set<Integer> colors = graph.colorClasses.get(colorClass).getAllColors();
			for(Integer color : colors){
				outFile.println(color);
			}
		}
		
		Set<Integer> nodes = graph.getNodeIndices();
		
		outFile.println(nodes.size());
		
		for(Integer index : nodes){
			EricssonNode node = (EricssonNode)graph.getNode(index);
			outFile.println(index);
			outFile.println(node.getStartColor());
			outFile.println(node.getColorClass());
			outFile.println(node.getNodeClass());
			outFile.println(node.getColorable());
		}
		
		Set<Pair<Integer, Integer>> bridges = graph.getBridgeIndices();
		
		for(Pair<Integer, Integer> pair : bridges){
			EricssonBridge bridge = (EricssonBridge) graph.getBridge(pair);
			outFile.println(pair.getLeft());
			outFile.println(pair.getRight());
			outFile.println(bridge.getWeight());
		}
		
		outFile.close();
	}

}
