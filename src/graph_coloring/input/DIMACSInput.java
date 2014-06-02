package graph_coloring.input;

import graph_coloring.structure.Graph;

import java.io.FileNotFoundException;
import java.io.IOException;

public class DIMACSInput implements FileFormat{

	private void generate(Graph graph, int nodes, int bridges){
		for(int i = 1 ; i <= nodes ; ++i){
			graph.addNode(i);
		}
	}
	
	@Override
	public Graph getGraphFromFile(String fileName)
			throws FileNotFoundException, NumberFormatException, IOException {
		
		Graph retGraph = new Graph();
		InputFile file = new InputFile(fileName);
		
		String line = file.getNextLine();
		int nodes = 0;
		int bridges = 0;
		
		while(line != null){
			String[] splitLine = line.split(" ");
			
			if (line.startsWith("p")){
				nodes = Integer.parseInt(splitLine[2]);
				bridges = Integer.parseInt(splitLine[3]);
				this.generate(retGraph, nodes, bridges);
			}
			else if (line.startsWith("e")){
				int id1 = Integer.parseInt(splitLine[1]);
				int id2 = Integer.parseInt(splitLine[2]);
				retGraph.addBridge(id1, id2);
			}
			line = file.getNextLine();
		}
		
		return retGraph;
	}

	
}
