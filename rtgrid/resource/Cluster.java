/**
 * 
 */
package rtgrid.resource;

import java.io.PrintStream;
import java.util.Vector;

import rtgrid.COMMON;
import rtgrid.taskmodel.SubTask;
import rtgrid.taskmodel.Task;
import rtgrid.util.SimResult;
import rtgrid.util.SysVector;

/**
 * Cluster.java
 * Package:  rtgrid.resource
 * @author Xuan, 2006-10-17 
 */
public class Cluster implements IResource {

    /**
     * the time when the headnode is free. (is able to dispatch the data)
     */
	public int fHeadnodeFreeTime=0;
	public int fHeadnodeFreeTime_ACT=0;
	
	public int size; //number of worker nodes;
	public int bandwidth; 
	public Vector macs;
	public Vector links;
	private int longtime=2000000000;
	public int EAT=0; //Earliest Available Time
	public Cluster(int num, int speed, int bandwidth) {
		size=num;
		this.bandwidth=bandwidth;
		
		macs=new Vector();
		links=new Vector();
		for (int i=0;i<size;i++) {
			Machine mac = new Machine (i);
			mac.bDynamic=false;
			mac.speed=speed;
			macs.add(mac);
			//Link link = new Link(i,bandwidth);
			//links.add(link);
		}
	}
	public Cluster (Cluster cl) {
		size=cl.size;
		this.bandwidth=cl.bandwidth;
		macs=new Vector();
		for (int i=0;i<size;i++) {
			Machine mac2=cl.getMachine(i);
			Machine mac = new Machine (mac2);
			macs.add(mac);
		}
	}
	
	public int getEarliestTime(){
		int EST= longtime;
		for (int i=0;i<size;i++) {
			Machine mac = (Machine) macs.elementAt(i);
			if (mac.getEndTime()<EST)
				EST=mac.getEndTime();
		}
		return EST;
	}
	
	public Machine getEarliestMachine() {
		Machine mac=null;
		int EST= longtime;
		for (int i=0;i<size;i++) {
			Machine mac2 = (Machine) macs.elementAt(i);
			if (mac2.getEndTime()<EST) {
				EST=mac2.getEndTime();
			    mac=mac2;
			}
		}
		return mac;
	}
	public int getLatestTime(){
		int LST= 0;
		for (int i=0;i<size;i++) {
			Machine mac = (Machine) macs.elementAt(i);
			if (mac.getEndTime()>LST)
				LST=mac.getEndTime();
		}
		return LST;
	}
	public int getAvailableMachine(int time) {
		int rst=0;
		for (int i=0;i<size;i++) {
			Machine mac = (Machine) macs.elementAt(i);
			if (mac.isAvailable(time)) rst++;
		}
		return rst;
	}
	
	public Machine getMachine(int i) {
		return (Machine) macs.elementAt(i);
	}
	
//	public Link getLink (int i) {
//		return (Link) links.elementAt(i);
//	}
	
	public int getMachineSpeed(int i) {
		Machine mac=this.getMachine(i);
		return mac.getSpeed();
	}
	
//	public int getLinkSpeed(int i) {
//		Link link=this.getLink(i);
//		return link.getSpeed();
//	}
	
	public String getType() {
		return "Cluster";
	}
	
	/* (non-Javadoc)
	 * @see rtgrid.resource.IResource#accept(rtgrid.resource.IResourceVisitor)
	 */
	
	public void accept(IResourceVisitor visitor) {
		visitor.visitCluster(this);
	}
	
	public Vector generateANList(int curTime){
		if (curTime<this.fHeadnodeFreeTime)
			curTime=this.fHeadnodeFreeTime;
		Vector data = new Vector();
		for (int i=0;i<size;i++) {
			Machine mac= this.getMachine(i);
			int time=mac.getEndTime();
			if (time<curTime) time=curTime;
			boolean b=false;
			for (int j=0;j<data.size();j++) {
				ANI ani=(ANI) data.elementAt(j);
				if (time==ani.time) {// already have this node
					ani.num++;
					b=true; 
					break;
				}
			}
			if (! b) { // donot have this node now, insert it in order
				ANI ani =new ANI (time,1);
				boolean c=false;
				for (int j=0;j<data.size();j++) {
					ANI tmp=(ANI) data.elementAt(j);
					if (ani.time<tmp.time) {
						data.insertElementAt(ani,j);
						c=true;
						break;
					}
					//else ani.num+=tmp.num;
				}
				if (!c) {
					data.insertElementAt(ani,data.size());
				}
			}
			
		}
		for (int j=1;j<data.size();j++) {
			ANI tmp=(ANI) data.elementAt(j);
			ANI tmp2=(ANI) data.elementAt(j-1);
			tmp.num+=tmp2.num;
		}
		return data;
	}
	
