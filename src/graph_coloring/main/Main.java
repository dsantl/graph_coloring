package graph_coloring.main;

import graph_coloring.input.FERFileFormat;
import graph_coloring.input.FileFormat;
import graph_coloring.input.NodeColorFormat;
import graph_coloring.order.OrderBridgeWeight;
import graph_coloring.order.OrderNodeCOL;
import graph_coloring.order.OrderNodeFIT;
import graph_coloring.order.OrderNodeLDO;
import graph_coloring.order.OrderNodeSDO;
import graph_coloring.order.OrderNodeSDOLDO;
import graph_coloring.order.OrderNodeSTDORD;
import graph_coloring.stat.ChangeColorGlobal;
import graph_coloring.stat.CheckValidColoring;
import graph_coloring.stat.ErrorFunctionEricsson;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonGraph;


public class Main {

	public static void main(String[] args) {
		
		//FileFormat fileFormat = new EricssonFileFormat();
		FileFormat fileFormat = new FERFileFormat();
		long start = System.currentTimeMillis();
		
		EricssonGraph graph = null;
		
		try {
			//graph = fileFormat.getGraphFromFile("/home/dino/Desktop/diplomski/FER-Tokai.txt");
			//graph = fileFormat.getGraphFromFile("/home/dino/Desktop/FER-Tokai_coloring.txt");
			
			//graph = fileFormat.getGraphFromFile("Tokai-new.out");
			graph = (EricssonGraph) fileFormat.getGraphFromFile("Tokai.out");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*
		FERoutput out = new FERoutput();
		try {
			out.convertGraphToFile(graph, "Tokai-new.out");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		*/
		
		try {
			NodeColorFormat.setColorsFromFileToGraph("/home/dino/Desktop/bojanje_Tokai3.txt", graph);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		graph.makeBridgeOrder(new OrderBridgeWeight());
		
		for(int i = 0 ; i < graph.getBridgeSize() ; ++i){
			System.out.println(graph.getBridgeWeight(i));
		}
		
		double oldError = ErrorFunctionEricsson.computeStat(graph);
		
		System.out.format("Error: %f\n", oldError);		
		
		System.out.format("Color change: %f\n", ChangeColorGlobal.computeStat(graph));
		System.out.format("Valid coloring: %b\n", CheckValidColoring.computeStat(graph));
		
		long end = System.currentTimeMillis();
		System.out.println(end-start);

	}
}
