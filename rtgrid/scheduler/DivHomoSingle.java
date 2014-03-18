/**
 * 
 */
package rtgrid.scheduler;

import rtgrid.COMMON;
import rtgrid.taskmodel.SubTask;
import rtgrid.taskmodel.Task;
import rtgrid.util.SysVector;

/**
 * DivHomoSingle.java
 * Package:  rtgrid.scheduler
 * @author Xuan, 2006-12-31 
 * 
 * 
 * DLT class for Homogeneous system, Single Round, No setup.
 * 
 */
public class DivHomoSingle implements IDivisible {

	/**
	 * 
	 */
	public DivHomoSingle() {
		
	}

	/* (non-Javadoc)
	 * @see rtgrid.scheduler.IDivisible#computeCompletionTime(int, rtgrid.taskmodel.Task, rtgrid.util.SysVector)
	 */
	public int computeCompletionTime(int curTime, Task task, SysVector sysv) {
		
		//compute Nmin;
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
		
		return task.getCompletionTime();
	}

	/* (non-Javadoc)
	 * @see rtgrid.scheduler.IDivisible#computeCompletionTime(int, int, rtgrid.taskmodel.Task, rtgrid.util.SysVector)
	 */
	public int computeCompletionTime(int curTime, int nodes, Task task,
			SysVector sysv) {
		
        //set Nmin;
		task.setNmin(nodes);
		
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
		
		
		this.devideSubTask(curTime, nodes, task, sysv);
		
		return task.getCompletionTime();
	}

	/* (non-Javadoc)
	 * @see rtgrid.scheduler.IDivisible#computeCost(int, rtgrid.taskmodel.Task, rtgrid.util.SysVector)
	 */
	public int computeCost(int nodes, Task task, SysVector sysv) {
		double beta=sysv.getCps()*1.0/(sysv.getCms()+sysv.getCps());
		double alpha1= (1-beta)/(1-Math.pow(beta,nodes));
		double dComputeTime=alpha1*task.getDatasize()*(sysv.getCms()+sysv.getCps());
		return (int) Math.ceil(dComputeTime);
	}

	/* (non-Javadoc)
	 * @see rtgrid.scheduler.IDivisible#computeDatasize(int, int, rtgrid.taskmodel.Task, rtgrid.util.SysVector)
	 */
	public int computeDatasize(int duration, int nodes, Task task, SysVector sysv) {
		double beta=sysv.getCps()*1.0/(sysv.getCms()+sysv.getCps());
		return (int)( (1-Math.pow(beta,nodes))*duration/ ((sysv.getCms()+sysv.getCps())*(1-beta)) )  ;
	}

	/* (non-Javadoc)
	 * @see rtgrid.scheduler.IDivisible#computeNmin(int, rtgrid.taskmodel.Task, rtgrid.util.SysVector)
	 */
	public int computeNmin(int curTime, Task task, SysVector sysv) {
        int startTime=curTime>task.arrTime ? curTime :task.arrTime;
		double gamma=1-task.getDatasize()*sysv.getCms()*1.0/(task.getAbsoluteDeadline()-startTime);
		double t1=Math.log(gamma);
		double beta=sysv.getCps()*1.0/(sysv.getCms()+sysv.getCps());
		double t2=Math.log(beta);
		return (int)Math.ceil(t1/t2);
	}

	/* (non-Javadoc)
	 * @see rtgrid.scheduler.IDivisible#computeDC(int, rtgrid.taskmodel.Task, rtgrid.util.SysVector)
	 */
	public double computeDC(Task task, SysVector sysv) {
		double beta=sysv.getCps()*1.0/(sysv.getCms()+sysv.getCps());
		
		double a,b;
		
		double alpha1= (1-beta)/(1-Math.pow(beta,task.Nmin+1));
		a=alpha1*task.getDatasize()*(sysv.getCms()+sysv.getCps());
		
		alpha1= (1-beta)/(1-Math.pow(beta,task.Nmin));
		b=alpha1*task.getDatasize()*(sysv.getCms()+sysv.getCps());
		if (task.Nmin!=sysv.getSize())
			task.DC= a*(task.Nmin+1)-b*task.Nmin;
		else 
			task.DC= 999999999;
		
		return task.DC;
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
		
	
		
		double alpha_1=1.0*(1-beta)/(1-Math.pow(beta,nodes));
		int commtime=0,aTime=0,sTime=curTime;
		int totaldatasize=0;
		for (int i=0;i<nodes;i++) {
			double alpha_i=alpha_1*Math.pow(beta,i);
			int datasize;
			if (i<(nodes-1)) {
				datasize=(int)Math.ceil(alpha_i*task.getDatasize());
				totaldatasize+=datasize;
			}
			else
				datasize=task.getDatasize()-totaldatasize;
			
			commtime = datasize*sysv.getCms();
			aTime=sTime+commtime;
			SubTask subT=new SubTask(task, i, datasize, sTime, aTime, task.compTime);
			sTime=aTime;
			task.addSubTask(subT);		
		}

	}

}
