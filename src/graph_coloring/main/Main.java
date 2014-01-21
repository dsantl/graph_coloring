package graph_coloring.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

import graph_coloring.algorithm.GraphAlgorithmContext;
import graph_coloring.algorithm.GraphColoringAlgorithm;
import graph_coloring.algorithmset.GreedyAlgorithm;
import graph_coloring.algorithmset.MinimalNeighbourWeight;
import graph_coloring.algorithmset.RandomAlgorithm;
import graph_coloring.algorithmset.TabuAlgorithm;
import graph_coloring.algorithmset.genetic.HybridGenetic;
import graph_coloring.algorithmset.genetic.SimpleGenetic;
import graph_coloring.input.EricssonFileFormat;
import graph_coloring.input.FERFileFormat;
import graph_coloring.input.FileFormat;
import graph_coloring.output.FERoutput;
import graph_coloring.stat.ChangeColorGlobal;
import graph_coloring.stat.ChangeColorValid;
import graph_coloring.stat.CountingColors;
import graph_coloring.stat.ErrorFunctionEricsson;
import graph_coloring.structure.ColorClass;
import graph_coloring.structure.EricssonGraph;
import graph_coloring.structure.Graph;

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
		
		double oldError = ErrorFunctionEricsson.computeStat(graph);
		
		System.out.format("Old error: %f\n", oldError);		
		System.out.format("Test error: %f\n", graph.getError());
		
		/*
		GreedyAlgorithm alg = new  GreedyAlgorithm(graph);
		alg.setParameters("SDOLDO", "ABW", 1);
		
		GraphAlgorithmContext algorithm = new GraphAlgorithmContext(alg);
		algorithm.startAlgorithm(graph.getColorableNodesId(), graph);
		*/
		
		GraphAlgorithmContext algorithm = new GraphAlgorithmContext(new TabuAlgorithm(graph));
		algorithm.startAlgorithm(graph.getColorableNodesId(), graph);
		
		System.out.format("New error: %f\n", ErrorFunctionEricsson.computeStat(graph));		
		
		System.out.format("Test new error: %f\n", graph.getError());		
		System.out.format("Color change: %f\n", ChangeColorGlobal.computeStat(graph));
		System.out.format("Color change: %f\n", ChangeColorValid.computeStat(graph));
		
		long end = System.currentTimeMillis();
		System.out.println(end-start);

	}
}
