package rtgrid.taskmodel;

import java.util.Collection;
import java.util.Vector;

import javax.swing.text.html.HTMLDocument.Iterator;

import rtgrid.COMMON;
import rtgrid.util.SysVector;
/**
 * Task.java
 * Package:  rtgrid.taskmodel
 * @author Xuan, 2006-11-2 
 */

public class Task implements ITask, IDivisibleTask {
	/**
	 * id of the task.
	 */
	public int id;
	public int deadline; // absolute deadline
	public int datasize; // the size of the data
	public int o_datasize; // the original datasize;
	
	public int arrTime; // the time when the system is submit to the system
	public int startTime; // the time when the task is dispatched
	public int dispDoneTime; // the time when task has arrived the processors and ready to begin computation
	public int compTime; // the time when the task is finished
	public int Nmin; // the minimum number of nodes it need to complete before deadline
	public int Nact; // number of machines actually assigned to it.
	public double DC; // derative cost
	
	//public int maxQueLen=0;
	
	public Vector _SubTasks=new Vector();
	
	//following parameters are used for calculate divisible load;
	public double alpha1=0,beta=0,gamma=0,phi=0;
	
	public int Cms; 
	public int Cps;
	
	public int ST;
	public int SC;
	
	
	public void addSubTask(SubTask t) {
		_SubTasks.add(t);
	}
	
	public void setCmsCps(int cms, int cps) {
		this.Cms=cms;
		this.Cps=cps;
		beta=Cps*1.0/(Cps+Cms);
		gamma=1-datasize*1.0*Cms/deadline;
	}
	
	public int getST() {
		return ST;
	}
	
	public int getSC() {
		return SC;
	}
	
	public int getCms() {
		return Cms;
	}
	
	public int getCps() {
		return Cps;
	}
	
	public void setSetCost(int st, int sc) {
		this.SC=sc;
		this.ST=st;
	}
	
	public int getAbsoluteDeadline() {
		return this.deadline+this.arrTime;
	}

	public int getArrTime() {
		return this.arrTime;
	}

	public int getCompletionTime() {
		return this.compTime;
	}
	
	public void setCompletionTime(int time) {
		this.compTime=time;
	}

	public int getExeTime() {
		
		return this.compTime-this.startTime;
	}

	public int getID() {
		
		return this.id;
	}

	public int getNodesAct() {
		
		return this.Nact;
	}

	public int getRelativeDeadline() {
		
		return this.deadline;
	}

	public int getStartTime() {
		
		return this.startTime;
	}
	
	
	public void setStartTime(int time) {
		this.startTime=time;
	}

	public int getDatasize() {
		
		return this.datasize;
	}

	public int getNmin() {
		return this.Nmin;
	}
	
	public double getDC() {
		return this.DC;
	}
	public void setNmin(int num) {
		this.Nmin=num;
	}
	

	public Task(Task task) {
		this.id=task.id;
		this.deadline=task.deadline; // absolute deadline
		this.datasize=task.datasize; // the size of the data	
		this.o_datasize=this.o_datasize;
		this.arrTime=task.arrTime; // the time when the system is submit to the system
		this.startTime=task.startTime; // the time when the task is dispatched
		this.compTime=task.compTime; // the time when the task is finished
		this.Nmin=task.Nmin; // the minimum number of nodes it need to complete before deadline
		this.Nact=task.Nact; // number of machines actually assigned to it.
		this.DC=task.DC;
		this.dispDoneTime=task.dispDoneTime;
		
		
		//following parameters are used for calculate divisible load;
		this.alpha1=task.alpha1;
		this.beta=task.beta;
		this.gamma=task.gamma;
		this.phi=task.phi;
		
		this.Cms=task.Cms; 
		this.Cps=task.Cps;
		
		this.ST=task.ST;
		this.SC=task.SC;
		
	}
	
