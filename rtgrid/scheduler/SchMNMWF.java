/**
 * 
 */
package rtgrid.scheduler;

import rtgrid.resource.Cluster;

/**
 * SchMNMWF.java
 * Package:  rtgrid.scheduler
 * @author lxuan, 2007-4-11 
 */
public class SchMNMWF extends SchMN {

	/**
	 * @param c
	 */
	public SchMNMWF(Cluster c) {
		super(c);
		_order=new OrderMWF();
	}

}
