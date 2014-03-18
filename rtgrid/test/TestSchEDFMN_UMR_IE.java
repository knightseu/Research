/**
 * 
 */
package rtgrid.test;


import junit.framework.TestCase;

import java.util.Vector;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rtgrid.COMMON;
import rtgrid.resource.ANI;
import rtgrid.resource.Cluster;
//import rtgrid.scheduler.SchEDFMN_UMR_IE;
import rtgrid.taskmodel.Task;

/**
 * TestSchEDFMN_UMR_IE.java
 * Package:  rtgrid.test
 * @author Xuan, 2006-12-2 
 * 
 * see figures in testcases.
 */
public class TestSchEDFMN_UMR_IE extends TestCase{
	ANI ani;
	Vector ANList;
    Task task;
    Cluster cl;
	/**
	 * @throws java.lang.Exception
	 */
	//@Before
	public void setUp() throws Exception {
		COMMON.PARA_CLUSTERSIZE=3;
		ANList=new Vector();
		ani=new ANI(100, 1);
		ANList.add(ani);
		ani=new ANI(200, 2);
		ANList.add(ani);
		ani=new ANI(300, 3);
		ANList.add(ani);
		cl=new Cluster(3,9,1);
	}

	/**
	 * @throws java.lang.Exception
	 */
	//@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link rtgrid.scheduler.SchEDFMN_UMR_IE#Schedulability_Test(rtgrid.taskmodel.Task, java.util.Vector, int)}.
	 */
	//@Test
	public void testSchedulability_Test() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link rtgrid.scheduler.SchEDFMN_UMR_IE#schedulable(rtgrid.taskmodel.Task, java.util.Vector)}.
	 */
	//@Test
	public void testSchedulable0() {
		//test not schedulable at the last test. (every failure should be in the last test, otherwise it will keep testing)
		
//		Task task=new Task(0,100,264,49);
//		task.setCmsCps(1, 9);
//		SchEDFMN_UMR_IE sch=new SchEDFMN_UMR_IE(cl);
//		Assert.assertEquals(false, sch.schedulable(task, ANList));
//		
//		Assert.assertEquals(3, ANList.size());
//		ani=ANList.elementAt(0);
//		Assert.assertEquals(1,ani.num);
//		Assert.assertEquals(100,ani.time);
//		ani=ANList.elementAt(1);
//		Assert.assertEquals(2,ani.num);
//		Assert.assertEquals(200,ani.time);
//		ani=ANList.elementAt(2);
//		Assert.assertEquals(3,ani.num);
//		Assert.assertEquals(300,ani.time);
	}
	
