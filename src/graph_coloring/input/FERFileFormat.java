package graph_coloring.input;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import graph_coloring.structure.Bridge;
import graph_coloring.structure.ColorClass;
import graph_coloring.structure.EricssonBridge;
import graph_coloring.structure.EricssonGraph;
import graph_coloring.structure.EricssonNode;
import graph_coloring.structure.Graph;
import graph_coloring.structure.Node;

public class FERFileFormat implements FileFormat{

	@Override
	public Graph getGraphFromFile(String fileName) throws NumberFormatException, IOException {
		
		EricssonGraph graph = new EricssonGraph();
		
		InputFile file = new InputFile(fileName);
		
		int numberOfColorClasses = Integer.parseInt(file.getNextLine());
		
		for(int i = 0 ; i < numberOfColorClasses ; ++i){
			ColorClass colorClass = new ColorClass();
			int classId = Integer.parseInt(file.getNextLine());
			int numberOfColors = Integer.parseInt(file.getNextLine());
			for(int j = 0 ; j < numberOfColors ; ++j){
				int color = Integer.parseInt(file.getNextLine());
				colorClass.addColor(color);
			}
			graph.addNewColorClass(classId, colorClass);
		}
		
		int numberOfNodes = Integer.parseInt(file.getNextLine());
		
		for(int i = 0 ; i < numberOfNodes ; ++i){
			int nodeId = Integer.parseInt(file.getNextLine());
			int startColor = Integer.parseInt(file.getNextLine());
			int colorClass = Integer.parseInt(file.getNextLine());
			char nodeClass = file.getNextLine().charAt(0);
			boolean colorable = Boolean.parseBoolean(file.getNextLine());
			int color = startColor;
			
			Node node = new EricssonNode(nodeId, color, startColor, colorClass, nodeClass, colorable);
			graph.addNode(node);
		}
		
		int numberOfBridges = Integer.parseInt(file.getNextLine());
		
		for(int i = 0 ; i < numberOfBridges ; ++i){
			int node1 = Integer.parseInt(file.getNextLine());
			int node2 = Integer.parseInt(file.getNextLine());
			double weight = Double.parseDouble(file.getNextLine());
			Bridge bridge = new EricssonBridge(node1, node2, weight);
			graph.addBridge(bridge);
		}
		
		graph.setError();
		return graph;
	}

}
