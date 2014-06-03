package graph_coloring.testing;

import graph_coloring.algorithm.GraphAlgorithmContext;
import graph_coloring.algorithm.unit.GeneralUnit;
import graph_coloring.algorithmset.RandomAlgorithm;
import graph_coloring.input.FERFileFormat;
import graph_coloring.input.FileFormat;
import graph_coloring.stat.ErrorFunctionEricsson;
import graph_coloring.stat.GraphFileFinder;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class RandomTest {
	
	private static void testGraph(String fileName, GraphAlgorithmContext alg){
		FileFormat fileFormat = new FERFileFormat();
		EricssonGraph graph = null;
		
		try {
			graph = (EricssonGraph) fileFormat.getGraphFromFile(fileName);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		GeneralUnit unit = new GeneralUnit(graph);
		double startError = ErrorFunctionEricsson.computeStat(graph);
		double avg = 0;
		List<Double> errors = new ArrayList<Double>();
		String[] splitName = fileName.split("/");
		String graphName = splitName[splitName.length-1]; 
		
		System.out.format("%s & ", graphName);
		System.out.format("%d & ", graph.getNodeSize());
		System.out.format("%d & ", graph.getBridgeSize());
		System.out.format("%.2e &", startError);
		int iter = 10;
		for(int i = 0 ; i < iter ; ++i){
			alg.startAlgorithm(graph);
			double tmpError = ErrorFunctionEricsson.computeStat(graph);
			avg += tmpError;
			errors.add(tmpError);
			unit.updateGraph();
		}
		System.out.format("%.2e \\\\ \\hline \n", avg/iter);
		System.gc();
	}
	
	public static void start(String path){
		
		try {
			System.setOut(new PrintStream(new File("/home/dino/Desktop/random-test.txt")));
		} catch (Exception e) {
		     e.printStackTrace();
		}
		
		
		GraphAlgorithmContext alg = new GraphAlgorithmContext(new RandomAlgorithm());
		
		List<String> fileNames = GraphFileFinder.find(path);
		
		for(String name : fileNames){
			testGraph(name, alg);
		}
		
	}
	
}