	public boolean isMachingAvailable(int index,int time) {
		Machine mac =this.getMachine(index);
		return mac.isAvailable(time);
	}
	
	
	
	public boolean isAllnodeAvailable(int time) {
		
		for (int i=0;i<this.size;i++)
			if (!this.isMachingAvailable(i,time)) 
				return false;
		return true;
	}
	
	/**
	 * print ANList to Screen
	 * @param ANList
	 */
	public void printANList(Vector ANList) {
		System.out.println("~~~~~~~~~~~~~~~~~~~");
		System.out.println("Begin to print ANList");
		System.out.println("tx	:	num");
		for (int i=0;i<ANList.size();i++) {
			ANI ani= (ANI) ANList.elementAt(i);
			System.out.println(ani.time+"	:	"+ani.num);
		}
		System.out.println("End of printing ANList");
	}
	
	/**
	 * print ANList to file
	 * @param ANList
	 * 
	 */
	public void printANList(Vector ANList,PrintStream p) {
		System.out.println("~~~~~~~~~~~~~~~~~~~");
		System.out.println("Begin to print ANList");
		System.out.println("tx	:	num");
		for (int i=0;i<ANList.size();i++) {
			ANI ani= (ANI) ANList.elementAt(i);
			p.println(ani.time+"	:	"+ani.num);
		}
		System.out.println("End of printing ANList");
		
	}
	
	
	
	public String toString(){
		String rst="\nSystem:\n";
		for (int i=0;i<macs.size();i++) {
			Machine mac=this.getMachine(i);
			rst+="(node "+i+", "+"starttime "+mac.curOccupiedTime+", endtime"+mac.getEndTime()+")\n";
		}
		return rst;
	}
	
	
	
	
	/**
	 * Dispatch according to the actual situation of the cluster
	 * @param task, the task that need to be dispatched
	 * @param type, the type of the scheduling algorithm
	 */
	
