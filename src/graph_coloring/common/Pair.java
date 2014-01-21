package graph_coloring.common;

public class Pair<L,R>{

	  protected L left;
	  protected R right;

	  public Pair(L left, R right) {
	    this.left = left;
	    this.right = right;
	  }

	  public L getLeft() { return left; }
	  public R getRight() { return right; }

	  @Override
	  public int hashCode() { return left.hashCode() ^ right.hashCode(); }

	  @Override
	  public boolean equals(Object o){
		  if (o == null)
			  return false;
		
		@SuppressWarnings("unchecked")
		Pair<L,R> current = (Pair<L, R>) o;
		if ( current.left.equals(this.left) && current.right.equals(this.right) )
			  return true;
		return false;
	  }
	  
}