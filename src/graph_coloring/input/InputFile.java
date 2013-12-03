package graph_coloring.input;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class InputFile {
	
	private BufferedReader textFile;
	private String fileName;
	private boolean isFileOpen;
	
	/**
	 * Method for testing if file is still open
	 * @return Return true if file is open or false if file is closed 
	 */
	public boolean isFileOpen()
	{
		return isFileOpen;
	}
	
	/**
	 * Constructor for InputFile, InputFile read text file line by line in buffered way
	 * @param fileName Name of text file
	 * @throws FileNotFoundException
	 */
	public InputFile(String fileName) throws FileNotFoundException
	{
		this.fileName = fileName;
		RestartTextFile();
		this.isFileOpen = true;
	}
	
	/**
	 * Restart intern structure to file name given in constructor
	 * @throws FileNotFoundException
	 */
	public void RestartTextFile() throws FileNotFoundException
	{
		textFile = new BufferedReader(new FileReader(fileName));
	}
	
	
	/**
	 * Get next line in file
	 * @return Next line in file if is file open, or null if file is on the end or closed
	 * @throws IOException
	 */
	public String getNextLine() throws IOException
	{
		if ( !isFileOpen )
			return null;
		
		String line = textFile.readLine();
		if (line == null)
		{
			isFileOpen = false;
			textFile.close();
		}
		return line;
	}
	
}
