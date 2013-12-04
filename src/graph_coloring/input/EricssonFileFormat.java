package graph_coloring.input;

import java.io.IOException;

import graph_coloring.structure.Graph;

public class EricssonFileFormat implements FileFormat{

	@Override
	public Graph getGraphFromFile(String fileName) throws NumberFormatException, IOException   {
		
		int numberOfColorSets = 0;
		int numberOfNodes = 0;
		int numberOfBridges = 0;
		
		InputFile file = new InputFile(fileName);
		
		numberOfColorSets = Integer.parseInt(file.getNextLine());
		
		for(int i = 0 ; i < numberOfColorSets ; ++i)
		{
			String[] byEqual = file.getNextLine().split("(\\s+)");
		}
		
		file.getNextLine();
		
		numberOfNodes = Integer.parseInt(file.getNextLine());
		
		for(int i = 0 ; i < numberOfNodes ; ++i)
		{
			System.out.println(file.getNextLine());
		}
		
		file.getNextLine();
		
		numberOfBridges = Integer.parseInt(file.getNextLine());
		
		for(int i = 0 ; i < numberOfBridges ; ++i)
		{
			file.getNextLine();
			
			//System.out.println(file.getNextLine());
		}
		
		return null;
		
	}

}
