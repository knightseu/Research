/**
 * 
 */
package rtgrid.test;

//import static org.junit.Assert.*;
import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rtgrid.COMMON;
import rtgrid.scheduler.DivHomoSingle;
import rtgrid.scheduler.DivSplit;
import rtgrid.scheduler.IDivisible;
import rtgrid.taskmodel.Task;
import rtgrid.util.SysVector;

/**
 * TestDivSplit.java
 * Package:  rtgrid.test
 * @author Xuan, 2007-1-9 
 */
public class TestDivSplit extends TestCase {
	Task task1;
    SysVector sysv;
    IDivisible dlt;
	/**
	 * @throws java.lang.Exception
	 */
	public void setUp() throws Exception {
		task1=new Task(1,100,500,30);
		sysv=new SysVector(COMMON.PARA_CLUSTERSIZE);
		sysv.setCms(1);
		sysv.setCps(20);
		dlt=new DivSplit();
	}

	/**
	 * @throws java.lang.Exception
	 */
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link rtgrid.scheduler.DivSplit#computeCompletionTime(int, rtgrid.taskmodel.Task, rtgrid.util.SysVector)}.
	 */
	public void testComputeCompletionTimeIntTaskSysVector() {
		
		Assert.assertEquals(730, dlt.computeCompletionTime(100, task1, sysv));
		Assert.assertEquals(800, dlt.computeCompletionTime(170, task1, sysv));
	}

	/**
	 * Test method for {@link rtgrid.scheduler.DivSplit#computeCompletionTime(int, int, rtgrid.taskmodel.Task, rtgrid.util.SysVector)}.
	 */
	//@Test
	public void testComputeCompletionTimeIntIntTaskSysVector() {
		Assert.assertEquals(-1, dlt.computeCompletionTime(100,2, task1, sysv));
	}

	/**
	 * Test method for {@link rtgrid.scheduler.DivSplit#computeCost(int, rtgrid.taskmodel.Task, rtgrid.util.SysVector)}.
	 */
	//@Test
	public void testComputeCost() {
		Assert.assertEquals(630, dlt.computeCost(1, task1, sysv));
	}
	/**
	 * Test method for {@link rtgrid.scheduler.DivSplit#computeDC(rtgrid.taskmodel.Task, rtgrid.util.SysVector)}.
	 */
	//@Test
	public void testComputeDC() {
		Assert.assertEquals(-1, dlt.computeDC(task1, sysv),0.001);
	}

	/**
	 * Test method for {@link rtgrid.scheduler.DivSplit#computeDatasize(int, int, rtgrid.taskmodel.Task, rtgrid.util.SysVector)}.
	 */
	//@Test
	public void testComputeDatasize() {
		Assert.assertEquals(-1, dlt.computeDatasize(1,1, task1, sysv));
	}

	/**
	 * Test method for {@link rtgrid.scheduler.DivSplit#computeNmin(int, rtgrid.taskmodel.Task, rtgrid.util.SysVector)}.
	 */
	//@Test
	public void testComputeNmin() {
		Assert.assertEquals(1, dlt.computeNmin(1, task1, sysv));
	}

}
