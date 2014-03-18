/**
 * 
 */
package rtgrid.util;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Vector;
import rtgrid.COMMON;
import rtgrid.taskmodel.*;
/**
 * SimResult.java
 * Package:  rtgrid.util
 * @author Xuan, 2006-11-6 
 */
public class SimResult {
	  private Vector schTable; // store tasks that have been scheduled
	  private Vector rejTable; // store tasks that have been rejected
	  private Vector missTable; // store tasks that miss the deadline
	  
	  private Vector Log; /*
	                        store all the informations
	                        its elements are tasklist
	                        the member 'tasks' of tasklist is also a vector, store HisTasks
	                       */ 
	  
	  private int numTotal=0;//number of total task to be scheduled.
	  private double numReject=0;//number of tasks that are rejected.
	  private int numMiss=0;
	  private int queLength=0;
	  private long responseTime=0;
	  private long waitingTime=0;
	  private int nodes=0; // how many nodes assigned to the task
	  private double iitRatio=0; //Inserted Idle Time/task computation time
	  
	  private int t_workload=0;//total workload of all tasks.
	  private int r_workload=0;//workload of the tasks that get rejected.
	  private int m_workload=0;//workload of the tasks that miss their deadlines.
	  
	  
	  
	  public SimResult() {
		  schTable=new Vector();
		  rejTable=new Vector();
		  missTable=new Vector();
		  Log=new Vector();
	  }

	  
	  
	  /**
	   * 
	   * @return total number of tasks
	   */
	  public int getTotalNum() {
		  return numTotal;
	  }
	  
	  /**
	   * 
	   * @return number of rejected tasks
	   */
	  public int getRejectNum() {
		  return (int)numReject;
	  }

	  /**
	   * 
	   * @return number of tasks that miss their deadlines
	   */
	  public int getMissNum(){
		  return numMiss;
	  }
	  
	  /**
	   * 
	   * @return number of tasks that are admitted by the system
	   */
	  public int getAdmitNum() {
		  return numTotal-(int)numReject;
	  }

	  /**
	   * 
	   * @return total workload of all tasks
	   */
	  public int getTotalWorkload(){
		  return this.t_workload;
	  }
	  
	  /**
	   * 
	   * @return workload of rejected tasks
	   */
	  public int getRejWorkload() {
		  return this.r_workload;
	  }
	   
	  /**
	   * 
	   * @return workload of admitted tasks
	   */
	  public int getAdWorkload() {
		  return this.t_workload-this.r_workload;
	  }
	  /**
	   * 
	   * @return ratio of admitted workload to total workload
	   */
	  public double getAdWorkloadRatio() {
		  return 1.0*this.getAdWorkload()/this.t_workload;
	  }

	 /**
	  * 
	  * @return workload of the tasks that get admitted and miss their deadlines.
	  */
	  public int getMissWorkload() {
		  return this.m_workload;
	  }
	  
	  /**
	   * 
	   * @return workload of the tasks that get admitted and do not miss their deadlines.
	   */
	  public int getWorkload(){
		  return this.getAdWorkload()-this.m_workload;
	  }
	  /**
	   * 
	   * @return tasks reject ratio
	   */
	  public double getRejectRate() {
		  if (numTotal==0)
			  return 0;
		  else {
			  //System.out.println("numRej: "+numReject+",  numTotal: "+numTotal);
			  return 1.0*numReject/numTotal;
		  }
	  }
	  
	  /**
	   * 
	   * @return tasks miss ratio
	   */
	  public double getMissRatio() {
		  if (this.getAdmitNum()==0) 
			  return 0;
		  else 
			  return 1.0*numMiss/this.getAdmitNum();
	  }

	  public void addNewTask(int time, Task t) {
//		  this.schTable.add(t);
//		  numTotal++;
//		  this.workload+=t.datasize;
		  this.log(time,t,COMMON.TASK_ARRIVE);
	  }

	  public void addReject(int time, Task t) {
//		  this.rejTable.add(t);
//		  numTotal++;
//		  numReject++;
		  this.log(time,t,COMMON.TASK_REJECT);
	  }
	  
	  public void addMiss(int time, Task t) {
//		  this.missTable.add(t);
//		  numMiss++;
		  this.log(time,t,COMMON.TASK_DEADLINEMISS);
	  }
	  
	  public void logQueue(int length) {
		  //this.queLength+=length*2+1;
		  this.queLength+=length;
	  }
	  
	  /**
	   * 
	   * @return The average queue length when scheduling
	   */
	  public double getAvgQueueLen() {
		  if (numTotal==0)
			  return 0;
		  else
			  return 1.0*this.queLength/numTotal;
	  }
	  
	  public long getAvgResponseTime() {
		  //return this.responseTime/this.numTotal;
		  if (this.numTotal==this.numReject)
			  return 0;
		  else
		  return this.responseTime/(this.numTotal-(int)this.numReject);
	  }
	  
	  public long getAvgWaitingTime() {
		  //return this.waitingTime/this.numTotal;
		  if (this.numTotal==this.numReject)
			  return 0;
		  else
		  return this.waitingTime/ (this.numTotal-(int)this.numReject);
	  }
	  
