package graph_coloring.color_selector;

import graph_coloring.structure.Node;

public interface ColorSelector {
	
	public int selectColor(Node node, Object param);
	
}