    /**
     * Test schedulable with last test, and using all nodes.
     *
     */
//	public void testSchedulable1() {
//		
//		Task task=new Task(0,100,265,49);
//		task.setCmsCps(1, 9);
//		SchEDFMN_UMR_IE sch=new SchEDFMN_UMR_IE(cl);
//		Assert.assertEquals(true, sch.schedulable(task, ANList));
//		
//		Assert.assertEquals(1, ANList.size());
//		ani=ANList.elementAt(0);
//		Assert.assertEquals(3,ani.num);
//		Assert.assertEquals(365,ani.time);
//	}
//	
//    /**
//     * Test schedulable with last test, and do not use all nodes.
//     *
//     */
//	public void testSchedulable2() {
//		COMMON.PARA_CLUSTERSIZE=4;
//		ani=ANList.elementAt(2);
//		ani.num=4;
//		Task task=new Task(0,100,265,49);
//		task.setCmsCps(1, 9);
//		SchEDFMN_UMR_IE sch=new SchEDFMN_UMR_IE(cl);
//		Assert.assertEquals(true, sch.schedulable(task, ANList));
//		
//		Assert.assertEquals(2, ANList.size());
//		ani=ANList.elementAt(0);
//		Assert.assertEquals(1,ani.num);
//		Assert.assertEquals(300,ani.time);
//		ani=ANList.elementAt(1);
//		Assert.assertEquals(4,ani.num);
//		Assert.assertEquals(365,ani.time);
//	}
//	
//	/**
//	 * test schedulable, in the middle test, end time greater than the last node
//	 *
//	 */
//	public void testSchedulable3() {
//		Task task=new Task(0,100,296,49);
//		task.setCmsCps(1, 9);
//		
//		SchEDFMN_UMR_IE sch=new SchEDFMN_UMR_IE(cl);
//		Assert.assertEquals(true, sch.schedulable(task, ANList));
//		
//		
//		Assert.assertEquals(2, ANList.size());
//		ANI ani2=(ANI)ANList.elementAt(0);
//		Assert.assertEquals(ani2.num, 1);
//		Assert.assertEquals(ani2.time, 300);
//		ANI ani3=(ANI)ANList.elementAt(1);
//		Assert.assertEquals(ani3.num, 3);
//		Assert.assertEquals(ani3.time, 377);
//	}
//	
//	/**
//	 * test schedulable, in the middle test, end time smaller than the last node
//	 *
//	 */
//	public void testSchedulable4() {
//		
//		Task task=new Task(0,100,296,49);
//		task.setCmsCps(1, 9);
//		ani=ANList.elementAt(2);
//		ani.time=400;
//		
//		SchEDFMN_UMR_IE sch=new SchEDFMN_UMR_IE(cl);
//		Assert.assertEquals(true, sch.schedulable(task, ANList));
//		
//		
//		Assert.assertEquals(2, ANList.size());
//		ANI ani2=(ANI)ANList.elementAt(0);
//		Assert.assertEquals(ani2.num, 2);
//		Assert.assertEquals(ani2.time, 377);
//		ANI ani3=(ANI)ANList.elementAt(1);
//		Assert.assertEquals(ani3.num, 3);
//		Assert.assertEquals(ani3.time, 400);
//	}
//	
//	/**
//	 * test schedulable, in the middle test, end time equal to the last node
//	 *
//	 */
//	public void testSchedulable5() {
//		
//		Task task=new Task(0,100,296,49);
//		task.setCmsCps(1, 9);
//		ani=ANList.elementAt(2);
//		ani.time=377;
//		
//		SchEDFMN_UMR_IE sch=new SchEDFMN_UMR_IE(cl);
//		Assert.assertEquals(true, sch.schedulable(task, ANList));
//		
//		
//		Assert.assertEquals(1, ANList.size());
//		ANI ani2=(ANI)ANList.elementAt(0);
//		Assert.assertEquals(ani2.num, 3);
//		Assert.assertEquals(ani2.time, 377);;
//	}
//	
//	/**
//	 * test schedulable, in the middle test, end time equal to the last node
//	 *
//	 */
//	public void testSchedulable6() {
//		
//		Task task=new Task(0,100,442,49);
//		task.setCmsCps(1, 9);
//		ani=ANList.elementAt(2);
//		ani.time=542;
//		
//		SchEDFMN_UMR_IE sch=new SchEDFMN_UMR_IE(cl);
//		Assert.assertEquals(true, sch.schedulable(task, ANList));
//		
//		
//		Assert.assertEquals(2, ANList.size());
//		ANI ani2=(ANI)ANList.elementAt(0);
//		Assert.assertEquals(1,ani2.num);
//		Assert.assertEquals(200,ani2.time);
//		ANI ani3=(ANI)ANList.elementAt(1);
//		Assert.assertEquals(3,ani3.num);
//		Assert.assertEquals(542,ani3.time);
//	}
//	
//	/**
//	 * test schedulable, in the middle test, end time equal to the last node
//	 *
//	 */
//	public void testSchedulable7() {
//		COMMON.PARA_CLUSTERSIZE=4;
//		Task task=new Task(0,100,377,49);
//		task.setCmsCps(1, 9);
//		ani=new ANI(377,4);
//		ANList.add(ani);
//		
//		
//		SchEDFMN_UMR_IE sch=new SchEDFMN_UMR_IE(cl);
//		Assert.assertEquals(true, sch.schedulable(task, ANList));
//		
//		
//		Assert.assertEquals(2, ANList.size());
//		ANI ani2=(ANI)ANList.elementAt(0);
//		Assert.assertEquals(1,ani2.num);
//		Assert.assertEquals(300,ani2.time);
//		ANI ani3=(ANI)ANList.elementAt(1);
//		Assert.assertEquals(4,ani3.num);
//		Assert.assertEquals(377,ani3.time);
//	}
//	
//	/**
//	 * test schedulable, in the middle test, end time equal to the middle
//	 *
//	 */
//	public void testSchedulable8() {
//		COMMON.PARA_CLUSTERSIZE=4;
//		Task task=new Task(0,100,377,49);
//		task.setCmsCps(1, 9);
//		ani=new ANI(400,4);
//		ANList.add(ani);
//		ani=ANList.elementAt(2);
//		ani.time=377;
//		
//		
//		SchEDFMN_UMR_IE sch=new SchEDFMN_UMR_IE(cl);
//		Assert.assertEquals(true, sch.schedulable(task, ANList));
//		
//		
//		Assert.assertEquals(2, ANList.size());
//		ANI ani2=(ANI)ANList.elementAt(0);
//		Assert.assertEquals(3,ani2.num);
//		Assert.assertEquals(377,ani2.time);
//		ANI ani3=(ANI)ANList.elementAt(1);
//		Assert.assertEquals(4,ani3.num);
//		Assert.assertEquals(400,ani3.time);
//	}
//	
//	/**
//	 * test schedulable, in the first test, end time equal to the second
//	 *
//	 */
//	public void testSchedulable9() {
//		Task task=new Task(0,100,442,49);
//		task.setCmsCps(1, 9);
//		ani=ANList.elementAt(1);
//		ani.time=542;
//		ani=ANList.elementAt(2);
//		ani.time=600;
//		
//		SchEDFMN_UMR_IE sch=new SchEDFMN_UMR_IE(cl);
//		Assert.assertEquals(true, sch.schedulable(task, ANList));
//		
//		
//		Assert.assertEquals(2, ANList.size());
//		ANI ani2=(ANI)ANList.elementAt(0);
//		Assert.assertEquals(2,ani2.num);
//		Assert.assertEquals(542,ani2.time);
//		ANI ani3=(ANI)ANList.elementAt(1);
//		Assert.assertEquals(3,ani3.num);
//		Assert.assertEquals(600,ani3.time);
//	}
//	
//	/**
//	 * test schedulable, in the first test, end time less than the second
//	 *
//	 */
//	public void testSchedulable10() {
//		Task task=new Task(0,100,442,49);
//		task.setCmsCps(1, 9);
//		ani=ANList.elementAt(1);
//		ani.time=550;
//		ani=ANList.elementAt(2);
//		ani.time=600;
//		
//		SchEDFMN_UMR_IE sch=new SchEDFMN_UMR_IE(cl);
//		Assert.assertEquals(true, sch.schedulable(task, ANList));
//		
//		
//		Assert.assertEquals(3, ANList.size());
//		ANI ani2=(ANI)ANList.elementAt(0);
//		Assert.assertEquals(1,ani2.num);
//		Assert.assertEquals(542,ani2.time);
//		ANI ani3=(ANI)ANList.elementAt(1);
//		Assert.assertEquals(2,ani3.num);
//		Assert.assertEquals(550,ani3.time);
//		ani=(ANI)ANList.elementAt(2);
//		Assert.assertEquals(3,ani.num);
//		Assert.assertEquals(600,ani.time);
//	}
//    
//	/**
//	 * test schedulable, in the first test, end time greater than the second but less than the last
//	 *
//	 */
//	public void testSchedulable11() {
//		Task task=new Task(0,100,442,49);
//		task.setCmsCps(1, 9);
//		ani=ANList.elementAt(1);
//		ani.time=500;
//		ani=ANList.elementAt(2);
//		ani.time=600;
//		
//		SchEDFMN_UMR_IE sch=new SchEDFMN_UMR_IE(cl);
//		Assert.assertEquals(true, sch.schedulable(task, ANList));
//		
//		
//		Assert.assertEquals(3, ANList.size());
//		ANI ani2=(ANI)ANList.elementAt(0);
//		Assert.assertEquals(1,ani2.num);
//		Assert.assertEquals(500,ani2.time);
//		ANI ani3=(ANI)ANList.elementAt(1);
//		Assert.assertEquals(2,ani3.num);
//		Assert.assertEquals(542,ani3.time);
//		ani=(ANI)ANList.elementAt(2);
//		Assert.assertEquals(3,ani.num);
//		Assert.assertEquals(600,ani.time);
//	}
//	
//	/**
//	 * test schedulable, in the first test, end time greater than the second and greater than the last
//	 *
//	 */
//	public void testSchedulable12() {
//		Task task=new Task(0,100,442,49);
//		task.setCmsCps(1, 9);
//		
//		SchEDFMN_UMR_IE sch=new SchEDFMN_UMR_IE(cl);
//		Assert.assertEquals(true, sch.schedulable(task, ANList));
//		
//		
//		Assert.assertEquals(3, ANList.size());
//		ANI ani2=(ANI)ANList.elementAt(0);
//		Assert.assertEquals(1,ani2.num);
//		Assert.assertEquals(200,ani2.time);
//		ANI ani3=(ANI)ANList.elementAt(1);
//		Assert.assertEquals(2,ani3.num);
//		Assert.assertEquals(300,ani3.time);
//		ani=(ANI)ANList.elementAt(2);
//		Assert.assertEquals(3,ani.num);
//		Assert.assertEquals(542,ani.time);
//	}
//	
//	/**
//	 * test schedulable, in the first test, end time equal to the middle
//	 *
//	 */
//	public void testSchedulable13() {
//		COMMON.PARA_CLUSTERSIZE=4;
//		Task task=new Task(0,100,442,49);
//		task.setCmsCps(1, 9);
//		ani=ANList.elementAt(2);
//		ani.time=542;
//		ani = new ANI(600,4);
//        ANList.add(ani);
//		
//		SchEDFMN_UMR_IE sch=new SchEDFMN_UMR_IE(cl);
//		Assert.assertEquals(true, sch.schedulable(task, ANList));
//		
//		
//		Assert.assertEquals(3, ANList.size());
//		ANI ani2=(ANI)ANList.elementAt(0);
//		Assert.assertEquals(1,ani2.num);
//		Assert.assertEquals(200,ani2.time);
//		ANI ani3=(ANI)ANList.elementAt(1);
//		Assert.assertEquals(3,ani3.num);
//		Assert.assertEquals(542,ani3.time);
//		ani3=(ANI)ANList.elementAt(2);
//		Assert.assertEquals(4,ani3.num);
//		Assert.assertEquals(600,ani3.time);
//	}
//	
//	/**
//	 * test schedulable, in the first test, end time equal to the middle
//	 *
//	 */
//	public void testSchedulable14() {
//		COMMON.PARA_CLUSTERSIZE=4;
//		Task task=new Task(0,100,280,49);
//		task.setCmsCps(1, 9);
//		ani=ANList.elementAt(2);
//		ani.time=400;
//		ani = new ANI(600,4);
//        ANList.add(ani);
//		
//		SchEDFMN_UMR_IE sch=new SchEDFMN_UMR_IE(cl);
//		Assert.assertEquals(true, sch.schedulable(task, ANList));
//		
//		
//		Assert.assertEquals(3, ANList.size());
//		ANI ani2=(ANI)ANList.elementAt(0);
//		Assert.assertEquals(2,ani2.num);
//		Assert.assertEquals(377,ani2.time);
//		ANI ani3=(ANI)ANList.elementAt(1);
//		Assert.assertEquals(3,ani3.num);
//		Assert.assertEquals(400,ani3.time);
//		ani3=(ANI)ANList.elementAt(2);
//		Assert.assertEquals(4,ani3.num);
//		Assert.assertEquals(600,ani3.time);
//	}
//	
//	
//	/**
//	 * test schedulable, in the first test, end time equal to the middle
//	 *
//	 */
//	public void testSchedulable15() {
////		COMMON.PARA_CLUSTERSIZE=4;
////		Task task=new Task(0,100,280,49);
////		task.setCmsCps(1, 9);
////		ani = new ANI(600,4);
////        ANList.add(ani);
////		
////		SchEDFMN_UMR_IE sch=new SchEDFMN_UMR_IE(cl);
////		Assert.assertEquals(true, sch.schedulable(task, ANList));
////		
////		
////		Assert.assertEquals(3, ANList.size());
////		ANI ani2=(ANI)ANList.elementAt(0);
////		Assert.assertEquals(1,ani2.num);
////		Assert.assertEquals(300,ani2.time);
////		ANI ani3=(ANI)ANList.elementAt(1);
////		Assert.assertEquals(3,ani3.num);
////		Assert.assertEquals(377,ani3.time);
////		ani3=(ANI)ANList.elementAt(2);
////		Assert.assertEquals(4,ani3.num);
////		Assert.assertEquals(600,ani3.time);
//	}
//	
//	/**
//	 * test schedulable, in the first test, end time equal to the middle
//	 *
//	 */
//	public void testSchedulable16() {
////		COMMON.PARA_CLUSTERSIZE=5;
////		Task task=new Task(0,100,280,49);
////		task.setCmsCps(1, 9);
////		ani = new ANI(377,4);
////        ANList.add(ani);
////		ani = new ANI(600,5);
////        ANList.add(ani);
////		
////		SchEDFMN_UMR_IE sch=new SchEDFMN_UMR_IE(cl);
////		Assert.assertEquals(true, sch.schedulable(task, ANList));
////		
////		
////		Assert.assertEquals(3, ANList.size());
////		ANI ani2=(ANI)ANList.elementAt(0);
////		Assert.assertEquals(1,ani2.num);
////		Assert.assertEquals(300,ani2.time);
////		ANI ani3=(ANI)ANList.elementAt(1);
////		Assert.assertEquals(4,ani3.num);
////		Assert.assertEquals(377,ani3.time);
////		ani3=(ANI)ANList.elementAt(2);
////		Assert.assertEquals(5,ani3.num);
////		Assert.assertEquals(600,ani3.time);
//	}
//	

}
