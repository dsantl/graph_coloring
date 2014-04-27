package graph_coloring.main;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

import graph_coloring.algorithm.GraphAlgorithmContext;
import graph_coloring.algorithmset.agents.AgentAlgorithm;
import graph_coloring.algorithmset.greedy.CombiGreedy;
import graph_coloring.algorithmset.greedy.Greedy;
import graph_coloring.algorithmset.simulated_anneling.GeneticAnneling;
import graph_coloring.algorithmset.simulated_anneling.SimulatedAnneling;
import graph_coloring.input.FERFileFormat;
import graph_coloring.input.FileFormat;
import graph_coloring.output.NodeColorOutput;
import graph_coloring.stat.ChangeColorGlobal;
import graph_coloring.stat.CheckValidColoring;
import graph_coloring.stat.ErrorFunctionEricsson;
import graph_coloring.stat.GetColorableGroupNodes;
import graph_coloring.stat.MakeSubGraph;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonGraph;


public class Main {

	public static void main(String[] args) {
		
		//FileFormat fileFormat = new EricssonFileFormat();
		FileFormat fileFormat = new FERFileFormat();
		long start = System.currentTimeMillis();
		
		EricssonGraph graph = null;
		
		try {
			//graph = (EricssonGraph) fileFormat.getGraphFromFile("/home/dino/Desktop/FER/9. SEM/PROJEKT/diplomski/FER-Kansai.txt");
			//graph = fileFormat.getGraphFromFile("/home/dino/Desktop/FER-Tokai_coloring.txt");
			
			//graph = (EricssonGraph) fileFormat.getGraphFromFile("Tokai-new.out");
			//String fileName = args[0];
			//graph = (EricssonGraph) fileFormat.getGraphFromFile(fileName);
			graph = (EricssonGraph) fileFormat.getGraphFromFile("Tokai.out");
			//graph = (EricssonGraph) fileFormat.getGraphFromFile("Kansai.out");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*
		FERoutput out = new FERoutput();
		try {
			out.convertGraphToFile(graph, "Kansai.out");
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
		
		EricssonGraph newGraph = MakeSubGraph.filterByNodeGroup(graph, 'A');
		graph = newGraph;
		
		double oldError = ErrorFunctionEricsson.computeStat(graph);
		
		//System.out.println(graph.getNodeSize());
		//System.out.println(graph.getBridgeSize());
		System.out.println("Algorithm...");
		GraphAlgorithmContext alg;
		
		System.out.println(graph.getNodeSize());
		System.out.println(graph.getBridgeSize());
		
		//alg = new GraphAlgorithmContext(new Greedy("SDO", "ABW", 1));
		//alg.startAlgorithm(graph);
				
		alg = new GraphAlgorithmContext(new CombiGreedy(5)); //5
		alg.startAlgorithm(graph);
		
		
		//alg = new GraphAlgorithmContext(new GeneticAnneling(10000000, 5, 1.0, 0.7));
		//alg.startAlgorithm(graph); 
		
		//int a = 5;
		//while(a==5){
		
		alg = new GraphAlgorithmContext(new SimulatedAnneling(0.1, 3100000, 100));
		alg.startAlgorithm(graph);
		
			//alg = new GraphAlgorithmContext(new Greedy("RND", "SWAP", 1, 0.1));
			//alg.startAlgorithm(graph);
				
		//}
		
		//Greedy greedy = new Greedy("LDO", "TRG", 15);
		//greedy.setColorSelectorParam(0.5);
		//alg = new GraphAlgorithmContext(greedy);
		//alg.startAlgorithm(graph);
	
		
		
		//alg = new GraphAlgorithmContext(new GeneticAlgorithm(10, 11, 310000, 1));
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

		/*
		NodeColorOutput out = new NodeColorOutput(); 
		try {
			out.convertGraphToFile(graph, "/home/dino/Desktop/bojanje_dino.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
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
