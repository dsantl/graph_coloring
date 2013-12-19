package graph_coloring.main;

import java.io.FileNotFoundException;
import java.io.IOException;

import graph_coloring.input.EricssonFileFormat;
import graph_coloring.input.FileFormat;
import graph_coloring.stat.ComputeStatistics;
import graph_coloring.stat.ErrorFunctionEricsson;
import graph_coloring.structure.Graph;

public class Main {

	public static void main(String[] args) {
		
		FileFormat fileFormat = new EricssonFileFormat();
		Graph graph = null;
		try {
			graph = fileFormat.getGraphFromFile("/home/dino/Desktop/diplomski/FER-Kansai.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ComputeStatistics stat = new ErrorFunctionEricsson();
		
		System.out.println(stat.computeStat(graph));
		
	}
}
