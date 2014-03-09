package graph_coloring.structure.weight_graph.ericsson_graph;

import java.util.Iterator;
import java.util.Set;

public interface IEricssonGraph {
	
	public void addEricssonNode(int id, char nodeGroup, int domainName, int startColor, boolean colorable);
	
	public char getNodeGroup(int id);
	
	public int getNodeDomainName(int id);
	
	public int getNodeStartColor(int id);
	
	public boolean getNodeColorable(int id);
	
	public boolean colorClassContain(int colorClassId, int color);
	
	public void addColorClass(int id);
	
	public void addColorToColorClass(int colorClassId, int color);
	
	public boolean checkValidColorOfNode(int index);
	
	public Iterator<Integer> getAvailableColorsOfNode(int index);
	
	public int getDomainNameSize();
	
	public Set<Integer> getDomainNames();
	
	public int getNumberOfColors(int domainName);
	
	public Iterator<Integer> getDomainNameIterator(int domainName);
}
