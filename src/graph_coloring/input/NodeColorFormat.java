package graph_coloring.input;

import graph_coloring.structure.Graph;

import java.io.FileNotFoundException;
import java.io.IOException;

public class NodeColorFormat {
	
	static public void setColorsFromFileToGraph(String fileName, Graph graph)
			throws FileNotFoundException, NumberFormatException, IOException {
		
		InputFile file = new InputFile(fileName);
		
		String text = file.getNextLine();
		
		while(text != null){
			String[] splitText = text.split(" ");
			
			if ( splitText.length != 2 )
				continue;
			
			int nodeId = Integer.parseInt(splitText[0]);
			int color = Integer.parseInt(splitText[1]);
			int nodeIndex = graph.getNodeIndex(nodeId);
			
			graph.setNodeColor(nodeIndex, color);
			text = file.getNextLine();
		}
		
	}

}
