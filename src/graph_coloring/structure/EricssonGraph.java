package graph_coloring.structure;


import java.util.HashMap;
import java.util.Map;

public class EricssonGraph extends Graph{
	public Map<Integer, ColorClass> colorClasses = new HashMap<Integer, ColorClass>();

	@Override
	public void addBridge(Bridge bridge){
		if (bridgeRepos.containsKey(bridge.getPair()))
		{
			return;
		}
		super.addBridge(bridge);
	}
	
}
