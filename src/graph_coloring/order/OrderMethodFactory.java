package graph_coloring.order;

public class OrderMethodFactory {
	
	public static OrderMethod factory(String name) throws Exception{
		
		if ( name.equals("COL") )
			return new OrderNodeCOL();
		else if ( name.equals("FIT") )
			return new OrderNodeFIT();
		else if ( name.equals("LDO") )
			return new OrderNodeLDO();
		else if ( name.equals("SDO" ) )
			return new OrderNodeSDO();
		else if ( name.equals("SDOLDO") )
			return new OrderNodeSDOLDO();
		else if ( name.equals("STDORD") )
			return new OrderNodeSTDORD();
		else if ( name.equals("BRIDGEWEIGHT") )
			return new OrderBridgeWeight();
		else
			throw new Exception("Wrong OrderMethod name!");
		
	}
	
}
