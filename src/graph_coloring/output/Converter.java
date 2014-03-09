package graph_coloring.output;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import graph_coloring.structure.Graph;

public interface Converter {

	public void convertGraphToFile(Graph g, String fileName) throws FileNotFoundException, UnsupportedEncodingException;	
}
