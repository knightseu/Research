/**
 * 
 */
package rtgrid.scheduler;

import java.util.Vector;

import rtgrid.COMMON;
import rtgrid.resource.ANI;
import rtgrid.resource.Cluster;
import rtgrid.taskmodel.Task;

/**
 * SchAN.java
 * Package:  rtgrid.scheduler
 * @author lxuan, 2007-4-11 
 * 
 * FIFO-OPR-AN
 * 
 */
public class SchAN extends Scheduler {

	/**
	 * @param c
	 */
	public SchAN(Cluster c) {
		super(c);
		_order=new OrderFIFO();  
		_dlt=new DivHomoSingle();
	}

	/* (non-Javadoc)
	 * @see rtgrid.scheduler.Scheduler2#Schedulability_Test(rtgrid.taskmodel.Task, java.util.Vector, int)
	 */
	public boolean Schedulability_Test(Task task,Vector ATList,int curTime) {
		//reschedule tasks in the admitted list and schedule the new task.
		//Vector USTList=(Vector) ATList.clone();
		Vector USTList=new Vector();
		for (int i=0;i<ATList.size();i++) {
			Task ts=(Task) ATList.elementAt(i);
			Task ts2=new Task(ts);
			USTList.add(ts2);
		}
		USTList.add(task);
		USTList=_order.order(USTList);
		
		//build ANList according to NIList.
		Vector ANList=_cluster.generateANList(curTime);
		

		//empty TempSTList.
		Vector TempSTList = new Vector();
		
		
		for (int i=0;i<USTList.size();i++) {
			Task t=(Task) USTList.elementAt(i);
			ANI ani=(ANI) ANList.elementAt(0);
			_dlt.computeCompletionTime(curTime, COMMON.PARA_CLUSTERSIZE, task, _sysv);
			//task.computeByNodes(ani.time, COMMON.PARA_CLUSTERSIZE, COMMON.SCH_FIFO_AN_OPR_Single);
			//System.out.println(t);
		
	    if (! t.isDeadlineMissed()) {
	        	ani.time=t.getCompletionTime();
	        	TempSTList.add(TempSTList.size(),t);
	        }
			else {
				return false;
			}
		}
		
		//now the TempSTList stores the scheduled result, since it is schedulable, 
		//we need to copy it to ATList, the official place where stores the admitted tasks.
		
		ATList.clear();
		ATList.addAll(TempSTList);

		return true;
	}

}
