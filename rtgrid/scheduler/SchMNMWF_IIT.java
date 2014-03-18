/**
 * 
 */
package rtgrid.scheduler;

import java.util.Vector;

import rtgrid.resource.ANI;
import rtgrid.resource.Cluster;
import rtgrid.taskmodel.Task;

/**
 * SchMNMWF_IIT.java
 * Package:  rtgrid.scheduler
 * @author Xuan, 2007-1-8 
 */
public class SchMNMWF_IIT extends SchMN_IIT {

	/**
	 * @param c
	 */
	public SchMNMWF_IIT(Cluster c) {
		super(c);
		_order=new OrderMWF();
	}
	
	
	/* (non-Javadoc)
	 * @see rtgrid.scheduler.Scheduler2#Schedulability_Test(rtgrid.taskmodel.Task, java.util.Vector, int)
	 */
	public boolean Schedulability_Test(Task task, 
			Vector ATList, int curTime) {
		    int dispatchtime=_cluster.fHeadnodeFreeTime;
			
			//		reschedule tasks in the admitted list and schedule the new task.
			Vector USTList=new Vector();
			for (int i=0;i<ATList.size();i++) {
				Task ts=(Task) ATList.elementAt(i);
				Task ts2=new Task(ts);
				USTList.add(ts2);
			}

		    USTList.add(task);
		    
		    USTList=_order.order(USTList);
			//System.out.println(task);
			//build ANList according to NIList.
			Vector ANList=_cluster.generateANList(curTime);

			
//			System.out.println(task);
			if (task.getID()==32) {
				System.out.println(task);
			    _cluster.printANList(ANList);
			}
			
			//empty TempSTList.
			Vector TempSTList = new Vector();
			
			int x=0; // x is the index of ANList;
			ANI ani=null;
			while (!USTList.isEmpty()) {
				

				//if (!computeMN(USTList,ani.time)) return false;
				boolean nextNode=false; // see if x need to move to next node
				for (int i=0;i<USTList.size();i++) {
					Task t = (Task) USTList.elementAt(i);
					ani=(ANI) ANList.elementAt(x);
					_dlt.computeCompletionTime(ani.time, t, _sysv);
					if (t.getNmin()<=0) return false;
					nextNode=true;
					if (t.getNmin()<=ani.num) {
						recalculateTask(t,ANList,x);
						//////////////////////////////////////////////////////////
						//
						// following codes try to solve the problem that several tasks are dispatched at the same time 
						//
						//////////////////////////////////////////////////////////////
						int dispatchwait=0;
						if (t.getStartTime()<dispatchtime) {
							dispatchwait=dispatchtime-t.getStartTime();
							t.setStartTime(t.getStartTime()+dispatchwait);
							t.setDispathDoneTime(t.getDispatchDoneTime()+dispatchwait);
							t.setCompletionTime(t.getCompletionTime()+dispatchwait);
							//Common.PARA_DISPATCHTIME=t.dispDoneTime;
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
						
						x++;
						nextNode=false;
					}
				}// end of for
				if (nextNode)  {
					ANI anitmp;
					do {
						x++;
						anitmp=(ANI)ANList.elementAt(x);
					} while (anitmp.num==0) ;
				
				}
				//if (ani.num >= COMMON.PARA_CLUSTERSIZE) return false;  
//				if (ani.num !=0) 	
//					x++;
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
	public boolean computeMN(Vector tasks, int curtime) {
		for (int i=0;i<tasks.size();i++) {
			Task t=(Task)tasks.elementAt(i);
			_dlt.computeCompletionTime(curtime, t, _sysv);
			if ((t.getNmin()<=0) || (t.getNmin()>_cluster.size)) 
				return false;			
		}
		return true;
	}

}
