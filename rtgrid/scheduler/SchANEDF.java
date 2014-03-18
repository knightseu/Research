/**
 * 
 */
package rtgrid.scheduler;

import rtgrid.resource.Cluster;

/**
 * SchANEDF.java
 * Package:  rtgrid.scheduler
 * @author lxuan, 2007-4-11 
 */
public class SchANEDF extends SchAN {

	/**
	 * @param c
	 */
	public SchANEDF(Cluster c) {
		super(c);
		_order=new OrderEDF();  
		//_dlt=new DivHomoSingle();
	}

}
