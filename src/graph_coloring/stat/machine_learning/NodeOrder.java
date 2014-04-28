package graph_coloring.stat.machine_learning;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import graph_coloring.algorithm.GraphAlgorithmContext;
import graph_coloring.algorithm.unit.GeneralUnit;
import graph_coloring.algorithmset.greedy.Greedy;
import graph_coloring.common.Pair;
import graph_coloring.stat.ErrorFunctionEricsson;
import graph_coloring.stat.GetColorableNodes;
import graph_coloring.structure.weight_graph.ericsson_graph.EricssonGraph;

public class NodeOrder {
	
	private static List<GreedyDataNode> initData(EricssonGraph g, List<Integer> colorableNodes){
		List<GreedyDataNode> data = new ArrayList<GreedyDataNode>();
		
		SortedMap<Integer, GreedyDataNode> nodeId = new TreeMap<Integer, GreedyDataNode>();
		
		double maxCOL=0, maxFIT=0, maxLDO=0, maxSDO=0;
		
		
		for(Integer id : colorableNodes){
			int index = g.getNodeIndex(id);
			maxCOL = Math.max(maxCOL, g.getNodeCollision(index));
			maxFIT = Math.max(maxFIT, g.getNodeError(index));
			maxLDO = Math.max(maxLDO, g.getNodeDegre(index));
			maxSDO = Math.max(maxSDO, g.getNodeSaturation(index));
		}
		
		for(Integer id : colorableNodes){
			int index = g.getNodeIndex(id);
			double COL = g.getNodeCollision(index)/maxCOL;
			double FIT = g.getNodeError(index)/maxFIT;
			double LDO = g.getNodeDegre(index)/maxLDO;
			double SDO = g.getNodeSaturation(index)/maxSDO;
			GreedyDataNode row = new GreedyDataNode(COL, FIT, LDO, SDO);
			nodeId.put(id, row);
		}
		
		for(Integer id : nodeId.keySet()){
			data.add(nodeId.get(id));
		}
		
		return data;
	}
	
	private static void saveAlgorithmRound(EricssonGraph g, List<GreedyDataNode> data, SortedMap<Double, List<Pair<Double, GreedyDataNode>>> oneRound, List<Integer> colorableNodes){
		
		List<Pair<Double, GreedyDataNode>> ret = new ArrayList<Pair<Double, GreedyDataNode>>(); 
		double error = ErrorFunctionEricsson.computeStat(g);
		SortedMap<Integer, Double> nodeId = new TreeMap<Integer, Double>();
		
		for(Integer id : colorableNodes){
			int index = g.getNodeIndex(id);
			double nodePotential = (double)index/(g.getNodeSize()-1);
			nodeId.put(id, nodePotential);
		}
		
		int cnt = 0;
		for(Integer id : nodeId.keySet()){
			ret.add(new Pair<Double, GreedyDataNode>(nodeId.get(id), data.get(cnt)));
			cnt += 1;
		}
		
		oneRound.put(error, ret);
	}
	
	public static List<Pair<Double, GreedyDataNode>> generateData(EricssonGraph g, int samples, double goodSolutions){
		
		List<Pair<Double, GreedyDataNode>> ret = new ArrayList<Pair<Double, GreedyDataNode>>(); 
		
		
		GraphAlgorithmContext alg = new GraphAlgorithmContext(new Greedy("STDORD", "ABW", 1));
		alg.startAlgorithm(g);
		
		GeneralUnit unit = new GeneralUnit(g);
		
		GraphAlgorithmContext algStep = new GraphAlgorithmContext(new Greedy("RND", "ABW", 1));
		
		List<Integer> colorableNodes = GetColorableNodes.getNodeIds(g);
		
		List<GreedyDataNode> data = initData(g, colorableNodes); //order is by ID, remove not colorable nodes
		
		SortedMap<Double, List<Pair<Double, GreedyDataNode>>> oneRound = new TreeMap<Double, List<Pair<Double, GreedyDataNode>>>();
		
		for(int i = 0 ; i < samples ; ++i){
			System.out.format("%d/%d\n", i+1, samples);
			algStep.startAlgorithm(g);
			saveAlgorithmRound(g, data, oneRound, colorableNodes);
			unit.updateGraph();
		}
		
		int cnt = 1;
		int maxCnt = (int)(oneRound.size()*goodSolutions);
		for(Map.Entry<Double, List<Pair<Double, GreedyDataNode>>> entry : oneRound.entrySet()){
			if ( cnt == maxCnt)
				break;
			cnt += 1;
			ret.addAll(entry.getValue());
		}
		
		return ret;
	}
	
}
