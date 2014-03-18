/**
 * 
 */
package rtgrid.test;

import java.util.Vector;


import rtgrid.COMMON;
import rtgrid.resource.ANI;
import rtgrid.resource.Cluster;
import rtgrid.resource.Machine;
import rtgrid.scheduler.*;
import rtgrid.taskmodel.*;
import rtgrid.util.SimResult;
import rtgrid.util.Simulator;
import rtgrid.util.SysVector;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * TestIIT.java
 * Package:  rtgrid.test
 * @author Xuan, 2007-1-4 
 */
public class TestIIT extends TestCase {
	Vector ANList;
	Vector ANList1;
	Task task;
	Simulator sim;
	Vector NTList;	
    Cluster cl;
    ANI ani;
	SchMN_IIT 	sch;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		sim=new Simulator();
        sim.readPara("config\\config.sys");
		NTList=sim.getFromFile("config\\newtestcase.seq");		
        cl=new Cluster(COMMON.PARA_CLUSTERSIZE,0,0);
        
		sch= new SchMN_IIT(cl);
		ANList=new Vector();
		ANI ani=new ANI(20,3);
		ANList.add(ani);
		ani=new ANI(30,5);
		ANList.add(ani);
		ani=new ANI(40,7);
		ANList.add(ani);
		ani=new ANI(50,10);
		ANList.add(ani);
		
		ANList1=new Vector();
		ani=new ANI(50,10);
		ANList1.add(ani);
		
		task=new Task(0,0,1000,100);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link rtgrid.scheduler.SchMN_IIT#recalculateTask(rtgrid.taskmodel.Task, java.util.Vector, int)}.
	 */
	public void testRecalculateTask() {
//		ANI ani=new ANI(0,1);
//		Vector ANList=new Vector();
//		ANList.add(ani);
//		ani=new ANI(2000,2);
//		ANList.add(ani);
//		ani=new ANI(5919,4);
//		ANList.add(ani);
//		Cluster cl=new Cluster(4, 0, 0);
//		SchMN_IIT sch=new SchMN_IIT(cl);
//		SysVector sysv=new SysVector(4);
//		sysv.setCms(1);
//		sysv.setCps(100);
//		
//		Task task=new Task(0,0,10000,120);
//		
//		TaskList tlst=new TaskList(0);
//		tlst.addTask(task);
//		Vector NTList=new Vector();
//		NTList.add(tlst);
//		sch._sysv=sysv;
//		IDivisible dlt=new DivHomoSingle();
//		dlt.computeCompletionTime(5919, task, sysv);
//		sch.recalculateTask(task, ANList, 2);
//		Assert.assertEquals(5919,task.startTime);
//		Assert.assertEquals(8240,task.compTime);
//		Assert.assertEquals(5940,task.dispDoneTime);
//			
//		Machine m0=cl.getMachine(0);
//		m0.curEndTime=5919;
//		Machine m1=cl.getMachine(1);
//		m1.curEndTime=2000;
//		Machine m2=cl.getMachine(2);
//		m2.curEndTime=5919;
//		sch.run(100000, NTList);
//		
//		task=new Task(0,0,8995,120);
//		dlt.computeCompletionTime(5919, task, sysv);
//		sch.recalculateTask(task, ANList, 2);
//		Assert.assertEquals(5919,task.startTime);
//		Assert.assertEquals(7681,task.compTime);
//		Assert.assertEquals(5951,task.dispDoneTime);
	}
	
