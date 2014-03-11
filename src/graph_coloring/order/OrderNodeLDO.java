package graph_coloring.order;

import graph_coloring.structure.ElementProperty;
import graph_coloring.structure.Node;

import java.util.List;

public class OrderNodeLDO extends OrderMethod{
	
	@Override
	public int compare(ElementProperty o1, ElementProperty o2) {
		return (Integer)o2.getProperty() - (Integer)o1.getProperty();
	}
	
	@Override
	protected void init(List<? extends ElementProperty> list) {
		for(ElementProperty element : list){
			element.addProperty(((Node)element).getBridgeSize());
		}
	}
}
