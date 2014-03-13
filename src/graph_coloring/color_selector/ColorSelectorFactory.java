package graph_coloring.color_selector;

public class ColorSelectorFactory {
	
	public static ColorSelector factory(String name) throws Exception{
		
		if ( name.equals("ABW") )
			return new ColorSelectorABW();
		else if ( name.equals("MC") )
			return new ColorSelectorMC();
		else if ( name.equals("MF") )
			return new ColorSelectorMF();
		else
			throw new Exception("Wrong color selector!");
	}
	
}
