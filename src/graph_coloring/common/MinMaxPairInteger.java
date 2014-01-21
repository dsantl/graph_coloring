package graph_coloring.common;

public class MinMaxPairInteger extends Pair<Integer,Integer>{

	public MinMaxPairInteger(Integer left, Integer right) {
		super(left, right);
		if ( left > right ){
			int tmp = left;
			left = right;
			right = tmp;
		}
		this.left = left;
		this.right = right;
	}

}
