package graph_coloring.algorithmset;

import graph_coloring.algorithm.GraphColoringAlgorithm;
import graph_coloring.algorithmset.greedy.Domain;
import graph_coloring.algorithmset.greedy.IColorSelector;
import graph_coloring.algorithmset.greedy.NodeGreedy;
import graph_coloring.common.Pair;
import graph_coloring.structure.Bridge;
import graph_coloring.structure.ColorClass;
import graph_coloring.structure.EricssonBridge;
import graph_coloring.structure.EricssonGraph;
import graph_coloring.structure.EricssonNode;
import graph_coloring.structure.Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class TabuAlgorithm implements GraphColoringAlgorithm{
    Map<Pair<NodeGreedy,Integer>, Integer> tabuList; //tabu lista poteza i pripadne sume kolizija
    LinkedList<Pair<NodeGreedy,Integer>> tabuOrder;
    private int tabuSize;
    private int maxMoves;
    private int maxIterations;
    private IColorSelector colorSelector;
    private Comparator<NodeGreedy> comparator;
    
    private HashMap<Integer, Domain> domains = new HashMap<Integer, Domain>();
    private HashMap<Integer, Integer> associativeColorMap = new HashMap<Integer, Integer>();
    private HashMap<Integer, Integer> associativeDomainMap = new HashMap<Integer, Integer>();
    private List<NodeGreedy> allNodes = new ArrayList<NodeGreedy>();
    private HashMap<Integer,Integer> indexMap = new HashMap<Integer, Integer>();
    
    public TabuAlgorithm(Graph graph){
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


    
    private void refreshGraphColoring(List<NodeGreedy> nodes, Graph graph) {
        for (NodeGreedy nodeGreedy : nodes) {
            graph.getNode(nodeGreedy.name).setColor(nodeGreedy.getColor());
        }
    }
    
    public void setParameters(int tabuSize, int numberOfMoves, int maxIterations, IColorSelector colorSelector, Comparator<NodeGreedy> comparator){
        this.tabuSize = tabuSize;
        this.maxMoves = numberOfMoves;
        this.maxIterations = maxIterations;
        this.colorSelector = colorSelector;
        this.comparator = comparator;
    }
    @Override
    public void startAlgorithm(List<Integer> nodes, Graph graph){
        List<NodeGreedy> nodeList = greedyNodeList(nodes);
        List<NodeGreedy> availableNodes = new ArrayList<NodeGreedy>();
        for (NodeGreedy node : nodeList) {
            if (node.allowed_change) availableNodes.add(node);
        }
        int numOfIterations = 0;
        tabuList = new HashMap<Pair<NodeGreedy, Integer>, Integer>();
        tabuOrder = new LinkedList<Pair<NodeGreedy, Integer>>();
        int minCollisionsOfNodes = numOfCollisions(availableNodes);     
        
        
        while ((numOfCollisions(availableNodes) > 0) && (numOfIterations < maxIterations)){
        	numOfIterations++;
            int minCollisionsOfSearch = Integer.MAX_VALUE;
            Pair<NodeGreedy, Integer> minMove = null;
            Collections.sort(availableNodes, comparator);
            int moves = 0;
            for (NodeGreedy node : availableNodes) {
                if (moves > maxMoves) break;
                if (node.getCollisions() > 0) {
                    int colorOld = node.getColor();
                    int colorNew = colorSelector.select(node);
                    moves++;                    
                    if (colorNew != colorOld) {
                        Pair<NodeGreedy, Integer> currentMove = new Pair<NodeGreedy, Integer>(node, colorNew);
                        int currentNumOfCollisions = minCollisionsOfNodes - node.getCollisions(colorOld) + node.getCollisions(colorNew);
                        if (currentNumOfCollisions < minCollisionsOfNodes) {
                            minMove = currentMove;
                            numOfIterations = 0;
                            break;
                        }
                        else if ((currentNumOfCollisions < minCollisionsOfSearch) && (!tabuList.containsKey(currentMove))){
                            minCollisionsOfSearch = currentNumOfCollisions;
                            minMove = new Pair<NodeGreedy, Integer>(node, colorNew);
                        }
                            
                    }
                } else break;
            }
            if (minMove == null){
                break;
            }
            minMove.getLeft().setColor(minMove.getRight());
            int newCollNumber = numOfCollisions(availableNodes);
            tabuList.put(minMove, newCollNumber);
            tabuOrder.add(minMove);
            if (tabuOrder.size() > tabuSize){
                tabuList.remove(tabuOrder.getFirst());
                tabuOrder.removeFirst();
            }
            
        }
        refreshGraphColoring(allNodes, graph);
    }
    
    private int numOfCollisions(List<NodeGreedy> nodes){
        int result = 0;
        for (NodeGreedy node : nodes) {
            result += node.getCollisions();
        }
        return result;
    }
    
    private ArrayList<NodeGreedy> greedyNodeList(List<Integer> nodes) {
        ArrayList<NodeGreedy> result = new ArrayList<NodeGreedy>();
        for (Integer node : nodes) {
            result.add(allNodes.get(indexMap.get(node)));
        }
        return result;
    }
}
