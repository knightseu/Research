/**
 * 
 */
package rtgrid.scheduler;

import rtgrid.resource.Cluster;

/**
 * SchMNMWF_SC.java
 * Package:  rtgrid.scheduler
 * @author lxuan, 2007-4-11 
 */
public class SchMNMWF_SC extends SchMN {

	/**
	 * @param c
	 */
	public SchMNMWF_SC(Cluster c) {
		super(c);
		_order=new OrderMWF();
		_dlt=new DivHomoSingle_SC();
	}

}
