package graph_coloring.structure.weight_graph.ericsson_graph;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import graph_coloring.structure.weight_graph.WeightGraph;

public class EricssonGraph extends WeightGraph implements IEricssonGraph{
	
	//map of domain names (color class) - key is domain name, value is color class object
	private Map<Integer, ColorClass> colorClasses = new HashMap<Integer, ColorClass>();
	
	/**
	 * Get object EricssonNode
	 * @param id
	 * @return EricsonNode object
	 */
	private EricssonNode getEricssonNode(int index){
		return (EricssonNode) this.getNode(index);
	}
	
	/**
	 * Get node group
	 * @param id
	 * @return node group (A, B, C) (char)
	 */
	@Override
	public char getNodeGroup(int index){
		return this.getEricssonNode(index).getNodeGroup();
	}
	
	/**
	 * Get domain name
	 * @param id
	 * @return domain name of node (integer)
	 */
	@Override
	public int getNodeDomainName(int index){
		return this.getEricssonNode(index).getDomainName();
	}
	
	/**
	 * Get start color of node
	 * @param id
	 * @return start color (integer)
	 */
	@Override
	public int getNodeStartColor(int index){
		return this.getEricssonNode(index).getStartColor();
	}
	
	/**
	 * Get node property colorable
	 * @param id
	 * @return colorable (boolean), true if node is colorable
	 */
	@Override
	public boolean getNodeColorable(int index){
		return this.getEricssonNode(index).getColorable();
	}

	/**
	 * Check if color class contain color
	 * @param colorClassId id of color class
	 * @param color color
	 * @return if color class contain color return true else return false
	 */
	@Override
	public boolean colorClassContain(int colorClassId, int color) {
		return colorClasses.get(colorClassId).contains(color);
	}

	/**
	 * Add new color class in graph
	 * @param id Id of color class
	 */
	@Override
	public void addColorClass(int id) {
		colorClasses.put(id, new ColorClass());
	}

	/**
	 * Add color to color class
	 * @param colorClassId Id of color class
	 * @param color
	 */
	@Override
	public void addColorToColorClass(int colorClassId, int color) {
		colorClasses.get(colorClassId).addColor(color);
	}

	/**
	 * Check if node have a valid color (color in color class) or if start color is same as color for not colorable nodes
	 * @param index index of node in current state
	 * @return return true if node have valid color (color in color class)
	 */
	@Override
	public boolean checkValidColorOfNode(int index) {
		if ( this.getNodeColorable(index) ){
			
			int domainName = this.getNodeDomainName(index);
			int color = this.getNodeColor(index);
			
			if ( this.colorClassContain(domainName, color) )
				return true;
			else
				return false;
		}
		else{
			if ( this.getNodeColor(index) == this.getNodeStartColor(index) )
				return true;
			else
				return false;
		}
	}

	/**
	 * Get available colors of node, get integers in color class of node, or null if node is not colorable
	 * @param index index of node
	 * @return Iterator<Integer> where integer are colors of node, or null if node is not colorable 
	 */
	@Override
	public Iterator<Integer> getAvailableColorsOfNode(int index) {
		if ( this.getNodeColorable(index) ){
			int domainName = this.getNodeDomainName(index);
			return colorClasses.get(domainName).getIterator();
		}
		else{
			return null;
		}
	}

	/**
	 * Get number of domain names
	 * @return number of domain names (integer)
	 */
	@Override
	public int getDomainNameSize() {
		return colorClasses.size();
	}

	/**
	 * Get set of integers - domain names
	 * @return Set<Integer> of domain names
	 */
	@Override
	public Set<Integer> getDomainNames() {
		return colorClasses.keySet();
	}

	/**
	 * Get number of colors in color class
	 * @param domainName domain name (integer)
	 * @return number of colors
	 */
	@Override
	public int getNumberOfColors(int domainName) {
		return colorClasses.get(domainName).getSize();
	}

	/**
	 * Get color class iterator for colors
	 * @param domainName name of domain
	 * @return Iterator<Integer> of colors in domain (color class) 
	 */
	@Override
	public Iterator<Integer> getDomainNameIterator(int domainName) {
		return colorClasses.get(domainName).getIterator();
	}
	
	/**
	 * Add new EricssonNode to structure
	 * @param id Id of node
	 * @param nodeGroup Node group (A, B, C)
	 * @param domainName Name of domain (integer)
	 * @param startColor Start color of node (integer)
	 * @param colorable True if node is colorable
	 */
	@Override
	public void addEricssonNode(int id, char nodeGroup, int domainName, int startColor, boolean colorable) {
		EricssonNode node = new EricssonNode(id, nodeGroup, domainName, startColor, colorable);
		this.addNode(node);
	}
}