	  public double getAvgNodes() {
		  if (this.numTotal==this.numReject)
			  return 0;
		  else
			  return this.nodes*1.0/(this.numTotal-this.numReject);
	  }
	  
	  public void log(int time,Task task,int type) {
		  switch (type) {
		  case COMMON.TASK_ARRIVE://task arrive
			  this.schTable.add(task);
			  numTotal++;
			  this.t_workload+=task.getDatasize();
			  break;
		  case COMMON.TASK_REJECT://task is rejected
			  this.rejTable.add(task);
			  numReject+=1;
			  this.r_workload+=task.getDatasize();
			  //this.queLength+=task.maxQueLen;
			  break;
		  case COMMON.TASK_DEADLINEMISS:// task is dispatched, and deadline miss
			  this.missTable.add(task);
			  numMiss++;
			  this.m_workload+=task.getDatasize();
			  responseTime+=task.getCompletionTime()-task.getArrTime();
			  waitingTime+=task.getStartTime()-task.getArrTime();
			  nodes+=task.getNmin();
			  break;
		  case COMMON.TASK_ADMIT:// task is scheduled
			  break;
		  case COMMON.TASK_DISPATCH:// task is dispatched, and deadline is mit
			  responseTime+=task.getCompletionTime()-task.getArrTime();
			  waitingTime+=task.getStartTime()-task.getArrTime();
			  nodes+=task.getNmin();
			  //this.queLength+=task.maxQueLen;
		      //System.out.println("id="+task.id+",Nmin="+task.getNmin());
			  //System.out.println(task.id+":"+responseTime);
			  break;	
		  }
		  
		  
		  
		  boolean b = false;
		  TaskList tlst=null;
		  // check if arrtime already exit;
		  for (int j = 0; j < Log.size(); j++) {
			  TaskList tmp = (TaskList) Log.elementAt(j);
			  if (tmp.time == time) {
				  tlst = tmp;
				  b = true;
				  break;
			  }
		  }
		  
		  if (!b)
			  tlst = new TaskList(time);
		  HisTask htask=new HisTask(type,task);
		  tlst.addHisTask(htask);
		  if (!b)
			  Log.add(tlst);
	  }
	  
	  
	  /**
	   * Same as function, but using in partial accept
	   * @param time
	   * @param task
	   * @param type
	   */
	  public void log2(int time,Task task,int type) {
		  switch (type) {
		  case COMMON.TASK_ARRIVE://task arrive
			  this.schTable.add(task);
			  numTotal++;
			  this.t_workload+=task.getDatasize();
			  break;
		  case COMMON.TASK_REJECT://task is rejected
			  this.rejTable.add(task);
			  numReject+=1;
			  this.r_workload+=task.getDatasize();
			  //this.queLength+=task.maxQueLen;
			  break;
		  case COMMON.TASK_DEADLINEMISS:// task is dispatched, and deadline miss
			  this.missTable.add(task);
			  numMiss++;
			  this.m_workload+=task.getDatasize();
			  responseTime+=task.getCompletionTime()-task.getArrTime();
			  waitingTime+=task.getStartTime()-task.getArrTime();
			  nodes+=task.getNmin();
			  break;
		  case COMMON.TASK_ADMIT:// task is scheduled
			  if (task.o_datasize!=task.getDatasize()) 
				  numReject+=1-task.getAcceptedRate();
			  System.out.println();
			  break;
			 
		  case COMMON.TASK_DISPATCH:// task is dispatched, and deadline is mit
			  responseTime+=task.getCompletionTime()-task.getArrTime();
			  waitingTime+=task.getStartTime()-task.getArrTime();
			  nodes+=task.getNmin();
			  //this.queLength+=task.maxQueLen;
		      //System.out.println("id="+task.id+",Nmin="+task.getNmin());
			  //System.out.println(task.id+":"+responseTime);
			  break;	
		  }
		  
		  
		  
		  boolean b = false;
		  TaskList tlst=null;
		  // check if arrtime already exit;
		  for (int j = 0; j < Log.size(); j++) {
			  TaskList tmp = (TaskList) Log.elementAt(j);
			  if (tmp.time == time) {
				  tlst = tmp;
				  b = true;
				  break;
			  }
		  }
		  
		  if (!b)
			  tlst = new TaskList(time);
		  HisTask htask=new HisTask(type,task);
		  tlst.addHisTask(htask);
		  if (!b)
			  Log.add(tlst);
	  }
	  
	  /**
	   * Log inserted idle time
	   * @param mac_et mac end time
	   * @param task
	   */
	  public void logIIT(double iitRatio) {
		  this.iitRatio+=iitRatio;
	  }
	  
	  public double getIITRatio() {
		  double rst=0;
		  if ((this.numTotal-this.numReject)==0)
			  return 0;
		  else
			  rst=this.iitRatio/(this.numTotal-this.numReject);
		  return rst;
	  }
	  
