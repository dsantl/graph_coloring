package graph_coloring.order;

import graph_coloring.structure.ElementProperty;
import graph_coloring.structure.Node;

import java.util.List;

public class OrderNodeSDO extends OrderMethod{
		
	@Override
	public int compare(ElementProperty elem1, ElementProperty elem2) {
		return (Integer)elem2.getProperty() - (Integer)elem1.getProperty();
	}

	@Override
	protected void init(List<? extends ElementProperty> list) {
		for(ElementProperty element : list){
			element.addProperty(((Node)element).getSaturation());
		}
	}
}
