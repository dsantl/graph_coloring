package graph_coloring.input;

import graph_coloring.structure.Graph;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonGraph;

import java.io.IOException;


public class FERFileFormat implements FileFormat{

	@Override
	public Graph getGraphFromFile(String fileName) throws NumberFormatException, IOException {
		
		EricssonGraph graph = new EricssonGraph();
		
		InputFile file = new InputFile(fileName);
		
		int numberOfColorClasses = Integer.parseInt(file.getNextLine());
		
		for(int i = 0 ; i < numberOfColorClasses ; ++i){
			int classId = Integer.parseInt(file.getNextLine());
			graph.addColorClass(classId);
			
			int numberOfColors = Integer.parseInt(file.getNextLine());
			for(int j = 0 ; j < numberOfColors ; ++j){
				int color = Integer.parseInt(file.getNextLine());
				graph.addColorToColorClass(classId, color);
			}
		}
		
		int numberOfNodes = Integer.parseInt(file.getNextLine());
		
		for(int i = 0 ; i < numberOfNodes ; ++i){
			int nodeId = Integer.parseInt(file.getNextLine());
			int startColor = Integer.parseInt(file.getNextLine());
			int domainName = Integer.parseInt(file.getNextLine());
			char nodeGroup = file.getNextLine().charAt(0);
			boolean colorable = Boolean.parseBoolean(file.getNextLine());
			graph.addEricssonNode(nodeId, nodeGroup, domainName, startColor, colorable);
		}
		
		int numberOfBridges = Integer.parseInt(file.getNextLine());
		
		for(int i = 0 ; i < numberOfBridges ; ++i){
			int node1 = Integer.parseInt(file.getNextLine());
			int node2 = Integer.parseInt(file.getNextLine());
			double weight = Double.parseDouble(file.getNextLine());
			graph.addWeightBridge(node1, node2, weight);
		}
		
		return graph;
	}

}
