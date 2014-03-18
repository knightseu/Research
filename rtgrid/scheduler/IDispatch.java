/**
 * 
 */
package rtgrid.scheduler;

import rtgrid.resource.Cluster;
import rtgrid.taskmodel.Task;
import rtgrid.util.SimResult;

/**
 * IDispatch.java
 * Package:  rtgrid.scheduler
 * @author Xuan, 2007-1-14 
 */
public interface IDispatch {
	public void Dispatch(Cluster cluster, Task task, SimResult simrst);
}
