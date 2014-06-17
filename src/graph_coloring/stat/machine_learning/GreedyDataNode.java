package graph_coloring.stat.machine_learning;

public class GreedyDataNode {
	
	private double COL;
	private double FIT;
	private double LDO;
	private double SDO;
	
	public GreedyDataNode(double COL, double FIT, double LDO, double SDO){
		this.COL = COL;
		this.FIT = FIT;
		this.LDO = LDO;
		this.SDO = SDO;
	}
	
	@Override
	public String toString(){
		return String.format("%f %f %f %f", COL, FIT, LDO, SDO);
	}
	
	
}
