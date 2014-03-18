package rtgrid.util;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Random;
import java.util.Vector;
import rtgrid.COMMON;
import  rtgrid.taskmodel.*;
/**
 * 
 * @author Xuan
 *
 *  The class to prepare the queuelist before the simulation start
 */
public class TasksGenerator {
	public TasksGenerator () { 
	}
	/**
	 * Generate incoming tasks sequence with exponetial distribution.
	 * @param sysload
	 * @param simtime
	 * @param cms
	 * @param cps
	 * @return a vector that store the sequence
	 */
	public Vector genExp(double sysload,long simtime,int cms, int cps) {
		//TODO change cdratio to double
		//TODO gen proper deadline, at least larger than T(n)
		int deadlinescale=COMMON.PARA_CDRATIO; //we might change this parameter to make deadlines tight or loose
		//double deadlinescale=1.5;
		Vector NLTlist=new Vector();
		TaskList tlst=new TaskList(-1);
		double lambda=0;
		int totalcomputation=0;
		Random ranDeadline=new Random();//random generator for deadline
		Random ranDatasize=new Random();//random generator for datasize
		RandomGen rg=new RandomGen();
		int workload=0; //store the actual total workload of the sequence
		int deadline=0,arrtime=0,datasize;
		
		int meandatasize=COMMON.PARA_DATASIZE;  //mean datasize of the tasks.
		
		Task task_tmp=new Task(0,0,1000000,meandatasize);

	    task_tmp.setCmsCps(cms, cps);
		
		int interval=0; //inter-arrival time of the tasks.
		
		//calculate how many tasks we need;
		task_tmp.computeByNodes(0,COMMON.PARA_CLUSTERSIZE,	COMMON.ROUND_SINGLE);
		System.out.print("sysload: "+sysload+"	|	");
		System.out.println("avg computation time: "+task_tmp.getCompletionTime());
		int num=(int) (sysload*simtime/task_tmp.getCompletionTime());
		System.out.println("num of tasks:"+num);
		
		//determine the inter-arrival time.
		lambda=simtime/num;
		
		System.out.println("****************** interval="+lambda+"*************");
		for (int i=0;i<num;i++) {
			if (i!=0) {
				//interval = (int) poisson.sample();
				interval = (int) rg.nextNegExp(lambda);
				arrtime+=interval;
			}
			
			if (arrtime>simtime)
				break;
			
			//ctime=(int)Math.ceil(ranCTime.nextGaussian()*timevar+timemean);
			//ctime=0;
			do {
				datasize=ranDatasize.nextInt(2*meandatasize); // task size is uniform distributed between [0,2*mean]
			} while (datasize==0);
			workload+=datasize;//keep record;
			
			
			Task task_tmp2=new Task(0,0,(int)simtime+1,datasize);

		    task_tmp2.setCmsCps(cms, cps);
			task_tmp2.computeByNodes(0,COMMON.PARA_CLUSTERSIZE,COMMON.ROUND_SINGLE);
			
			////deadline mean
			int deadlinemean=(int)(task_tmp2.getCompletionTime()*deadlinescale);
			
			////deadline range
			int lowbound,upbound; //bound of deadline
			lowbound=(int)(deadlinemean-task_tmp2.getCompletionTime()*deadlinescale/2);
			upbound=(int)(deadlinemean+task_tmp2.getCompletionTime()*deadlinescale/2);
			
			
			deadline=(int)(ranDeadline.nextDouble()*(upbound-lowbound)+lowbound);
			
			
			
			boolean b=false;
			//check if arrtime already exit;
			for (int j=0;j<NLTlist.size();j++) {
				TaskList tmp=(TaskList) NLTlist.elementAt(j);
				if (tmp.time==arrtime) {
					tlst=tmp;
					b=true;
					break;
				}
			}
			
			if (!b)
				tlst=new TaskList(arrtime);
			Task task_=new Task(i,arrtime,deadline,datasize);

		    task_.setCmsCps(cms, cps);
			task_.computeByNodes(0,COMMON.PARA_CLUSTERSIZE,COMMON.ROUND_SINGLE);
			totalcomputation+=task_.getCompletionTime();
			//task_.SC=50;
			//task_.ST=50;
			tlst.addTask(task_);
			if (!b)
				NLTlist.add(tlst);			
		}
		
		System.out.println("actual system load="+totalcomputation*1.0/simtime);
		return NLTlist;
	}
	
	
	/**
	 * This method will generate tasks sequence with exponetial distribution with system load from "lowSysLoad" to "highSysLoad"
	 * @param startSysLoad The 
	 * @param endSysLoad
	 * @param step
	 */
	public void genExpTasks(int lowSysLoad, int highSysLoad, int step) {
		//System.out.println("cps="+COMMON.PARA_CPS+", cms="+COMMON.PARA_CMS);
		Vector datalist;
		for (int i=lowSysLoad;i<highSysLoad+1;i+=step) {
			for (int j=0;j<COMMON.PARA_EXP_ITERATION;j++) {
				datalist = this.genExp(1.0*i/10,COMMON.PARA_SIMULATION_TIME,COMMON.PARA_CMS,COMMON.PARA_CPS);
				//sim.printList(datalist,false,"exp"+i+"-"+j+".seq");
				//printList(datalist,COMMON.PARA_SEQ_NAME+i+"-"+j+".seq");
			}
		}
		System.out.println();		
	}
	
	
	/**
	 * Save the task sequence to a file
	 * 
	 * @param datalist
	 * @param filename
	 */
	public void printList(Vector datalist,String filename) {
		if (datalist==null) {
			System.out.print("empty");
			return;
		}
		FileOutputStream out; // declare a file output object
		PrintStream p; // declare a print stream object
		
		try {
			// connected to "myfile.txt"
			out = new FileOutputStream(filename);
			// Connect print stream to the output stream
			p = new PrintStream( out );
			
			for (int s = 0; s < datalist.size(); s++) {
				TaskList tlst = (TaskList) datalist.elementAt(s);
				Vector tasks = tlst.tasks;
				if (tasks.size() > 0) {
					for (int jjj = 0; jjj < tasks.size(); jjj++) {
						Task t = (Task) tasks.elementAt(jjj);
						p.println(t);
					}
				}
			}
			p.close();
		} catch (Exception e) {
			System.err.println ("Error writing to file");
		}
		
		
	}
	
