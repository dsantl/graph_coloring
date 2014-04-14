package graph_coloring.order;

import graph_coloring.structure.ElementProperty;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public abstract class OrderMethod  implements Comparator<ElementProperty>{
	
	/**
	 * Make order in graph
	 * @param nodeProp list of ElementProperty (Node)
	 */
	public void makeOrder(List<? extends ElementProperty> nodeProp){
		init(nodeProp);
		sortElements(nodeProp);
	}
	
	/**
	 * Init ElementProperty (for Node)
	 * @param list List of ElementProperty
	 */
	protected abstract void init(List<? extends ElementProperty> list);
	
	/**
	 * Sort elements by property
	 * @param list
	 */
	protected void sortElements(List<? extends ElementProperty> list){
		Collections.sort(list, this);
	}
}
