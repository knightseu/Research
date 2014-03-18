/**
 * 
 */
package rtgrid.scheduler;

import java.util.Vector;

import rtgrid.COMMON;
import rtgrid.resource.ANI;
import rtgrid.resource.Cluster;
import rtgrid.taskmodel.SubTask;
import rtgrid.taskmodel.Task;
import rtgrid.util.SysVector;

/**
 * SchMN_IIT.java
 * Package:  rtgrid.scheduler
 * @author Xuan, 2007-1-3 
 */
public class SchMN_IIT extends Scheduler {
	public int iitnum;
	public int optiitnum;
	protected IDivisible _dlt_hetero;
	/**
	 * @param c
	 */
	public SchMN_IIT(Cluster c) {
		super(c);
		_order=new OrderFIFO();  
		_dlt=new DivHomoSingle();
		_dlt_hetero=new DivHeteroSingle();
		iitnum=0;
		optiitnum=0;
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

			
			//System.out.println(task);
			if (task.getID()==80) {
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
		
		/**
		 * Try to assign more nodes to see if the task is schedulable
		 * @param task
		 * @param curtime
		 * @param dispatchwait
		 * @return    0 if the task can be scheduled by add more nodes to it
		 *            1 if the task can not be schedulable even all available nodes assign to it.
		 *            2 direct reject the current task that to be scheduled.
		 */
	public int assignMoreNodes(Task t, ANI ani, int dispatchwait) {
		if (t.getNmin()<ani.num) {
			while ((t.getNmin()<ani.num) && t.isDeadlineMissed() )  {
				_dlt.computeCompletionTime(ani.time+dispatchwait, t.getNmin()+1, t, _sysv);	
			}
			if (t.isDeadlineMissed()) return 1;
		}
		else 
			return 1;
		return 0;
	}
		
		/**
		 * recalculate the time parameter of the task if needed, in this case, if there is inserted-idle-time.
		 * @param task
		 * @param ANList
		 * @param position the current position of ANList 
		 */
		public void recalculateTask(Task task,Vector ANList ,int position){
            int num=0;
			if (existIIT(ANList,position)) {
				//System.out.println(task);
				           iitnum++;
				           if (existOptIIT(ANList,position,task.getDatasize()*_sysv.getCms())) 
				        	                            optiitnum++;
                int Nmin=task.Nmin;
                ANI node_n=(ANI) ANList.elementAt(position);
                int t_n=node_n.time;
                int c=task.getExeTime();  // the original cost without removing IIT
                
				////////////////////////////////build cplist
	            SysVector heteroSysV=new SysVector(Nmin);
	            heteroSysV.setCms(_sysv.getCms());
				int k=0;
				for (int i=0;i<=position;i++) {
					ANI ani = (ANI) ANList.elementAt(i);
					if (ani.num==0)
						continue;
					if (i==0)
						num=ani.num;
					else {
						ANI ani_pre=(ANI) ANList.elementAt(i-1);
						num=ani.num-ani_pre.num;
					}
					for (int j=0;j<num;j++) {
						int p=t_n-ani.time;
						int cps=(int)Math.ceil(1.0*_sysv.getCps()*c/(p+c));
						heteroSysV.setCps(k, cps);
						k++;
						if (k>=Nmin) break;
					}
					
				}
				
				//System.out.println(heteroSysV);
				
	            /////////////////////////////////calculate the estimated time
				int t_est=_dlt_hetero.computeCost(task.Nmin, task, heteroSysV);		
				//System.out.println("estimated cost:"+t_est);
				
				/////////////////////////////////adjust the task time.
				task.compTime=task.startTime+t_est;
				//System.out.println("estimated time:"+task.compTime);
				////////////////////deal with dispatchdone time
				int []datasize=new int[Nmin];
				for (int i=0;i<Nmin;i++) {
					datasize[i]=0;
				}
				
				
				/////////////////////////////////adjust the subtasks.
				for (int i=0;i<task._SubTasks.size();i++) {
					SubTask subt=(SubTask) task._SubTasks.elementAt(i);
					subt._compTime=t_est;
				}
			}
			else
				return;
		}
		
		/**
		 * check if there are any Inserted-Idle-Time
		 * @param ANList
		 * @param position
		 * @return
		 */
		protected boolean existIIT(Vector ANList,int position) {
			if (position>ANList.size()) {
				System.out.println();
				System.exit(0);
			}
			for (int i=0;i<position;i++) {
				ANI ani = (ANI) ANList.elementAt(i);
				if (ani.num!=0) {
					//System.out.println("IIT"+ani.time);
					return true;
				}
			}
			return false;
		}
		
		/**
		 * check if there are any Inserted-Idle-Time
		 * @param ANList
		 * @param position
		 * @return
		 */
		protected boolean existOptIIT(Vector ANList,int position,int bound) {
			if (position>ANList.size()) {
				System.out.println();
				System.exit(0);
			}
			int r1=0;
			boolean b=false;
			for (int i=0;i<position;i++) {
				ANI ani = (ANI) ANList.elementAt(i);
				if (ani.num==0){
					 if (!b)
					      continue;
				   else
					     return false; //some node in the  middle has num=0
				}
				if (ani.num!=1) {
					//System.out.println("IIT"+ani.time);
					return false; // more than 1 processors are available at the same time;
				}
				else {//ani.num==1
					 if (!b) {
						 r1=ani.time;
						 b=true;
					 }
					 else {
						 if ( (ani.time-r1)<bound )
							   return false; // bound not hold
						 else
							     r1=ani.time;
					 }
				}
			}
			return true;
		}

}
