package graph_coloring.stat;

import graph_coloring.structure.EricssonGraph;
import graph_coloring.structure.EricssonNode;

public class ChangeColorValid {

		public static double computeStat(EricssonGraph graph) {
			
			int counter = 0;
			
			for(Integer nodeId : graph.getNodeIndices()){
				EricssonNode eNode = (EricssonNode) graph.getNode(nodeId);
				if ( graph.colorClassContainColor(eNode.getColorClass(), eNode.getStartColor()) )
					if ( eNode.getColor() != eNode.getStartColor() )
						counter += 1;
			}
			return ((double)counter)/graph.getNodeSize();
		}
}