	public Task(int id,int arrTime, int deadline, int datasize){
		this.id=id;
		this.arrTime=arrTime;
		this.o_datasize=this.datasize=datasize;
		this.deadline=deadline;
		Nmin=0;
		Nact=0;
		
		Cms=COMMON.PARA_CMS;
		Cps=COMMON.PARA_CPS;
		beta=Cps*1.0/(Cps+Cms);
		gamma=1-datasize*1.0*Cms/deadline;
		
		ST=0;
		SC=0;
		
		phi=1.0*ST/(this.datasize*(Cms+Cps));
	}
	
	public Task(int id,int arrTime, int deadline, int datasize, int ST, int SC){
		this.id=id;
		this.arrTime=arrTime;
		this.o_datasize=this.datasize=datasize;
		this.deadline=deadline;
		Nmin=0;
		Nact=0;
		
		Cms=COMMON.PARA_CMS;
		Cps=COMMON.PARA_CPS;
		beta=Cps*1.0/(Cps+Cms);
		gamma=1-datasize*1.0*Cms/deadline;
		
		this.SC=SC;
		this.ST=ST;
		
		phi=1.0*ST/(this.datasize*(Cms+Cps));
	}
	
	/**
	 * compute Nmin, the minimum number of nodes the task need to finish before its deadline at time "curTime";
	 * (This function replace with the function "compute" of the previous version)
	 * @param curTime, the current time
	 * @param type, Single round or UMR
	 * @return Nmin the minimum number of nodes the task need to finish before its deadline at time "curTime".
	 *         -1 if the task will definetely miss its deadline at this thime.
	 */
	public int computeNmin(int curTime, int type) {
		if (curTime>=this.arrTime)
			this.startTime=curTime;
		else
			this.startTime=this.arrTime;
		switch (type) {
		case COMMON.SCH_MWF_MN_OPR_Single:
		case COMMON.SCH_EDF_AN_OPR_Single:
		case COMMON.SCH_EDF_MN_OPR_Single:
		case COMMON.SCH_FIFO_AN_OPR_Single:
		case COMMON.SCH_FIFO_MN_OPR_Single:
			if ( (ST==0) && (SC==0) ) {
				
				phi=1.0*ST/(this.datasize*(Cms+Cps));
				gamma=1-datasize*Cms*1.0/(deadline-curTime+arrTime);
				double t1=Math.log(gamma);
				beta=Cps*1.0/(Cms+Cps);
				double t2=Math.log(beta);
				Nmin=(int)Math.ceil(t1/t2);
			}
			else {
				//TODO the computation time of Nmin can be decreased to be logN
				int rst=0;
				phi=1.0*ST/(this.datasize*(Cms+Cps));
				beta=Cps*1.0/(Cms+Cps);
				if (Cms!=0) {
					do {
						rst++;	
						double beta1=1-Math.pow(beta,rst);
						compTime=(int) Math.ceil (startTime+ ST+ SC+datasize*1.0*(Cms+Cps)* ((1-beta)/beta1 )+ rst*phi/beta1- phi/(1-beta) ); // +(1-Math.pow(beta,rst)) ;

					} while ((compTime>(this.deadline+this.arrTime)) && (rst<COMMON.PARA_CLUSTERSIZE) );
				}
				else {
					do {
						rst++;
						compTime=(int) Math.ceil (startTime+ ST+ SC+datasize*1.0*(Cms+Cps)/rst);
					} while ((compTime>(this.deadline+this.arrTime)) && (rst<COMMON.PARA_CLUSTERSIZE) );
				}
				Nmin=rst;
			}
			break;
		case COMMON.SCH_MWF_MN_OPR_UMR:
		case COMMON.SCH_EDF_AN_OPR_UMR:
		case COMMON.SCH_EDF_MN_OPR_UMR:
		case COMMON.SCH_FIFO_AN_OPR_UMR:
		case COMMON.SCH_FIFO_MN_OPR_UMR:
			if ( (ST==0) && (SC==0) ) {
				int rDeadline=this.deadline-curTime+this.arrTime;
				long delta;
				delta=(long)rDeadline*rDeadline-2*Cms*Cps*datasize;
			
				if (delta<0)
					this.Nmin=-1;
				else if (delta==0) {
					if ((rDeadline % Cms)==0) 
						Nmin=rDeadline/Cms;
					else
						Nmin=-1;
				}
				else { //delta>0
					int root1,root2;
					root1=(int)Math.ceil(1.0*(rDeadline-Math.sqrt(delta))/Cms);
					root2=(int)Math.floor(1.0*(rDeadline+Math.sqrt(delta))/Cms);
					if (root2<=0) {
						Nmin=-1;
						break;
					}
					else {
						if (root1>=1) Nmin=root1;
						else Nmin=1;
					}
				}
			}
			else { //
			
			}
			break;
		case COMMON.SCH_USERSPLIT:
			this.Nmin=1;
			break;
		
		case COMMON.SCH_FIFO_MN_SIMPLE:  //  FIFO, MN, simple heuristic
			double t1=1.0*this.datasize*(Cms+Cps)/(this.deadline+this.arrTime-curTime);
			this.Nmin=(int) Math.ceil(t1);
			break;
		case COMMON.SCH_FIFO_MN_SIMPLE2:  //  FIFO, MN, simple heuristic2
			double t2=1.0*this.datasize*(Cms+Cps)/(this.deadline+this.arrTime-curTime);
			int t=(int) Math.ceil(t2);
			int N=0;
			if (t!=1) {
				t-=1;
				while (t!=0) {
					N++;
					t=t/2;
				}
				
			}
			this.Nmin=(int)Math.pow(2,N);
			break;
		
		case COMMON.SCH_EDF_MN_SIMPLE3:
			this.startTime=curTime;
			double t3=1.0*this.datasize*Cps/(this.deadline+this.arrTime-curTime-this.datasize*Cms);
			this.Nmin=(int) Math.ceil(t3);
			break;
		}
		
		//validate the Nmin
		if ((Nmin<=0) || (Nmin>COMMON.PARA_CLUSTERSIZE)) { 
			if (COMMON.DEBUG==true) {
				System.out.println("Task, id="+this.id+",	Nmin="+Nmin);
				System.out.println(this);
			}
			Nmin=-1;
		}
		
		//compute completetion
		//if (Nmin!=-1)
			computeCompletionTime(Nmin,type);
//		else
//			this.compTime=-100000;
		
		//compute DC is necessary
		if ((type==COMMON.SCH_MWF_MN_OPR_Single)||(type==COMMON.SCH_MWF_MN_OPR_UMR) ) 
			computeDC(type);
		return Nmin;

	}


	
	/**
	 * should be called after computeNmin.
	 * @return compute the Derivative Cost
	 */
	protected double computeDC(int type) {
		double a,b;
		a=this.computeCompletionTime(Nmin+1,type)-this.startTime;
		b=this.computeCompletionTime(Nmin,type)-this.startTime;
		//a=this.computeCompletionTime(Nmin+1,type)-this.arrTime;
		//b=this.computeCompletionTime(Nmin,type)-this.arrTime;
		if (Nmin!=COMMON.PARA_CLUSTERSIZE)
			DC =a*(Nmin+1)-b*Nmin;
		else 
			DC=999999999;
		return DC;
	}
	
