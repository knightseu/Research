/**
 * 
 */
package rtgrid.test;


import junit.framework.Assert;
import junit.framework.TestCase;
import rtgrid.taskmodel.*;
import rtgrid.util.SysVector;
import rtgrid.COMMON;

/**
 * TestClassTask.java
 * Package:  rtgrid.test
 * @author Xuan, 2006-11-15 
 */
public class TestClassTask extends TestCase {
	Task task1;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		task1=new Task(1,100,500,30);
		task1.setCmsCps(1, 20);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link rtgrid.taskmodel.Task#getAbsoluteDeadline()}.
	 */
	public void testGetAbsoluteDeadline() {
		Assert.assertEquals(600,task1.getAbsoluteDeadline());
	}

	/**
	 * Test method for {@link rtgrid.taskmodel.Task#getArrTime()}.
	 */
	public void testGetArrTime() {
		Assert.assertEquals(100,task1.getArrTime());
	}

	/**
	 * Test method for {@link rtgrid.taskmodel.Task#getCompletionTime()}.
	 */
	public void testGetCompletionTime() {
		Assert.assertEquals(0,task1.getCompletionTime());
		task1.computeByNodes(0,4, COMMON.ROUND_SINGLE);
		Assert.assertEquals(270, task1.getCompletionTime());
	    
	}

	/**
	 * Test method for {@link rtgrid.taskmodel.Task#setCompletionTime(int)}.
	 */
	public void testSetCompletionTime() {
		task1.setCompletionTime(-1);
		Assert.assertEquals(-1, task1.getCompletionTime());
	}

	/**
	 * Test method for {@link rtgrid.taskmodel.Task#getExeTime()}.
	 */
	public void testGetExeTime() {
		task1.computeByNodes(0,4, COMMON.ROUND_SINGLE);
		Assert.assertEquals(170, task1.getExeTime());
	}

	/**
	 * Test method for {@link rtgrid.taskmodel.Task#getID()}.
	 */
	public void testGetID() {
		Assert.assertEquals(1, task1.getID());
	}

	/**
	 * Test method for {@link rtgrid.taskmodel.Task#getNodesAct()}.
	 */
	public void testGetNodesAct() {
		Assert.assertEquals(0, task1.getNodesAct());
	}

	/**
	 * Test method for {@link rtgrid.taskmodel.Task#getRelativeDeadline()}.
	 */
	public void testGetRelativeDeadline() {
		Assert.assertEquals(500, task1.getRelativeDeadline());
	}

	/**
	 * Test method for {@link rtgrid.taskmodel.Task#getStartTime()}.
	 */
	public void testGetStartTime() {
		Assert.assertEquals(0, task1.getStartTime());
		task1.computeNmin(0, COMMON.ROUND_SINGLE);
		Assert.assertEquals(100, task1.getStartTime());
	}

	/**
	 * Test method for {@link rtgrid.taskmodel.Task#setStartTime(int)}.
	 */
	public void testSetStartTime() {
		task1.setStartTime(-1);
		Assert.assertEquals(-1, task1.getStartTime());
	}

	/**
	 * Test method for {@link rtgrid.taskmodel.Task#getDatasize()}.
	 */
	public void testGetDatasize() {
		Assert.assertEquals(30, task1.getDatasize());
	}

	/**
	 * Test method for {@link rtgrid.taskmodel.Task#getNmin()}.
	 */
	public void testGetNmin() {
		task1.setNmin(-1);
		Assert.assertEquals(-1, task1.getNmin());
	}

