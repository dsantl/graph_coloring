package graph_coloring.color_selector;

public class ColorSelectorFactory {
	
	public static ColorSelector factory(String name) throws Exception{
		
		if ( name.equals("ABW") )
			return new ColorSelectorABW();
		else if ( name.equals("MC") )
			return new ColorSelectorMC();
		else if ( name.equals("MF") )
			return new ColorSelectorMF();
		else if ( name.equals("TRG") )
			return new ColorSelectorTRG();
		else if ( name.equals("START") )
			return new ColorSelectorSTART();
		else if ( name.equals("SWAP") )
			return new ColorSelectorSWAP();
		else if ( name.equals("SWAPSTART") )
			return new ColorSelectorSWAPSTART();
		else if ( name.equals("ABWSTART") )
			return new ColorSelectorABWSTART();
		else if ( name.equals("ABWMD") )
			return new ColorSelectorABWMD();
		else if ( name.equals("RND") )
			return new ColorSelectorRND();
		else if ( name.equals("MC_D") )
			return new ColorSelectorMC_D();
		else if ( name.equals("SWAP_D") )
			return new ColorSelectorSWAP_D();
		else if ( name.equals("RND_D") )
			return new ColorSelectorRND_D();
		else
			throw new Exception("Wrong color selector!");
	}
	
}