	/**
	 * calculate the completion time
	 * @param Nmin
	 * @param type, UMR or single round
	 * @return the computation time
	 */
	protected double computeCompletionTime(int nodes,int type) {
		double alpha1;
		double beta1;
		int iComputeTime=0;
		double dComputeTime=0;
		switch (type) {
		
		case COMMON.SCH_MWF_MN_OPR_Single:
		case COMMON.SCH_EDF_AN_OPR_Single:
		case COMMON.SCH_EDF_MN_OPR_Single:
		case COMMON.SCH_FIFO_AN_OPR_Single:
		case COMMON.SCH_FIFO_MN_OPR_Single:
		case COMMON.SCH_USR_RESERVE:
			if ( (ST==0) && (SC==0) )  {
				alpha1= (1-beta)/(1-Math.pow(beta,nodes));
				dComputeTime=alpha1*datasize*(Cms+Cps);
				iComputeTime=(int) Math.ceil(dComputeTime);
				compTime=iComputeTime+startTime;
				this.dispDoneTime=startTime+datasize*Cms;
			}
			else {
				//TODO dispatch done time, with setup cost
				beta1=1-Math.pow(beta,nodes);
				alpha1= (1-beta)/beta1+nodes*1.0*phi/beta1-phi/(1-beta);
				dComputeTime=alpha1*datasize;
				dComputeTime*=(Cms+Cps);
				dComputeTime+=(ST+SC);
				iComputeTime=(int) Math.ceil(dComputeTime);
				compTime=iComputeTime+startTime;
				
			}
			break;
		case COMMON.SCH_MWF_MN_OPR_UMR:
		case COMMON.SCH_EDF_AN_OPR_UMR:
		case COMMON.SCH_EDF_MN_OPR_UMR:
		case COMMON.SCH_FIFO_AN_OPR_UMR:
		case COMMON.SCH_FIFO_MN_OPR_UMR:
			if ( (ST==0) && (SC==0) )  {
				dComputeTime=1.0*Cms*nodes/2+1.0*this.datasize*Cps/nodes;
				iComputeTime=(int)Math.ceil(dComputeTime);
				compTime=iComputeTime+startTime;
				this.dispDoneTime=startTime+datasize*Cms;
			}
			else {
				//not implemented for UMR with setup cost
				}
			break;
		case COMMON.SCH_USERSPLIT:
			this.dispDoneTime=startTime+datasize*Cms;
			dComputeTime=datasize*Cms+datasize*Cps+this.SC+this.ST;
			this.compTime=datasize*Cms+datasize*Cps+this.SC+this.ST+this.startTime;
			break;
		case COMMON.SCH_FIFO_MN_SIMPLE:  //  FIFO, MN, simple heuristic
			dComputeTime=datasize*(Cms+Cps)*1.0/nodes+this.SC+this.ST;
			iComputeTime=(int) Math.ceil(dComputeTime);
			this.compTime=iComputeTime+this.startTime;
			this.dispDoneTime=this.startTime+datasize*Cms;
			break;
		case COMMON.SCH_FIFO_MN_SIMPLE_ACT:  //  FIFO, MN, simple heuristic, actural dispatch
			dComputeTime=datasize*Cps*1.0/nodes+datasize*Cms+this.SC+(nodes-1)*this.ST;
			iComputeTime=(int) Math.ceil(dComputeTime);
			this.compTime=iComputeTime+this.startTime;
			//this.compTime=(int) Math.ceil(datasize*Cps*1.0/nodes)+datasize*Cms+this.SC+(nodes-1)*this.ST+this.startTime;
			this.dispDoneTime=this.startTime+datasize*Cms;
			break;
		case COMMON.SCH_FIFO_MN_SIMPLE2:  //  FIFO, MN, simple heuristic
			dComputeTime=datasize*(Cms+Cps)*1.0/nodes+this.SC+this.ST;
			iComputeTime=(int) Math.ceil(dComputeTime);
			this.compTime=iComputeTime+this.startTime;
			break;
		case COMMON.SCH_FIFO_MN_SIMPLE2_ACT:  //  FIFO, MN, simple heuristic, actural dispatch
			//this.compTime=(int) Math.ceil(datasize*Cps*1.0/nodes)+datasize*Cms+this.SC+(nodes-1)*this.ST+this.startTime;
			dComputeTime=datasize*Cps*1.0/nodes+datasize*Cms+this.SC+(nodes-1)*this.ST;
			iComputeTime=(int) Math.ceil(dComputeTime);
			this.compTime=iComputeTime+this.startTime;
			break;
		case COMMON.SCH_EDF_AN:
			break;
		case COMMON.SCH_EDF_MN:
			break;
		case COMMON.SCH_EDF_MN_SIMPLE3:
			if (nodes==0)
				this.compTime=-1;
			else
				this.compTime=(int) Math.ceil(datasize*Cms+datasize*1.0*Cps/nodes+this.startTime);
			break;
		case COMMON.SCH_MCDF_COMPARE:
			break;
		default:
			if (COMMON.DEBUG) {
				System.out.println("No such scheduling algorithm");
				System.out.println(this);
			}
			break;
		}
		return (dComputeTime+this.startTime);
	}
	

	
	/**
	 *  compute completion time using nodes num. (This function replace with the previous "computeAN"
	 * -the difference from computeCompletionTime() is that it set Nmin to num, and calculate the DC
	 * @param curTime the time the task can start
	 * @param num the number of nodes assigned to the task
	 * @param type type of the algorithm
	 */
	public void computeByNodes(int curTime,int num, int type) {
		if (curTime>=this.arrTime)
			this.startTime=curTime;
		else
			this.startTime=this.arrTime;
		if (num<=0) {
			if (COMMON.DEBUG)
				System.out.println("error in computeByNodes, -1 is passed to num");
			this.compTime=-100000;
			return;
		}
		this.Nmin=num;
		this.computeCompletionTime(Nmin, type);
		//compute DC is necessary
		if ((type==COMMON.SCH_MWF_MN_OPR_Single)||(type==COMMON.SCH_MWF_MN_OPR_UMR) ) 
			computeDC(type);
	}
	
	
	public int sequentialTime() {
		return (int) Math.ceil(datasize*(Cps+Cms));
	}
	
	
	public boolean isDeadlineMissed() {
		return (this.compTime>(this.arrTime+this.deadline));
	}
	
