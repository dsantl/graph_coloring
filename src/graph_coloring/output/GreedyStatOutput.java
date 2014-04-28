package graph_coloring.output;

import graph_coloring.common.Pair;
import graph_coloring.stat.machine_learning.GreedyDataNode;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class GreedyStatOutput {

	public void saveStatistics(List<Pair<Double, GreedyDataNode>> list, String fileName) throws FileNotFoundException, UnsupportedEncodingException{
		
		PrintWriter outFile = new PrintWriter(fileName, "UTF-8");
		outFile.format("ORDER COL FIT LDO SDO\n");		
		for(Pair<Double, GreedyDataNode> elem : list){
			outFile.format("%f %s\n", elem.getFirst(), elem.getSecond().toString());
		}
		
		outFile.close();

	}
}
