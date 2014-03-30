package graph_coloring.main;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import graph_coloring.algorithm.GraphAlgorithmContext;
import graph_coloring.algorithmset.RandomAlgorithm;
import graph_coloring.algorithmset.agents.AgentAlgorithm;
import graph_coloring.algorithmset.genetic.GeneticAlgorithm;
import graph_coloring.algorithmset.greedy.CombiGreedy;
import graph_coloring.algorithmset.greedy.Greedy;
import graph_coloring.algorithmset.simulated_anneling.SimpleAnneling;
import graph_coloring.algorithmset.simulated_anneling.SimulatedAnneling;
import graph_coloring.color_selector.ColorSelector;
import graph_coloring.color_selector.ColorSelectorTRG;
import graph_coloring.common.Pair;
import graph_coloring.input.EricssonFileFormat;
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
			//graph = (EricssonGraph) fileFormat.getGraphFromFile("/home/dino/Desktop/FER/9. SEM/PROJEKT/diplomski/FER-Tokai.txt");
			//graph = fileFormat.getGraphFromFile("/home/dino/Desktop/FER-Tokai_coloring.txt");
			
			//graph = (EricssonGraph) fileFormat.getGraphFromFile("Tokai-new.out");
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
		
		int id = GetColorableGroupNodes.getNodeClass(graph, 'A').get(0);
		EricssonGraph newGraph = MakeSubGraph.createEricssonSubGraphBFS(graph, id, 31000);
		graph = newGraph;
		
		
		double oldError = ErrorFunctionEricsson.computeStat(graph);
		
		System.out.println(graph.getNodeSize());
		System.out.println("Algorithm...");
		GraphAlgorithmContext alg;
		
		
		//alg = new GraphAlgorithmContext(new Greedy("SDO", "ABW", 15));
		//alg.startAlgorithm(graph);
			
		alg = new GraphAlgorithmContext(new CombiGreedy(15));
		alg.startAlgorithm(graph);
		
		alg = new GraphAlgorithmContext(new SimpleAnneling(0.5, 3100000, 10));
		alg.startAlgorithm(graph); 
				
		
		//alg = new GraphAlgorithmContext(new GeneticAlgorithm(5, 10, 10000));
		//alg.startAlgorithm(graph);
		
		//alg = new GraphAlgorithmContext(new Greedy("SDO", "ABW", 5));
		//alg.startAlgorithm(graph);
		
				
		/*
		Set<Integer> touchableNodes = new HashSet<Integer>();
		for(int i = 0 ; i < graph.getNodeSize() ; ++i){
			if ( graph.getNodeError(i) > 0 )
				touchableNodes.add(graph.getNodeId(i));
		}		
		*/
		
		//alg = new GraphAlgorithmContext(new AgentAlgorithm(3*graph.getNodeSize()/2, 20, "SDO", "ABW"));
		//alg.startAlgorithm(graph);
		
		
		//alg = new GraphAlgorithmContext(new SimulatedAnneling(3, 1000, 2, 0.1, "MF"));
		//alg.startAlgorithm(graph); 
		
		/*
		alg = new GraphAlgorithmContext(new Greedy("BRIDGEWEIGHT", "MF", 1, true));
		alg.startAlgorithm(graph);
		*/
		
		//alg = new GraphAlgorithmContext(new Greedy("SDO", "ABW", 5));
		//alg.startAlgorithm(graph);
		
		
		//alg = new GraphAlgorithmContext(new AgentAlgorithm(3*graph.getNodeSize()/4, 1000, "SDO", "ABW"));
		//alg.startAlgorithm(graph);
		
		
		System.out.format("Old error: %f\n", oldError);		
		System.out.format("New error: %f\n", ErrorFunctionEricsson.computeStat(graph));
		
		System.out.format("Color change: %f\n", ChangeColorGlobal.computeStat(graph));
		System.out.format("Valid coloring: %b\n", CheckValidColoring.computeStat(graph));
		
		long end = System.currentTimeMillis();
		System.out.println(end-start);

	}
}
