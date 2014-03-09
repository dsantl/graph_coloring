
package graph_coloring.algorithmset.greedy;

public class ColorSelectorFactory {
    
    public static IColorSelector make(String value)
    {
        IColorSelector colorSelector = null;
        if ( value.equals("ABW") )
        	colorSelector = new ColorSelectorABW(new SelectFirst());
        if (value.equals("ABW-MC"))
            colorSelector = new ColorSelectorABW(new SelectMinCol());
        if ( value.equals("ABW-MC2") )
        	colorSelector = new ColorSelectorABW(new SelectMinColStartColor());
        if (value.equals("MF"))       
            colorSelector = new ColorSelectorMF();
        if (value.equals("MC"))
        	colorSelector = new ColorSelectorMC();
        return colorSelector;
    }
}
