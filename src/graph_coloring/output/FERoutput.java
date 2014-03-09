package graph_coloring.output;

import graph_coloring.common.OrderPair;
import graph_coloring.structure.Graph;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonGraph;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

public class FERoutput implements Converter{

	@Override
	public void convertGraphToFile(Graph g, String fileName) throws FileNotFoundException, UnsupportedEncodingException {
		
		EricssonGraph graph = (EricssonGraph)g;
		
		PrintWriter outFile = new PrintWriter(fileName, "UTF-8");
		outFile.println(graph.getDomainNameSize()); //number of color classes
		
		for(Integer colorClass : graph.getDomainNames()){
			outFile.println(colorClass); //name of color class
			outFile.println(graph.getNumberOfColors(colorClass)); //number of colors
			Iterator<Integer> colors = graph.getDomainNameIterator(colorClass);
			while(colors.hasNext()){
				outFile.println(colors.next()); //id of color
			}
		}
		
		int nodeSize = graph.getNodeSize();
		
		outFile.println(nodeSize); //number of nodes
		
		for(int i = 0 ; i < nodeSize ; ++i){
			outFile.println(graph.getNodeId(i)); //node id
			outFile.println(graph.getNodeStartColor(i)); //start color
			outFile.println(graph.getNodeDomainName(i)); //color class
			outFile.println(graph.getNodeGroup(i)); //node class (A, B, C)
			outFile.println(graph.getNodeColorable(i)); //colorable
		}
		
		
		int bridgeSize = graph.getBridgeSize();
		
		outFile.println(bridgeSize); //number of bridges
		for(int i = 0 ; i < bridgeSize ; ++i){
			OrderPair bridge = graph.getBridge(i);
			outFile.println(bridge.getFirst()); //first node
			outFile.println(bridge.getSecond()); //second node
			outFile.println(graph.getBridgeWeight(i)); //weight of bridge
		}
		
		outFile.close();
	}



}
