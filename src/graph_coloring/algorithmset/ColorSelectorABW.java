
package graph_coloring.algorithmset;

import java.util.*;

public class ColorSelectorABW implements IColorSelector {
    private IABWExtendedColorSelector colorSelectExt = null;
    public ColorSelectorABW(IABWExtendedColorSelector colorSelectExtendor){
        colorSelectExt = colorSelectExtendor;
    }
    
    
    @Override
    public Integer select(NodeGreedy node) {
        HashMap<Integer, Integer> availableColorsMap = new HashMap<Integer, Integer>();
        if (node.color_inside_domain) 
            availableColorsMap.put(node.color_start, 0);
    
        for (Integer colorInteger : node.domain.colors) {
        	if (!availableColorsMap.containsKey(colorInteger)) 
        		availableColorsMap.put(colorInteger, colorInteger);
        }
        
        
        for (int i = 0; i < node.bridges.size(); i++) {
            availableColorsMap.remove(node.bridges.get(i).node.getColor());
            if (availableColorsMap.size() == 1) break;
        }
        if (availableColorsMap.containsKey(node.color_start)) return node.color_start;
        else {
            return colorSelectExt.select(node, availableColorsMap);
        }
    }

}
