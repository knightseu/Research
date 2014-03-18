/**
 * 
 */
package rtgrid.test;

import rtgrid.scheduler.DivHomoSingle;
import rtgrid.scheduler.IDivisible;
import rtgrid.taskmodel.SubTask;
import rtgrid.taskmodel.Task;
import rtgrid.util.SysVector;
import rtgrid.COMMON;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * TestDivHomoSingle.java
 * Package:  rtgrid.test
 * @author Xuan, 2006-12-31 
 */
public class TestDivHomoSingle extends TestCase {
    Task task1;
    SysVector sysv;
    IDivisible dlt;
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		task1=new Task(1,100,500,30);
		sysv=new SysVector(COMMON.PARA_CLUSTERSIZE);
		sysv.setCms(1);
		sysv.setCps(20);
		dlt=new DivHomoSingle();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link rtgrid.scheduler.DivHomoSingle#computeCompletionTime(int, rtgrid.taskmodel.Task, rtgrid.util.SysVector)}.
	 */
	public void testComputeCompletionTimeIntTaskSysVector() {
		dlt.computeCompletionTime(100, task1, sysv);
		Assert.assertEquals(2, task1.Nmin);
		Assert.assertEquals(423, task1.getCompletionTime());
		Assert.assertEquals(100, task1.startTime);
		Assert.assertEquals(130, task1.getDispatchDoneTime());
		SubTask subT=(SubTask) task1._SubTasks.elementAt(0);
		Assert.assertEquals(16,subT.getDatasize());
		Assert.assertEquals(100,subT.getCommStartTime());
		Assert.assertEquals(116,subT.getCompStartTime());
		Assert.assertEquals(423,subT._compTime);
//		Assert.assertEquals();
//		Assert.assertEquals();
		SubTask subT2=(SubTask) task1._SubTasks.elementAt(1);
		Assert.assertEquals(14,subT2.getDatasize());
		Assert.assertEquals(116,subT2.getCommStartTime());
		Assert.assertEquals(130,subT2.getCompStartTime());
		Assert.assertEquals(423,subT2._compTime);
	}

	/**
	 * Test method for {@link rtgrid.scheduler.DivHomoSingle#computeCompletionTime(int, int, rtgrid.taskmodel.Task, rtgrid.util.SysVector)}.
	 */
	public void testComputeCompletionTimeIntIntTaskSysVector() {
		dlt.computeCompletionTime(100, 7, task1, sysv);
		Assert.assertEquals(7, task1.Nmin);
		Assert.assertEquals(204, task1.getCompletionTime());
		Assert.assertEquals(100, task1.startTime);
		Assert.assertEquals(130, task1.getDispatchDoneTime());
	}

	/**
	 * Test method for {@link rtgrid.scheduler.DivHomoSingle#computeCost(int, rtgrid.taskmodel.Task, rtgrid.util.SysVector)}.
	 */
	public void testComputeCost() {
		Assert.assertEquals(104, dlt.computeCost(7, task1, sysv));
		Assert.assertEquals(0, task1.getNmin());
	}

	/**
	 * Test method for {@link rtgrid.scheduler.DivHomoSingle#computeDatasize(int, int, rtgrid.taskmodel.Task, rtgrid.util.SysVector)}.
	 */
	public void testComputeDatasize() {
		Assert.assertEquals(30, dlt.computeDatasize(170, 4, task1, sysv));
		Assert.assertEquals(30, dlt.computeDatasize(73, 11, task1, sysv));
		Assert.assertEquals(30, dlt.computeDatasize(56, 16, task1, sysv));
		Assert.assertEquals(0, task1.getNmin());
		
	}

	/**
	 * Test method for {@link rtgrid.scheduler.DivHomoSingle#computeNmin(int, rtgrid.taskmodel.Task, rtgrid.util.SysVector)}.
	 */
	public void testComputeNmin() {
		
		Assert.assertEquals(2, dlt.computeNmin(100, task1, sysv));
		Assert.assertEquals(0, task1.getNmin());

	}

	/**
	 * Test method for {@link rtgrid.scheduler.DivHomoSingle#computeDC(int, rtgrid.taskmodel.Task, rtgrid.util.SysVector)}.
	 */
	public void testComputeDC() {
		dlt.computeCompletionTime(100, task1, sysv);
		dlt.computeDC(task1, sysv);
		Assert.assertEquals(15.6095, task1.DC,  0.0001);
	}

}
