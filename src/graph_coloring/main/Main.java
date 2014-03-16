package graph_coloring.main;

import java.util.Iterator;

import graph_coloring.algorithm.GraphAlgorithmContext;
import graph_coloring.algorithmset.RandomAlgorithm;
import graph_coloring.algorithmset.agents.AgentAlgorithm;
import graph_coloring.algorithmset.greedy.CombiGreedy;
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
import graph_coloring.stat.GetColorableGroupNodes;
import graph_coloring.stat.MakeSubGraph;
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
		System.out.println(graph.getNodeSize());
		int id = GetColorableGroupNodes.getNodeClass(graph, 'C').get(0);
		EricssonGraph newGraph = MakeSubGraph.createEricssonSubGraphBFS(graph, id, 15000);
		graph = newGraph;
		
		double oldError = ErrorFunctionEricsson.computeStat(graph);
		
		
		System.out.println("Algorithm...");
		GraphAlgorithmContext alg;
		
		
		alg = new GraphAlgorithmContext(new Greedy("STDORD", "ABW", 1));
		alg.startAlgorithm(newGraph);
		
		/*
		alg = new GraphAlgorithmContext(new Greedy("SDO", "ABW", 5));
		alg.startAlgorithm(graph);
		
		
		alg = new GraphAlgorithmContext(new AgentAlgorithm(graph.getNodeSize(), 1000));
		alg.startAlgorithm(graph);
		*/
		
		System.out.format("Old error: %f\n", oldError);		
		System.out.format("New error: %f\n", ErrorFunctionEricsson.computeStat(graph));
		
		System.out.format("Color change: %f\n", ChangeColorGlobal.computeStat(graph));
		System.out.format("Valid coloring: %b\n", CheckValidColoring.computeStat(graph));
		
		long end = System.currentTimeMillis();
		System.out.println(end-start);

	}
}
