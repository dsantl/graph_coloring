package graph_coloring.input;

import java.io.FileNotFoundException;
import java.io.IOException;

import graph_coloring.structure.Graph;

public interface FileFormat {
	
	public Graph getGraphFromFile(String fileName) throws FileNotFoundException, NumberFormatException, IOException;
	
}
