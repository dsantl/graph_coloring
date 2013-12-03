package graph_coloring.input;

import graph_coloring.structure.Graph;

public interface FileFormat {
	
	public Graph getGraphFromFile(String fileName);
	
}
