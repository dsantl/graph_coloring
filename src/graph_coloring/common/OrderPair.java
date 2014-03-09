package graph_coloring.common;

public class OrderPair extends Pair<Integer,Integer>{

	public OrderPair(Integer left, Integer right) {
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
