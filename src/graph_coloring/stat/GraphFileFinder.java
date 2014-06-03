package graph_coloring.stat;

import graph_coloring.output.FERoutput;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class GraphFileFinder {

	public static List<String> find(String parentName){
		List<String> ret = new ArrayList<String>();
		File dir = new File(parentName);
		findFile(dir, ret);
		return ret;	
	}
	
	private static void findFile(File dir, List<String> names){
		File[] files = dir.listFiles();
		for(File file : files){
			if (file.isFile())
				names.add(file.getAbsolutePath());
			else if (file.isDirectory())
				findFile(file, names);
		}
	}
}
