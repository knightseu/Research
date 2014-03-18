/**
 * 
 */
package rtgrid.scheduler;

import rtgrid.resource.Cluster;

/**
 * SchANEDF_SC.java
 * Package:  rtgrid.scheduler
 * @author lxuan, 2007-4-11 
 */
public class SchANEDF_SC extends SchAN {

	/**
	 * @param c
	 */
	public SchANEDF_SC(Cluster c) {
		super(c);
		_order=new OrderEDF();  
		_dlt=new DivHomoSingle_SC();
	}

}
