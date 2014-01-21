package graph_coloring.structure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ColorClass {
	private List<Integer> colors = new ArrayList<Integer>();

	public boolean containsColor(int color) {
		return colors.contains(color);
	}

	public void addColor(int color) {
		colors.add(color);
	}
	
	public int getColorCount(){
		return colors.size();
	}
	
	public List<Integer> getAllColors(){
		return colors;
	}
}
