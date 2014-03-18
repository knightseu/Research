/**
 * 
 */
package rtgrid.scheduler;

import rtgrid.COMMON;
import rtgrid.taskmodel.SubTask;
import rtgrid.taskmodel.Task;
import rtgrid.util.SysVector;

/**
 * DivSplit.java
 * Package:  rtgrid.scheduler
 * @author Xuan, 2007-1-9 
 */
public class DivSplit implements IDivisible {

	/* (non-Javadoc)
	 * @see rtgrid.scheduler.IDivisible#computeCompletionTime(int, rtgrid.taskmodel.Task, rtgrid.util.SysVector)
	 */
	public int computeCompletionTime(int curTime, Task task, SysVector sysv) {
		task.setNmin(this.computeNmin(curTime, task, sysv));
		
        //		validate the Nmin
	if ((task.Nmin<=0) || (task.Nmin>COMMON.PARA_CLUSTERSIZE)) { 
		if (COMMON.DEBUG==true) {
			System.out.println(task);
			System.out.println(this);
		}
		task.setNmin(-1);
		return -1;
	}
	
	//set startTime
	if (curTime>=task.arrTime)
		task.startTime=curTime;
	else
		task.startTime=task.arrTime;
	
	//compute cost
	int cost=this.computeCost(task.Nmin, task, sysv);
	
	//set completion time
	task.compTime=cost+task.startTime;
	
	//set the time when the data has been dispatched
	task.dispDoneTime=task.startTime+task.datasize*sysv.getCms();
	
	
	this.devideSubTask(curTime, task.Nmin, task, sysv);
	
	//this.devideSubTask(curTime, task.Nmin, task, sysv);
	if (task.isDeadlineMissed() || (task.getCompletionTime()<=0)) {
		task.Nmin=-1;
		return -1;
	}
	return task.getCompletionTime();
	}
	
	/**
	 * Devided the task into subtasks
	 * @param curTime
	 * @param nodes
	 * @param task
	 * @param sysv
	 */
	public void devideSubTask(int curTime,int nodes, Task task, SysVector sysv) {
		
	
		task._SubTasks.clear();
		double beta=sysv.getCps()*1.0/(sysv.getCms()+sysv.getCps());
		
			SubTask subT=new SubTask(task, 0, task.datasize, task.startTime, task.dispDoneTime, task.compTime);
			task.addSubTask(subT);		
	
	}


	/* (non-Javadoc)
	 * @see rtgrid.scheduler.IDivisible#computeCompletionTime(int, int, rtgrid.taskmodel.Task, rtgrid.util.SysVector)
	 */
	public int computeCompletionTime(int curTime, int nodes, Task task,
			SysVector sysv) {
		
		return -1;
	}

	/* (non-Javadoc)
	 * @see rtgrid.scheduler.IDivisible#computeCost(int, rtgrid.taskmodel.Task, rtgrid.util.SysVector)
	 */
	public int computeCost(int nodes, Task task, SysVector sysv) {
		return task.datasize*(sysv.getCms()+sysv.getCps());
	}

	/* (non-Javadoc)
	 * @see rtgrid.scheduler.IDivisible#computeDC(rtgrid.taskmodel.Task, rtgrid.util.SysVector)
	 */
	public double computeDC(Task task, SysVector sysv) {
		return -1;
	}

	/* (non-Javadoc)
	 * @see rtgrid.scheduler.IDivisible#computeDatasize(int, int, rtgrid.taskmodel.Task, rtgrid.util.SysVector)
	 */
	public int computeDatasize(int duration, int nodes, Task task,
			SysVector sysv) {
		return -1;
	}

	/* (non-Javadoc)
	 * @see rtgrid.scheduler.IDivisible#computeNmin(int, rtgrid.taskmodel.Task, rtgrid.util.SysVector)
	 */
	public int computeNmin(int curTime, Task task, SysVector sysv) {
		
		return 1;
	}

}
