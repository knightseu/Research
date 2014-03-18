/**
 * 
 */
package rtgrid.scheduler;

import rtgrid.resource.Cluster;

/**
 * SchMNEDF_SC.java
 * Package:  rtgrid.scheduler
 * @author lxuan, 2007-4-10 
 */
public class SchMNEDF_SC extends SchMN {

	/**
	 * @param c
	 */
	public SchMNEDF_SC(Cluster c) {
		super(c);
   _order=new OrderEDF();
		_dlt=new DivHomoSingle_SC();
	}

}
