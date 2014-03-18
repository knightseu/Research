/**
 * 
 */
package rtgrid.test;

import java.util.Vector;

import rtgrid.resource.Cluster;
import rtgrid.scheduler.IScheduler;
import rtgrid.util.Simulator;
import junit.framework.TestCase;

/**
 * TestSimulator.java
 * Package:  rtgrid.test
 * @author lxuan, Jul 28, 2008 
 */
public class TestSimulator extends TestCase {
	boolean bGenLog;
	Vector NTList;
	IScheduler sch;
	Simulator sim;
	Cluster cl;

	/**
	 * Test method for {@link rtgrid.util.Simulator#setScheduler(rtgrid.scheduler.IScheduler)}.
	 */
	public void testSetScheduler() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link rtgrid.util.Simulator#genFromRealWorkload(java.lang.String)}.
	 */
	public void testGenFromRealWorkload() {
		System.out.println("Good Unit Test!");
		sim =new Simulator();
		sim.genFromRealWorkload("E:\\Users\\lxuan\\Documents\\workspace\\Data\\Bao\\execInfo_red.dat");
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link rtgrid.util.Simulator#getFromFile(java.lang.String)}.
	 */
	public void testGetFromFileString() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link rtgrid.util.Simulator#getFromFile(java.lang.String, int, int)}.
	 */
	public void testGetFromFileStringIntInt() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link rtgrid.util.Simulator#getSplitTasksFromFile(java.lang.String, boolean)}.
	 */
	public void testGetSplitTasksFromFile() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link rtgrid.util.Simulator#readPara(java.lang.String)}.
	 */
	public void testReadPara() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link rtgrid.util.Simulator#getInput(java.lang.String)}.
	 */
	public void testGetInput() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link rtgrid.util.Simulator#genUserReserve(java.util.Vector)}.
	 */
	public void testGenUserReserve() {
		//fail("Not yet implemented");
	}

}
