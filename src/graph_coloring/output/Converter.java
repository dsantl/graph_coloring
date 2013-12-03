package graph_coloring.output;

import graph_coloring.structure.Graph;

public interface Converter {

	public void convertGraphToFile(Graph graph, String fileName);
	
}
