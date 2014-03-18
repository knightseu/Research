/**
 * 
 */
package rtgrid.test;


import java.util.Vector;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rtgrid.COMMON;
import rtgrid.resource.ANI;
import rtgrid.resource.Cluster;
import rtgrid.scheduler.IScheduler;
import rtgrid.scheduler.SchMNEDF_IIT;
import rtgrid.scheduler.SchMN_IIT;
import rtgrid.scheduler.SchMN_IIT2;
import rtgrid.scheduler.SchSplitNoPA;
import rtgrid.taskmodel.Task;
import rtgrid.taskmodel.TaskList;
import rtgrid.util.SimResult;
import rtgrid.util.Simulator;

/**
 * TestSchSplitNoPA.java
 * Package:  rtgrid.test
 * @author Xuan, 2007-1-9 
 */
public class TestSchSplitNoPA extends TestCase  {
	Task task;
	Simulator sim;
	Vector NTList;	
	TaskList tlst;
    Cluster cl;
    ANI ani;
	SchSplitNoPA 	sch;
	Vector ATList;
	/**
	 * @throws java.lang.Exception
	 */
	//@Before
	public void setUp() throws Exception {
		COMMON.PARA_CMS=1;
		COMMON.PARA_CPS=10;
		COMMON.PARA_CLUSTERSIZE=10;
		//cl=new Cluster(COMMON.PARA_CLUSTERSIZE,0,0);
		
		ATList=new Vector();
		NTList=new Vector();
		tlst=new TaskList(0);
		for (int i=0;i<10;i++) {
		task=new Task(0, 0, 199, 10);
		tlst.addTask(task);
		}
		NTList.add(tlst);
		
		//sch=new SchSplitNoPA(cl);
	}

	/**
	 * @throws java.lang.Exception
	 */
	//@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link rtgrid.scheduler.SchSplitNoPA#run(int, java.util.Vector)}.
	 */
	//@Test
	public void testSchedulability() {
		//Assert.assertEquals(false,sch.Schedulability_Test(tlst.tasks, ATList, 0));
	}
	
	public void testRun() {
		String inputfile="config\\exp7-0.seq.split";
		Simulator sim=new Simulator();
        sim.readPara("config\\config2.sys");
        Vector NTList=sim.getFromFile(inputfile);	
		//Vector NTList=sim.getFromFile("config\\newtestcase.seq");		
        Cluster cl=new Cluster(COMMON.PARA_CLUSTERSIZE,0,0);
        
		IScheduler sch= new SchSplitNoPA(cl);
		SimResult simrst;
//		simrst=sch.run(COMMON.PARA_SIMULATION_TIME,NTList);
//		System.out.println("SchSplitNoPA:    "+simrst.getRejectRate());
//		System.out.println("   Total Num:    "+simrst.getTotalNum());
//		System.out.println("   Admit Num:    "+simrst.getAdmitNum());
//		System.out.println("   Reject Num:    "+simrst.getRejectNum());
		
//		simrst.printLog(inputfile+".splitnopa.log");
		
//		 NTList=sim.getFromFile(inputfile);	
//         cl=new Cluster(COMMON.PARA_CLUSTERSIZE,0,0);
//        
//		sch= new SchUserSplit(cl);
//		
//		simrst=sch.run(COMMON.PARA_SIMULATION_TIME,NTList);
//		System.out.println("SchUsrSplit:    "+simrst.getRejectRate());
//		System.out.println("   Total Num:    "+simrst.getTotalNum());
//		System.out.println("   Admit Num:    "+simrst.getAdmitNum());
//		System.out.println("   Reject Num:    "+simrst.getRejectNum());
//		simrst.printLog(inputfile+".usrsplit.log");
		
		
		
		inputfile="config\\exp7-0.seq";

//		 NTList=sim.getFromFile(inputfile);	
//			//Vector NTList=sim.getFromFile("config\\newtestcase.seq");		
//	         cl=new Cluster(COMMON.PARA_CLUSTERSIZE,0,0);
//	        
//			sch= new SchEDFMN(cl);
//			
//			simrst=sch.run(COMMON.PARA_SIMULATION_TIME,NTList);
//			System.out.println("Single:    "+simrst.getRejectRate());
//			System.out.println("   Total Num:    "+simrst.getTotalNum());
//			System.out.println("   Admit Num:    "+simrst.getAdmitNum());
//			System.out.println("   Reject Num:    "+simrst.getRejectNum());
//			simrst.printLog(inputfile+".single.log");
			
	    
			 NTList=sim.getFromFile(inputfile);	
				//Vector NTList=sim.getFromFile("config\\newtestcase.seq");		
		         cl=new Cluster(COMMON.PARA_CLUSTERSIZE,0,0);
		        
				sch= new SchMNEDF_IIT(cl);
				
				simrst=sch.run(COMMON.PARA_SIMULATION_TIME,NTList);
				System.out.println("Single with IIT:    "+simrst.getRejectRate());
				System.out.println("   Total Num:    "+simrst.getTotalNum());
				System.out.println("   Admit Num:    "+simrst.getAdmitNum());
				System.out.println("   Reject Num:    "+simrst.getRejectNum());
//				simrst.printLog(inputfile+".iit.log");
				
				
				 NTList=sim.getFromFile(inputfile);	
					//Vector NTList=sim.getFromFile("config\\newtestcase.seq");		
			         cl=new Cluster(COMMON.PARA_CLUSTERSIZE,0,0);
			        
					sch= new SchMN_IIT2(cl);
					
					simrst=sch.run(COMMON.PARA_SIMULATION_TIME,NTList);
					System.out.println("Single with IIT2:    "+simrst.getRejectRate());
					System.out.println("   Total Num:    "+simrst.getTotalNum());
					System.out.println("   Admit Num:    "+simrst.getAdmitNum());
					System.out.println("   Reject Num:    "+simrst.getRejectNum());
				//	simrst.printLog(inputfile+".iit2.log");
				
				

		  

	}

}
