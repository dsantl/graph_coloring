package graph_coloring.structure;

public abstract class ElementProperty {
	protected Object property = null;
	
	/**
	 * Add property to this object
	 * @param o Some parameter for use in property
	 */
	public void addProperty(Object o){
		this.property = o;
	}
	
	/**
	 * Get property of this object
	 * @return Get property of this object (Object)
	 */
	public Object getProperty(){
		return property;
	}
}
