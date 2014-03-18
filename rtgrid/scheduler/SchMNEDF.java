/**
 * 
 */
package rtgrid.scheduler;

import java.util.Vector;

import rtgrid.resource.ANI;
import rtgrid.resource.Cluster;
import rtgrid.taskmodel.Task;

/**
 * SchMNEDF.java
 * Package:  rtgrid.scheduler
 * @author Xuan, 2007-1-1 
 * 
 * 
 * EDF-OPR-MN
 */
public class SchMNEDF extends SchMN {

	/**
	 * @param c
	 */
	public SchMNEDF(Cluster c) {
		super(c);
		_order=new OrderEDF();
		
		// The default is already DivHomoSingle
		//_dlt=new DivHomoSingle();
		
	}

}