	/**
	 * Print the tasks sequence
	 * @param datalist
	 */
	public void printList(Vector datalist) {
		if (datalist==null) {
			System.out.print("empty");
			return;
		}
		
		
		for (int s = 0; s < datalist.size(); s++) {
			TaskList tlst = (TaskList) datalist.elementAt(s);
			Vector tasks = tlst.tasks;
			for (int jjj = 0; jjj < tasks.size(); jjj++) {
				Task t = (Task) tasks.elementAt(jjj);
				System.out.println(t);
			}
		}
		
		
		
		
	}
	
	/**
	 * Generate incoming tasks sequence with exponetial distribution.
	 * @param sysload
	 * @param simtime
	 * @param cms
	 * @param cps
	 * @return a vector that store the sequence
	 */
	public Vector genMix(double sysload,long simtime,int cms, int cps) {
		//TODO change cdratio to double
		//TODO gen proper deadline, at least larger than T(n)
		
		int intMixIndex=0;
		int deadlinescale=COMMON.PARA_CDRATIO; //we might change this parameter to make deadlines tight or loose
		//double deadlinescale=1.5;
		Vector NLTlist=new Vector();
		TaskList tlst=new TaskList(-1);
		double lambda=0;
		int totalcomputation=0;
		Random ranDeadline=new Random();//random generator for deadline
		Random ranDatasize=new Random();//random generator for datasize
		Random ranNext=new Random();
		RandomGen rg=new RandomGen();
		int workload=0; //store the actual total workload of the sequence
		int deadline=0,arrtime=0,datasize;
		
		int meandatasize=COMMON.PARA_DATASIZE;  //mean datasize of the tasks.
		
		Task task_tmp=new Task(0,0,1000000,meandatasize);

	    task_tmp.setCmsCps(cms, cps);
		
		int interval=0; //inter-arrival time of the tasks.
		
		//calculate how many tasks we need;
		task_tmp.computeByNodes(0,COMMON.PARA_CLUSTERSIZE,COMMON.ROUND_SINGLE);
		System.out.print("sysload: "+sysload+"	|	");
		System.out.println("avg computation time: "+task_tmp.getCompletionTime());
		int num=(int) (sysload*simtime/task_tmp.getCompletionTime());
		System.out.println("num of tasks:"+num);
		
		//determine the inter-arrival time.
		lambda=simtime/num;
		
		
		for (int i=0;i<num;i++) {
			//intMixIndex=i%3;
			if (i!=0) {
				//interval = (int) poisson.sample();
				int tmp=ranNext.nextInt(3);
				if (tmp==0) {
					interval = (int) rg.nextNegExp(lambda);
					arrtime+=interval;
				}
			}
			
			if (arrtime>simtime)
				break;
			
			//ctime=(int)Math.ceil(ranCTime.nextGaussian()*timevar+timemean);
			//ctime=0;
			
			int curmeandatasize=0;
			intMixIndex=ranNext.nextInt(3);
			double dd=1.0*(intMixIndex+1);
			curmeandatasize=(int) (meandatasize*dd*2/2);
//			switch (intMixIndex) {
//			case 0:
//			curmeandatasize=meandatasize;
//			break;
//			case 1:
//			curmeandatasize=2*meandatasize;
//			break;
//			case 2:
//			curmeandatasize=3*meandatasize;
//			break;	
//			}
			do {
				//datasize=ranDatasize.nextInt(2*meandatasize); // task size is uniform distributed between [0,2*mean]
				datasize=ranDatasize.nextInt(curmeandatasize); // task size is uniform distributed between [0,2*mean]
			} while (datasize==0);
			workload+=datasize;//keep record;
			
			
			Task task_tmp2=new Task(0,0,(int)simtime+1,datasize);

		    task_tmp2.setCmsCps(cms, cps);
			task_tmp2.computeByNodes(0,COMMON.PARA_CLUSTERSIZE,COMMON.ROUND_SINGLE);
			
			////deadline mean
			int deadlinemean=(int)(task_tmp2.getCompletionTime()*deadlinescale);
			
			////deadline range
			int lowbound,upbound; //bound of deadline
			lowbound=(int)(deadlinemean-task_tmp2.getCompletionTime()*deadlinescale/2);
			upbound=(int)(deadlinemean+task_tmp2.getCompletionTime()*deadlinescale/2);
			
			
			deadline=(int)(ranDeadline.nextDouble()*(upbound-lowbound)+lowbound);
			
			
			
			boolean b=false;
			//check if arrtime already exit;
			for (int j=0;j<NLTlist.size();j++) {
				TaskList tmp=(TaskList) NLTlist.elementAt(j);
				if (tmp.time==arrtime) {
					tlst=tmp;
					b=true;
					break;
				}
			}
			
			if (!b)
				tlst=new TaskList(arrtime);
			Task task_=new Task(i,arrtime,deadline,datasize);
			task_.setCmsCps(cms, cps);
			task_.computeByNodes(0,COMMON.PARA_CLUSTERSIZE,COMMON.ROUND_SINGLE);
			totalcomputation+=task_.getCompletionTime();
			//task_.SC=50;
			//task_.ST=50;
			tlst.addTask(task_);
			if (!b)
				NLTlist.add(tlst);			
		}
		
		System.out.println("actual system load="+totalcomputation*1.0/simtime);
		return NLTlist;
	}
	
	
	public Vector genMix2(double sysload,long simtime,int cms, int cps) {
		//TODO change cdratio to double
		//TODO gen proper deadline, at least larger than T(n)
		
		int intMixIndex=0;
		int deadlinescale=COMMON.PARA_CDRATIO; //we might change this parameter to make deadlines tight or loose
		//double deadlinescale=1.5;
		Vector NLTlist=new Vector();
		TaskList tlst=new TaskList(-1);
		double lambda=0;
		int totalcomputation=0;
		Random ranDeadline=new Random();//random generator for deadline
		Random ranDatasize=new Random();//random generator for datasize
		Random ranNext=new Random();
		RandomGen rg=new RandomGen();
		int workload=0; //store the actual total workload of the sequence
		int deadline=0,arrtime=0,datasize;
		
		int meandatasize=COMMON.PARA_DATASIZE;  //mean datasize of the tasks.
		
		Task task_tmp=new Task(0,0,1000000,meandatasize);
		task_tmp.setCmsCps(cms, cps);
		
		int interval=0; //inter-arrival time of the tasks.
		
		//calculate how many tasks we need;
		task_tmp.computeByNodes(0,COMMON.PARA_CLUSTERSIZE,COMMON.ROUND_SINGLE);
		System.out.print("sysload: "+sysload+"	|	");
		System.out.println("avg computation time: "+task_tmp.getCompletionTime());
		int num=(int) (sysload*simtime/task_tmp.getCompletionTime())/5;
		System.out.println("num of tasks:"+num);
		
		//determine the inter-arrival time.
		lambda=simtime/num;
		
		
		for (int i=0;i<num;i++) {
			//intMixIndex=i%3;
			if (i!=0) {
				//interval = (int) poisson.sample();
				int tmp=ranNext.nextInt(3);
				if (tmp==0) {
					interval = (int) rg.nextNegExp(lambda);
					arrtime+=interval;
				}
			}
			
			if (arrtime>simtime)
				break;
			
			//ctime=(int)Math.ceil(ranCTime.nextGaussian()*timevar+timemean);
			//ctime=0;
			
			int curmeandatasize=0;
			intMixIndex=ranNext.nextInt(3);
			double dd=1.0*(intMixIndex+1);
			curmeandatasize=(int) (meandatasize*dd*2/2);
			switch (intMixIndex) {
			case 0:
				curmeandatasize=2;
				break;
			case 1:
				curmeandatasize=2*meandatasize;
				break;
			case 2:
				curmeandatasize=20*meandatasize;
				break;	
			}
			do {
				//datasize=ranDatasize.nextInt(2*meandatasize); // task size is uniform distributed between [0,2*mean]
				datasize=ranDatasize.nextInt(curmeandatasize); // task size is uniform distributed between [0,2*mean]
			} while (datasize==0);
			workload+=datasize;//keep record;
			
			
			Task task_tmp2=new Task(0,0,(int)simtime+1,datasize);
			
			task_tmp2.setCmsCps(cms, cps);
			task_tmp2.computeByNodes(0,COMMON.PARA_CLUSTERSIZE,COMMON.ROUND_SINGLE);
			
			////deadline mean
			int deadlinemean=(int)(task_tmp2.getCompletionTime()*deadlinescale);
			
			////deadline range
			int lowbound,upbound; //bound of deadline
			lowbound=(int)(deadlinemean-task_tmp2.getCompletionTime()*deadlinescale/2);
			upbound=(int)(deadlinemean+task_tmp2.getCompletionTime()*deadlinescale/2);
			
			
			deadline=(int)(ranDeadline.nextDouble()*(upbound-lowbound)+lowbound);
			
			
			
			boolean b=false;
			//check if arrtime already exit;
			for (int j=0;j<NLTlist.size();j++) {
				TaskList tmp=(TaskList) NLTlist.elementAt(j);
				if (tmp.time==arrtime) {
					tlst=tmp;
					b=true;
					break;
				}
			}
			
			if (!b)
				tlst=new TaskList(arrtime);
			Task task_=new Task(i,arrtime,deadline,datasize);
			task_.setCmsCps(cms, cps);
			task_.computeByNodes(0,COMMON.PARA_CLUSTERSIZE,COMMON.ROUND_SINGLE);
			totalcomputation+=task_.getCompletionTime();
			//task_.SC=50;
			//task_.ST=50;
			tlst.addTask(task_);
			if (!b)
				NLTlist.add(tlst);			
		}
		
		System.out.println("actual system load="+totalcomputation*1.0/simtime);
		return NLTlist;
	}
	
	
	/**
	 * Generate incoming tasks sequence with exponetial distribution.
	 * @param inter_arrival
	 * @param simtime
	 * @param cms
	 * @param cps
	 * @return a vector that store the sequence
	 */
	public Vector genExp( int inter_arrival,
			long simtime,
			int cms, 
			int cps
	) 
	{
		//TODO change cdratio to double
		//TODO gen proper deadline, at least larger than T(n)
		int deadlinescale=COMMON.PARA_CDRATIO; //we might change this parameter to make deadlines tight or loose
		//double deadlinescale=1.5;
		Vector NLTlist=new Vector();
		TaskList tlst=new TaskList(-1);
		double lambda=0;
		int totalcomputation=0;
		Random ranDeadline=new Random();//random generator for deadline
		Random ranDatasize=new Random();//random generator for datasize
		RandomGen rg=new RandomGen();
		int workload=0; //store the actual total workload of the sequence
		int deadline=0,arrtime=0,datasize;
		
		int meandatasize=COMMON.PARA_DATASIZE;  //mean datasize of the tasks.
		
		Task task_tmp=new Task(0,0,1000000,meandatasize);
		task_tmp.setCmsCps(cms, cps);
		
		int interval=0; //inter-arrival time of the tasks.
		
		//calculate how many tasks we need;
		task_tmp.computeByNodes(0,COMMON.PARA_CLUSTERSIZE,COMMON.ROUND_SINGLE);
		//System.out.print("sysload: "+sysload+"	|	");
		System.out.println("avg computation time: "+task_tmp.getCompletionTime());
		//int num=(int) (sysload*simtime/task_tmp.getCompletionTime());
		int num=(int)simtime/inter_arrival;
		System.out.println("num of tasks:"+num);
		
		//determine the inter-arrival time.
		//lambda=simtime/num;
		lambda=inter_arrival;
		
		for (int i=0;i<num;i++) {
			if (i!=0) {
				//interval = (int) poisson.sample();
				interval = (int) rg.nextNegExp(lambda);
				arrtime+=interval;
			}
			
			
			
			if (arrtime>simtime)
				break;
			
			//ctime=(int)Math.ceil(ranCTime.nextGaussian()*timevar+timemean);
			//ctime=0;
			do {
				datasize=ranDatasize.nextInt(2*meandatasize); // task size is uniform distributed between [0,2*mean]
			} while (datasize==0);
			workload+=datasize;//keep record;
			
			
			Task task_tmp2=new Task(0,0,(int)simtime+1,datasize);
			task_tmp2.setCmsCps(cms, cps);
			task_tmp2.computeByNodes(0,COMMON.PARA_CLUSTERSIZE,COMMON.ROUND_SINGLE);
			
			////deadline mean
			int deadlinemean=(int)(task_tmp2.getCompletionTime()*deadlinescale);
			
			////deadline range
			int lowbound,upbound; //bound of deadline
			lowbound=(int)(deadlinemean-task_tmp2.getCompletionTime()*deadlinescale/2);
			upbound=(int)(deadlinemean+task_tmp2.getCompletionTime()*deadlinescale/2);
			
			
			deadline=(int)(ranDeadline.nextDouble()*(upbound-lowbound)+lowbound);
			
			
			
			boolean b=false;
			//check if arrtime already exit;
			for (int j=0;j<NLTlist.size();j++) {
				TaskList tmp=(TaskList) NLTlist.elementAt(j);
				if (tmp.time==arrtime) {
					tlst=tmp;
					b=true;
					break;
				}
			}
			
			if (!b)
				tlst=new TaskList(arrtime);
			Task task_=new Task(i,arrtime,deadline,datasize);
			task_.setCmsCps(cms, cps);
			task_.computeByNodes(0,COMMON.PARA_CLUSTERSIZE,COMMON.ROUND_SINGLE);
			totalcomputation+=task_.getCompletionTime();
			//task_.SC=50;
			//task_.ST=50;
			tlst.addTask(task_);
			if (!b)
				NLTlist.add(tlst);			
		}
		
		System.out.println("actual system load="+totalcomputation*1.0/simtime);
		return NLTlist;
	}
	

	
	public Vector genExpTask( int inter_arrival,
			long simtime,
			int cms, 
			int cps,
			int numtask
	) 
	{
//		TODO change cdratio to double
//		TODO gen proper deadline, at least larger than T(n)
		int deadlinescale=COMMON.PARA_CDRATIO; //we might change this parameter to make deadlines tight or loose
//		double deadlinescale=1.5;
		Vector NLTlist=new Vector();
		TaskList tlst=new TaskList(-1);
		double lambda=0;
		int totalcomputation=0;
		Random ranDeadline=new Random();//random generator for deadline
		Random ranDatasize=new Random();//random generator for datasize
		Random ranNext=new Random();
		RandomGen rg=new RandomGen();
		int workload=0; //store the actual total workload of the sequence
		int deadline=0,arrtime=0,datasize;
		
		int meandatasize=COMMON.PARA_DATASIZE;  //mean datasize of the tasks.
		
		Task task_tmp=new Task(0,0,1000000,meandatasize);
		task_tmp.setCmsCps(cms, cps);
		int interval=0; //inter-arrival time of the tasks.
		
//		calculate how many tasks we need;
		task_tmp.computeByNodes(0,COMMON.PARA_CLUSTERSIZE,COMMON.ROUND_SINGLE);
//		System.out.print("sysload: "+sysload+"	|	");
		System.out.println("avg computation time: "+task_tmp.getCompletionTime());
//		int num=(int) (sysload*simtime/task_tmp.getCompletionTime());
		int num=(int)simtime/inter_arrival;
		System.out.println("num of tasks:"+num);
		
//		determine the inter-arrival time.
//		lambda=simtime/num;
		lambda=inter_arrival;
		
		for (int i=0;i<num;i++) {
			
			if (i!=0) {
				//interval = (int) poisson.sample();
				int tmp=ranNext.nextInt(numtask);
				i--;
				if (tmp==0) {
					interval = (int) rg.nextNegExp(lambda);
					arrtime+=interval;
					i++;
				}
			}
			
			
			
			if (arrtime>simtime)
				break;
			
//			ctime=(int)Math.ceil(ranCTime.nextGaussian()*timevar+timemean);
//			ctime=0;
			do {
				datasize=ranDatasize.nextInt(2*meandatasize); // task size is uniform distributed between [0,2*mean]
			} while (datasize==0);
			workload+=datasize;//keep record;
			
			
			Task task_tmp2=new Task(0,0,(int)simtime+1,datasize);
			task_tmp2.setCmsCps(cms, cps);
			task_tmp2.computeByNodes(0,COMMON.PARA_CLUSTERSIZE,COMMON.ROUND_SINGLE);
			
////			deadline mean
			int deadlinemean=(int)(task_tmp2.getCompletionTime()*deadlinescale);
			
////			deadline range
			int lowbound,upbound; //bound of deadline
			lowbound=(int)(deadlinemean-task_tmp2.getCompletionTime()*deadlinescale/2);
			upbound=(int)(deadlinemean+task_tmp2.getCompletionTime()*deadlinescale/2);
			
			
			deadline=(int)(ranDeadline.nextDouble()*(upbound-lowbound)+lowbound);
			
			
			
			boolean b=false;
//			check if arrtime already exit;
			for (int j=0;j<NLTlist.size();j++) {
				TaskList tmp=(TaskList) NLTlist.elementAt(j);
				if (tmp.time==arrtime) {
					tlst=tmp;
					b=true;
					break;
				}
			}
			
			if (!b)
				tlst=new TaskList(arrtime);
			Task task_=new Task(i,arrtime,deadline,datasize);
			task_.setCmsCps(cms, cps);
			task_.computeByNodes(0,COMMON.PARA_CLUSTERSIZE,COMMON.ROUND_SINGLE);
			totalcomputation+=task_.getCompletionTime();
//			task_.SC=50;
//			task_.ST=50;
			tlst.addTask(task_);
			if (!b)
				NLTlist.add(tlst);			
		}
		
		System.out.println("actual system load="+totalcomputation*1.0/simtime);
		return NLTlist;
	}
	
	
	
