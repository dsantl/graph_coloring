package graph_coloring.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import graph_coloring.algorithm.GraphAlgorithmContext;
import graph_coloring.algorithmset.CounterMinimize;
import graph_coloring.algorithmset.HillClimbing;
import graph_coloring.algorithmset.agents.AgentAlgorithm;
import graph_coloring.algorithmset.genetic.GeneticAlgorithm;
import graph_coloring.algorithmset.greedy.CombiGreedy;
import graph_coloring.algorithmset.greedy.Greedy;
import graph_coloring.algorithmset.simulated_annealing.GeneticAnneling;
import graph_coloring.algorithmset.simulated_annealing.SimulatedAnneling;
import graph_coloring.algorithmset.simulated_annealing.SimulatedAnneling_D;
import graph_coloring.common.Pair;
import graph_coloring.input.DIMACSInput;
import graph_coloring.input.EricssonFileFormat;
import graph_coloring.input.FERFileFormat;
import graph_coloring.input.FileFormat;
import graph_coloring.input.NodeColorFormat;
import graph_coloring.order.OrderMethod;
import graph_coloring.order.OrderMethodFactory;
import graph_coloring.output.FERoutput;
import graph_coloring.output.GreedyStatOutput;
import graph_coloring.output.NodeColorOutput;
import graph_coloring.stat.ChangeColorGlobal;
import graph_coloring.stat.CheckValidColoring;
import graph_coloring.stat.CollisionCounter;
import graph_coloring.stat.ErrorFunctionEricsson;
import graph_coloring.stat.GenerateGraphFiles;
import graph_coloring.stat.GetColorableGroupNodes;
import graph_coloring.stat.GetColorableNodes;
import graph_coloring.stat.GraphFileFinder;
import graph_coloring.stat.GraphGenerator;
import graph_coloring.stat.MakeSubGraph;
import graph_coloring.stat.machine_learning.GreedyDataNode;
import graph_coloring.stat.machine_learning.NodeOrder;
import graph_coloring.stat.machine_learning.ProcessGraphs;
import graph_coloring.structure.Graph;
import graph_coloring.structure.weight_graph.WeightNode;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonGraph;
import graph_coloring.testing.MetaheuristicTest;
import graph_coloring.testing.RandomTest;


public class Main {

