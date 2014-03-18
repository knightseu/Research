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

/**
 * SchSplitNoPA.java
 * Package:  rtgrid.scheduler
 * @author Xuan, 2007-1-9 
 */
public class SchSplitNoPA extends Scheduler {
	int _preid;

	/**
	 * @param c
	 */
	public SchSplitNoPA(Cluster c) {
		super(c);
		_order=new OrderEDF();
		_dlt=new DivSplit();
		//_sysv= c.GenSysVector();
	}


	public SimResult run(int duration,  Vector NTList) {
		SimResult simrst=new SimResult();

		while (_curTime<=duration) {
			if (!NTList.isEmpty()){
				TaskList tlst=(TaskList) NTList.elementAt(0);
				if (tlst.time==_curTime) {
					Vector tasks=tlst.tasks;
					for (int i=0;i<tasks.size();i++) {
						Task task=(Task) tasks.elementAt(i);
						simrst.log(_curTime,task,COMMON.TASK_ARRIVE);
					}
					if (!Schedulability_Test(tasks, _ATList,_curTime)) {
						for (int i=0;i<tasks.size();i++) {
							Task task=(Task) tasks.elementAt(i);
							simrst.log(_curTime,task,COMMON.TASK_REJECT);
						}

					}
					else {
						for (int i=0;i<tasks.size();i++) {
							Task task=(Task) tasks.elementAt(i);
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


	/* (non-Javadoc)
	 * @see rtgrid.scheduler.Scheduler2#Schedulability_Test(rtgrid.taskmodel.Task, java.util.Vector, int)
	 */
	public boolean Schedulability_Test(Task task, Vector ATList, int curTime) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean Schedulability_Test(Vector tasks, Vector ATList, int curTime) {
		int dispatchtime=_cluster.fHeadnodeFreeTime;

		//		reschedule tasks in the admitted list and schedule the new task.
		
		Task kkk=(Task)tasks.lastElement();
		//System.out.println(kkk);
		Vector USTList=new Vector();
		for (int i=0;i<ATList.size();i++) {
			Task ts=(Task) ATList.elementAt(i);
			Task ts2=new Task(ts);
			USTList.add(ts2);
		}

		USTList.addAll(tasks);

		USTList=_order.order(USTList);
		//System.out.println(task);
		//build ANList according to NIList.
		Vector ANList=_cluster.generateANList(curTime);
        
		if (kkk.id==28) {
			System.out.println(kkk);
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


					if (t.isDeadlineMissed()) {  //deadline miss because the dispatch delay
//						System.out.println("Deadline miss because of the dispatchwait.");
//						for (int k=0;k<tasks.size();k++) {
//							Task task=(Task) tasks.elementAt(k);
//							System.out.println(task);
//						}
						return false;
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
//			if (ani.num !=0) 	
//			x++;
		} // end of  "while (!USTList.isEmpty()) {"

		//now the TempSTList stores the scheduled result, since it is schedulable, 
		//we need to copy it to ATList, the official place where store the admitted tasks.
		ATList.clear();

		ATList.addAll(TempSTList);

		return true;	
	}

}
