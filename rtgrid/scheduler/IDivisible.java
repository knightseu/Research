package rtgrid.scheduler;

import rtgrid.taskmodel.Task;
import rtgrid.util.*;


/**
 * IDivisible.java
 * Package:  rtgrid.scheduler
 * @author Xuan, 2006-12-31 
 * 
 * 
 * This interface contains method to calculate the related DLT computation
 *   
 */


public interface IDivisible {

  /**
   * Compute the minimum nodes the task needed to complete before its deadline. This will not change the tasks' parameters
   * @param curTime the current time 
   * @param task the task
   * @param sysv the parameter vector (cms,cps)
   * @return the minimum number of nodes 
   */	
  public int computeNmin(int curTime, Task task, SysVector sysv);

  
  /**
   * Compute the cost to complete the task, including communication and the computation
   * @param nodes the number of nodes assigned to the task
   * @param task the task
   * @param sysv the parameter vector (cms,cps)
   * @return the cost
   */
  public int computeCost(int nodes, Task task, SysVector sysv);

  /**
   * Compute the time and the cost of a task, using minimum number of nodes.
   * It will call computeNmin first. This will affect the tasks' parameters.
   * @param curTime the current time
   * @param task the task
   * @param sysv the parameter vector (cms,cps)
   * @return the completion time of the task
   */
  public int computeCompletionTime(int curTime, Task task, SysVector sysv);

  /**
   * Compute the time and the cost of a task, using the specified number of nodes
   * @param curTime the current time
   * @param nodes the specified nodes
   * @param task the task
   * @param sysv the parameter vector (cms,cps)
   * @return the completion of the task
   */
  public int computeCompletionTime(int curTime,int nodes, Task task, SysVector sysv);
  
  /**
   * Compute the datasize that can be finished in certain period with certain number of nodes. This will not change the task.
   * @param duration the period of computation
   * @param task the task
   * @param the number of nodes assinged to the task
   * @param sys the parameter vector (cms,cps)
   * @return the datasize that can be computed in this period
   */
  public int computeDatasize(int duration,int nodes, Task task, SysVector sysv);
  
  
  
  /**
   * Compute the derative cost for the task. This will change the DC parameters of the task.
   * @param task the task
   * @param sysv the parameter vector (cms,cps)
   * @return DC
   */
  public double computeDC(Task task, SysVector sysv);
  
  
  
  
  
  
}