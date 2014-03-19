package graph_coloring.color_selector;

import java.util.Iterator;

import graph_coloring.structure.Node;

public interface ColorSelector {
	
	public int selectColor(Node node, Iterator<Integer> colors);
	public int setParam(Object param);
}
