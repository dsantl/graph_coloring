package graph_coloring.algorithmset;


import java.util.*;
import graph_coloring.algorithm.*;
import graph_coloring.structure.Bridge;
import graph_coloring.structure.ColorClass;
import graph_coloring.structure.EricssonBridge;
import graph_coloring.structure.EricssonGraph;
import graph_coloring.structure.EricssonNode;
import graph_coloring.structure.Graph;
import graph_coloring.structure.Node;
import java.util.Map.Entry;

public class GreedyAlgorithm implements GraphColoringAlgorithm {

    private HashMap<Integer,Domain> domains = new HashMap<Integer,Domain>();
    private HashMap<Integer, Integer> associativeColorMap = new HashMap<Integer,Integer>();
    private HashMap<Integer, Integer> associativeDomainMap = new HashMap<Integer,Integer>();
    private List<NodeGreedy> allNodes = new ArrayList<NodeGreedy>();
    private HashMap<Integer,Integer> indexMap = new HashMap<Integer,Integer>();
    private Comparator<NodeGreedy> comparator;
    private IColorSelector colorSelector;
    private int iterations;

    public GreedyAlgorithm(Graph graph) {
        transformNodes(graph);
    }

    private void transformNodes(Graph graph) {

        EricssonGraph eGraph = (EricssonGraph) graph;

        for (Entry<Integer, ColorClass> entry : eGraph.getColorClasssEntrySet()) {
            Domain newDomain = new Domain(entry.getKey());
            associativeDomainMap.put(entry.getKey(), associativeDomainMap.size());
            List<Integer> colors = entry.getValue().getAllColors();
            for (Integer colorInteger : colors) {
                newDomain.colors.add(colorInteger);
                if (!associativeColorMap.containsKey(colorInteger)) {
                    associativeColorMap.put(colorInteger, associativeColorMap.size());
                }
            }
            domains.put(entry.getKey(),newDomain);
        }
        Set<Integer> nodeIndices = eGraph.getNodeIndices();
        for (Integer index : nodeIndices) {
            
            EricssonNode eNode = (EricssonNode)eGraph.getNode(index);
            Domain domain = null;
            if (domains.containsKey(eNode.getColorClass())) domain = domains.get(eNode.getColorClass());
            boolean color_inside_domain = false;
            if (domain != null){
                if (domain.colors.contains(eNode.getStartColor())){
                    color_inside_domain = true;
                }
            }
            if (!associativeColorMap.containsKey(eNode.getStartColor())){
                associativeColorMap.put(eNode.getStartColor(), associativeColorMap.size());
            }
            NodeGreedy nodeGreedy = new NodeGreedy(index,
                     Character.toString(eNode.getNodeClass()),
                     eNode.getColorClass(),
                     eNode.getStartColor(),
                     eNode.getColorable(),
                     domain,
                     color_inside_domain);
            
            indexMap.put(index, allNodes.size());
            allNodes.add(nodeGreedy);
            
        }
        for (NodeGreedy nodeGreedy : allNodes) {
            List<Bridge> bridges = eGraph.getNodeBridges(nodeGreedy.name);
            for (Bridge bridge : bridges) {
                EricssonBridge eBridge = (EricssonBridge) bridge;
                NodeGreedy otherNode = null;
                if (nodeGreedy.name.equals(eBridge.getLeft())) otherNode = allNodes.get(indexMap.get(eBridge.getRight()));
                else otherNode = allNodes.get(indexMap.get(eBridge.getLeft()));
                nodeGreedy.addBridge(otherNode, eBridge.getWeight());
            }
        }
        for (NodeGreedy nodeGreedy : allNodes) {
            nodeGreedy.initialise(associativeColorMap, associativeDomainMap);
        }
    }

    private LinkedList<NodeGreedy> greedyNodeList(List<Node> nodes) {
        LinkedList<NodeGreedy> result = new LinkedList<NodeGreedy>();
        for (Node node : nodes) {
                    result.add(allNodes.get(indexMap.get(node.getId())));
        }
        return result;
    }


    public void setParameters(String comparatorString, String colorSelectorString, int numOfIter) {
        comparator = ComparatorFactory.make(comparatorString);
        colorSelector = ColorSelectorFactory.make(colorSelectorString);
        iterations = numOfIter;
    }

    @Override
    public void startAlgorithm(List<Integer> nodes, Graph graph) {
       
    	List<Node> sendNodes = new ArrayList<Node>();
    	
    	for(Integer id : nodes){
    		sendNodes.add(graph.getNode(id));
    	}
    	
    	for (int i = 0; i < iterations; i++) {
            LinkedList<NodeGreedy> nodesToVisit = greedyNodeList(sendNodes);
            while (nodesToVisit.size() > 0) {
                Collections.sort(nodesToVisit, comparator);
                nodesToVisit.getFirst().setColor(colorSelector.select(nodesToVisit.getFirst()));
                EricssonNode eNode = (EricssonNode) graph.getNode(nodesToVisit.getFirst().name);
                eNode.setColor(nodesToVisit.getFirst().getColor());
                nodesToVisit.removeFirst();
            }
        }
    }

}