	public String toString() {
		String rst="";
		rst+="(A="+this.arrTime+",";
		rst+="id="+this.id+",";
		rst+="w="+this.datasize+",";
		rst+="D="+this.deadline+",";
		rst+="SC="+this.SC+",";
		rst+="ST="+ this.ST+",";
		rst+="stime="+this.startTime+",";
		rst+="ctime="+this.compTime+",";
		rst+="Nmin="+this.Nmin+",";
		rst+="Cms="+this.Cms+",";
		rst+="Cps="+ this.Cps+")";
		return rst;
	}

	/* (non-Javadoc)
	 * @see rtgrid.taskmodel.ITask#getRespondsTime()
	 */
	public int getRespondsTime() {
		return (this.getCompletionTime()-this.arrTime);
	}

	/* (non-Javadoc)
	 * @see rtgrid.taskmodel.IDivisibleTask#getDispatchDoneTime()
	 */
	public int getDispatchDoneTime() {
		return this.dispDoneTime;
	}

	/* (non-Javadoc)
	 * @see rtgrid.taskmodel.IDivisibleTask#setDispathDoneTime(int)
	 */
	public void setDispathDoneTime(int time) {
		this.dispDoneTime=time;
		
	}
	/**
	 * compute the datasize that can be finished in certain period with certain number of nodes
	 * @param duration  the period of computation
	 * @param nodes number of nodes that participate into the computation
	 * @param type which type of algorithms are used.
	 * @return the datasize that can be computed in this period
	 */
	public int computeDatasize(int duration,int nodes,int type) {
		int datasize=0;
		datasize=(int)( (1-Math.pow(beta,nodes))*duration/ ((Cms+Cps)*(1-beta)) )  ;
	    return datasize;
	}
	
