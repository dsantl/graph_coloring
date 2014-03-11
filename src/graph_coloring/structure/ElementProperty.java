package graph_coloring.structure;

public abstract class ElementProperty {
	protected Object property = null;
	
	public void addProperty(Object o){
		this.property = o;
	}
	
	public Object getProperty(){
		return property;
	}
}
