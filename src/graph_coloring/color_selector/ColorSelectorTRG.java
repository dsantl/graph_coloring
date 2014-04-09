package graph_coloring.color_selector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import graph_coloring.structure.Node;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonNode;

public class ColorSelectorTRG implements ColorSelector{

	double targetError = 0.0;
	Random rnd = new Random();
	boolean paramSet = false;
	
	@Override
	public int selectColor(Node node, Iterator<Integer> colors) {
		
		if (paramSet == false){
			try {
				throw new Exception("Param for TRG is not set!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		EricssonNode eNode = (EricssonNode)node;
		int memColor = node.getColor();
		
		if (eNode.getColorable() == false)
			return eNode.getColor();
		
		List<Integer> trgColors = new ArrayList<Integer>();
		while(colors.hasNext()){
			int currColor = colors.next();
			double err = eNode.getError(currColor);
			
			if ( err <= this.targetError ){
				trgColors.add(currColor);
			}
		}
		
		if ( trgColors.size() == 0 )
			return memColor;
		
		if ( trgColors.contains(eNode.getStartColor()) )
			return eNode.getStartColor();
		
		return trgColors.get(rnd.nextInt(trgColors.size()));
	}

	@Override
	public int setParam(Object param) {
		this.paramSet = true;
		this.targetError = (Double) param;
		return 0;
	}

}
