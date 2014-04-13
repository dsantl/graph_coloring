package graph_coloring.output;

import graph_coloring.common.OrderPair;
import graph_coloring.common.Pair;
import graph_coloring.structure.Graph;
import graph_coloring.structure.weight_graph.WeightNode;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonGraph;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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
		
		for(int nodeIndex = 0 ; nodeIndex < graph.getNodeSize() ; ++nodeIndex){
			
			int id = graph.getNodeId(nodeIndex);
			Set<OrderPair> bridgeFlag = new HashSet<OrderPair>();
			Iterator<Pair<Double, WeightNode>> neighbourIterator = graph.getNeighbours(nodeIndex);
			
			while(neighbourIterator.hasNext()){
				
				Pair<Double, WeightNode> doubleNode = neighbourIterator.next();
				double weight = doubleNode.getFirst();
				WeightNode neighbourNode = doubleNode.getSecond();
				
				int node1 = id;
				int node2 = neighbourNode.getId();
				
				OrderPair orderPair = new OrderPair(node1, node2);
				
				if ( !bridgeFlag.contains(orderPair) ){
					bridgeFlag.add(orderPair);
				}
				else{
					bridgeFlag.remove(orderPair);
					continue;
				}
				
				outFile.println(orderPair.getFirst()); //first node
				outFile.println(orderPair.getSecond()); //second node
				outFile.println(weight); //weight of bridge
			}
		}
		
		outFile.close();
	}



}