	public Vector genMix3(int interarr,long simtime,int cms, int cps,int mixnum) {
		//TODO change cdratio to double
		//TODO gen proper deadline, at least larger than T(n)
		
		int intMixIndex=0;
		int deadlinescale=COMMON.PARA_CDRATIO; //we might change this parameter to make deadlines tight or loose
		//double deadlinescale=1.5;
		Vector NLTlist=new Vector();
		TaskList tlst=new TaskList(-1);
		double lambda=0;
		int totalcomputation=0;
		Random ranDeadline=new Random();//random generator for deadline
		Random ranDatasize=new Random();//random generator for datasize
		Random ranNext=new Random();
		RandomGen rg=new RandomGen();
		int workload=0; //store the actual total workload of the sequence
		int deadline=0,arrtime=0,datasize;
		
		int meandatasize=COMMON.PARA_DATASIZE;  //mean datasize of the tasks.
		
		Task task_tmp=new Task(0,0,1000000,meandatasize);
		task_tmp.setCmsCps(cms, cps);
		
		int interval=0; //inter-arrival time of the tasks.
		
		//calculate how many tasks we need;
		task_tmp.computeByNodes(0,COMMON.PARA_CLUSTERSIZE,COMMON.ROUND_SINGLE);
		//System.out.print("sysload: "+sysload+"	|	");
		System.out.println("avg computation time: "+task_tmp.getCompletionTime());
		int num=(int) (simtime/interarr);
		System.out.println("num of tasks:"+num);
		
		//determine the inter-arrival time.
		lambda=interarr;
		
		
		for (int i=0;i<num;i++) {
			//intMixIndex=i%3;
			if (i!=0) {				
					interval = (int) rg.nextNegExp(lambda);
					arrtime+=interval;
			}
			
			if (arrtime>simtime)
				break;
			
			//ctime=(int)Math.ceil(ranCTime.nextGaussian()*timevar+timemean);
			//ctime=0;
			
			int curmeandatasize=0;
			intMixIndex=ranNext.nextInt(3);
			double dd=1.0*(intMixIndex+1);
			curmeandatasize=(int) (meandatasize*dd*2/2);
			switch (intMixIndex) {
			case 0:
				curmeandatasize=2;
				break;
			case 1:
				curmeandatasize=2*meandatasize;
				break;
			case 2:
				curmeandatasize=mixnum*meandatasize;
				break;	
			}
			do {
				//datasize=ranDatasize.nextInt(2*meandatasize); // task size is uniform distributed between [0,2*mean]
				datasize=ranDatasize.nextInt(curmeandatasize); // task size is uniform distributed between [0,2*mean]
			} while (datasize==0);
			workload+=datasize;//keep record;
			
			
			Task task_tmp2=new Task(0,0,(int)simtime+1,datasize);
			task_tmp2.setCmsCps(cms, cps);
			task_tmp2.computeByNodes(0,COMMON.PARA_CLUSTERSIZE,COMMON.ROUND_SINGLE);
			
			////deadline mean
			int deadlinemean=(int)(task_tmp2.getCompletionTime()*deadlinescale);
			
			////deadline range
			int lowbound,upbound; //bound of deadline
			lowbound=(int)(deadlinemean-task_tmp2.getCompletionTime()*deadlinescale/2);
			upbound=(int)(deadlinemean+task_tmp2.getCompletionTime()*deadlinescale/2);
			
			
			deadline=(int)(ranDeadline.nextDouble()*(upbound-lowbound)+lowbound);
			
			
			
			boolean b=false;
			//check if arrtime already exit;
			for (int j=0;j<NLTlist.size();j++) {
				TaskList tmp=(TaskList) NLTlist.elementAt(j);
				if (tmp.time==arrtime) {
					tlst=tmp;
					b=true;
					break;
				}
			}
			
			if (!b)
				tlst=new TaskList(arrtime);
			Task task_=new Task(i,arrtime,deadline,datasize);
			task_.setCmsCps(cms, cps);
			task_.computeByNodes(0,COMMON.PARA_CLUSTERSIZE,COMMON.ROUND_SINGLE);
			totalcomputation+=task_.getCompletionTime();
			//task_.SC=50;
			//task_.ST=50;
			tlst.addTask(task_);
			if (!b)
				NLTlist.add(tlst);			
		}
		
		System.out.println("actual system load="+totalcomputation*1.0/simtime);
		return NLTlist;
	}
	
	
	/**
	 * 
	 * @param period
	 * @param datasize
	 * @param deadline
	 * @param simtime
	 * @param cms
	 * @param cps
	 * @param bPrintInfo true if we want to print the information of the task.
	 * @return
	 */
	public Vector genPeriodic( int period,
			int datasize,
			int deadline,
			long simtime,
			int cms, 
			int cps,
		    boolean bPrintInfo
	) 
	{
		
		Vector NLTlist=new Vector();
		TaskList tlst=new TaskList(-1);
	
	    int arrtime=0;
	    int workload=0;
		int num=(int)simtime/period;
		if (bPrintInfo)
		      System.out.println("num of tasks:"+num);
		
		for (int i=0;i<num;i++) {
			if (i!=0) {
				arrtime+=period;
			}
			
			if (arrtime>simtime)
				break;
		
			workload+=datasize;//keep record;
			
			boolean b=false;
			//check if arrtime already exit;
			for (int j=0;j<NLTlist.size();j++) {
				TaskList tmp=(TaskList) NLTlist.elementAt(j);
				if (tmp.time==arrtime) {
					tlst=tmp;
					b=true;
					break;
				}
			}
			
			if (!b)
				tlst=new TaskList(arrtime);
			Task task_=new Task(i,arrtime,deadline,datasize);
			tlst.addTask(task_);
			if (!b)
				NLTlist.add(tlst);			
		}
		
		return NLTlist;
	}

