package graph_coloring.testing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import graph_coloring.algorithm.GraphAlgorithmContext;
import graph_coloring.algorithm.unit.GeneralUnit;
import graph_coloring.algorithmset.greedy.CombiGreedy;
import graph_coloring.algorithmset.simulated_annealing.SimulatedAnneling;
import graph_coloring.input.FERFileFormat;
import graph_coloring.input.FileFormat;
import graph_coloring.stat.ChangeColorGlobal;
import graph_coloring.stat.CheckValidColoring;
import graph_coloring.stat.ErrorFunctionEricsson;
import graph_coloring.stat.GraphFileFinder;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonGraph;

public class SimulatedAnnealingTest{
	
	private static void testGraph(String fileName, GraphAlgorithmContext alg){
		FileFormat fileFormat = new FERFileFormat();
		EricssonGraph graph = null;
		String[] splitName = fileName.split("/");
		String graphName = splitName[splitName.length-1]; 
		
		try {
			graph = (EricssonGraph) fileFormat.getGraphFromFile(fileName);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.err.println("START");
		GraphAlgorithmContext greedy = new GraphAlgorithmContext(new CombiGreedy(5));
		greedy.startAlgorithm(graph);
		
		double oldError =  ErrorFunctionEricsson.computeStat(graph);
		
		try {
			System.setOut(new PrintStream(new File("/home/dino/Desktop/sa/simulated_annealing_test_"+graphName)));
		} catch (Exception e) {
		     e.printStackTrace();
		}
		
		long start = System.currentTimeMillis();
		
		alg.startAlgorithm(graph);
		
		long end = System.currentTimeMillis() - start;
		
		System.out.format("STAT\n");
		System.out.format("Graph: %s\n", graphName);
		System.out.format("Old error: %f\n", oldError);
		System.out.format("New error: %f\n", ErrorFunctionEricsson.computeStat(graph));
		System.out.format("Color change: %f\n", ChangeColorGlobal.computeStat(graph));
		System.out.format("Time: %f s\n", (double)end/1000);
		System.out.format("Valid coloring: %b\n", CheckValidColoring.computeStat(graph));
		System.err.println("DONE!");
	}
	
	public static void start(String path){
		
		List<String> fileNames = GraphFileFinder.find(path);
		GraphAlgorithmContext alg = new GraphAlgorithmContext(new SimulatedAnneling(0.5, 10000, 100, 0.7, 0.9999, "ABW", "SWAP"));
		
		for(String name : fileNames){
			testGraph(name, alg);
		}	
	}	
}


