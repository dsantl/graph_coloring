package graph_coloring.algorithmset.greedy;

import java.util.*;
import java.util.Map.Entry;

public class ColorSelectorMF implements IColorSelector {

    @Override
    public Integer select(NodeGreedy node) {
        Map<Integer, Double> hm = new HashMap<Integer, Double>();
        for (BridgeGreedy nw : node.bridges) {
            if (hm.containsKey(nw.node.getColor())) {
                hm.put(nw.node.getColor(), hm.get(nw.node.getColor()) + nw.weight);
            } else {
                hm.put(nw.node.getColor(), nw.weight);
            }
        }
        Map<Integer, Integer> availableColors = new HashMap<Integer,Integer>();
        for (Integer colorInteger : node.domain.colors) {
            availableColors.put(colorInteger, 1);
        }
        Integer color = node.getColor();
        Double minDouble = Double.MAX_VALUE;
        for (Entry<Integer, Double> e : hm.entrySet()) {
            if (e.getValue() < minDouble) {
                if (availableColors.containsKey(e.getKey())) {
                    minDouble = e.getValue();
                    color = e.getKey();
                }
            }
        }
        return color;
    }

}