	public static void main(String[] args) {
		
		
		//RandomTest.start("/home/dino/Desktop/FER/10. SEM/Diplomski/Test/Graphs/");
		
		//MetaheuristicTest.start("/home/dino/Desktop/FER/10. SEM/Diplomski/Test/Graphs/public/");
		
		//GenerateGraphFiles.generate("/home/dino/Desktop/FER/10. SEM/Diplomski/Test/Graphs/sintetic/medium/");
		
		//ProcessGraphs.SaveStat("/home/dino/Desktop/FER/10. SEM/Diplomski/Test/Graphs/sintetic/small", 2000, 0.1, "ABW");
		
		/*
		int k = 5;
		if (k == 5 )
			return;
		*/
		
		//FileFormat fileFormat = new EricssonFileFormat();
		//FileFormat fileFormat = new FERFileFormat();
		FileFormat fileFormat = new DIMACSInput();
		//EricssonGraph graph = null;
		Graph dimacsGraph = null;
		
		try {
			//graph = (EricssonGraph) fileFormat.getGraphFromFile("/home/dino/Desktop/FER/9. SEM/PROJEKT/diplomski/FER-Kansai.txt");
			//graph = (EricssonGraph) fileFormat.getGraphFromFile("/home/dino/Desktop/Tokyo-FER-graph.txt");
			
			//graph = (EricssonGraph) fileFormat.getGraphFromFile("Tokai-new.out");
			//String fileName = args[0];
			//graph = (EricssonGraph) fileFormat.getGraphFromFile(fileName);
			//graph = (EricssonGraph) fileFormat.getGraphFromFile("Tokai.out");
			//graph = (EricssonGraph) fileFormat.getGraphFromFile("Kansai.out");
			
			dimacsGraph = fileFormat.getGraphFromFile(
					"/home/dino/Desktop/FER/10. SEM/Diplomski/Test/DIMACS/dsjr500.5.col");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		try {
			System.setOut(new PrintStream(new File("/home/dino/Desktop/dimacs_test/dsjr500.5/test_125_4.out")));
		} catch (Exception e) {
		     e.printStackTrace();
		}
		
		dimacsGraph.setMaxColors(125);
		
		long start = System.currentTimeMillis();

		
		GraphAlgorithmContext alg = new GraphAlgorithmContext(new Greedy("SDO", "MC_D", 20));
		alg.startAlgorithm(dimacsGraph);
		
		alg = new GraphAlgorithmContext(new SimulatedAnneling_D(200.0, 5000, 100, 0.6, 0.999, "MC_D", "SWAP_D"));
		alg.startAlgorithm(dimacsGraph);
		
		alg = new GraphAlgorithmContext(new Greedy("SDO", "SWAP_D", 20));
		alg.startAlgorithm(dimacsGraph);
		
		alg = new GraphAlgorithmContext(new SimulatedAnneling_D(10.0, 5000, 100, 0.6, 0.999, "MC_D", "SWAP_D"));
		alg.startAlgorithm(dimacsGraph);
		
		
		long end = System.currentTimeMillis() - start;
		System.out.println(end);
		System.out.println(CollisionCounter.computeStat(dimacsGraph));
		
		
		/*
		for(int i = 0 ; i < dimacsGraph.getNodeSize() ; ++i){
			if ( dimacsGraph.getNodeCollision(i) > 0)
				System.out.println(dimacsGraph.getNodeSaturation(i));
		}
		*/
		
		
		
		int k = 5;
		if (k == 5 )
			return;
		
		
		/*
		for(int i = 0 ; i < dimacsGraph.getNodeSize() ; ++i)
			dimacsGraph.setNodeColor(i, 1);
		
		System.out.println(CollisionCounter.computeStat(dimacsGraph));
		
		dimacsGraph.setMaxColors(10);
		GraphAlgorithmContext alg = new GraphAlgorithmContext(new Greedy("LDO", "RND_D", 20));
		alg.startAlgorithm(dimacsGraph);
		
		System.out.println(CollisionCounter.computeStat(dimacsGraph));
		*/	
		
		//System.out.println(dimacsGraph.getNodeSize());
		
		
		//GenerateGraphFiles.generate("/home/dino/Desktop/graph_test/");
		
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
		/*
		NodeColorOutput out = new NodeColorOutput();
		try {
			out.convertGraphToFile(graph, "Tokai-Combi20.out");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		*/
		
		
		
		try {
			//NodeColorFormat.setColorsFromFileToGraph("Tokai-Combi20-A.out", graph);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
		//graph = GraphGenerator.generate(1000, 1.0, 8, 300, 512, 0.1, 0.07);
		
		//double oldError = ErrorFunctionEricsson.computeStat(graph);
		
		//System.out.println(graph.getNodeSize());
		//System.out.println(graph.getBridgeSize());
		//System.out.println("Algorithm...");
		
		/*
		List<String> fileNames = GraphFileFinder.find("/home/dino/Desktop/FER/10. SEM/Diplomski/Test/Graphs/");
		
		for(String name : fileNames){
			System.out.println(name);
		}
		*/
		
		
		//EricssonGraph newGraph = MakeSubGraph.filterByNodeGroup(graph, 'C');
		//graph = newGraph;
		
		
		
		
		/*
		FERoutput out = new FERoutput();
		try {
			out.convertGraphToFile(graph, "/home/dino/Desktop/Tokai-A.txt");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		*/
		
		/*
		double oldError =  ErrorFunctionEricsson.computeStat(graph);
		System.err.println(graph.getNodeSize());
		System.err.println(graph.getBridgeSize());
		System.err.println(oldError);
		*/
		
		/*
		try {
			System.setOut(new PrintStream(new File("/home/dino/Desktop/weight-tokai.txt")));
		} catch (Exception e) {
		     e.printStackTrace();
		}
		
		for(int i = 0 ; i < graph.getNodeSize() ; ++i){
			Iterator<Pair<Double, WeightNode>> neighbours = graph.getNeighbours(i);
			while(neighbours.hasNext()){
				System.out.println(neighbours.next().getFirst());
			}
		}
		
		if (5 == 2+3)
			return;
		
		*/
		
		/*
		GraphAlgorithmContext alg;
		
		
		//Treba naci optimalni boj lokalizacije!
		alg = new GraphAlgorithmContext(new CombiGreedy(5));
		alg.startAlgorithm(graph);
		
		long start = System.currentTimeMillis();
		
		//alg = new GraphAlgorithmContext(new SimulatedAnneling(0.5, 5000, 100, 0.7, 0.9999, "ABW", "SWAP"));
		//alg.startAlgorithm(graph);
		
		alg = new GraphAlgorithmContext(new AgentAlgorithm(graph.getNodeSize(), 100, "MF", 0.01, 0.3));
		alg.startAlgorithm(graph);


		
		long end = System.currentTimeMillis() - start;
		
		System.out.format("STAT\n", oldError);
		System.out.format("Old error: %f\n", oldError);
		System.out.format("New error: %f\n", ErrorFunctionEricsson.computeStat(graph));
		System.out.format("Color change: %f\n", ChangeColorGlobal.computeStat(graph));
		System.out.format("Time: %f s\n", (double)end/1000);
		System.out.format("Valid coloring: %b\n", CheckValidColoring.computeStat(graph));
		System.err.println("DONE!");
		
		/*
		Set<Integer> errors = new HashSet<Integer>();
		for(int i = 0 ; i < graph.getNodeSize() ; ++i ){
			if ( graph.getNodeError(i) != 0.0 && graph.getNodeColorable(i)){
				errors.add(graph.getNodeId(i));
			}
		}
		/
		for(Integer id : errors){
			int index = graph.getNodeIndex(id);
			double currError = graph.getNodeError(index); 
			Iterator<Integer> colors = graph.getAvailableColorsOfNode(index);
			while(colors.hasNext()){
				int color = colors.next();
				double newError = graph.getNodeColorError(index, color);
				if ( newError < currError )
					System.out.format("Manja greska: %f %f\n", newError, currError);
			}
		}
		
		System.out.format("Percent: %f\n", (double)errors.size()/graph.getNodeSize());
		System.out.format("Number: %d\n", errors.size());
		
		
		//System.out.println(graph.getNodeSize());
		//System.out.println(graph.getBridgeSize());
		
		
		//alg = new GraphAlgorithmContext(new Greedy("SDO", "ABW", 1));
		//alg.startAlgorithm(graph);
		
		
		//alg = new GraphAlgorithmContext(new GeneticAnneling(10000000, 5, 5.0, 0.9, 0.6, "ABW", "SWAP"));
		//alg.startAlgorithm(graph); 
				
		
		//alg = new GraphAlgorithmContext(new SimulatedAnneling(1.0, 1000, 500, 0.8, 0.99999, "ABW", "SWAP"));
		//alg.startAlgorithm(graph);
		
		
		/*
		NodeColorOutput out = new NodeColorOutput();
		try {
			out.convertGraphToFile(graph, "Tokai-Combi20-A.out");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		*/
		
		
		//alg = new GraphAlgorithmContext(new GeneticAlgorithm(5, 7, 31000, 10, 0.8,  "ABW", "SWAP"));
		//alg.startAlgorithm(graph);
		
		
		
		/*
		alg = new GraphAlgorithmContext(new Greedy("SDO", "ABW", 1)); 
		alg.startAlgorithm(graph);
		alg = new GraphAlgorithmContext(new HillClimbing());
		alg.startAlgorithm(graph);
		alg = new GraphAlgorithmContext(new Greedy("SDO", "ABW", 5)); 
		alg.startAlgorithm(graph);
		*/
		
		//alg = new GraphAlgorithmContext(new CounterMinimize(1000000));
		//alg.startAlgorithm(graph);
		
		
		
		//alg = new GraphAlgorithmContext(new SimulatedAnneling(0.5, 8000, 100, 0.7, 0.9999, "ABWSTART", "SWAPSTART"));
		//alg.startAlgorithm(graph);
		/*
		int cnt = 0;
		Set<Integer> errors = new HashSet<Integer>();
		for(int i = 0 ; i < graph.getNodeSize() ; ++i ){
			if ( graph.getNodeError(i) != 0.0 && graph.getNodeColorable(i)){
				cnt += 1;
				errors.add(graph.getNodeId(i));
				for(int j = 0 ; j < graph.getNodeDegre(i) ; ++j){
					errors.add(graph.getNodeNeighburId(i, j));
				}
			}
		}
		
		System.out.format("Percent: %f\n", (double)errors.size()/graph.getNodeSize());
		
		//alg = new GraphAlgorithmContext(new SimulatedAnneling(0.2, 5000, 100, 0.7, 0.9999, "ABW", "SWAP"));
		//alg.startAlgorithm(graph, errors);
		
		
		//alg = new GraphAlgorithmContext(new GeneticAlgorithm(5, 7, 31000, 150, 0.8,  "MF", "SWAP"));
		//alg.startAlgorithm(graph);
						
		
		//alg = new GraphAlgorithmContext(new SimulatedAnneling(0.3, 10000, 100, 0.7, 0.9999, "ABWSTART", "SWAPSTART"));
		//alg.startAlgorithm(graph);
		
		
		
		/*
		GraphAlgorithmContext swap = new GraphAlgorithmContext(new Greedy("SDO", "SWAP", 1));
		alg = new GraphAlgorithmContext(new SimulatedAnneling(0.5, 5000, 100, 0.7, 0.9999, "ABW", "SWAP"));
		int k = 5;
		while(k == 5){
			alg.startAlgorithm(graph);
			swap.startAlgorithm(graph);
		}
		*/
		
		//alg = new GraphAlgorithmContext(new GeneticAlgorithm(5, 7, 31000, 150, 0.8,  "MF", "SWAP"));
		//alg.startAlgorithm(graph);
		
		
		//alg = new GraphAlgorithmContext(new AgentAlgorithm(graph.getNodeSize(), 1000, "MF"));
		//alg.startAlgorithm(graph);

		
		//alg = new GraphAlgorithmContext(new GeneticAnneling(10000000, 5, 1.0, 0.7));
		//alg.startAlgorithm(graph); 
		
		//int a = 5;
		//while(a==5){
		
		
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
		
		
	}
}
