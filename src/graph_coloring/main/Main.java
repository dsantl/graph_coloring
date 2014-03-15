package graph_coloring.main;

import java.util.Iterator;

import graph_coloring.algorithm.GraphAlgorithmContext;
import graph_coloring.algorithmset.RandomAlgorithm;
import graph_coloring.algorithmset.greedy.Greedy;
import graph_coloring.common.Pair;
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
import graph_coloring.structure.weight_graph.WeightNode;
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
		
		/*
		try {
			NodeColorFormat.setColorsFromFileToGraph("/home/dino/Desktop/bojanje.txt", graph);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		*/
		
		double oldError = ErrorFunctionEricsson.computeStat(graph);
		
		
		System.out.println("Algorithm...");
		GraphAlgorithmContext alg;
		
		int i = 1000;
		
		while(i!=0){
			
			i -= 1;
			
			double error = ErrorFunctionEricsson.computeStat(graph);
			System.out.format("Old error: %f\n", error);		
			System.out.format("Color change: %f\n\n", ChangeColorGlobal.computeStat(graph));
			
			if ( error > 2800.0 ){
				alg = new GraphAlgorithmContext(new Greedy("LDO", "ABW", 1));
				alg.startAlgorithm(graph);
			}
			else
			{
				alg = new GraphAlgorithmContext(new Greedy("SDO", "ABW", 1));
				alg.startAlgorithm(graph);
			}
			
			double newError = ErrorFunctionEricsson.computeStat(graph);
			
			if ( error - newError <= 2.0){
				alg = new GraphAlgorithmContext(new Greedy("SDOLDO", "MF", 1));
				alg.startAlgorithm(graph);
			}
			else if ( error - newError <= 10.0){
				alg = new GraphAlgorithmContext(new Greedy("FIT", "MF", 1));
				alg.startAlgorithm(graph);
			}
			
		}
		
		System.out.format("Old error: %f\n", oldError);		
		System.out.format("New error: %f\n", ErrorFunctionEricsson.computeStat(graph));
		
		System.out.format("Color change: %f\n", ChangeColorGlobal.computeStat(graph));
		System.out.format("Valid coloring: %b\n", CheckValidColoring.computeStat(graph));
		
		long end = System.currentTimeMillis();
		System.out.println(end-start);

	}
}
