/**
 * 
 */
package rtgrid.test;

import rtgrid.COMMON;
import rtgrid.scheduler.DivHomoSingle_SC;
import rtgrid.scheduler.IDivisible;
import rtgrid.taskmodel.Task;
import rtgrid.util.SysVector;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * TestDivHomoSingleSC.java
 * Package:  rtgrid.test
 * @author lxuan, 2007-4-10 
 */
public class TestDivHomoSingleSC extends TestCase {
	Task task1;
    SysVector sysv;
    IDivisible dlt;
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		task1=new Task(1,100,500,30);
		task1.SC=20;
		task1.ST=20;
		sysv=new SysVector(COMMON.PARA_CLUSTERSIZE);
		sysv.setCms(1);
		sysv.setCps(20);
		dlt=new DivHomoSingle_SC();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link rtgrid.scheduler.DivHomoSingle_SC#computeCompletionTime(int, rtgrid.taskmodel.Task, rtgrid.util.SysVector)}.
	 */
	public void testComputeCompletionTimeIntTaskSysVector() {
		task1.SC=101;
		task1.ST=50;
		Assert.assertEquals(600, dlt.computeCompletionTime(100, task1, sysv));
		Assert.assertEquals(2, task1.Nmin);
		Assert.assertEquals(230,task1.dispDoneTime);
	}

	/**
	 * Test method for {@link rtgrid.scheduler.DivHomoSingle_SC#computeCompletionTime(int, int, rtgrid.taskmodel.Task, rtgrid.util.SysVector)}.
	 */
	public void testComputeCompletionTimeIntIntTaskSysVector() {
		task1.SC=101;
		task1.ST=50;
		Assert.assertEquals(600, dlt.computeCompletionTime(100,2, task1, sysv));
		Assert.assertEquals(2, task1.Nmin);
		Assert.assertEquals(230,task1.dispDoneTime);
		task1.ST=150;
		Assert.assertEquals(755, dlt.computeCompletionTime(100,4, task1, sysv));
		Assert.assertEquals(4, task1.Nmin);
		Assert.assertEquals(730,task1.dispDoneTime);
	}

	/**
	 * Test method for {@link rtgrid.scheduler.DivHomoSingle_SC#computeCost(int, rtgrid.taskmodel.Task, rtgrid.util.SysVector)}.
	 */
	public void testComputeCost() {
		task1.SC=101;
		task1.ST=50;
		Assert.assertEquals(500, dlt.computeCost(2, task1, sysv));
		task1.SC=101;
		task1.ST=150;
		Assert.assertEquals(627, dlt.computeCost(3, task1, sysv));
	}

	/**
	 * Test method for {@link rtgrid.scheduler.DivHomoSingle_SC#computeDC(rtgrid.taskmodel.Task, rtgrid.util.SysVector)}.
	 */
	public void testComputeDC() {
		task1.SC=101;
		task1.ST=50;
		Assert.assertEquals(600, dlt.computeCompletionTime(100, task1, sysv));
		Assert.assertEquals(270, (int)dlt.computeDC(task1, sysv));
		task1.SC=101;
		task1.ST=150;
		Assert.assertEquals(727, dlt.computeCompletionTime(100,3, task1, sysv));
		Assert.assertEquals(738, (int)dlt.computeDC(task1, sysv));
	}

	/**
	 * Test method for {@link rtgrid.scheduler.DivHomoSingle_SC#computeDatasize(int, int, rtgrid.taskmodel.Task, rtgrid.util.SysVector)}.
	 */
	public void testComputeDatasize() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link rtgrid.scheduler.DivHomoSingle_SC#computeNmin(int, rtgrid.taskmodel.Task, rtgrid.util.SysVector)}.
	 */
	public void testComputeNmin() {
		task1.SC=20;
		task1.ST=20;
		Assert.assertEquals(2, dlt.computeNmin(100, task1, sysv));
		task1.SC=120;
		task1.ST=50;
		Assert.assertEquals(3, dlt.computeNmin(100, task1, sysv));
		task1.SC=101;
		task1.ST=50;
		Assert.assertEquals(2, dlt.computeNmin(100, task1, sysv));
		Assert.assertEquals(0, task1.getNmin());
		task1.SC=102;
		task1.ST=50;
		Assert.assertEquals(3, dlt.computeNmin(100, task1, sysv));
		Assert.assertEquals(0, task1.getNmin());
	}

	public void testComputeNopt() {
		task1=new Task(1,0,1000000000,200);
		task1.SC=20;
		task1.ST=20;
		sysv=new SysVector(COMMON.PARA_CLUSTERSIZE);
		sysv.setCms(1);
		sysv.setCps(20);
		dlt=new DivHomoSingle_SC();
	}
}