	  public double getSplitRejRatio() {
		  double rst=0;
		  // schTable stores all tasks
		  int m=schTable.size();
		  // rejTable stores rejected tasks
		  //int n=rejTable.size();
		  
		  int indexm=0,indexn=0,rej=0,total=0;
		  int totaltask=0;
		  Task taskm,taskn;
		  
		  int curid=-1;
		  
		  while (indexm<m ) {
			  
			  taskm=(Task) schTable.elementAt(indexm);
			  curid=taskm.getID();
			  totaltask++;
			  int km=0;// the num of arrived tasks
			  while (taskm.getID()==curid) {			  
				  indexm++;
				  km++;
				  if (indexm>=schTable.size()) break;
				  taskm=(Task) schTable.elementAt(indexm);
			  }
			  //System.out.print("id="+curid+":  "+km);
			 total+=km;
			  indexn=-1;
			  int kn=0;
			  for (int i=0;i<rejTable.size();i++) {
				  taskn=(Task) rejTable.elementAt(i);
				  if (taskn.getID()==curid) {
					  indexn=i;
					  break;
				  }
			  }
			  
			  if (indexn!=-1) {
				  taskn=(Task) rejTable.elementAt(indexn);
		          while (taskn.getID()==curid) {
					  kn++;
					  indexn++;	
					  if (indexn==rejTable.size())
						  break;
					  taskn=(Task) rejTable.elementAt(indexn);
				  }
				  
				  
			  }
			  rej+=kn;
			  //System.out.println(" , "+kn);
			  rst+=kn*1.0/km;
		  }
		  //System.out.println();
		  //System.out.println(totaltask);
		  //System.out.println("("+schTable.size()+","+rejTable.size()+")	:	("+total+","+rej+")");
		  return rst/totaltask;
	  }
	  
	  
	  public double getSplitRejRatio2() {
		  double rst=0;
		  // schTable stores all tasks
		  int m=schTable.size();
		  // rejTable stores rejected tasks
		  //int n=rejTable.size();
		  
		  int indexm=0,indexn=0,rej=0,total=0;
		  int totaltask=0;
		  Task taskm,taskn;
		  
		  int curid=-1;
		  
		  while (indexm<m ) {
			  
			  taskm=(Task) schTable.elementAt(indexm);
			  curid=taskm.getID();
			  totaltask++;
			  int km=0;// the num of arrived tasks
			  while (taskm.getID()==curid) {			  
				  indexm++;
				  km++;
				  if (indexm>=schTable.size()) break;
				  taskm=(Task) schTable.elementAt(indexm);
			  }
			  //System.out.print("id="+curid+":  "+km);
			  
	          total+=km;
			  if (indexn>=rejTable.size()) continue;
			  taskn=(Task) rejTable.elementAt(indexn);		  
			  int kn=0;// the num of rejected tasks
			  while (taskn.getID()==curid) {
				  kn++;
				  indexn++;
				  if (indexn>=rejTable.size()) break;
				  taskn=(Task) rejTable.elementAt(indexn);
			  }
			  //System.out.println(" , "+kn);
			  rst+=kn*1.0/km;
			  rej+=kn;
		  }
		  System.out.println();
		  //System.out.println(totaltask);
		  //System.out.println("("+schTable.size()+","+rejTable.size()+")	:	("+total+","+rej+")");
		  return rst/totaltask;
	  }
	  public void printSchedule() {
		  System.out.println();
			System.out.println("****************************************************");
			System.out.println();
			System.out.println("Start printing schedule:");
			System.out.println();
			for (int i=0;i<schTable.size();i++) 
				System.out.println(schTable.elementAt(i));
	  }
	  
	  
	  public void printRejTasks(){
		  System.out.println("Start printing rejected tasks:");
		  for (int i=0;i<rejTable.size();i++) 
				System.out.println(rejTable.elementAt(i));
	  }
	  
	  public void printMisTasks(){
		  System.out.println("Start printing deadline-missed tasks:");
		  for (int i=0;i<missTable.size();i++) 
				System.out.println(missTable.elementAt(i));
	  }
	  
	  /**
	   * print Log to screen
	   *
	   */
	  public void printLog() {
		if (Log == null) {
				System.out.print("empty");
				return;
			}

			for (int s = 0; s < Log.size(); s++) {
				TaskList tlst = (TaskList) Log.elementAt(s);
				Vector tasks = tlst.tasks;
				for (int jjj = 0; jjj < tasks.size(); jjj++) {
					HisTask ht = (HisTask) tasks.elementAt(jjj);
					if (ht.printable())
						System.out.println(ht);
				}
			}
			
	  }
	  
	  
	  /**
	   * print Log to file
	   * @param filename
	   */
	  public void printLog(String filename){
		  if (Log==null) {
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
			 
			  
			  for (int s = 0; s < Log.size(); s++) {
				  TaskList tlst = (TaskList) Log.elementAt(s);
				  Vector tasks = tlst.tasks;
				  for (int jjj = 0; jjj < tasks.size(); jjj++) {
					  HisTask ht = (HisTask) tasks.elementAt(jjj);
					  //System.out.println(ht);
					  if (ht.printable())
						  p.println (ht);
				  }
			  }
			  
			  p.close();
		  } catch (Exception e) {
			  System.err.println ("Error writing to file");
		  }  
	  }
	   


}
