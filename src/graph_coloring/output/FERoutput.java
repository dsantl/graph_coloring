package graph_coloring.output;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;

import graph_coloring.common.MinMaxPairInteger;
import graph_coloring.common.Pair;
import graph_coloring.structure.ColorClass;
import graph_coloring.structure.EricssonBridge;
import graph_coloring.structure.EricssonGraph;
import graph_coloring.structure.EricssonNode;
import graph_coloring.structure.Graph;

public class FERoutput implements Converter{

	@Override
	public void convertGraphToFile(Graph g, String fileName) throws FileNotFoundException, UnsupportedEncodingException {
		
		EricssonGraph graph = (EricssonGraph) g;
		
		PrintWriter outFile = new PrintWriter(fileName, "UTF-8");
		outFile.println(graph.getColorClassSize()); //number of color classes
		
		for(Integer colorClass : graph.getColorClasses()){
			outFile.println(colorClass); //name of color class
			outFile.println(graph.getColorCount(colorClass)); //number of colors
			List<Integer> colors = graph.getAllColorsOfClass(colorClass);
			for(Integer color : colors){
				outFile.println(color); //id of color
			}
		}
		
		Set<Integer> nodes = graph.getNodeIndices();
		
		outFile.println(nodes.size()); //number of nodes
		
		for(Integer index : nodes){
			EricssonNode node = (EricssonNode)graph.getNode(index);
			outFile.println(index); //node id
			outFile.println(node.getStartColor()); //start color
			outFile.println(node.getColorClass()); //color class
			outFile.println(node.getNodeClass()); //node class (A, B, C)
			outFile.println(node.getColorable()); //colorable
		}
		
		
		Set<MinMaxPairInteger> bridges = graph.getBridgeIndices();
		
		outFile.println(bridges.size()); //number of bridges
		for(MinMaxPairInteger pair : bridges){
			EricssonBridge bridge = (EricssonBridge) graph.getBridge(pair);
			outFile.println(pair.getLeft()); //first node
			outFile.println(pair.getRight()); //second node
			outFile.println(bridge.getWeight()); //weight of bridge
		}
		
		outFile.close();
	}

}