	/**
	 * genTaskInRange return a vector that contains tasks whose inter-arrival is in Range [lowbound,highbound) and 
	 * is exponetial distributed with mean in the middle of the range
	 * @param rangelow 
	 * @param rangehigh
	 * @return
	 */

	public Vector genTaskInRange( int rangelow, 
			int rangehigh,
			int deadline,
			long simtime,
			int cms, 
			int cps
	) 
	{
        int inter_arrival=(rangelow+rangehigh)/2;
		int deadlinescale=COMMON.PARA_CDRATIO; //we might change this parameter to make deadlines tight or loose
		//double deadlinescale=1.5;
		Vector NLTlist=new Vector();
		TaskList tlst=new TaskList(-1);
		double lambda=0;
		int totalcomputation=0;
		Random ranDeadline=new Random();//random generator for deadline
		Random ranDatasize=new Random();//random generator for datasize
		RandomGen rg=new RandomGen();
		int workload=0; //store the actual total workload of the sequence
		int arrtime=0,datasize;
		
		
		
		Task task_tmp=new Task(0,0,1000000,COMMON.PARA_DATASIZE);
		task_tmp.setCmsCps(cms, cps);
		
		int interval=0; //inter-arrival time of the tasks.
		
		//calculate how many tasks we need;
		task_tmp.computeByNodes(0,COMMON.PARA_CLUSTERSIZE,COMMON.ROUND_SINGLE);
		//System.out.print("sysload: "+sysload+"	|	");
		//System.out.println("avg computation time: "+task_tmp.getCompletionTime());
		//int num=(int) (sysload*simtime/task_tmp.getCompletionTime());
		int num=(int)simtime/inter_arrival;
		//System.out.println("num of tasks:"+num);
		
		//determine the inter-arrival time.
		//lambda=simtime/num;
		lambda=inter_arrival;
		
		for (int i=0;i<num;i++) {
			if (i!=0) {
				//interval = (int) poisson.sample();
				while ((interval<rangelow) || (interval>=rangehigh)) 
					interval = (int) rg.nextNegExp(lambda);
				arrtime+=interval;
				interval=0;
			}
			
			
			
			if (arrtime>simtime)
				break;
			
			//ctime=(int)Math.ceil(ranCTime.nextGaussian()*timevar+timemean);
			//ctime=0;
	
			
			boolean b=false;
			//check if arrtime already exit;
			for (int j=0;j<NLTlist.size();j++) {
				TaskList tmp=(TaskList) NLTlist.elementAt(j);
				if (tmp.time==arrtime) {
					tlst=tmp;
					b=true;
					break;
				}
			}
			
			if (!b)
				tlst=new TaskList(arrtime);
			Task task_=new Task(i,arrtime,deadline,COMMON.PARA_DATASIZE);
			task_.setCmsCps(cms, cps);
			task_.computeByNodes(0,COMMON.PARA_CLUSTERSIZE,COMMON.ROUND_SINGLE);
			totalcomputation+=task_.getCompletionTime();
			//task_.SC=50;
			//task_.ST=50;
			tlst.addTask(task_);
			if (!b)
				NLTlist.add(tlst);			
		}
		
		//System.out.println("actual system load="+totalcomputation*1.0/simtime);
		return NLTlist;
	}
}
