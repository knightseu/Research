/**
 * 
 */
package rtgrid.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import rtgrid.COMMON;
import rtgrid.taskmodel.Task;
import rtgrid.taskmodel.TaskList;
import rtgrid.util.Simulator;
import junit.framework.TestCase;

/**
 * TestWorkloads.java
 * Package:  rtgrid.test
 * @author Xuan, 2006-11-28 
 */
public class TestWorkloads extends TestCase {
    int totalWorkload=0;
	int numOfTask=0;
	double dcratio=0;
	int totalDatasize=0;
	int oldArrTime=0;
	int totalInterarrivalTime=0;
	String configFile,inputFile;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	
	/**
	 * Read tasks sequence from a file
	 * @param filename
	 * @return return a vector that contains such sequence
	 */
	
	protected Vector getFromFile(String filename) {
		totalWorkload=0;
		numOfTask=0;
		dcratio=0;
		totalDatasize=0;
		oldArrTime=0;
		totalInterarrivalTime=0;
//		TODO getFromFile not tested, especially several tasks in the same time point

		System.out.println();
		System.out.println("--------------------------------------------------");
		System.out.println("...Begin to read input file: "+filename +"  ...");
		System.out.println();
		
		String record = null;
		Vector NLTlist=new Vector();
	    TaskList tlst=new TaskList(-1);
	  
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			record = new String();
			int index,arrtime,tid,deadline,datasize,SC,ST,Cms,Cps;
			String sTmp,sKey;
			while ((record = br.readLine()) != null) {
		    	//parse String
		    	sKey="A=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                arrtime=Integer.parseInt(sTmp);
                //System.out.println(sTmp);
                
                sKey="id=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                tid=Integer.parseInt(sTmp);
                //System.out.println(sTmp);
                
                sKey="w=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                datasize=Integer.parseInt(sTmp);
                //System.out.println(sTmp);
                
                sKey="D=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                deadline=Integer.parseInt(sTmp);
                //System.out.println(sTmp);
                
                
                sKey="SC=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                SC=Integer.parseInt(sTmp);
                //System.out.println(sTmp);
                
                sKey="ST=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                ST=Integer.parseInt(sTmp);
                //System.out.println(sTmp);
                
                sKey="Cms=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(",");
                sTmp=record.substring(0,index);
                //Cms=Integer.parseInt(sTmp);
                Cms=COMMON.PARA_CMS;
                //System.out.println(sTmp);
                
                sKey="Cps=";
                index=record.indexOf(sKey);
                record=record.substring(index+sKey.length());
                index=record.indexOf(")");
                sTmp=record.substring(0,index);
                //Cps=Integer.parseInt(sTmp);
                Cps=COMMON.PARA_CPS;
                //System.out.println(sTmp);
                
                
                /**************************************************
		    	 * insert task to the task list
		    	 * 
		    	 ***********************************************/
				boolean b=false;
		        //check if arrtime already exit;
				//TODO best solution here if everything assume to be in order
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
			    Task task_=new Task(tid,arrtime,deadline,datasize);
			    task_.setCmsCps(Cms, Cps);
			    task_.setSetCost(ST, SC);
			    
			    
			    
			    /////////////////////////////begin test
			    
			    
			    task_.computeByNodes(arrtime, COMMON.PARA_CLUSTERSIZE, COMMON.ROUND_SINGLE);
			    if (task_.isDeadlineMissed())
			    	System.out.println("Deadline Missed: "+task_.toString());
			    totalWorkload+=task_.getExeTime();
			    numOfTask++;
			    totalDatasize+=task_.getDatasize();
			    dcratio+=1.0*task_.getRelativeDeadline()/task_.getExeTime();
			    totalInterarrivalTime+=task_.getArrTime()-oldArrTime;
			    oldArrTime=task_.getArrTime();
			    
			    
			    
			    
			    
			    
			    
			    /////////////////////////////end test
			    tlst.addTask(task_);
			    if (!b)
			    	NLTlist.add(tlst);
			}
			br.close();
			fr.close();
		} catch (IOException e) {
			System.out.println("Uh oh, got an IOException error!");
			e.printStackTrace();
		}
		

		System.out.println("Workload: "+1.0*totalWorkload/COMMON.PARA_SIMULATION_TIME);

		System.out.println("Number of tasks: "+numOfTask);

		System.out.println("Average DCRATIO: "+dcratio/numOfTask);
		
		System.out.println("Average Datasize: "+1.0*totalDatasize/numOfTask);
		
		System.out.println("Average Inter-arrival Time: "+1.0*totalInterarrivalTime/numOfTask);
		
		//printListScreen(NLTlist);
	    return NLTlist;
	  
	}

	public void testSplit() {
		configFile="E:\\MyDocuments\\aaaProjects\\Testcases\\config.sys";
		inputFile="E:\\MyDocuments\\aaaProjects\\Testcases\\split\\exp7-0.seq";
		Simulator sim= new Simulator();
		System.out.println("...Begin to read config file...");
		sim.readPara(configFile);
		getFromFile(inputFile);
		inputFile="E:\\MyDocuments\\aaaProjects\\Testcases\\split\\exp7-0.seq.split";
		getFromFile(inputFile);
		inputFile="E:\\MyDocuments\\aaaProjects\\Testcases\\split\\exp7-0.seq.splitan";
		getFromFile(inputFile);
		
	}

}
