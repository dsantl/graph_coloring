package graph_coloring.algorithmset;

import java.util.*;

public class NodeGreedy {

    public final Integer name;
    public final String group;
    public final Integer domain_name;
    public final Integer color_start;
    public final boolean allowed_change;
    public final Domain domain;
    public ArrayList<BridgeGreedy> bridges = new ArrayList<BridgeGreedy>();
    public final boolean color_inside_domain;
    
    private Integer color_current;   
    private Integer color_old = -1;
    private Double fitness = 0.0;
    private AssociativeHashMap colorHashMap;
    private AssociativeHashMap domainHashMap;

    public NodeGreedy(Integer name, String group, Integer domain_name, Integer color_start, boolean allowed_change, Domain domain, boolean color_inside_domain) {
        this.name = name;
        this.group = group;
        this.domain_name = domain_name;
        this.color_start = color_start;
        this.allowed_change = allowed_change;
        this.color_current = color_start;
        this.domain = domain;
        this.color_inside_domain = color_inside_domain;
    }

    public Integer getColor() {
        return color_current;
    }
    
    public void setColor(Integer colorInteger) {
        if (colorInteger != color_current) {
            color_old = color_current;
            color_current = colorInteger;
            for (BridgeGreedy bridge : bridges) {
                bridge.node.colorHashMap.remove(this.color_old);
                bridge.node.colorHashMap.put(this.color_current);
                if (bridge.node.color_current == this.color_current) {
                    this.fitness += bridge.weight;
                    bridge.node.fitness += bridge.weight;
                } else if (bridge.node.color_current == this.color_old) {
                    this.fitness -= bridge.weight;
                    bridge.node.fitness -= bridge.weight;
                }
            }
        }
    }
    
    public void addBridge(NodeGreedy neighbour, double weight) {
        bridges.add(new BridgeGreedy(neighbour, weight));
    }
    
    public void initialise(HashMap<Integer, Integer> colorMap, HashMap<Integer,Integer> domainMap){
        colorHashMap = new AssociativeHashMap(colorMap);
        domainHashMap = new AssociativeHashMap(domainMap);
        fitness = 0.0;
        Collections.sort(bridges);
        for (BridgeGreedy bridge : bridges) {
            colorHashMap.put(bridge.node.getColor());
            if (bridge.node.domain != null){
                domainHashMap.put(bridge.node.domain.name);
            }
            if (color_current.equals(bridge.node.getColor())){
                if (this.allowed_change || bridge.node.allowed_change) fitness += bridge.weight;
            }
        }
    }
    
    public Integer getSaturation(){
        return colorHashMap.size();
    }
    
    public Double getFitness(){
        return fitness;
    }
    
    public int getCollisions(int color){
        return colorHashMap.get(color);
    }
    
    public int getCollisions(){
        return colorHashMap.get(this.color_current);
    }
    
    @Override
    public String toString(){
       return name.toString();
    }
    
}
