/**
 * 
 */
package rtgrid.scheduler;

import rtgrid.resource.Cluster;

/**
 * SchMN_SC.java
 * Package:  rtgrid.scheduler
 * @author lxuan, 2007-4-10 
 * 
 *  FIFO_OPR_MN (SC)
 */
public class SchMN_SC extends SchMN {

	/**
	 * @param c
	 */
	public SchMN_SC(Cluster c) {
		super(c);
		_dlt=new DivHomoSingle_SC();
	}

}
