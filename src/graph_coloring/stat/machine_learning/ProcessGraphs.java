package graph_coloring.stat.machine_learning;

import graph_coloring.common.Pair;
import graph_coloring.input.FERFileFormat;
import graph_coloring.input.FileFormat;
import graph_coloring.output.GreedyStatOutput;
import graph_coloring.stat.GraphFileFinder;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonGraph;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class ProcessGraphs {

	private static void ComputeForGraph(String fileName, int patterns, double best, String colorSelector){
		
		FileFormat fileFormat = new FERFileFormat();
		EricssonGraph graph = null;
		String[] splitName = fileName.split("/");
		String graphName = splitName[splitName.length-1]+".in"; 
		
		try {
			graph = (EricssonGraph) fileFormat.getGraphFromFile(fileName);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List<Pair<Double, GreedyDataNode>> list = NodeOrder.generateData(graph, patterns, best, colorSelector);
		
		GreedyStatOutput out = new GreedyStatOutput();
		
		try {
			out.saveStatistics(list, "/home/dino/Desktop/ml/"+graphName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}
	
	public static void SaveStat(String dirName, int patterns, double best, String colorSelector){
		
		List<String> fileNames = GraphFileFinder.find(dirName);
		
		for(String name : fileNames){
			ComputeForGraph(name, patterns, best, colorSelector);
		}
		
	}
	
}
