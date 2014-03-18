/**
 * 
 */
package rtgrid.scheduler;

import rtgrid.resource.Cluster;

/**
 * SchAN_SC.java
 * Package:  rtgrid.scheduler
 * @author lxuan, 2007-4-11 
 */
public class SchAN_SC extends SchAN {

	/**
	 * @param c
	 */
	public SchAN_SC(Cluster c) {
		super(c);
		//_order=new OrderFIFO();  
		_dlt=new DivHomoSingle_SC();
	}

}
