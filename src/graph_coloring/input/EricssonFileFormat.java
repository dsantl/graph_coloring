package graph_coloring.input;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import graph_coloring.common.OrderPair;
import graph_coloring.structure.Graph;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonGraph;

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

			graph.addColorClass(colorClassId);
			for (int j = 1; j < splitLine.length; ++j) {
				graph.addColorToColorClass(colorClassId, Integer.parseInt(splitLine[j]));
			}
		}

	}

	private void loadNodes(InputFile file, EricssonGraph graph) throws IOException {
		
		int numberOfNodes = 0;
		
		file.getNextLine();

		numberOfNodes = Integer.parseInt(file.getNextLine());

		for (int i = 0; i < numberOfNodes; ++i) {
			String line = file.getNextLine();
			int id, startColor, nodeDomain;
			char nodeGroup;
			boolean colorable;
			String[] splitLine = line.split("\\s+");
			id = Integer.parseInt(splitLine[0]);
			colorable = Boolean.parseBoolean(splitLine[4]);
			nodeGroup = splitLine[1].charAt(0);
			startColor = Integer.parseInt(splitLine[3]);
			if (colorable)
				nodeDomain = Integer.parseInt(splitLine[2]);
			else
				nodeDomain = -1;
			
			graph.addEricssonNode(id, nodeGroup, nodeDomain, startColor, colorable); 
		}

	}

	private void loadBridges(InputFile file, EricssonGraph graph) throws IOException {
		
		int numberOfBridges = 0;
		
		file.getNextLine();
		
		Set<OrderPair> bridgeFlag = new HashSet<OrderPair>();
		
		numberOfBridges = Integer.parseInt(file.getNextLine());
		
		for (int i = 0; i < numberOfBridges; ++i) {
			System.out.format("%d\n", numberOfBridges - i - 1);
			String line = file.getNextLine();
			String[] splitLine = line.split("\\s+");
			int node1, node2;
			double weight;

			node1 = Integer.parseInt(splitLine[0]);
			node2 = Integer.parseInt(splitLine[1]);
			weight = Double.parseDouble(splitLine[2]);
			
			if ( bridgeFlag.contains(new OrderPair(node1, node2))) {
				bridgeFlag.remove(new OrderPair(node1, node2));
				continue;
			}
			
			bridgeFlag.add(new OrderPair(node1, node2));
			
			graph.addWeightBridge(node1, node2, weight);
		}

	}

	@Override
	public Graph getGraphFromFile(String fileName) throws NumberFormatException, IOException {

		EricssonGraph retGraph = new EricssonGraph();

		InputFile file = new InputFile(fileName);
		
		this.loadColorClasses(file, retGraph);
		this.loadNodes(file, retGraph);
		this.loadBridges(file, retGraph);
		
		return retGraph;
	}

}
