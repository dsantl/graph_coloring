package graph_coloring.order;

import graph_coloring.structure.ElementProperty;
import graph_coloring.structure.weight_graph.WeightNode;

import java.util.List;

public class OrderNodeCOL extends OrderMethod{
	@Override
	public int compare(ElementProperty elem1, ElementProperty elem2) {
		return (Integer)elem2.getProperty() - (Integer)elem1.getProperty();
	}

	@Override
	protected void init(List<? extends ElementProperty> list) {
		for(ElementProperty element : list){
			element.addProperty(((WeightNode)element).getCollision());
		}
	}
}