	public boolean Act_Dispatch(Task task, int type){

//		int N=task.getNmin();
//		if (N>COMMON.PARA_CLUSTERSIZE) {
//			//System.out.println(task.id+"; N="+task.Nmin);
//			N=COMMON.PARA_CLUSTERSIZE;	
//		}
//		int num=0;
//		long time=200000000;
//		//System.out.println("-enter-"+task.id);
//		for (int i=0;i<size;i++) {
//			Machine mac = this.getMachine(i);
//			mac.b=false;
//		}
//		while (num<N) {
//			//System.out.println("-enter-"+task.id+"num:"+num);
//			time=200000000; //let time large enough
//			//find the first available time
//			for (int i=0;i<size;i++) {
//				Machine mac = this.getMachine(i);
//				if ((mac.curEndTime<time) && (!mac.b))
//					time=mac.curEndTime;
//			}
//			
//			//begin to dispatch
//			for (int i=0;i<size;i++) {
//				Machine mac = this.getMachine(i);
//				if ((mac.curEndTime==time) && (!mac.b)){
//					mac.b=true;
//					num++;
//					if (num==N) {//find enough nodes to dispatch
//						//first, calculate the real completion time
//						if (task.getStartTime() < time) 
//							task.startTime=(int)time;
//						if (task.startTime < COMMON.PARA_DISPATCHTIME_ACT)
//							task.startTime=COMMON.PARA_DISPATCHTIME_ACT;
//						switch (type) {
//						case COMMON.SCH_FIFO_AN:
//							break;
//						case COMMON.SCH_FIFO_MN_DLT_MULTI:
//							break;
//						case COMMON.SCH_FIFO_MN_DLT_SINGLE:
//							break;
//						case COMMON.SCH_FIFO_MN_SIMPLE:  //  FIFO, MN, simple heuristic
//							task.computeCompletionTime(N,COMMON.SCH_FIFO_MN_SIMPLE_ACT);
//							break;
//						case COMMON.SCH_FIFO_MN_SIMPLE2:  //  FIFO, MN, simple heuristic2
//							task.computeCompletionTime(N,COMMON.SCH_FIFO_MN_SIMPLE2_ACT);
//							break;
//						case COMMON.SCH_EDF_AN:
//							break;
//						case COMMON.SCH_EDF_AN_SIMPLEHEU1:
//							task.compute(task.startTime,COMMON.SCH_EDF_AN_SIMPLEHEU1_ACT);
//							break;
//						case COMMON.SCH_EDF_MN:
//							break;
//						case COMMON.SCH_EDF_MN_SIMPLE3:
//							//task.computeCompletionTime(N,COMMON.SCH_FIFO_MN_SIMPLE2_ACT);
//							break;
//						case COMMON.SCH_MCDF:
//							break;
//						case COMMON.SCH_MCDF_COMPARE:
//							task.computeCompletionTime(N,COMMON.SCH_MCDF);
//							break;
//						default:
//							System.out.println("No such scheduling algorithm");
//						break;
//						}
//						
//						
//						int cTime=task.getCompletionTime();
//						
//						if  ( (type==COMMON.SCH_FIFO_MN_SIMPLE) || (type==COMMON.SCH_FIFO_MN_SIMPLE2) 
//								 || (type==COMMON.SCH_EDF_AN_SIMPLEHEU1) ) {
//							int k=0;
//							for (int j=0;j<size;j++) {
//								Machine m=getMachine(j);
//								if (m.b) {
//									//task.compTime+=netdelay;
//									task.compTime=(int)Math.ceil((k+1)*task.datasize*task.Cms*1.0/N+task.datasize*task.Cps*1.0/N+task.startTime);
//									SubTask subT=new SubTask(j,task);
//									m.assignTask(subT);
//									k++;
//								}
//							}
//							COMMON.PARA_DISPATCHTIME_ACT=task.dispDoneTime;
//						}
//						else if (type==COMMON.SCH_EDF_MN_SIMPLE3) {
//							int k=0;
//							for (int j=0;j<size;j++) {
//								Machine m=getMachine(j);
//								if (m.b) {
//									//task.compTime+=netdelay;
//									task.compTime=(int)Math.ceil((k+1)*task.datasize*task.Cms*1.0/N+task.datasize*task.Cps*1.0/N+task.startTime);
//									SubTask subT=new SubTask(j,task);
//									m.assignTask(subT);
//									k++;
//								}
//							}
//							COMMON.PARA_DISPATCHTIME=task.dispDoneTime;
//						}
//						else
//							//begin to dispatch
//							for (int j=0;j<size;j++) {
//								Machine m=getMachine(j);
//								if (m.b) {
//									SubTask subT=new SubTask(j,task);
//									m.assignTask(subT);
//								}
//							}
//						//System.out.println("exit");
//						task.compTime=cTime;
//						return (cTime<=(task.arrTime+task.deadline));
//					}
//				}
//				
//			}
//		}
		return true;
	}
	
	
	
	/**
	 * 
	 * @param task
	 * @param cluster
	 * @param simrst
	 */
	public void DispatchTask(Task task, SimResult simrst) {
		Vector subtasks=task._SubTasks;
		int k,num,iiTime,i;
		k=num=subtasks.size();iiTime=0;i=0;
		double iitratio=0;
		simrst.log(task.getStartTime(),task,COMMON.TASK_DISPATCH);
		
		while (k>0) {
			if (k==0) {
				this.fHeadnodeFreeTime=task.getDispatchDoneTime();
				iitratio=iiTime*1.0/(task.getCompletionTime()-task.getStartTime());
				simrst.logIIT(iitratio);
				return;
			}
			Machine mac= this.getEarliestMachine();

			      int iit_start=0,iit=0;
				  if (mac.getEndTime()>task.getArrTime())
					  iit_start=mac.getEndTime();
				  else
					  iit_start=task.getArrTime();
				  iit=task.getStartTime()-iit_start;
				  
				  iiTime+=iit;
			
		
			//dispatch subtask
			SubTask subT=(SubTask)subtasks.elementAt(num-k);
			mac.assignTask(subT);
			k--;
			i++;
				
			//System.out.println(cluster);
		}
		this.fHeadnodeFreeTime=task.getDispatchDoneTime();
		//System.out.println();
	}
	
	
	
	
	/**
	 * currently, only support homogeneous system
	 * @return
	 */
	public SysVector GenSysVector(){
		//TODO currently, only support homogeneous system
		SysVector sysv=new SysVector(this.size);
		sysv.setCms(COMMON.PARA_CMS);
		sysv.setCps(COMMON.PARA_CPS);
		return sysv;
		
	}
}
