package graph_coloring.structure;

import graph_coloring.common.Pair;

public abstract class Bridge {

	protected int leftNode;
	protected int rightNode;
	
	Bridge(int leftNode, int rightNode){
		this.leftNode = leftNode;
		this.rightNode = rightNode;
	}
	
	public int getLeftNode(){
		return leftNode;
	}
	
	public int getRightNode(){
		return rightNode;
	}

	public Pair<Integer, Integer> getPair() {
		Pair<Integer, Integer> pair = new Pair<Integer, Integer>(leftNode, rightNode);
		return pair;
	}
}
