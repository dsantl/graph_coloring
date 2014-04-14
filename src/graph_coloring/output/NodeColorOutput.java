package graph_coloring.output;

import graph_coloring.structure.Graph;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class NodeColorOutput implements Converter{
	
	@Override
	public void convertGraphToFile(Graph g, String fileName) throws FileNotFoundException, UnsupportedEncodingException{
		
		PrintWriter outFile = new PrintWriter(fileName, "UTF-8");

		for(int i = 0 ; i < g.getNodeSize() ; ++i){
			outFile.format("%d %d\n", g.getNodeId(i), g.getNodeColor(i));
		}
		
		outFile.close();

	}
	
}