	/**
	 * Test method for {@link rtgrid.taskmodel.Task#getDC()}.
	 */
	public void testGetDC() {
		task1.computeNmin(0, COMMON.SCH_MWF_MN_OPR_Single);
		//Assert.assertEquals(17, task1.getDC());
		Assert.assertEquals(15.6095, task1.getDC(), 0.0001);
		task1.computeByNodes(0, COMMON.PARA_CLUSTERSIZE, COMMON.SCH_MWF_MN_OPR_Single);
		Assert.assertEquals(999999999, task1.getDC(),0.1);
		
		//TODO for UMR?
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link rtgrid.taskmodel.Task#setNmin(int)}.
	 */
	public void testSetNmin() {
		task1.setNmin(-100);
		Assert.assertEquals(-100, task1.getNmin());
	}

	/**
	 * Test method for {@link rtgrid.taskmodel.Task#Task(rtgrid.taskmodel.Task)}.
	 */
	public void testTaskTask() {
		Task task2=new Task(task1);
		
		Assert.assertEquals(task1.getID(),task2.getID());
		Assert.assertEquals(task1.getRelativeDeadline(),task2.getRelativeDeadline()); // absolute deadline
		Assert.assertEquals(task1.getDatasize(),task2.getDatasize()); // the size of the data	
		Assert.assertEquals(task1.getArrTime(),task2.getArrTime()); // the time when the system is submit to the system
		Assert.assertEquals(task1.getStartTime(),task2.getStartTime()); // the time when the task is dispatched
		Assert.assertEquals(task1.getCompletionTime(),task2.getCompletionTime()); // the time when the task is finished
		Assert.assertEquals(task1.getNmin(),task2.getNmin()); // the minimum number of nodes it need to complete before deadline
		Assert.assertEquals(task1.getNodesAct(),task2.getNodesAct()); // number of machines actually assigned to it.get
		Assert.assertEquals(task1.getDC(),task2.getDC(),0.001);
		Assert.assertEquals(task1.getDispatchDoneTime(),task2.getDispatchDoneTime());
		
		
		//following parameters are used for calculate divisible load);
//		Assert.assertEquals(task1.getalpha1(),task2.getalpha1);
//		Assert.assertEquals(task1.getbeta(),task2.getbeta);
//		Assert.assertEquals(task1.getgamma(),task2.getgamma);
//		Assert.assertEquals(task1.getphi(),task2.getphi);
		
		Assert.assertEquals(task1.getCms(),task2.getCms()); 
		Assert.assertEquals(task1.getCps(),task2.getCps());
		
		Assert.assertEquals(task1.getST(),task2.getST());
		Assert.assertEquals(task1.getSC(),task2.getSC());
	}

	/**
	 * Test method for {@link rtgrid.taskmodel.Task#Task(int, int, int, int)}.
	 */
	public void testTaskIntIntIntInt() {
		Task task2=new Task(5,6,7,8);
		Assert.assertEquals(5, task2.getID());
		Assert.assertEquals(6, task2.getArrTime());
		Assert.assertEquals(7, task2.getRelativeDeadline());
		Assert.assertEquals(8, task2.getDatasize());
		
	}

	/**
	 * Test method for {@link rtgrid.taskmodel.Task#Task(int, int, int, int, int, int)}.
	 */
	public void testTaskIntIntIntIntIntInt() {
		Task task2=new Task(5,6,7,8,9,10);
		Assert.assertEquals(5, task2.getID());
		Assert.assertEquals(6, task2.getArrTime());
		Assert.assertEquals(7, task2.getRelativeDeadline());
		Assert.assertEquals(8, task2.getDatasize());
		Assert.assertEquals(9, task2.getST());
		Assert.assertEquals(10, task2.getSC());
		
	}

	/**
	 * Test method for {@link rtgrid.taskmodel.Task#computeNmin(int, int)}.
	 */
	public void testComputeNmin() {
		task1.computeNmin(100, COMMON.SCH_MWF_MN_OPR_Single);
		Assert.assertEquals(2, task1.getNmin());
	}

	/**
	 * Test method for {@link rtgrid.taskmodel.Task#computeDC()}.
	 */
	public void testComputeDC() {
		task1.computeNmin(0, COMMON.SCH_MWF_MN_OPR_Single);
	}

	/**
	 * Test method for {@link rtgrid.taskmodel.Task#computeCompletionTime(int, int)}.
	 */
	public void testComputeCompletionTime() {
		//it is protected, called in computeNmin
	}

	/**
	 * Test method for {@link rtgrid.taskmodel.Task#computeByNodes(int, int, int)}.
	 */
	public void testComputeByNodes() {
		task1.computeByNodes(0, 7,COMMON.SCH_MWF_MN_OPR_Single);
		Assert.assertEquals(104, task1.getExeTime());
	}

	/**
	 * Test method for {@link rtgrid.taskmodel.Task#sequentialTime()}.
	 */
	public void testSequentialTime() {
		Assert.assertEquals(630, task1.sequentialTime());
	}

	/**
	 * Test method for {@link rtgrid.taskmodel.Task#isDeadlineMissed()}.
	 */
	public void testIsDeadlineMissed() {
		task1.computeByNodes(0, 1,COMMON.SCH_MWF_MN_OPR_Single);
		Assert.assertEquals(true, task1.isDeadlineMissed());
	}

	/**
	 * Test method for {@link rtgrid.taskmodel.Task#toString()}.
	 */
	public void testToString() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link rtgrid.taskmodel.Task#getRespondsTime()}.
	 */
	public void testGetRespondsTime() {
		task1.computeByNodes(0, 8,COMMON.SCH_MWF_MN_OPR_Single);
		Assert.assertEquals(93, task1.getRespondsTime());
		task1.computeByNodes(110, 8,COMMON.SCH_MWF_MN_OPR_Single);
		Assert.assertEquals(103, task1.getRespondsTime());
	}

	/**
	 * Test method for {@link rtgrid.taskmodel.Task#getDispatchDoneTime()}.
	 */
	public void testGetDispatchDoneTime() {
		task1.computeNmin(0, COMMON.SCH_MWF_MN_OPR_Single);
		Assert.assertEquals(130, task1.getDispatchDoneTime());
	}

	/**
	 * Test method for {@link rtgrid.taskmodel.Task#setDispathDoneTime(int)}.
	 */
	public void testSetDispathDoneTime() {
		task1.setDispathDoneTime(-1);
		Assert.assertEquals(-1, task1.getDispatchDoneTime());
	}
	
	public void testComputeDatasize() {
		Assert.assertEquals(30, task1.computeDatasize(170, 4, 0));
		Assert.assertEquals(30, task1.computeDatasize(73, 11, 0));
		Assert.assertEquals(30, task1.computeDatasize(56, 16, 0));
	}
	
	public void testExeTimeHetero() {
		SysVector sysv=new SysVector(2);
		sysv.setCms(1);
		sysv.setCps(0,50);
		sysv.setCps(1,100);
		Task task=new Task(0,0,10000,100);
		Assert.assertEquals(3412,task.computeExecutiontime(sysv, COMMON.SCH_EDF_MN_OPR_Single));
		sysv=new SysVector(3);
		sysv.setCms(1);
		sysv.setCps(0,50);
		sysv.setCps(1,100);
		sysv.setCps(2,100);
		Assert.assertEquals(2570,task.computeExecutiontime(sysv, COMMON.SCH_EDF_MN_OPR_Single));
		
	}

}
