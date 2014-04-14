package graph_coloring.color_selector;

import java.util.Iterator;

import graph_coloring.structure.Node;

public interface ColorSelector {
	
	/**
	 * Choose color for node
	 * @param node Node for coloring
	 * @param colors Available colors for node
	 * @return new color
	 */
	public int selectColor(Node node, Iterator<Integer> colors);
	
	/**
	 * Set parameter to color selector (if he needed)
	 * @param param parameter
	 * @return 1 if error appears else 0
	 */
	public int setParam(Object param);
}
