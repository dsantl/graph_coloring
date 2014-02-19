package graph_coloring.structure;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class EricssonGraph extends Graph{
	private Map<Integer, ColorClass> colorClasses = new HashMap<Integer, ColorClass>();
	
	private double graphError;
	private int colorableNodes = 0;
	private int changeColorNodes = 0;
	
	@Override
	public void addNode(Node node){
		super.addNode(node);
		EricssonNode eNode = (EricssonNode) node;
		if ( eNode.getColorable() )
			colorableNodes += 1;
	}
	
	public EricssonGraph(EricssonGraph graph){
		super(graph);
		
		for(Integer node : graph.getNodeIndices()){
			Node newNode = new EricssonNode((EricssonNode)graph.getNode(node));
			this.nodeRepos.put(node, newNode);
		}
		
		graphError = graph.getError();
		this.colorClasses = graph.colorClasses;
	}
	
	public void setError(){
		this.graphError = 0;
		this.changeColorNodes = 0;
		for(Integer node : this.getNodeIndices()){
			EricssonNode eNode = (EricssonNode) this.getNode(node);
			double error = this.getNodeError(node);
			graphError += error;
			if ( eNode.getColorable() && eNode.getColor() != eNode.getStartColor() )
				this.changeColorNodes += 1;
		}
		
		this.graphError /= 2.0;
	}
	
	public double getColorChange(){
		return ((double)this.changeColorNodes)/this.colorableNodes;
	}
	
	public EricssonGraph(){
		setError();
	}
	
	public double getError(){
		return graphError;
	}
	
	public double getNodeError(int node){
		
		double error = 0;
		EricssonNode eNode = (EricssonNode) this.getNode(node);
		int len = eNode.getNeighborsSize();
		
		for(int i = 0 ; i < len ; ++i){
			int neighbour = this.getNode(node).getNeighbor(i);
			EricssonNode eNeighbour = (EricssonNode) this.getNode(neighbour);
			if ( eNeighbour.getColorable() || eNode.getColorable() )
				if ( eNeighbour.getColor() == eNode.getColor() )
					error += ((EricssonBridge)this.getBridge(node, neighbour)).getWeight();
		}
		return error;
	}
	
	public double getNodeError(int node, int color){
		
		double error = 0;
		EricssonNode eNode = (EricssonNode) this.getNode(node);
		int len = eNode.getNeighborsSize();
		
		for(int i = 0 ; i < len ; ++i){
			int neighbour = this.getNode(node).getNeighbor(i);
			EricssonNode eNeighbour = (EricssonNode) this.getNode(neighbour);
			if ( eNeighbour.getColorable() || eNode.getColorable() )
				if ( eNeighbour.getColor() == color )
					error += ((EricssonBridge)this.getBridge(node, neighbour)).getWeight();
		}
		return error;
	}
	
	@Override
	public void addBridge(Bridge bridge){
		if (bridgeRepos.containsKey(bridge.getPair()))
		{
			return;
		}
		super.addBridge(bridge);
	}

	
	public boolean setNodeColor(int node, int color){
		
		EricssonNode eNode = (EricssonNode)this.getNode(node);
		
		int colorClass = eNode.getColorClass();
		List<Integer> colorSet = this.getAllColorsOfClass(colorClass);
		if ( !colorSet.contains(color) ||  !eNode.getColorable() )
			return false;
		
		double oldError = this.getNodeError(node);
		int oldColor = this.getNode(node).getColor();
		
		eNode.setColor(color);
		double newError = this.getNodeError(node);
		
		this.graphError += newError - oldError;
		
		int changeTmp = 0;
		
		if ( oldColor == eNode.getStartColor() && color != eNode.getStartColor() )
			changeTmp = 1;
		else if ( oldColor != eNode.getStartColor() && color == eNode.getStartColor() )
			changeTmp = -1;
		
		this.changeColorNodes += changeTmp;
				
		return true;
	}
	
	public void addNewColorClass(int classId, ColorClass colorClass) {
		colorClasses.put(classId, colorClass);
	}

	public boolean isColorOfNodeValid(Integer i) {
		EricssonNode node = (EricssonNode) this.getNode(i);
		int nodeColor = node.getColor();
		int nodeColorClass = node.getColorClass();
		
		if ( node.getColorable() == true && colorClasses.containsKey(nodeColorClass) )
			if ( !colorClasses.get(nodeColorClass).containsColor(nodeColor) )
				return false;
		
		return true;
	}

	public boolean colorClassContainColor(int colorClass, int color){
		
		if ( colorClasses.containsKey(colorClass ) )
			if ( colorClasses.get(colorClass).containsColor(color) )
				return true;
		
			return false;
	}
	
	public ColorClass getColorClass(int id){
		return colorClasses.get(id);
	}
	
	public int getColorClassSize() {
		return colorClasses.size(); 
	}

	public Set<Integer> getColorClasses() {
		return colorClasses.keySet();
	}

	public int getColorCount(Integer colorClass) {
		return colorClasses.get(colorClass).getColorCount();
	}

	public List<Integer> getAllColorsOfClass(Integer colorClassIndex) {
		ColorClass colorClass = colorClasses.get(colorClassIndex);
		return colorClass.getAllColors();
	}

	public Set<Entry<Integer, ColorClass>> getColorClasssEntrySet() {
		return colorClasses.entrySet();
	}

	public List<Node> getColorableNodes(){
		List<Node> ret = new ArrayList<Node>();
		for(Node node : nodeRepos.values()){
			EricssonNode eNode = (EricssonNode) node;
			if (eNode.getColorable())
				ret.add(node);
		}
		return ret;
	}
	
	public List<Integer> getColorableNodesId(){
		List<Integer> ret = new ArrayList<Integer>();
		for(Node node : nodeRepos.values()){
			EricssonNode eNode = (EricssonNode) node;
			if (eNode.getColorable())
				ret.add(eNode.getId());
		}
		return ret;
	}

	public void reset(double oldError) {
		for(Integer node : this.getNodeIndices() ){
			EricssonNode eNode = (EricssonNode) nodeRepos.get(node);
			eNode.setColor(eNode.getStartColor());
		}
		this.graphError = oldError;
	}
	
}
