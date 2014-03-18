/**
 * 
 */
package rtgrid.scheduler;

import java.util.Vector;

import rtgrid.resource.IResource;
import rtgrid.util.*;

/**
 * IScheduler.java
 * Package:  rtgrid.scheduler
 * @author Xuan, 2006-11-6 
 */
public interface IScheduler {

	  /**
	   * The standard method to run the scheduler
	   * 
	   * @param duration -How long the simulation will run
	   * @param r -Usually, an resource class will be passed in, such as "Cluster" 
	   * @param NTList -The vector that store the tasks sequence
	   * @return A "SimResult" object that stores the simulation info will be returned
	   * 
	   */
	  public SimResult run(int duration, Vector NTList);

}
