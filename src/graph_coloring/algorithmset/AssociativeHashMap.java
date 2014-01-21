
package graph_coloring.algorithmset;

import java.util.*;

public class AssociativeHashMap {
    
    public Map<Integer, Integer> associativeMap;
    private final int[] colors;
    private int size = 0;
    public AssociativeHashMap(HashMap<Integer, Integer> associativeMap)
    {
        this.associativeMap = associativeMap;
        colors = new int[associativeMap.size()];
        Arrays.fill(colors, 0);
    }
    
    public int get(int value){
        return colors[associativeMap.get(value)];
    }
    public void put(Integer value){
        if((colors[associativeMap.get(value)]) == 0){ size++;}
        colors[associativeMap.get(value)]++;
    }
    public void remove(Integer value){
        colors[associativeMap.get(value)]--;
        if(colors[associativeMap.get(value)] == 0) size--;
        else if (colors[associativeMap.get(value)] < 0) colors[associativeMap.get(value)] = 0;
    }
    public boolean containsKey(Integer value) {
        return colors[associativeMap.get(value)] > 0;
    }
    public int size(){
        return size;
    }
    
}
