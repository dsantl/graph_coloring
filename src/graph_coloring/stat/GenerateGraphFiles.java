package graph_coloring.stat;

import graph_coloring.input.FERFileFormat;
import graph_coloring.output.FERoutput;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Random;

public class GenerateGraphFiles {

	private static Random rnd = new Random();
	
	public static void generate(String file){
		File parent = new File(file);
		generate(parent);
	}
	
	private static void generateGraph(File dir, int nodeSize, double density){
		System.gc();
		System.out.format("%s %d %f\n", dir.getAbsoluteFile(), nodeSize, density);
		EricssonGraph graph = GraphGenerator.generate(nodeSize, density, 8, 300, 512, 0.2, 0.07);
		FERoutput file = new FERoutput();
		String fileName = String.format("graph_%d_%f.txt", nodeSize, density);
		System.out.println(graph.getNodeSize());
		System.out.println(graph.getBridgeSize());
		try {
			file.convertGraphToFile(graph, dir.getAbsolutePath()+"/"+fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		
		System.out.println("DONE!");
	}
	
	private static void densityFolder(File dir, int nodeSize){
		File[] files = dir.listFiles();
		for(File file : files){
			double density = Double.parseDouble(file.getName());
			density /= 10;
			generateGraph(file, nodeSize, density);
		}
	}
	
	private static void generate(File parent){
		File[] files = parent.listFiles();
		for(File file : files){
			if ( file.isDirectory() ){
				if (file.getName().startsWith("n")){
					int nodeSize = Integer.parseInt(file.getName().substring(1));
					densityFolder(file, nodeSize);
				}
				else if ( file.isDirectory() )
					generate(file);
			}
		}
	}
	
}