	public void testRun() {
		boolean bGenLog=false;
		Simulator sim=new Simulator();
        sim.readPara("config\\config.sys");
        Vector NTList=sim.getFromFile("config\\testdata.txt");	
		//Vector NTList=sim.getFromFile("config\\newtestcase.seq");		
        Cluster cl=new Cluster(COMMON.PARA_CLUSTERSIZE,0,0);
        
		IScheduler sch= new SchMNEDF(cl);
		
		SimResult simrst=sch.run(COMMON.PARA_SIMULATION_TIME,NTList);
		System.out.println("SchEDFMN:    "+simrst.getRejectRate());
		
		NTList=sim.getFromFile("config\\testdata.txt");	
		//NTList=sim.getFromFile("config\\newtestcase.seq");		
        cl=new Cluster(COMMON.PARA_CLUSTERSIZE,0,0);
        
		sch= new SchMNEDF_IIT(cl);
		
		simrst=sch.run(COMMON.PARA_SIMULATION_TIME,NTList);
		System.out.println("IIT:    "+simrst.getRejectRate());
		
		
		
		if (bGenLog)
		    	simrst.printLog("config\\testrst\\testdata-schedfmnIIT.log");
		fail("'compare log - testrst\\testdata-schedfmn.log' WITH 'config\\testdata-edfmn.log'" );
	}
//	
//	public void testInsertANList1() {
////		starttime not found
//		task.startTime=15;
//	    Assert.assertEquals(-1, sch.Insert_ANList(task, ANList));
//	    task.startTime=55;
//	    Assert.assertEquals(-1, sch.Insert_ANList(task, ANList));
//	}
//	
//public void testInsertANList2() {
//		
//	    //last but not first
//	    task.startTime=50;
//	    task.compTime=100;
//	    task.dispDoneTime=60;
//	    task.Nmin=9;
//	    //cl.printANList(ANList);
//	   sch.Insert_ANList(task, ANList);
//	   //cl.printANList(ANList);
//	   ANI ani=(ANI)ANList.elementAt(0);
//	   Assert.assertEquals(20, ani.time);
//	   Assert.assertEquals(0, ani.num);
//	   ani=(ANI)ANList.elementAt(1);
//	   Assert.assertEquals(30, ani.time);
//	   Assert.assertEquals(0, ani.num);
//	   ani=(ANI)ANList.elementAt(2);
//	   Assert.assertEquals(40, ani.time);
//	   Assert.assertEquals(0, ani.num);
//	   ani=(ANI)ANList.elementAt(3);
//	   Assert.assertEquals(50, ani.time);
//	   Assert.assertEquals(0, ani.num);
//	   ani=(ANI)ANList.elementAt(4);
//	   Assert.assertEquals(60, ani.time);
//	   Assert.assertEquals(1, ani.num);
//	   ani=(ANI)ANList.elementAt(5);
//	   Assert.assertEquals(100, ani.time);
//	   Assert.assertEquals(10, ani.num);
//	    
//	}
//
//public void testInsertANList3() {
//	
//    //last but first
//    task.startTime=50;
//    task.compTime=100;
//    task.dispDoneTime=60;
//    task.Nmin=9;
//    //cl.printANList(ANList);
//   sch.Insert_ANList(task, ANList1);
//   //cl.printANList(ANList);
//   ANI ani=(ANI)ANList1.elementAt(0);
//   Assert.assertEquals(50, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList1.elementAt(1);
//   Assert.assertEquals(60, ani.time);
//   Assert.assertEquals(1, ani.num);
//   ani=(ANI)ANList1.elementAt(2);
//   Assert.assertEquals(100, ani.time);
//   Assert.assertEquals(10, ani.num);
//}
//
//public void testInsertANList4() {
//	
//    //larger than
//    task.startTime=50;
//    task.compTime=100;
//    task.dispDoneTime=60;
//    task.Nmin=11;
//   cl.printANList(ANList);
//   Assert.assertEquals(-1, sch.Insert_ANList(task, ANList));
//   cl.printANList(ANList);
//
//    
//}
//
//public void testInsertANList5() {
////	Test Case 5  		(Key = 4.1.1.1.)
////	   Starttime         :  find but not last 
////	   nodes             :  less than 
////	   completion time   :  less than next 
////	   dispatchdone time :  less than next
//	
//    
//    task.startTime=20;
//    task.compTime=25;
//    task.dispDoneTime=22;
//    task.Nmin=2;
//    //cl.printANList(ANList);
//   sch.Insert_ANList(task, ANList);
//   //cl.printANList(ANList);
//   ANI ani=(ANI)ANList.elementAt(0);
//   Assert.assertEquals(20, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList.elementAt(1);
//   Assert.assertEquals(22, ani.time);
//   Assert.assertEquals(1, ani.num);
//   ani=(ANI)ANList.elementAt(2);
//   Assert.assertEquals(25, ani.time);
//   Assert.assertEquals(3, ani.num);
//   ani=(ANI)ANList.elementAt(3);
//   Assert.assertEquals(30, ani.time);
//   Assert.assertEquals(5, ani.num);
//   ani=(ANI)ANList.elementAt(4);
//   Assert.assertEquals(40, ani.time);
//   Assert.assertEquals(7, ani.num);
//   ani=(ANI)ANList.elementAt(5);
//   Assert.assertEquals(50, ani.time);
//   Assert.assertEquals(10, ani.num);
//    
//}
//
//public void testInsertANList10() {
////	Test Case 10 		(Key = 4.1.2.1.)
////	   Starttime         :  find but not last 
////	   nodes             :  less than 
////	   completion time   :  equal to next
////	   dispatchdone time :  less than next
//    
//    task.startTime=20;
//    task.Nmin=2;
//    task.compTime=30;
//    task.dispDoneTime=22;
//
//    //cl.printANList(ANList);
//   sch.Insert_ANList(task, ANList);
//   //cl.printANList(ANList);
//   ANI ani=(ANI)ANList.elementAt(0);
//   Assert.assertEquals(20, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList.elementAt(1);
//   Assert.assertEquals(22, ani.time);
//   Assert.assertEquals(1, ani.num);
//   ani=(ANI)ANList.elementAt(2);
//   Assert.assertEquals(30, ani.time);
//   Assert.assertEquals(5, ani.num);
//   ani=(ANI)ANList.elementAt(3);
//   Assert.assertEquals(40, ani.time);
//   Assert.assertEquals(7, ani.num);
//   ani=(ANI)ANList.elementAt(4);
//   Assert.assertEquals(50, ani.time);
//   Assert.assertEquals(10, ani.num);
//    
//}
//
//public void testInsertANList15() {
//
////	Test Case 15 		(Key = 4.1.3.1.)
////	   Starttime         :  find but not last 
////	   nodes             :  less than 
////	   completion time   :  larger than next bu less than last
////	   dispatchdone time :  less than next
//    
//    task.startTime=30;
//    task.Nmin=4;
//    task.compTime=45;
//    task.dispDoneTime=32;
//
//    //cl.printANList(ANList);
//   sch.Insert_ANList(task, ANList);
//   //cl.printANList(ANList);
//   ANI ani=(ANI)ANList.elementAt(0);
//   Assert.assertEquals(20, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList.elementAt(1);
//   Assert.assertEquals(30, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList.elementAt(2);
//   Assert.assertEquals(32, ani.time);
//   Assert.assertEquals(1, ani.num);
//   ani=(ANI)ANList.elementAt(3);
//   Assert.assertEquals(40, ani.time);
//   Assert.assertEquals(3, ani.num);
//  ani=(ANI)ANList.elementAt(4);
//   Assert.assertEquals(45, ani.time);
//   Assert.assertEquals(7, ani.num);
//   ani=(ANI)ANList.elementAt(5);
//   Assert.assertEquals(50, ani.time);
//   Assert.assertEquals(10, ani.num);
//    
//}
//
//public void testInsertANList16() {
//
////	Test Case 16 		(Key = 4.1.3.2.)
////	   Starttime         :  find but not last 
////	   nodes             :  less than 
////	   completion time   :  larger than next bu less than last
////	   dispatchdone time :  equal to next
//    
//    task.startTime=30;
//    task.Nmin=4;
//    task.compTime=45;
//    task.dispDoneTime=40;
//
//    //cl.printANList(ANList);
//   sch.Insert_ANList(task, ANList);
//   //cl.printANList(ANList);
//   ANI ani=(ANI)ANList.elementAt(0);
//   Assert.assertEquals(20, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList.elementAt(1);
//   Assert.assertEquals(30, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList.elementAt(2);
//   Assert.assertEquals(40, ani.time);
//   Assert.assertEquals(3, ani.num);
//  ani=(ANI)ANList.elementAt(3);
//   Assert.assertEquals(45, ani.time);
//   Assert.assertEquals(7, ani.num);
//   ani=(ANI)ANList.elementAt(4);
//   Assert.assertEquals(50, ani.time);
//   Assert.assertEquals(10, ani.num);
//    
//}
//
//
//public void testInsertANList17() {
//
////	Test Case 17 		(Key = 4.1.3.3.)
////	   Starttime         :  find but not last 
////	   nodes             :  less than 
////	   completion time   :  larger than next bu less than last
////	   dispatchdone time :  larger than next bu less than last
//    
//    task.startTime=30;
//    task.Nmin=4;
//    task.compTime=45;
//    task.dispDoneTime=42;
//
//    //cl.printANList(ANList);
//   sch.Insert_ANList(task, ANList);
//   //cl.printANList(ANList);
//   ANI ani=(ANI)ANList.elementAt(0);
//   Assert.assertEquals(20, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList.elementAt(1);
//   Assert.assertEquals(30, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList.elementAt(2);
//   Assert.assertEquals(40, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList.elementAt(3);
//   Assert.assertEquals(42, ani.time);
//   Assert.assertEquals(3, ani.num);
//  ani=(ANI)ANList.elementAt(4);
//   Assert.assertEquals(45, ani.time);
//   Assert.assertEquals(7, ani.num);
//   ani=(ANI)ANList.elementAt(5);
//   Assert.assertEquals(50, ani.time);
//   Assert.assertEquals(10, ani.num);
//    
//}
//
//
//public void testInsertANList20() {
//
////	Test Case 20 		(Key = 4.1.4.1.)
////	   Starttime         :  find but not last 
////	   nodes             :  less than 
////	   completion time   :  equal to the last
////	   dispatchdone time :  less than next
//    
//    task.startTime=30;
//    task.Nmin=4;
//    task.compTime=50;
//    task.dispDoneTime=35;
//
//    //cl.printANList(ANList);
//   sch.Insert_ANList(task, ANList);
//   //cl.printANList(ANList);
//   ANI ani=(ANI)ANList.elementAt(0);
//   Assert.assertEquals(20, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList.elementAt(1);
//   Assert.assertEquals(30, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList.elementAt(2);
//   Assert.assertEquals(35, ani.time);
//   Assert.assertEquals(1, ani.num);
//   ani=(ANI)ANList.elementAt(3);
//   Assert.assertEquals(40, ani.time);
//   Assert.assertEquals(3, ani.num);
//   ani=(ANI)ANList.elementAt(4);
//   Assert.assertEquals(50, ani.time);
//   Assert.assertEquals(10, ani.num);
//    
//}
//
//
//
//
//public void testInsertANList21() {
//
////	Test Case 21 		(Key = 4.1.4.2.)
////	   Starttime         :  find but not last 
////	   nodes             :  less than 
////	   completion time   :  equal to the last
////	   dispatchdone time :  equal to next
//    
//    task.startTime=30;
//    task.Nmin=4;
//    task.compTime=50;
//    task.dispDoneTime=40;
//
//    //cl.printANList(ANList);
//   sch.Insert_ANList(task, ANList);
//   //cl.printANList(ANList);
//   ANI ani=(ANI)ANList.elementAt(0);
//   Assert.assertEquals(20, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList.elementAt(1);
//   Assert.assertEquals(30, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList.elementAt(2);
//   Assert.assertEquals(40, ani.time);
//   Assert.assertEquals(3, ani.num);
//   ani=(ANI)ANList.elementAt(3);
//   Assert.assertEquals(50, ani.time);
//   Assert.assertEquals(10, ani.num);
//    
//}
//
//
//public void testInsertANList22() {
//
////	Test Case 22 		(Key = 4.1.4.3.)
////	   Starttime         :  find but not last 
////	   nodes             :  less than 
////	   completion time   :  equal to the last
////	   dispatchdone time :  larger than next bu less than last
//    
//    task.startTime=30;
//    task.Nmin=4;
//    task.compTime=50;
//    task.dispDoneTime=45;
//
//    //cl.printANList(ANList);
//   sch.Insert_ANList(task, ANList);
//   //cl.printANList(ANList);
//   ANI ani=(ANI)ANList.elementAt(0);
//   Assert.assertEquals(20, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList.elementAt(1);
//   Assert.assertEquals(30, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList.elementAt(2);
//   Assert.assertEquals(40, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList.elementAt(3);
//   Assert.assertEquals(45, ani.time);
//   Assert.assertEquals(3, ani.num);
//   ani=(ANI)ANList.elementAt(4);
//   Assert.assertEquals(50, ani.time);
//   Assert.assertEquals(10, ani.num);
//    
//}
//
//
//public void testInsertANList25() {
//
////	Test Case 25 		(Key = 4.1.5.1.)
////	   Starttime         :  find but not last 
////	   nodes             :  less than 
////	   completion time   :  larger than the last
////	   dispatchdone time :  less than next
//    
//    task.startTime=30;
//    task.Nmin=4;
//    task.compTime=55;
//    task.dispDoneTime=35;
//
//    //cl.printANList(ANList);
//   sch.Insert_ANList(task, ANList);
//   //cl.printANList(ANList);
//   ANI ani=(ANI)ANList.elementAt(0);
//   Assert.assertEquals(20, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList.elementAt(1);
//   Assert.assertEquals(30, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList.elementAt(2);
//   Assert.assertEquals(35, ani.time);
//   Assert.assertEquals(1, ani.num);
//   ani=(ANI)ANList.elementAt(3);
//   Assert.assertEquals(40, ani.time);
//   Assert.assertEquals(3, ani.num);
//   
//   ani=(ANI)ANList.elementAt(4);
//   Assert.assertEquals(50, ani.time);
//   Assert.assertEquals(6, ani.num);
//   ani=(ANI)ANList.elementAt(5);
//   Assert.assertEquals(55, ani.time);
//   Assert.assertEquals(10, ani.num);
//    
//}
//
//
//
//public void testInsertANList26() {
//
////	Test Case 26 		(Key = 4.1.5.2.)
////	   Starttime         :  find but not last 
////	   nodes             :  less than 
////	   completion time   :  larger than the last
////	   dispatchdone time :  equal to next
//    
//    task.startTime=30;
//    task.Nmin=4;
//    task.compTime=55;
//    task.dispDoneTime=40;
//
//    //cl.printANList(ANList);
//   sch.Insert_ANList(task, ANList);
//   //cl.printANList(ANList);
//   ANI ani=(ANI)ANList.elementAt(0);
//   Assert.assertEquals(20, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList.elementAt(1);
//   Assert.assertEquals(30, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList.elementAt(2);
//   Assert.assertEquals(40, ani.time);
//   Assert.assertEquals(3, ani.num);
//   
//   ani=(ANI)ANList.elementAt(3);
//   Assert.assertEquals(50, ani.time);
//   Assert.assertEquals(6, ani.num);
//   ani=(ANI)ANList.elementAt(4);
//   Assert.assertEquals(55, ani.time);
//   Assert.assertEquals(10, ani.num);
//    
//}
//
//
//public void testInsertANList27() {
//
////	Test Case 27 		(Key = 4.1.5.3.)
////	   Starttime         :  find but not last 
////	   nodes             :  less than 
////	   completion time   :  larger than the last
////	   dispatchdone time :  larger than next bu less than last
//    
//    task.startTime=30;
//    task.Nmin=4;
//    task.compTime=55;
//    task.dispDoneTime=45;
//
//    //cl.printANList(ANList);
//   sch.Insert_ANList(task, ANList);
//   //cl.printANList(ANList);
//   ANI ani=(ANI)ANList.elementAt(0);
//   Assert.assertEquals(20, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList.elementAt(1);
//   Assert.assertEquals(30, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList.elementAt(2);
//   Assert.assertEquals(40, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList.elementAt(3);
//   Assert.assertEquals(45, ani.time);
//   Assert.assertEquals(3, ani.num);
//   ani=(ANI)ANList.elementAt(4);
//   Assert.assertEquals(50, ani.time);
//   Assert.assertEquals(6, ani.num);
//   ani=(ANI)ANList.elementAt(5);
//   Assert.assertEquals(55, ani.time);
//   Assert.assertEquals(10, ani.num);
//    
//}
//
//public void testInsertANList28() {
//
////	Test Case 28 		(Key = 4.1.5.4.)
////	   Starttime         :  find but not last 
////	   nodes             :  less than 
////	   completion time   :  larger than the last
////	   dispatchdone time :  equal to the last
//    
//    task.startTime=30;
//    task.Nmin=4;
//    task.compTime=55;
//    task.dispDoneTime=50;
//
//    //cl.printANList(ANList);
//   sch.Insert_ANList(task, ANList);
//   //cl.printANList(ANList);
//   ANI ani=(ANI)ANList.elementAt(0);
//   Assert.assertEquals(20, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList.elementAt(1);
//   Assert.assertEquals(30, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList.elementAt(2);
//   Assert.assertEquals(40, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList.elementAt(3);
//   Assert.assertEquals(50, ani.time);
//   Assert.assertEquals(6, ani.num);
//   ani=(ANI)ANList.elementAt(4);
//   Assert.assertEquals(55, ani.time);
//   Assert.assertEquals(10, ani.num);
//    
//}
//
//
//public void testInsertANList29() {
//
////	Test Case 29 		(Key = 4.1.5.5.)
////	   Starttime         :  find but not last 
////	   nodes             :  less than 
////	   completion time   :  larger than the last
////	   dispatchdone time :  larger than the last
//    
//    task.startTime=30;
//    task.Nmin=4;
//    task.compTime=55;
//    task.dispDoneTime=52;
//
//    //cl.printANList(ANList);
//   sch.Insert_ANList(task, ANList);
//   //cl.printANList(ANList);
//   ANI ani=(ANI)ANList.elementAt(0);
//   Assert.assertEquals(20, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList.elementAt(1);
//   Assert.assertEquals(30, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList.elementAt(2);
//   Assert.assertEquals(40, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList.elementAt(3);
//   Assert.assertEquals(50, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList.elementAt(4);
//   Assert.assertEquals(52, ani.time);
//   Assert.assertEquals(6, ani.num);
//   ani=(ANI)ANList.elementAt(5);
//   Assert.assertEquals(55, ani.time);
//   Assert.assertEquals(10, ani.num);
//    
//}
//
//
//public void testInsertANList30() {
//
////	Test Case 30 		(Key = 4.2.1.1.)
////	   Starttime         :  find but not last 
////	   nodes             :  equal
////	   completion time   :  less than next 
////	   dispatchdone time :  less than next
//    
//    task.startTime=30;
//    task.Nmin=5;
//    task.compTime=35;
//    task.dispDoneTime=32;
//
//    //cl.printANList(ANList);
//   sch.Insert_ANList(task, ANList);
//   //cl.printANList(ANList);
//   ANI ani=(ANI)ANList.elementAt(0);
//   Assert.assertEquals(20, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList.elementAt(1);
//   Assert.assertEquals(30, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList.elementAt(2);
//   Assert.assertEquals(35, ani.time);
//   Assert.assertEquals(5, ani.num);
//   ani=(ANI)ANList.elementAt(3);
//   Assert.assertEquals(40, ani.time);
//   Assert.assertEquals(7, ani.num);
//   ani=(ANI)ANList.elementAt(4);
//   Assert.assertEquals(50, ani.time);
//   Assert.assertEquals(10, ani.num);
//   
//    
//}
//
//public void testInsertANList35() {
////	Test Case 35 		(Key = 4.2.2.1.)
////	   Starttime         :  find but not last 
////	   nodes             :  equal
////	   completion time   :  equal to next
////	   dispatchdone time :  less than next
//    
//    task.startTime=30;
//    task.Nmin=5;
//    task.compTime=40;
//    task.dispDoneTime=35;
//
//    //cl.printANList(ANList);
//   sch.Insert_ANList(task, ANList);
//   //cl.printANList(ANList);
//   ANI ani=(ANI)ANList.elementAt(0);
//   Assert.assertEquals(20, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList.elementAt(1);
//   Assert.assertEquals(30, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList.elementAt(2);
//   Assert.assertEquals(40, ani.time);
//   Assert.assertEquals(7, ani.num);
//   ani=(ANI)ANList.elementAt(3);
//   Assert.assertEquals(50, ani.time);
//   Assert.assertEquals(10, ani.num);
//   
//    
//}
//
//
//public void testInsertANList40() {
//
////	Test Case 40 		(Key = 4.2.3.1.)
////	   Starttime         :  find but not last 
////	   nodes             :  equal
////	   completion time   :  larger than next bu less than last
////	   dispatchdone time :  less than next
//    
//    task.startTime=30;
//    task.Nmin=5;
//    task.compTime=45;
//    task.dispDoneTime=35;
//
//    //cl.printANList(ANList);
//   sch.Insert_ANList(task, ANList);
//   //cl.printANList(ANList);
//   ANI ani=(ANI)ANList.elementAt(0);
//   Assert.assertEquals(20, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList.elementAt(1);
//   Assert.assertEquals(30, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList.elementAt(2);
//   Assert.assertEquals(40, ani.time);
//   Assert.assertEquals(2, ani.num);
//   ani=(ANI)ANList.elementAt(3);
//   Assert.assertEquals(45, ani.time);
//   Assert.assertEquals(7, ani.num);
//   ani=(ANI)ANList.elementAt(4);
//   Assert.assertEquals(50, ani.time);
//   Assert.assertEquals(10, ani.num);
//   
//    
//}
//
//
//public void testInsertANList41() {
//
////	Test Case 41 		(Key = 4.2.3.2.)
////	   Starttime         :  find but not last 
////	   nodes             :  equal
////	   completion time   :  larger than next bu less than last
////	   dispatchdone time :  equal to next
//    
//    task.startTime=30;
//    task.Nmin=5;
//    task.compTime=45;
//    task.dispDoneTime=40;
//
//    //cl.printANList(ANList);
//   sch.Insert_ANList(task, ANList);
//   //cl.printANList(ANList);
//   ANI ani=(ANI)ANList.elementAt(0);
//   Assert.assertEquals(20, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList.elementAt(1);
//   Assert.assertEquals(30, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList.elementAt(2);
//   Assert.assertEquals(40, ani.time);
//   Assert.assertEquals(2, ani.num);
//   ani=(ANI)ANList.elementAt(3);
//   Assert.assertEquals(45, ani.time);
//   Assert.assertEquals(7, ani.num);
//   ani=(ANI)ANList.elementAt(4);
//   Assert.assertEquals(50, ani.time);
//   Assert.assertEquals(10, ani.num);
//   
//    
//}
//
//public void testInsertANList42() {
//
//
////	Test Case 42 		(Key = 4.2.3.3.)
////	   Starttime         :  find but not last 
////	   nodes             :  equal
////	   completion time   :  larger than next bu less than last
////	   dispatchdone time :  larger than next bu less than last
////    
//    task.startTime=30;
//    task.Nmin=5;
//    task.compTime=45;
//    task.dispDoneTime=32;
//
//    //cl.printANList(ANList);
//   sch.Insert_ANList(task, ANList);
//   //cl.printANList(ANList);
//   ANI ani=(ANI)ANList.elementAt(0);
//   Assert.assertEquals(20, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList.elementAt(1);
//   Assert.assertEquals(30, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList.elementAt(2);
//   Assert.assertEquals(40, ani.time);
//   Assert.assertEquals(2, ani.num);
//   ani=(ANI)ANList.elementAt(3);
//   Assert.assertEquals(45, ani.time);
//   Assert.assertEquals(7, ani.num);
//   ani=(ANI)ANList.elementAt(4);
//   Assert.assertEquals(50, ani.time);
//   Assert.assertEquals(10, ani.num);
//   
//    
//}
//
//
//public void testInsertANList45() {
//
////
////	Test Case 45 		(Key = 4.2.4.1.)
////	   Starttime         :  find but not last 
////	   nodes             :  equal
////	   completion time   :  equal to the last
////	   dispatchdone time :  less than next
//	   
//	   
//    task.startTime=30;
//    task.Nmin=5;
//    task.compTime=50;
//    task.dispDoneTime=32;
//
//    //cl.printANList(ANList);
//   sch.Insert_ANList(task, ANList);
//   //cl.printANList(ANList);
//   ANI ani=(ANI)ANList.elementAt(0);
//   Assert.assertEquals(20, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList.elementAt(1);
//   Assert.assertEquals(30, ani.time);
//   Assert.assertEquals(0, ani.num);
//   ani=(ANI)ANList.elementAt(2);
//   Assert.assertEquals(40, ani.time);
//   Assert.assertEquals(2, ani.num);
//   ani=(ANI)ANList.elementAt(3);
//   Assert.assertEquals(50, ani.time);
//   Assert.assertEquals(10, ani.num);
//   
//    
//}
//
//public void testInsertANList46() {
//
////	Test Case 46 		(Key = 4.2.4.2.)
////	   Starttime         :  find but not last 
////	   nodes             :  equal
////	   completion time   :  equal to the last
////	   dispatchdone time :  equal to next
//		   
//		   
//	    task.startTime=30;
//	    task.Nmin=5;
//	    task.compTime=50;
//	    task.dispDoneTime=40;
//
//	    //cl.printANList(ANList);
//	   sch.Insert_ANList(task, ANList);
//	   //cl.printANList(ANList);
//	   ANI ani=(ANI)ANList.elementAt(0);
//	   Assert.assertEquals(20, ani.time);
//	   Assert.assertEquals(0, ani.num);
//	   ani=(ANI)ANList.elementAt(1);
//	   Assert.assertEquals(30, ani.time);
//	   Assert.assertEquals(0, ani.num);
//	   ani=(ANI)ANList.elementAt(2);
//	   Assert.assertEquals(40, ani.time);
//	   Assert.assertEquals(2, ani.num);
//	   ani=(ANI)ANList.elementAt(3);
//	   Assert.assertEquals(50, ani.time);
//	   Assert.assertEquals(10, ani.num);
//	   
//	    
//	}
//
//
//public void testInsertANList47() {
//
////	Test Case 47 		(Key = 4.2.4.3.)
////	   Starttime         :  find but not last 
////	   nodes             :  equal
////	   completion time   :  equal to the last
////	   dispatchdone time :  larger than next bu less than last
//		   
//		   
//	    task.startTime=30;
//	    task.Nmin=5;
//	    task.compTime=50;
//	    task.dispDoneTime=45;
//
//	    //cl.printANList(ANList);
//	   sch.Insert_ANList(task, ANList);
//	   //cl.printANList(ANList);
//	   ANI ani=(ANI)ANList.elementAt(0);
//	   Assert.assertEquals(20, ani.time);
//	   Assert.assertEquals(0, ani.num);
//	   ani=(ANI)ANList.elementAt(1);
//	   Assert.assertEquals(30, ani.time);
//	   Assert.assertEquals(0, ani.num);
//	   ani=(ANI)ANList.elementAt(2);
//	   Assert.assertEquals(40, ani.time);
//	   Assert.assertEquals(0, ani.num);
//	   ani=(ANI)ANList.elementAt(3);
//	   Assert.assertEquals(45, ani.time);
//	   Assert.assertEquals(2, ani.num);
//	   ani=(ANI)ANList.elementAt(4);
//	   Assert.assertEquals(50, ani.time);
//	   Assert.assertEquals(10, ani.num);
//	   
//	    
//	}
//
//public void testInsertANList50() {
//
////
////	Test Case 50 		(Key = 4.2.5.1.)
////	   Starttime         :  find but not last 
////	   nodes             :  equal
////	   completion time   :  larger than the last
////	   dispatchdone time :  less than next
////		   
//		   
//	    task.startTime=30;
//	    task.Nmin=5;
//	    task.compTime=55;
//	    task.dispDoneTime=35;
//
//	    //cl.printANList(ANList);
//	   sch.Insert_ANList(task, ANList);
//	   //cl.printANList(ANList);
//	   ANI ani=(ANI)ANList.elementAt(0);
//	   Assert.assertEquals(20, ani.time);
//	   Assert.assertEquals(0, ani.num);
//	   ani=(ANI)ANList.elementAt(1);
//	   Assert.assertEquals(30, ani.time);
//	   Assert.assertEquals(0, ani.num);
//	   ani=(ANI)ANList.elementAt(2);
//	   Assert.assertEquals(40, ani.time);
//	   Assert.assertEquals(2, ani.num);
//	   ani=(ANI)ANList.elementAt(3);
//	   Assert.assertEquals(50, ani.time);
//	   Assert.assertEquals(5, ani.num);
//	   ani=(ANI)ANList.elementAt(4);
//	   Assert.assertEquals(55, ani.time);
//	   Assert.assertEquals(10, ani.num);
//	    
//	}
//
//
//public void testInsertANList51() {
//
////	Test Case 51 		(Key = 4.2.5.2.)
////	   Starttime         :  find but not last 
////	   nodes             :  equal
////	   completion time   :  larger than the last
////	   dispatchdone time :  equal to next 
////		   
//	    task.startTime=30;
//	    task.Nmin=5;
//	    task.compTime=55;
//	    task.dispDoneTime=40;
//
//	    //cl.printANList(ANList);
//	   sch.Insert_ANList(task, ANList);
//	   //cl.printANList(ANList);
//	   ANI ani=(ANI)ANList.elementAt(0);
//	   Assert.assertEquals(20, ani.time);
//	   Assert.assertEquals(0, ani.num);
//	   ani=(ANI)ANList.elementAt(1);
//	   Assert.assertEquals(30, ani.time);
//	   Assert.assertEquals(0, ani.num);
//	   ani=(ANI)ANList.elementAt(2);
//	   Assert.assertEquals(40, ani.time);
//	   Assert.assertEquals(2, ani.num);
//	   ani=(ANI)ANList.elementAt(3);
//	   Assert.assertEquals(50, ani.time);
//	   Assert.assertEquals(5, ani.num);
//	   ani=(ANI)ANList.elementAt(4);
//	   Assert.assertEquals(55, ani.time);
//	   Assert.assertEquals(10, ani.num);
//	    
//	}
//
//
//public void testInsertANList52() {
//
////
////	Test Case 52 		(Key = 4.2.5.3.)
////	   Starttime         :  find but not last 
////	   nodes             :  equal
////	   completion time   :  larger than the last
////	   dispatchdone time :  larger than next bu less than last	   
//	
//	    task.startTime=30;
//	    task.Nmin=5;
//	    task.compTime=55;
//	    task.dispDoneTime=45;
//
//	    //cl.printANList(ANList);
//	   sch.Insert_ANList(task, ANList);
//	   //cl.printANList(ANList);
//	   ANI ani=(ANI)ANList.elementAt(0);
//	   Assert.assertEquals(20, ani.time);
//	   Assert.assertEquals(0, ani.num);
//	   ani=(ANI)ANList.elementAt(1);
//	   Assert.assertEquals(30, ani.time);
//	   Assert.assertEquals(0, ani.num);
//	   ani=(ANI)ANList.elementAt(2);
//	   Assert.assertEquals(40, ani.time);
//	   Assert.assertEquals(0, ani.num);
//	   ani=(ANI)ANList.elementAt(3);
//	   Assert.assertEquals(45, ani.time);
//	   Assert.assertEquals(2, ani.num);
//	   ani=(ANI)ANList.elementAt(4);
//	   Assert.assertEquals(50, ani.time);
//	   Assert.assertEquals(5, ani.num);
//	   ani=(ANI)ANList.elementAt(5);
//	   Assert.assertEquals(55, ani.time);
//	   Assert.assertEquals(10, ani.num);
//	    
//	}
//
//public void testInsertANList53() {
//
//
////	Test Case 53 		(Key = 4.2.5.4.)
////	   Starttime         :  find but not last 
////	   nodes             :  equal
////	   completion time   :  larger than the last
////	   dispatchdone time :  equal to the last   
//		
//		    task.startTime=30;
//		    task.Nmin=5;
//		    task.compTime=55;
//		    task.dispDoneTime=50;
//
//		    //cl.printANList(ANList);
//		   sch.Insert_ANList(task, ANList);
//		   //cl.printANList(ANList);
//		   ANI ani=(ANI)ANList.elementAt(0);
//		   Assert.assertEquals(20, ani.time);
//		   Assert.assertEquals(0, ani.num);
//		   ani=(ANI)ANList.elementAt(1);
//		   Assert.assertEquals(30, ani.time);
//		   Assert.assertEquals(0, ani.num);
//		   ani=(ANI)ANList.elementAt(2);
//		   Assert.assertEquals(40, ani.time);
//		   Assert.assertEquals(0, ani.num);
//		   ani=(ANI)ANList.elementAt(3);
//		   Assert.assertEquals(50, ani.time);
//		   Assert.assertEquals(5, ani.num);
//		   ani=(ANI)ANList.elementAt(4);
//		   Assert.assertEquals(55, ani.time);
//		   Assert.assertEquals(10, ani.num);
//		    
//		}
//
//public void testInsertANList54() {
////
////	Test Case 54 		(Key = 4.2.5.5.)
////	   Starttime         :  find but not last 
////	   nodes             :  equal
////	   completion time   :  larger than the last
////	   dispatchdone time :  larger than the last 
//		
//		    task.startTime=30;
//		    task.Nmin=5;
//		    task.compTime=55;
//		    task.dispDoneTime=52;
//
//		    //cl.printANList(ANList);
//		   sch.Insert_ANList(task, ANList);
//		   //cl.printANList(ANList);
//		   ANI ani=(ANI)ANList.elementAt(0);
//		   Assert.assertEquals(20, ani.time);
//		   Assert.assertEquals(0, ani.num);
//		   ani=(ANI)ANList.elementAt(1);
//		   Assert.assertEquals(30, ani.time);
//		   Assert.assertEquals(0, ani.num);
//		   ani=(ANI)ANList.elementAt(2);
//		   Assert.assertEquals(40, ani.time);
//		   Assert.assertEquals(0, ani.num);
//		   ani=(ANI)ANList.elementAt(3);
//		   Assert.assertEquals(50, ani.time);
//		   Assert.assertEquals(0, ani.num);
//		   ani=(ANI)ANList.elementAt(4);
//		   Assert.assertEquals(52, ani.time);
//		   Assert.assertEquals(5, ani.num);
//		   ani=(ANI)ANList.elementAt(5);
//		   Assert.assertEquals(55, ani.time);
//		   Assert.assertEquals(10, ani.num);
//		    
//		}

}
