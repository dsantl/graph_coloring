package graph_coloring.structure;

import java.util.HashSet;
import java.util.Set;

public class ColorClass {
	private Set<Integer> colors = new HashSet<Integer>();

	public boolean containsColor(int color) {
		return colors.contains(color);
	}

	public void addColor(int color) {
		colors.add(color);
	}
	
	public int getColorCount(){
		return colors.size();
	}
	
	public Set<Integer> getAllColors(){
		return colors;
	}
}
