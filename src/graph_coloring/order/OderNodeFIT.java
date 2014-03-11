package graph_coloring.order;

import graph_coloring.structure.ElementProperty;
import graph_coloring.structure.Node;

import java.util.List;

public class OderNodeFIT extends OrderMethod{

	@Override
	public int compare(ElementProperty elem1, ElementProperty elem2) {
		
		double prop1 = (Double)elem1.getProperty();
		double prop2 = (Double)elem2.getProperty();
		
		return -Double.compare(prop1, prop2);
	}

	@Override
	protected void init(List<? extends ElementProperty> list) {
		for(ElementProperty element : list){
			element.addProperty(((EricssonNode)element).getNodeError());
		}
	}

}
