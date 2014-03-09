package graph_coloring.structure.weight_graph.ericsson_graph;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ColorClass {
	
	//set of class colors
	private Set<Integer> colors = new HashSet<Integer>();
	
	/**
	 * Add color to color class
	 * @param color
	 */
	public void addColor(int color){
		colors.add(color);
	}
	
	/**
	 * Get iterator of colors
	 * @return Integer iterator
	 */
	public Iterator<Integer> getIterator(){
		return colors.iterator();
	}
	
	
	/**
	 * Check if color class contain color
	 * @param color
	 * @return true if color class contains color else return false
	 */
	public boolean contains(int color){
		return colors.contains(color);
	}
	
	/**
	 * Get number of colors
	 * @return number of colors in color class
	 */
	public int getSize(){
		return colors.size();
	}
	
}
