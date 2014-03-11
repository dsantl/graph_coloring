package graph_coloring.order;

import graph_coloring.common.Pair;
import graph_coloring.structure.ElementProperty;
import graph_coloring.structure.Node;

import java.util.List;

public class OrderNodeSDOLDO extends OrderMethod{

	@SuppressWarnings("unchecked")
	@Override
	public int compare(ElementProperty elem1, ElementProperty elem2) {
		Pair<Integer, Integer> prop1 = ((Pair<Integer, Integer>) elem1.getProperty());
		Pair<Integer, Integer> prop2 = ((Pair<Integer, Integer>) elem2.getProperty());
		int sdo = prop2.getFirst() - prop1.getFirst();
		if (sdo != 0)
			return sdo;
		
		return prop2.getSecond() - prop1.getSecond();
	}

	@Override
	protected void init(List<? extends ElementProperty> list) {
		for(ElementProperty element : list){
			int sdo = ((Node)element).getSaturation();
			int ldo = ((Node)element).getBridgeSize();
			element.addProperty(new Pair<Integer, Integer>(sdo, ldo));
		}
	}
}
