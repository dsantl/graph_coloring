
package graph_coloring.algorithmset;

public class ColorSelectorMC implements IColorSelector{

    @Override
    public Integer select(NodeGreedy node) {
        int resultColor;
        if (node.color_inside_domain) resultColor = node.color_start;
        else resultColor = node.domain.colors.get(0);
        int minValue = node.getCollisions(resultColor);
        for (Integer colorInteger : node.domain.colors) {
            if (node.getCollisions(colorInteger) < minValue){
                resultColor = colorInteger;
                minValue = node.getCollisions(colorInteger);
            }
        }
        return resultColor;
    }
    
}
