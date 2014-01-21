package graph_coloring.input;

import java.io.IOException;
import java.util.Iterator;

import graph_coloring.structure.Bridge;
import graph_coloring.structure.ColorClass;
import graph_coloring.structure.EricssonBridge;
import graph_coloring.structure.EricssonGraph;
import graph_coloring.structure.EricssonNode;
import graph_coloring.structure.Graph;
import graph_coloring.structure.Node;

public class EricssonFileFormat implements FileFormat {

	private void loadColorClasses(InputFile file, EricssonGraph graph) throws IOException {
		
		int numberOfColorSets = 0;
		
		numberOfColorSets = Integer.parseInt(file.getNextLine());

		for (int i = 0; i < numberOfColorSets; ++i) {
			String line = file.getNextLine();
			int colorClassId;
			line = line.replaceFirst(" = ", " ");

			String[] splitLine = line.split(" ");

			colorClassId = Integer.parseInt(splitLine[0]);

			ColorClass colorClass = new ColorClass();
			for (int j = 1; j < splitLine.length; ++j) {
				colorClass.addColor(Integer.parseInt(splitLine[j]));
			}
			graph.addNewColorClass(colorClassId, colorClass);
		}

	}

	private void loadNodes(InputFile file, EricssonGraph graph) throws IOException {
		
		int numberOfNodes = 0;
		
		file.getNextLine();

		numberOfNodes = Integer.parseInt(file.getNextLine());

		for (int i = 0; i < numberOfNodes; ++i) {
			String line = file.getNextLine();
			int id, color = -1, startColor = -1, colorClass = -1;
			char nodeClass;
			boolean colorable;
			String[] splitLine = line.split("\\s+");
			id = Integer.parseInt(splitLine[0]);
			colorable = Boolean.parseBoolean(splitLine[4]);
			nodeClass = splitLine[1].charAt(0);
			startColor = Integer.parseInt(splitLine[3]);
			color = startColor;
			colorClass = Integer.parseInt(splitLine[2]);
			
			Node node = new EricssonNode(id, color, startColor, colorClass,
										 nodeClass, colorable);
			graph.addNode(node);
		}

	}

	private void loadBridges(InputFile file, EricssonGraph graph) throws IOException {
		
		int numberOfBridges = 0;

		file.getNextLine();

		numberOfBridges = Integer.parseInt(file.getNextLine());

		for (int i = 0; i < numberOfBridges; ++i) {
			String line = file.getNextLine();
			String[] splitLine = line.split("\\s+");
			int node1, node2;
			double weight;

			node1 = Integer.parseInt(splitLine[0]);
			node2 = Integer.parseInt(splitLine[1]);
			weight = Double.parseDouble(splitLine[2]);
			Bridge bridge = new EricssonBridge(node1, node2, weight);
			graph.addBridge(bridge);
		}

	}

	@Override
	public Graph getGraphFromFile(String fileName)
			throws NumberFormatException, IOException {

		EricssonGraph retGraph = new EricssonGraph();

		InputFile file = new InputFile(fileName);
		
		this.loadColorClasses(file, retGraph);
		this.loadNodes(file, retGraph);
		this.loadBridges(file, retGraph);
		
		retGraph.setError();
		
		return retGraph;
	}

}
