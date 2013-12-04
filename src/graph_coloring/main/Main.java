package graph_coloring.main;

import java.io.FileNotFoundException;
import java.io.IOException;

import graph_coloring.input.EricssonFileFormat;
import graph_coloring.input.FileFormat;

public class Main {

	public static void main(String[] args) {
		
		FileFormat fileFormat = new EricssonFileFormat();
		try {
			fileFormat.getGraphFromFile("/home/dino/Desktop/diplomski/FER-Kansai.txt");
			System.out.println("END");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
