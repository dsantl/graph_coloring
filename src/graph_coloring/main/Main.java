package graph_coloring.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import graph_coloring.algorithm.GraphAlgorithmContext;
import graph_coloring.algorithmset.MinimalNeighbourWeight;
import graph_coloring.algorithmset.RandomAlgorithm;
import graph_coloring.input.EricssonFileFormat;
import graph_coloring.input.FERFileFormat;
import graph_coloring.input.FileFormat;
import graph_coloring.output.FERoutput;
import graph_coloring.stat.ComputeStatistics;
import graph_coloring.stat.ErrorFunctionEricsson;
import graph_coloring.structure.Graph;

public class Main {

	public static void main(String[] args) {
		
		//FileFormat fileFormat = new EricssonFileFormat();
		FileFormat fileFormat = new FERFileFormat();
		
		Graph graph = null;
		long start = System.currentTimeMillis();
		
		try {
			//graph = fileFormat.getGraphFromFile("/home/dino/Desktop/diplomski/FER-Tokai.txt");
			//graph = fileFormat.getGraphFromFile("/home/dino/Desktop/FER-Tokai_coloring.txt");
			
			//graph = fileFormat.getGraphFromFile("Tokai-new.out");
			graph = fileFormat.getGraphFromFile("Tokai.out");
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
		
		ComputeStatistics stat = new ErrorFunctionEricsson();
		double oldError = stat.computeStat(graph);
		
		GraphAlgorithmContext algorithm = new GraphAlgorithmContext(new MinimalNeighbourWeight());
		algorithm.startAlgorithm(null, graph);
		
		System.out.format("Old error: %f\n", oldError);		
		System.out.format("New error: %f\n", stat.computeStat(graph));		
		
		long end = System.currentTimeMillis();
		System.out.println(end-start);

	}
}
