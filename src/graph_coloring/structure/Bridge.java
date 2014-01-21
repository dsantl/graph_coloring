package graph_coloring.structure;

import graph_coloring.common.MinMaxPairInteger;

public abstract class Bridge {

	protected MinMaxPairInteger connection;
	
	Bridge(int leftNode, int rightNode){
		connection = new MinMaxPairInteger(leftNode, rightNode);
	}
	
	public int getLeft(){
		return connection.getLeft();
	}
	
	public int getRight(){
		return connection.getRight();
	}
	
	public MinMaxPairInteger getPair() {
		return connection;
	}
}
