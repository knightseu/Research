/**
 * 
 */
package rtgrid.scheduler;

import java.util.Vector;

import rtgrid.COMMON;
import rtgrid.resource.ANI;
import rtgrid.resource.Cluster;
import rtgrid.taskmodel.Task;
import rtgrid.taskmodel.TaskList;
import rtgrid.util.SimResult;
import rtgrid.util.SysVector;

/**
 * Scheduler2.java
 * Package:  rtgrid.scheduler
 * @author Xuan, 2007-1-1 
 */
public abstract class Scheduler implements IScheduler {

	public Cluster _cluster;
	public Vector _ATList;
	public int _curTime;
	
	public IOrder _order;
	public IDivisible _dlt;
	public SysVector _sysv;
	
	public Scheduler (Cluster c) {
		_cluster=c;
		_cluster.fHeadnodeFreeTime=0;
		_cluster.fHeadnodeFreeTime_ACT=0;
		_ATList=new Vector();
		_curTime=0;
		_sysv=c.GenSysVector();
	}
	

	
	/**
	 * 
	 * @param task
	 * @param ANList
	 */
	public int  Insert_ANList(Task task,Vector ANList) {
		int k=0;
		int num=0;
		boolean b=false;
		ANI ani;
		
		
		for (int i=0;i<ANList.size();i++) {
			ani=(ANI) ANList.elementAt(i);
			if (ani.time==task.getStartTime()) {
				if (ani.num<task.Nmin) {
					System.out.println("Error Insert-NL: no enough nodes");
					System.out.println(task);
					return -1;
				}
				k=i;
				num=ani.num;
				ani.num-=task.getNmin();
				//ani.num=0; //***
				for (int j=0; j<k;j++) { 
					ani=(ANI) ANList.elementAt(j);
					ani.num-=task.getNmin();
					
				}
				b=true;
				break;
			}
		}
		
		if (b) {  //find the node, the index is k
			int i;
			for (i=k+1;i<ANList.size();i++) {
				ani=(ANI) ANList.elementAt(i);
				num=ani.num;
				if  (ani.time<task.getCompletionTime())  {
					//num=ani.num;
					ani.num-=task.getNmin();
					//k++;

				}
				
				else {
					k=i;
					break;
				}
			}
			if (i==ANList.size()){
				ANI ani2 = new ANI(task.getCompletionTime(),num);
				ANList.add(i,ani2);
			}
			
			ani=(ANI)ANList.elementAt(i);
			if (ani.time==task.getCompletionTime()) {
				//ani.num-=task.Nmin;
			}
			else {
				//if (k!=ANList.size())k--;
				ani=(ANI)ANList.elementAt(i-1);
				ANI ani2 = new ANI(task.getCompletionTime(),ani.num+task.getNmin());
				ANList.add(k,ani2);
			}
			
			//Try to eliminate those node with num not greater than 0
			for (int s=0;s<ANList.size();s++) {
				ani=(ANI)ANList.elementAt(s);
				if (ani.num<0) {
					ani.num=0;
				}
			}
			
			
			//try to eliminate node with time less than the dispatchdone time
			int itime=-1;
			int index=-1;
			int inum=-1;
			boolean bLess=false;
			for (int s=0;s<ANList.size();s++) {
				ani=(ANI)ANList.elementAt(s);
				if ((ani.time<task.dispDoneTime) && (ani.num>0)) {
					inum=ani.num;
					ani.num=0;
					index=s;
					itime=ani.time;
					bLess=true;
				} else if (ani.time>task.dispDoneTime){
					break;
				}
				else if (ani.time==task.dispDoneTime) {
					bLess=false;
					break;
				}
			}
			
			if (bLess) {
//				ANI aniDisp=(ANI) ANList.elementAt(index);
//				aniDisp.time=task.dispDoneTime;
//				aniDisp.num=inum;
				
				ANI aniDisp=new ANI(task.dispDoneTime,inum);
				ANList.add(index+1, aniDisp);
				return 1;
			}
			
		}
		else {
			System.out.println("Error Insert-NL: starttime not found");
			System.out.println(task);
			return -1;
		}
		
		
		
		return 0;
	}
	
	
	

	/**
	 * Determine whether the task is schedulable
	 * @param task the current task that need to be tested its schedulablility
	 * @param cluster
	 * @param ATList
	 * @param curTime
	 * @return
	 */
	public abstract boolean Schedulability_Test(Task task, Vector ATList,int curTime);
	

	
	public SimResult run(int duration,  Vector NTList) {
		SimResult simrst=new SimResult();
		
		while (_curTime<=duration) {
			if (!NTList.isEmpty()){
				TaskList tlst=(TaskList) NTList.elementAt(0);
				if (tlst.time==_curTime) {
					Vector tasks=tlst.tasks;
					int queuelength=_ATList.size()+tasks.size();   //current length of the queue
					for (int i=0;i<tasks.size();i++) {
						Task task=(Task) tasks.elementAt(i);
						//if (task.maxQueLen<queuelength)  task.maxQueLen=queuelength; //updatequeue length
						_dlt.computeCompletionTime(_curTime, task, _sysv);
						//computeTaskTime(task,_curTime);
						//task.compute(curTime);
						simrst.log(_curTime,task,COMMON.TASK_ARRIVE);
					}
					
					tasks=_order.order(tasks);
					
				    //tasks=this.OrderByDeadline(tasks);
					
					for (int i=0;i<tasks.size();i++) {
						Task task=(Task) tasks.elementAt(i);	
						//TODO for several tasks come at the same time, should we pass all the tasks into Schedulability_Test and reschedule them?
						if (!Schedulability_Test(task, _ATList,_curTime)) {
							simrst.log(_curTime,task,COMMON.TASK_REJECT);
						}
						else {
							simrst.log(_curTime,task,COMMON.TASK_ADMIT);
						}
					}
					NTList.remove(0);
				}
			}
			
			
			if (! _ATList.isEmpty()) {
				for (int i=0;i<_ATList.size();i++) {
					Task task=(Task) _ATList.elementAt(i);
					if (task.getStartTime()==_curTime) {
						if (task.getNmin()>COMMON.PARA_CLUSTERSIZE) {
							System.out.println("Nmin is larger than the system size");
							System.out.println(task);
							System.exit(0);
						}
						
						_cluster.DispatchTask(task, simrst);
						
						_ATList.remove(i);
						i--;
					}
				}
			}
			_curTime++;
		}
		//System.out.println(cluster);
		//System.out.println();
		return simrst;
	}

}
