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
 * SchMNAbstract.java
 * Package:  rtgrid.scheduler
 * @author Xuan, 2007-1-1 
 */
public abstract class SchMNAbstract extends Scheduler {

	/**
	 * @param c
	 */
	public SchMNAbstract(Cluster c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see rtgrid.scheduler.Scheduler2#Schedulability_Test(rtgrid.taskmodel.Task, java.util.Vector, int)
	 */
	public boolean Schedulability_Test(Task task, Vector ATList, int curTime) {
	    int dispatchtime=_cluster.fHeadnodeFreeTime;
		if (COMMON.DEBUG==true) { // for debug only
			System.out.println(task);
			if (task.id==143) {
				System.out.println(_cluster);
			}
		}
		
		//		reschedule tasks in the admitted list and schedule the new task.
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
		
		int x=0; // x is the index of ANList;
		ANI ani=null;
		while (!USTList.isEmpty()) {
			ani=(ANI) ANList.elementAt(x);
	
			if (!computeMN(USTList,ani.time)) return false;
			
			for (int i=0;i<USTList.size();i++) {
				Task t = (Task) USTList.elementAt(i);
				if (t.getNmin()<=ani.num) {
					recalculateTask(t,ANList,x);
					//////////////////////////////////////////////////////////
					//
					// following codes try to solve the problem that several tasks are dispatched at the same time 
					//
					//////////////////////////////////////////////////////////////
					int dispatchwait=0;
					if (t.getStartTime()<dispatchtime) {
//						System.out.println("Dispatch Error");
//						System.out.println(task);
//						System.exit(0);
						dispatchwait=dispatchtime-t.getStartTime();
						t.setStartTime(t.getStartTime()+dispatchwait);
						t.setDispathDoneTime(t.getDispatchDoneTime()+dispatchwait);
						t.setCompletionTime(t.getCompletionTime()+dispatchwait);
						//Common.PARA_DISPATCHTIME=t.dispDoneTime;
						t.adjustDelay(dispatchwait);
					}				
					
				
					//if (t.getCompletionTime()>(t.getArrTime()+t.getRelativeDeadline())) {  //deadline miss because the dispatch delay
					if (t.isDeadlineMissed()) {  //deadline miss because the dispatch delay
							
						switch (assignMoreNodes(t,ani,dispatchwait)) {
						//System.out.println(t);
						case 0:
							break;
						case 1:
							continue;
						case 2:
							return false;
						}
					}
					
					dispatchtime=t.getDispatchDoneTime();
					//remove this task from USTList
					USTList.remove(i);
					i--;
					TempSTList.add(t);
					t.setStartTime(t.getStartTime()-dispatchwait);
					Insert_ANList(t,ANList);

					t.setStartTime(t.getStartTime()+dispatchwait);
					if (ani.num==0) {
						x++;
						break;
					}
				}
			}// end of for
			
			if (ani.num >= COMMON.PARA_CLUSTERSIZE) return false;  
			if (ani.num !=0) 	x++;
		} // end of  "while (!USTList.isEmpty()) {"
		
		//now the TempSTList stores the scheduled result, since it is schedulable, 
		//we need to copy it to ATList, the official place where store the admitted tasks.
		ATList.clear();
		
		ATList.addAll(TempSTList);
		
		return true;	
	}
	
	/**
	 * calculate the time parameter of the tasks according to the current time (typically, ani.time)
	 * @param tasks
	 * @param curtime
	 * @return depends on algorithm, some may want to decide the current task is unschedulable and then return false.
	 *         otherwise, return true;
	 */
	public abstract boolean computeMN(Vector tasks,int curtime);
	
	/**
	 * Try to assign more nodes to see if the task is schedulable
	 * @param task
	 * @param curtime
	 * @param dispatchwait
	 * @return    0 if the task can be scheduled by add more nodes to it
	 *            1 if the task can not be schedulable even all available nodes assign to it.
	 *            2 direct reject the current task that to be scheduled.
	 */
	public abstract int assignMoreNodes(Task task,ANI ani, int dispatchwait);
	
	
	/**
	 * recalculate the time parameter of the task if needed
	 * @param task
	 * @param ANList
	 * @param position the current position of ANList 
	 */
	public abstract void recalculateTask(Task task,Vector ANList ,int position);



}
