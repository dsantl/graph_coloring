package graph_coloring.order;

import graph_coloring.structure.ElementProperty;

import java.util.List;
import java.util.Random;

public class OrderNodeRND extends OrderMethod{
	
	Random rnd = new Random();
	
	@Override
	public int compare(ElementProperty elem1, ElementProperty elem2) {
		return (Integer)elem2.getProperty() - (Integer)elem1.getProperty();
	}

	@Override
	protected void init(List<? extends ElementProperty> list) {
		for(ElementProperty element : list){
			element.addProperty(rnd.nextInt());
		}
	}
}
