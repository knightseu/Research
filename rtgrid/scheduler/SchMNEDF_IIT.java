/**
 * 
 */
package rtgrid.scheduler;

import rtgrid.resource.Cluster;

/**
 * SchMNEDF_IIT.java
 * Package:  rtgrid.scheduler
 * @author Xuan, 2007-1-8 
 */
public class SchMNEDF_IIT extends SchMN_IIT {

	/**
	 * @param c
	 */
	public SchMNEDF_IIT(Cluster c) {
		super(c);
		_order=new OrderEDF(); 
	}

}
