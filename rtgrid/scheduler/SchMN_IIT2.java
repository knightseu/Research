/**
 * 
 */
package rtgrid.scheduler;

import java.util.Vector;

import rtgrid.COMMON;
import rtgrid.resource.ANI;
import rtgrid.resource.Cluster;
import rtgrid.resource.Machine;
import rtgrid.taskmodel.SubTask;
import rtgrid.taskmodel.Task;
import rtgrid.taskmodel.TaskList;
import rtgrid.util.SimResult;
import rtgrid.util.SysVector;

/**
 * SchMN_IIT2.java
 * Package:  rtgrid.scheduler
 * @author Xuan, 2007-1-14 
 */
public class SchMN_IIT2 extends Scheduler implements IDispatch {
	protected IDivisible _dlt_hetero;
	/**
	 * @param c
	 */
	public SchMN_IIT2(Cluster c) {
		super(c);
		_order=new OrderEDF();  
		_dlt=new DivHomoSingle();
		_dlt_hetero=new DivHeteroSingle();
	}

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
						
						//_cluster.DispatchTask(task, simrst);
						this.Dispatch(_cluster, task, simrst);
						
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
				System.out.println(task);
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
				if (ani.num!=0)
					return true;
			}
			return false;
		}

		/* (non-Javadoc)
		 * @see rtgrid.scheduler.IDispatch#Dispatch(rtgrid.resource.Cluster, rtgrid.taskmodel.Task, rtgrid.util.SimResult)
		 */
		public void Dispatch(Cluster cluster, Task task, SimResult simrst) {
			System.out.println(cluster);
			SysVector sysv=cluster.GenSysVector();
			Vector subtasks=task._SubTasks;
			int k,num,iiTime,i;
			k=num=subtasks.size();iiTime=0;i=0;
			double iitratio=0;
			simrst.log(task.getStartTime(),task,COMMON.TASK_DISPATCH);
			
			while (k>0) {
				if (k==0) {
					cluster.fHeadnodeFreeTime=task.getDispatchDoneTime();
					iitratio=iiTime*1.0/(task.getCompletionTime()-task.getStartTime());
					simrst.logIIT(iitratio);
					return;
				}
				Machine mac= cluster.getEarliestMachine();

				      int iit_start=0,iit=0;
					  if (mac.getEndTime()>task.getArrTime())
						  iit_start=mac.getEndTime();
					  else
						  iit_start=task.getArrTime();
					  iit=task.getStartTime()-iit_start;
					  
					  iiTime+=iit;
				
			
				//dispatch subtask
				
				SubTask subT=(SubTask)subtasks.elementAt(num-k);
				subT._sTime=Math.max(Math.max(mac.getEndTime(), task.arrTime),cluster.fHeadnodeFreeTime);
				subT._aTime=subT._sTime+subT._datasize*sysv.getCms();
				subT._compTime=subT._aTime+subT._datasize*sysv.getCps();
				cluster.fHeadnodeFreeTime=subT._aTime;
				mac.assignTask(subT);
				k--;
				i++;
					
				//System.out.println(cluster);
			}
			cluster.fHeadnodeFreeTime=task.getDispatchDoneTime();
			//System.out.println();
			
		}

    
	
	
}
