/**
 * 
 */
package rtgrid.scheduler;

import rtgrid.COMMON;
import rtgrid.taskmodel.SubTask;
import rtgrid.taskmodel.Task;
import rtgrid.util.SysVector;

/**
 * DivHomoSingleSC.java
 * Package:  rtgrid.scheduler
 * @author lxuan, 2007-4-10 
 */
public class DivHomoSingle_SC implements IDivisible {

	/* (non-Javadoc)
	 * @see rtgrid.scheduler.IDivisible#computeCompletionTime(int, rtgrid.taskmodel.Task, rtgrid.util.SysVector)
	 */
	public int computeCompletionTime(int curTime, Task task, SysVector sysv) {
//		compute Nmin;
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
		task.dispDoneTime=task.ST*task.Nmin+task.startTime+task.datasize*sysv.getCms();
		
		
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
		task.dispDoneTime=task.ST*task.Nmin+task.startTime+task.datasize*sysv.getCms();
		
		
		this.devideSubTask(curTime, nodes, task, sysv);
		
		return task.getCompletionTime();
	}

	/* (non-Javadoc)
	 * @see rtgrid.scheduler.IDivisible#computeCost(int, rtgrid.taskmodel.Task, rtgrid.util.SysVector)
	 */
	public int computeCost(int nodes, Task task, SysVector sysv) {
		int sc=task.SC;
		int st=task.ST;
		int cms=sysv.getCms();
		int cps=sysv.getCps();
		int maxn=sysv.getSize();
		int sigma=task.getDatasize();
		
		double beta=1.0*cps/(cms+cps);
		double phi=1.0*st/(sigma*(cms+cps));	
		
		double dComputeTime=st+sc+sigma*(cms+cps)*b_n(nodes,beta,phi);
		return (int) Math.ceil(dComputeTime);
	}

	/* (non-Javadoc)
	 * @see rtgrid.scheduler.IDivisible#computeDC(rtgrid.taskmodel.Task, rtgrid.util.SysVector)
	 */
	public double computeDC(Task task, SysVector sysv) {
		int sc=task.SC;
		int st=task.ST;
		int cms=sysv.getCms();
		int cps=sysv.getCps();
		int maxn=sysv.getSize();
		int sigma=task.getDatasize();
		
		double beta=1.0*cps/(cms+cps);
		double phi=1.0*st/(sigma*(cms+cps));	
		double t1,t2;
		t1=b_n(task.Nmin+1,beta,phi);
		t2=b_n(task.Nmin,beta,phi);
		double dCost1=(st+sc+sigma*(cms+cps)*t1)*(task.Nmin+1);
		double dCost2=(st+sc+sigma*(cms+cps)*t2)*task.Nmin;
		
		return dCost1-dCost2;
	}

	/* (non-Javadoc)
	 * @see rtgrid.scheduler.IDivisible#computeDatasize(int, int, rtgrid.taskmodel.Task, rtgrid.util.SysVector)
	 */
	public int computeDatasize(int duration, int nodes, Task task,
			SysVector sysv) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see rtgrid.scheduler.IDivisible#computeNmin(int, rtgrid.taskmodel.Task, rtgrid.util.SysVector)
	 */
	public int computeNmin(int curTime, Task task, SysVector sysv) {
		int sc=task.SC;
		int st=task.ST;
		int cms=sysv.getCms();
		int cps=sysv.getCps();
		int maxn=sysv.getSize();
		int sigma=task.getDatasize();
		int startTime=curTime>task.arrTime ? curTime :task.arrTime;
		
		double beta=1.0*cps/(cms+cps);
		double phi=1.0*st/(sigma*(cms+cps));	
		double maxcost=task.getAbsoluteDeadline()-startTime;
		int nodes=0;
		double cost_n;
		do {
			nodes++;
			cost_n=st+sc+sigma*(cms+cps)*b_n(nodes,beta,phi);
		} while ((cost_n>maxcost) && (nodes<maxn));
		
		if (cost_n>maxcost) return -1;
		else		return nodes;
	}
	

	
	/**
	 * refer to B(n) in TR p 17 eqn (6.22)
	 * @param n
	 * @param beta
	 * @param phi
	 * @return
	 */
	private double b_n(int n, double beta, double phi) {
		return (1-beta)/(1-Math.pow(beta, n))+phi*n/(1-Math.pow(beta, n))-phi/(1-beta);
	}
	
	
	/**
	 * Devided the task into subtasks
	 * @param curTime
	 * @param nodes
	 * @param task
	 * @param sysv
	 */
	public void devideSubTask(int curTime,int nodes, Task task, SysVector sysv) {
		
		int sc=task.SC;
		int st=task.ST;
		int cms=sysv.getCms();
		int cps=sysv.getCps();
		int maxn=sysv.getSize();
		int sigma=task.getDatasize();
		int startTime=curTime>task.arrTime ? curTime :task.arrTime;
		
		double beta=1.0*cps/(cms+cps);
		double phi=1.0*st/(sigma*(cms+cps));	
		
		
		task._SubTasks.clear();
		
		
		double alpha_1=b_n(nodes,beta,phi);
		
		int commtime=0,aTime=0,sTime=curTime;
		int totaldatasize=0;
		for (int i=0;i<nodes;i++) {
			double alpha_i=alpha_1*Math.pow(beta,i)-phi*(1-Math.pow(beta, i))/(1-beta);
			int datasize;
			if (i<(nodes-1)) {
				datasize=(int)Math.ceil(alpha_i*task.getDatasize());
				totaldatasize+=datasize;
			}
			else
				datasize=task.getDatasize()-totaldatasize;
			
			commtime = datasize*sysv.getCms()+task.SC;
			aTime=sTime+commtime;
			SubTask subT=new SubTask(task, i, datasize, sTime, aTime, task.compTime);
			sTime=aTime;
			task.addSubTask(subT);		
		}

	}
	
	
	/**
	 * 
	 * @param curTime
	 * @param task
	 * @param sysv
	 * @return return the optimal number of processors
	 */
	public int computeNopt(int curTime, Task task, SysVector sysv) {
		int sc=task.SC;
		int st=task.ST;
		int cms=sysv.getCms();
		int cps=sysv.getCps();
		int maxn=sysv.getSize();
		int sigma=task.getDatasize();
		int startTime=curTime>task.arrTime ? curTime :task.arrTime;
		
		double beta=1.0*cps/(cms+cps);
		double phi=1.0*st/(sigma*(cms+cps));	
		double maxcost=task.getAbsoluteDeadline()-startTime;
		int nodes=0;
		double cost_n,cost_old=0;
		do {
			nodes++;
			cost_n=st+sc+sigma*(cms+cps)*b_n(nodes,beta,phi);
			if  ( (nodes!=1) && (cost_n>=cost_old) ) {
					     if (cost_old>maxcost) return -1;
					     else return (--nodes);
			       }								
			  cost_old=cost_n;
		} while ((cost_n>maxcost) && (nodes<maxn));
		
		if (cost_n>maxcost) return -1;
		else		return nodes;
	}


}
