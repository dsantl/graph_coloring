package graph_coloring.order;

import graph_coloring.structure.ElementProperty;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public abstract class OrderMethod  implements Comparator<ElementProperty>{
	
	public void makeOrder(List<? extends ElementProperty> nodeProp){
		init(nodeProp);
		sortElements(nodeProp);
	}
	
	
	protected abstract void init(List<? extends ElementProperty> list);
	
	protected void sortElements(List<? extends ElementProperty> list){
		Collections.sort(list, this);
	}
}
