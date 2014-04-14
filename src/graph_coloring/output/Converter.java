package graph_coloring.output;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import graph_coloring.structure.Graph;

public interface Converter {
	
	/**
	 * Save graph as file
	 * @param g Graph
	 * @param fileName File name
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public void convertGraphToFile(Graph g, String fileName) throws FileNotFoundException, UnsupportedEncodingException;	
}
