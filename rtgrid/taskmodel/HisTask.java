package rtgrid.taskmodel;

import rtgrid.COMMON;
/**
 * 
 * @author Xuan
 *
 * The HistTask Class is used to store the history of the task.
 * 
 * How it is scheduled, when it is dispatched, does it miss its deadline, etc.
 * 
 */

public class HisTask {
  public int type;
  
  /**
   * reference of original task
   */
  public Task task;
  
  private int num; //number of nodes in the system.
  
  private boolean nodes[]; // whether the task is dispatch on the nodes;
  
  public HisTask (int type, Task task) {
	  // TODO construction
	  this.type=type;
	  this.task=task;
      nodes= new boolean[COMMON.PARA_CLUSTERSIZE];
      for (int i=0;i<COMMON.PARA_CLUSTERSIZE;i++)
    	  nodes[i]=false;
      num=0;
  }
  
  /**
   * How many nodes are assigned to the task
   * @return
   */
  public int getNodeNum(){
	  if (type!=2) return -1;
	  return num;
  }
  
  public void setNodeNum(int i) {
	  num=i;
  }
  
  /**
   * See if node i is assigned to the task.
   * @param index
   * @return
   */
  public boolean nodeinfo(int index){
	  if (type!=2) return false;
	  return nodes[index];
  }
  
  /**
   * According to the system configuration, decide whether we need to print this kind of information
   * @return
   */
  public boolean printable() {
	  switch (type){
	  case COMMON.TASK_ARRIVE :
		  if (COMMON.LOG_PRINT_ARRIVE)
		     return true;
		  else
			  return false;
	  case COMMON.TASK_REJECT:
		  if (COMMON.LOG_PRINT_REJECT)
			     return true;
		  else
			  return false;
	  case COMMON.TASK_DEADLINEMISS :
		  if (COMMON.LOG_PRINT_DEADLINEMISS)
			     return true;
		  else
			  return false;
	  case COMMON.TASK_ADMIT:
		  if (COMMON.LOG_PRINT_ADMIT)
			     return true;
		  else
			  return false;
	  case COMMON.TASK_DISPATCH:
		  if (COMMON.LOG_PRINT_DISPATCH)
			     return true;
		  else
			  return false;
	  }
	  return true;
  }
  
  public String toString() {
	  String typestr="";
	  switch (type){
	  case COMMON.TASK_ARRIVE :
		  typestr="ARRIVE";
		  break;
	  case COMMON.TASK_REJECT:
		  typestr="REJECT";
		  break;
	  case COMMON.TASK_DEADLINEMISS :
		  typestr="DISPATCHMISS";
		  break;
	  case COMMON.TASK_ADMIT:
		  typestr="SCHEDULE";
		  break;
	  case COMMON.TASK_DISPATCH:
		  typestr="DISPATCH";
		  break;
	  }
	  String rst="";
	  rst+=typestr;
	  rst+="-"+task.toString();
	  return rst;
  }
  
  
}