	public void setDatasize(int data) {
		this.datasize=data;
	}
	
	
	public double getAcceptedRate() {
		return 1.0*datasize/o_datasize;
	}

	/* (non-Javadoc)
	 * @see rtgrid.taskmodel.IDivisibleTask#computeExecutiontime(rtgrid.util.SysVector, int)
	 */
	public int computeExecutiontime(SysVector sysv, int type) {
		// please refer to the hetero analysis for the computation
		
        int size=sysv.getSize();
		double [] X =new double[size];
		double [] alpha =new double [size];
		//int [] datasize;
		
		
		//calculate X 
		
		X[0]=0;
		for (int i=1;i<size;i++) {
			X[i]=1.0*sysv.getCps(i-1)/(sysv.getCms()+sysv.getCps(i));
		}
		
		//calculate alpha
		double tmp=0;
		for (int i=1;i<size;i++) {
			double tmp2=1;
			for (int j=1;j<=i;j++) {
			   tmp2*=X[j];
			}
			tmp+=tmp2;
		}
		alpha[0]=1.0/(1+tmp);
		
		for (int i=1;i<size;i++) {
			alpha[i]=X[i]*alpha[i-1];
		}
		
		System.out.println("i, X, alpha");
		for (int i=0;i<size;i++) {
	        System.out.println(""+(i+1)+", "+X[i]+", "+alpha[i]);
	        
		}
		
		
		
		System.out.println((int) Math.ceil( alpha[0]*datasize*(sysv.getCms()+sysv.getCps()) ));
		return (int) Math.ceil( alpha[0]*datasize*(sysv.getCms()+sysv.getCps()) );
		
//		switch (type) {
//		
//		case COMMON.SCH_MWF_MN_OPR_Single:
//		case COMMON.SCH_EDF_AN_OPR_Single:
//		case COMMON.SCH_EDF_MN_OPR_Single:
//		case COMMON.SCH_FIFO_AN_OPR_Single:
//		case COMMON.SCH_FIFO_MN_OPR_Single:
//		case COMMON.SCH_USR_RESERVE:
//			
//			break;
//		case COMMON.SCH_MWF_MN_OPR_UMR:
//		case COMMON.SCH_EDF_AN_OPR_UMR:
//		case COMMON.SCH_EDF_MN_OPR_UMR:
//		case COMMON.SCH_FIFO_AN_OPR_UMR:
//		case COMMON.SCH_FIFO_MN_OPR_UMR:
//			
//			break;
//		case COMMON.SCH_USERSPLIT:
//			break;
//		case COMMON.SCH_FIFO_MN_SIMPLE:  //  FIFO, MN, simple heuristic
//			
//			break;
//		case COMMON.SCH_FIFO_MN_SIMPLE_ACT:  //  FIFO, MN, simple heuristic, actural dispatch
//			
//			break;
//		case COMMON.SCH_FIFO_MN_SIMPLE2:  //  FIFO, MN, simple heuristic
//		
//			break;
//		case COMMON.SCH_FIFO_MN_SIMPLE2_ACT:  //  FIFO, MN, simple heuristic, actural dispatch
//			
//			break;
//		case COMMON.SCH_EDF_AN:
//			break;
//		case COMMON.SCH_EDF_MN:
//			break;
//		case COMMON.SCH_EDF_MN_SIMPLE3:
//			
//			break;
//		case COMMON.SCH_MCDF_COMPARE:
//			break;
//		default:
//			if (COMMON.DEBUG) {
//				System.out.println("No such scheduling algorithm");
//				System.out.println(this);
//			}
//			break;
//		}
//		return 0;
	}

	public void adjustDelay(int delay) {
		for (int i=0;i<_SubTasks.size();i++) {
			SubTask subt=(SubTask) _SubTasks.elementAt(i);
			subt._aTime+=delay;
			subt._sTime+=delay;
			subt._compTime+=delay;
		}
	}

	}
